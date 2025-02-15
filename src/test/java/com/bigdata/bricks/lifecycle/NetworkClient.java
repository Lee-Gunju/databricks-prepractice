package com.bigdata.bricks.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }
    private void connect() {
        System.out.println("connect: "  + url);
    }

    public void call(String msg) {
        System.out.println("call: " + url + " message = " + msg);
    }

    public void disconnect() {
        System.out.println("disconnect: "  + url);
    }
//    @PostConstruct
    @PostConstruct
    public void init() {
        System.out.println("==Open NetworkClient Start==");
        connect();
        call("초기화 연결 메세지");
        System.out.println("==Open NetworkClient End==");
    }
//    @PreDestroy
    @PreDestroy
    public void close() {
        System.out.println("==Close NetworkClient Start==");
        disconnect();
        System.out.println("==Close NetworkClient End==");
    }
}
