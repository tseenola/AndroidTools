package com.tssenola.debugtool.logserver;

import org.java_websocket.WebSocket;

public interface ServiceWebSocketListener {
    /**当客户端连接打开时调用
     * @param conn
     * @param address
     */
    void onClientOpen(WebSocket conn, String address);
    // 当客户端连接关闭时调用

    /** 当客户端连接关闭时调用
     * @param conn
     * @param code
     * @param reason
     * @param remote
     */
    void onClientClose(WebSocket conn, int code, String reason, boolean remote);

    /**当接收到客户端消息时调用
     * @param conn
     * @param message
     */
    void onClientMessage(WebSocket conn, String message);

    /**
     *  当客户端连接发生错误时调用
     * @param conn
     * @param ex
     */
    void onClientError(WebSocket conn, Exception ex);

    /**
     *当WebSocket服务器启动时调用
     */
    void onServerStart();
}
