package com.lym2024.newtptest

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "8ae260011109446d221ccd1d131690e7")
    }
}