package com.example.myapplication3.interfaces

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(view: View, `object`: T)
}