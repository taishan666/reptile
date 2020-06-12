package com.tarzan.reptile.utils;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by tarzan liu on 2020/5/5.
 */
public class MyX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

	}

	public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
