package com.example.myapplication3.interfaces;

import com.example.myapplication3.models.MyExtraData;

public interface IDialog {
    public void onDialogClick(boolean isOk, int tag, MyExtraData extraData);
}
