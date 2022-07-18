package com.share.auth.center.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 创建SSL安全连接
 *
 * @author liuhao
 * @version 2020-03-23
 */
@Slf4j
public class SslClient {
    private SslClient(){}
    public static SSLConnectionSocketFactory createSslConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
                @Override
                public boolean verify(String requestedHost, SSLSession remoteServerSession) {
                  return requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost());
                }
            });
        } catch (GeneralSecurityException e) {
            log.error("e：{}", e);
        }
        return sslsf;
    }
}  