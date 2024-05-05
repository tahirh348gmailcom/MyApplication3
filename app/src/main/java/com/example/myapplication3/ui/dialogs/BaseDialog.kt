package com.example.myapplication3.base

import androidx.fragment.app.DialogFragment
import com.example.myapplication3.interfaces.IDialog
import com.example.myapplication3.models.MyExtraData

open class BaseDialog : DialogFragment(), IDialog
{
    override fun onDialogClick(isOk: Boolean, tag: Int, extraData: MyExtraData?) {

    }

}