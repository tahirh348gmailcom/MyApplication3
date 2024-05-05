package com.example.myapplication3.interfaces

import android.view.View

interface OnItemClickListenerWithTag<T> {
    fun onItemClick(view: View, `object`: T, tag: Int)
}