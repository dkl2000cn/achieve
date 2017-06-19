package com.easygoal.achieve;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 项目名称：achieve
 * 类描述：
 * 创建人：Acer
 * 创建时间：2017/6/2 0:21
 * 修改人：Acer
 * 修改时间：2017/6/2 0:21
 * 修改备注：
 */

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements  X509TrustManager{

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {


    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {


    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {

        return null;
    }

}
