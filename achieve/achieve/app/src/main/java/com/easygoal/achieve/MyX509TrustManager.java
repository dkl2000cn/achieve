package com.easygoal.achieve;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * ��Ŀ���ƣ�achieve
 * ��������
 * �����ˣ�Acer
 * ����ʱ�䣺2017/6/2 0:21
 * �޸��ˣ�Acer
 * �޸�ʱ�䣺2017/6/2 0:21
 * �޸ı�ע��
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
