package com.tssenola.debugtool.server;

import android.util.Log;

import com.tssenola.debugtool.logserver.AndroidWebSocketServer;
import com.tssenola.debugtool.logserver.ServiceWebSocketListener;

import org.java_websocket.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LogServer {
    private AndroidWebSocketServer server;
//    private WebSocket mConn;
    private ArrayList<WebSocket> mConList = new ArrayList<>();
    private static LogServer mLogServer = null;
    private String lastErrorStr ;
    public static LogServer getIns(){
        if (mLogServer == null) {
            mLogServer = new LogServer();
        }
        return mLogServer;
    }

    private LogServer() {
    }

    public void init(){
        server = new AndroidWebSocketServer(8085, new ServiceWebSocketListener() {
            @Override
            public void onClientOpen(final WebSocket conn, String address) {
                Log.d("vbvb", "LogServer onClientOpen addr :" + address);
                mConList.add(conn);
                lastErrorStr = "";
            }

            @Override
            public void onClientClose(WebSocket conn, int code, String reason, boolean remote) {
                lastErrorStr = "onClientClose " + reason;
                boolean ret = mConList.remove(conn);
                Log.d("vbvb", "LogServer onClientClose: " + ret);
            }

            @Override
            public void onClientMessage(WebSocket conn, final String message) {
                Log.d("vbvb", "LogServer onClientMessage: " + message);
            }

            @Override
            public void onClientError(WebSocket conn, final Exception ex) {
                lastErrorStr = "onClientError "+ ex.getMessage();
                Log.d("vbvb", "LogServer onClientError: " + ex.getMessage());
            }

            @Override
            public void onServerStart() {
                Log.d("vbvb", "LogServer onServerStart: "  );
            }
        });
        server.start();
    }

    public void sendMsg(String data){
        if (mConList.isEmpty()) {
            Log.e("vbvb", "mConn null " + lastErrorStr  );
        }else {
            for (WebSocket webSocket : mConList) {
                webSocket.send(data);
            }
        }
    }

    public void disconnect(){
        try {
            Log.d("vbvb", "LogServer disconnect: ");
            if (!mConList.isEmpty()) {
                for (WebSocket webSocket : mConList) {
                    webSocket.close();
                }
            }
            server.stop();
        } catch (IOException pE) {
            pE.printStackTrace();
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }
    }
}
