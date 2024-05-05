package com.example.myapplication3.utils

import android.util.Log
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

 class CustomInterceptor(private val logger:Logger): Interceptor {
 lateinit var token:String
 var headers: Headers? = null
 private var level = Level.NONE


 interface Logger {
 fun log(message:String)

companion object {

 val DEFAULT:Logger = object:Logger {
override fun log(message:String) {
 //Platform.get().logW(message);
                Utils.log("--response--", message)
            }
}
}
}


 constructor(token:String) : this(Logger.DEFAULT) {
setLevel(Level.BODY)
this.token = token
}


 fun setLevel(level:Level) {
this.level = level
}


override fun intercept(chain: Interceptor.Chain): Response {

val level = this.level
val request = chain.request()

if (token != null && !token!!.isEmpty())
{
Log.d("Builder", "login " + token!!)
request.newBuilder()
.addHeader("content-type", "application/json")

}
else
{
Log.d("Builder", "not login ")
request.newBuilder().addHeader("os", "android")
.addHeader("content-type", "application/json")


}

val logBody = level == Level.BODY
val logHeaders = logBody || level == Level.HEADERS

val requestBody = request.body()
val hasRequestBody = requestBody != null

val connection = chain.connection()
val protocol = if (connection != null) connection!!.protocol() else Protocol.HTTP_1_1


var requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol
if (!logHeaders && hasRequestBody)
{
requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
}
logger.log(requestStartMessage)


if (logHeaders)
{
if (hasRequestBody)
{
 // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody!!.contentType() != null)
{
logger.log("Content-Type: " + requestBody!!.contentType())
}
if (requestBody!!.contentLength() != -1L)
{
logger.log("Content-Length: " + requestBody!!.contentLength())
}
}

val headers = request.headers()
var i = 0
val count = headers.size()
while (i < count)
{
val name = headers.name(i)
 // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true))
{
logger.log(name + ": " + headers.value(i))
}
i++
}

if (!logBody || !hasRequestBody)
{
logger.log("--> END " + request.method())
}
else if (bodyEncoded(request.headers()))
{
logger.log("--> END " + request.method() + " (encoded body omitted)")
}
else if (requestBody!!.contentLength() < 1 * 1000 * 1024)
{
val buffer = Buffer()

requestBody!!.writeTo(buffer)

var charset = UTF8
val contentType = requestBody!!.contentType()
if (contentType != null)
{
charset = contentType!!.charset(UTF8)
}

logger.log("")
logger.log(buffer.readString(charset))

logger.log("--> END " + request.method()
+ " (" + requestBody!!.contentLength() + "-byte body)")
}
}

val startNs = System.nanoTime()
val response = chain.proceed(request)
val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

val responseBody = response.body()
val contentLength = responseBody!!.contentLength()
val bodySize = if (contentLength != -1L) (contentLength).toString() + "-byte" else "unknown-length"
logger.log(("<-- " + response.code() + ' ' + response.message() + ' '
+ response.request().url() + " (" + tookMs + "ms" + (if (!logHeaders)
    ", "
    + bodySize + " body"
else
"") + ')'))

if (logHeaders)
{
val headers = response.headers()
var i = 0
val count = headers.size()
while (i < count)
{
logger.log(headers.name(i) + ": " + headers.value(i))
i++
}

 //  !HttpEngine.hasBody(response)
            if (!logBody)
{
logger.log("<-- END HTTP")
}
else if (bodyEncoded(response.headers()))
{
logger.log("<-- END HTTP (encoded body omitted)")
}
else
{
val source = responseBody.source()
source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
val buffer = source.buffer()

var charset = UTF8
val contentType = responseBody.contentType()
if (contentType != null)
{
try
{
charset = contentType!!.charset(UTF8)
}
catch (e: UnsupportedCharsetException) {
logger.log("")
logger.log("Couldn't decode the response body; charset is likely malformed.")
logger.log("<-- END HTTP")

return response
}

}

if (contentLength != 0L)
{
logger.log("")
logger.log(buffer.clone().readString(charset))
}

logger.log("<-- END HTTP (" + buffer.size() + "-byte body)")
}
}

return response
}

private fun bodyEncoded(headers: Headers):Boolean {
val contentEncoding = headers.get("Content-Encoding")
return contentEncoding != null && !contentEncoding!!.equals("identity", ignoreCase = true)
}


 enum class Level {
NONE,
BASIC,
HEADERS,
BODY
}

companion object {

private val UTF8 = Charset.forName("UTF-8")

private fun protocol(protocol: Protocol):String {
return if (protocol == Protocol.HTTP_1_0) "HTTP/1.0" else "HTTP/1.1"
}

private fun requestPath(url: HttpUrl):String {
val path = url.encodedPath()
val query = url.encodedQuery()
return if (query != null) (path + '?' + query) else path
}
}


}
