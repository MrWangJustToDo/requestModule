package com.java.request.http;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

public class SocketFactoryRegistry {
    @Resource(name = "mySSLContext")
    private SSLContext sslContext;

    public Registry<ConnectionSocketFactory> getSocketFactoryRegistry() {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();
    }
}
