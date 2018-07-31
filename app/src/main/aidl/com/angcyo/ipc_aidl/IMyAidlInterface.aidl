// IMyAidlInterface.aidl
package com.angcyo.ipc_aidl;

//自定有的类型, 需要手动导入, 否则编译不过;
import com.angcyo.ipc_aidl.MsgBean;
import com.angcyo.ipc_aidl.ICallback;

// Declare any non-default types here with import statements

interface IMyAidlInterface {

    int sendMsg(in MsgBean msg);

    MsgBean getMsg(int flag);

    void addCallback(ICallback callback);
}
