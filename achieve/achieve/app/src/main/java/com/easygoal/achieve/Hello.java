package com.easygoal.achieve;

/**
 * Created by Acer on 2017/5/19.
 */

public class Hello {

    static{
        System.loadLibrary("JniTest");
    }

    public static native String sayHello();
}
