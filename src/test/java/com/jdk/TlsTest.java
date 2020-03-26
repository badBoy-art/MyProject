package com.jdk;

import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author xuedui.zhao
 * @create 2019-07-06
 */
public class TlsTest {

    static {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
    }

    @Test
    public void testTsl() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);

            SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket();

            String[] protocols = socket.getSupportedProtocols();

            System.out.println("Supported Protocols: " + protocols.length);
            for (int i = 0; i < protocols.length; i++) {
                System.out.println(" " + protocols[i]);
            }

            protocols = socket.getEnabledProtocols();

            System.out.println("Enabled Protocols: " + protocols.length);
            for (int i = 0; i < protocols.length; i++) {
                System.out.println(" " + protocols[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJdkVersion() {
        System.out.println(System.getProperty("java.version"));
    }

    @Test
    public void testHeadless() {
        System.out.println(System.getProperty("java.awt.headless"));
    }

}
