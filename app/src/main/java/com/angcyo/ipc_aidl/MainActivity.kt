package com.angcyo.ipc_aidl

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {


    var myAidlInterface: IMyAidlInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        L.e("进程名: onCreate -> ${Util.getProcessName(this)}")

        val remoteServiceIntent = Intent(this, RemoteService::class.java)
        val remoteCon = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                L.e("call: onServiceDisconnected -> $p0")

                showText("onServiceDisconnected_${p0}")

                myAidlInterface = null
            }

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                L.e("call: onServiceConnected -> $p0 $p1")
                showText("onServiceConnected_${p0}")

                myAidlInterface = IMyAidlInterface.Stub.asInterface(p1)
                sendText(1, "客户端连接成功...")
            }
        }

        findViewById<View>(R.id.start).setOnClickListener {
            startService(remoteServiceIntent)
        }

        findViewById<View>(R.id.stop).setOnClickListener {
            stopService(remoteServiceIntent)
        }

        findViewById<View>(R.id.bind).setOnClickListener {
            bindService(remoteServiceIntent, remoteCon, Service.BIND_AUTO_CREATE)
        }

        findViewById<View>(R.id.unbind).setOnClickListener {
            myAidlInterface?.let {
                unbindService(remoteCon)
                myAidlInterface = null
            }
        }

        findViewById<View>(R.id.send).setOnClickListener {
            sendText(2, "客户端发送的测试消息:_${df.format(Date(System.currentTimeMillis()))}")
        }

        findViewById<View>(R.id.sleep).setOnClickListener {
            sendText(3, "客户端发送的死循环:_${df.format(Date(System.currentTimeMillis()))}")
        }
    }

    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
    private fun showText(str: String) {
        findViewById<TextView>(R.id.text_view).apply {
            text = "${df.format(Date(System.currentTimeMillis()))}\n${str}\n\n$text"
        }
    }

    private fun sendText(what: Int, text: String) {
        myAidlInterface?.let {
            it.addCallback(object : ICallback.Stub() {
                override fun callback(str: String?, msg: MsgBean?) {
                    showText("callback 回调:$str $msg")
                }
            })

            val sendMsg = it.sendMsg(MsgBean("${what}_${text}"))
            L.e("call: sendText -> 返回值:$sendMsg  ${it.getMsg(404)}")
        }
    }
}
