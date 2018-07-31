package com.angcyo.ipc_aidl

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/07/30 13:36
 * 修改人员：Robi
 * 修改时间：2018/07/30 13:36
 * 修改备注：
 * Version: 1.0.0
 */
class RemoteService : Service() {

    /*用来和客户端通信的回调*/
    var callback: ICallback? = null

    private fun sendText(what: Int, text: String) {
        callback?.callback("服务端回调:", MsgBean(text))
    }

    val myAidlInterface = object : IMyAidlInterface.Stub() {
        override fun addCallback(callback: ICallback?) {
            this@RemoteService.callback = callback
        }

        override fun sendMsg(msg: MsgBean?): Int {
            sendText(200, msg?.message ?: "empty")
            return 200
        }

        override fun getMsg(flag: Int): MsgBean {
            return MsgBean("getMsg 测试_flag:$flag")
        }
    }


    override fun onBind(p0: Intent?): IBinder {
        L.e("call: onBind -> $p0")
        return myAidlInterface
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        L.e("call: onConfigurationChanged -> ")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        L.e("call: onRebind -> ")
    }

    /*每启动1次, startId自增*/
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        L.e("call: onStartCommand -> $intent $flags $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        L.e("call: onCreate -> ")

        L.e("进程名: onCreate -> ${Util.getProcessName(this)}")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        L.e("call: onLowMemory -> ")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        L.e("call: onStart -> ")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        L.e("call: onTaskRemoved -> ")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        L.e("call: onTrimMemory -> ")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        L.e("call: onUnbind -> ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        L.e("call: onDestroy -> ")
    }
}