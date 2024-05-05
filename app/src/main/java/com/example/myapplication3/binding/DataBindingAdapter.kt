package com.example.myapplication3.utils

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*


class DataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("text_en", "text_ar")
        fun text(view: TextView, textEnglish: String?, textArabic: String?) {

            view.setText(Utils.getLocalisedText(textEnglish, textArabic))
        }

        @JvmStatic
        @BindingAdapter("capitalise")
        fun capitalise(view: TextView, value: String?) {
            if(StringUtility.validateString(value)) {
                view.text = value!!.toLowerCase(Locale.getDefault()).capitalize()
            }else
            {
                view.text = ""
            }
        }

        @JvmStatic
        @BindingAdapter("bind:url", "bind:placeholder")
        fun url(view: ImageView, url: String?, drawable: Drawable) {
            if (url != null && StringUtility.validateString(url)) {
                Utils.log("Url", url)
                Glide
                    .with(view.context)
                    .load(url)
                    .placeholder(drawable)
                    .into(view)
            } else {
                view.setImageDrawable(drawable)
            }
/*            Glide
                .with(view.context)
                .load(Constants.TEMP_IMAGE)
                .placeholder(drawable)
                .into(view)*/
        }

        @JvmStatic
        @BindingAdapter("bind:urlTemp", "bind:placeholder")
        fun urlTemp(view: ImageView, url: String?, drawable: Drawable) {
            if (url != null && StringUtility.validateString(url)) {
                Utils.log("Url", url)
                Glide
                    .with(view.context)
                    .load(url)
                    .placeholder(drawable)
                    .into(view)
            } else {
                view.setImageDrawable(drawable)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:layoutParamsForVideoCall")
        fun layoutParamsForVideoCall(view: RelativeLayout, value: String?) {
            var width = Utils.req_width(50, view.context)
            var heigh = Utils.req_height(50, view.context)
            val param = RelativeLayout.LayoutParams(width, heigh)
            view.setLayoutParams(param)
        }


        /*To set gravity and other thing of view as per language*/
        @JvmStatic
        @BindingAdapter("bind:localizeViewAsPerLanguage")
        fun localize(view: EditText, emptyText: String) {
            view.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            view.textDirection = View.TEXT_DIRECTION_LOCALE
            if(Utils.isArabic())
            {
                view.gravity = Gravity.END
            }else
            {
                view.gravity = Gravity.START
            }

        }
    }
}