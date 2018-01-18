package com.cangwang.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by air on 2017/2/19.
 */

class BodyService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
