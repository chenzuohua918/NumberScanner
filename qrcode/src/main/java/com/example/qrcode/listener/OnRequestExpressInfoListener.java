package com.example.qrcode.listener;

import com.example.qrcode.bean.ExpressDynamic;

import java.util.List;

public interface OnRequestExpressInfoListener {
    void onResponse(List<ExpressDynamic> list);

    void onErrorResponse();
}
