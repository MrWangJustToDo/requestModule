package com.java.request.http;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;

@Component
public class IgnoreVerifySSL {

    @Resource(name = "sslContext")
    private SSLContext sslContext;
    @Resource(name = "trustManager")
    private X509TrustManager trustManager;

    @Bean
    public SSLContext mySSLContext() throws KeyManagementException {
        sslContext.init(null, new TrustManager[] { trustManager }, null);
        return sslContext;
    }
}
