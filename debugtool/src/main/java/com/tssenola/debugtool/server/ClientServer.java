package com.tssenola.debugtool.server;
import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
public class ClientServer implements Runnable {
    private static final String TAG = "ClientServer";
    private final int mPort;
    private boolean mIsRunning;
    private ServerSocket mServerSocket;
    private final RequestHandler mRequestHandler;
    public ClientServer(Context context, int port) {
        Log.d("vbvb", "ClientServer ClientServer: ");
        mRequestHandler = new RequestHandler(context);
        mPort = port;
    }

    public void start() {
        Log.d("vbvb", "ClientServer start: ");
        mIsRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        Log.d("vbvb", "ClientServer stop: ");
        try {
            mIsRunning = false;
            if (null != mServerSocket) {
                mServerSocket.close();
                mServerSocket = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error closing the server socket.", e);
        }
    }

    @Override
    public void run() {
        Log.d("vbvb", "ClientServer run mPort : " + mPort);
        try {
            mServerSocket = new ServerSocket(mPort);
            while (mIsRunning) {
                Socket socket = mServerSocket.accept();
                Log.d("vbvb", "ClientServer run: 2 accept");
                mRequestHandler.handle2(socket);
                socket.close();
                Log.d("vbvb", "ClientServer run: 2 close");
            }
        } catch (SocketException e) {
            // The server was stopped; ignore.
        } catch (IOException e) {
            Log.e(TAG, "Web server error.", e);
        } catch (Exception ignore) {
        }
    }
}
