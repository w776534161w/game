package com.yx.firstGame.config;

/**
 * 读取yml配置文件中的信息
 */
public class NettyConfig {

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NettyConfig(int port){
        this.port=port;
    }
}
