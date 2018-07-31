// ICallback.aidl
package com.angcyo.ipc_aidl;

// Declare any non-default types here with import statements

import com.angcyo.ipc_aidl.MsgBean;

interface ICallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void callback(String str,in MsgBean msg);
}
