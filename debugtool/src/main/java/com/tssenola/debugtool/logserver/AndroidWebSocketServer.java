package com.tssenola.debugtool.logserver;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class AndroidWebSocketServer extends WebSocketServer {
    // 事件监听器，用于将事件通知给ServiceActivity
    private ServiceWebSocketListener serviceWebSocketListener;
    /**
     * 构造函数。
     * @param port 服务器侦听的端口号。
     * @param serviceWebSocketListener 事件发生时的回调监听器。
     */
    public AndroidWebSocketServer(int port, ServiceWebSocketListener serviceWebSocketListener) {
        super(new InetSocketAddress(port));
        this.serviceWebSocketListener = serviceWebSocketListener;
    }

    public AndroidWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // 客户端连接打开时调用监听器的onClientOpen方法
        String address = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        serviceWebSocketListener.onClientOpen(conn, address);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // 客户端连接关闭时调用监听器的onClientClose方法
        String address = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        serviceWebSocketListener.onClientClose(conn, code, reason, remote);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // 接收到客户端消息时调用监听器的onClientMessage方法
        serviceWebSocketListener.onClientMessage(conn, message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // 连接发生错误时调用监听器的onClientError方法
        serviceWebSocketListener.onClientError(conn, ex);
    }

    @Override
    public void onStart() {
        // WebSocket服务器启动时调用监听器的onServerStart方法
        serviceWebSocketListener.onServerStart();
    }

    // 发送消息到所有连接的WebSocket客户端
    public void broadcastMessage(String message) {
        broadcast(message);
    }

    // 发送消息到特定的WebSocket客户端
    public void sendMessageToClient(WebSocket conn, String message) {
        if (conn != null) {
            conn.send(message);
        }
    }
}