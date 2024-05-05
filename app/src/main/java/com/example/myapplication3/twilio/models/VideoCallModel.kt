package com.example.myapplication3.twilio.models

import com.example.myapplication3.models.BaseModel
import com.twilio.video.VideoTrack

class VideoCallModel : BaseModel()
{
    var videoTrack: VideoTrack? = null
    var id: String = ""
    var name: String = ""
    var userId = ""

    override fun equals(obj: Any?): Boolean {
        return (obj as? VideoCallModel)?.userId?.equals(userId) ?: super.equals(obj)
    }
}