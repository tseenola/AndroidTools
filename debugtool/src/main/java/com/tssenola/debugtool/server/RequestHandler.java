
package com.tssenola.debugtool.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tssenola.debugtool.utils.NetworkUtils;
import com.tssenola.debugtool.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class RequestHandler {
    private final Context mContext;

    public RequestHandler(Context context) {
        Log.d("vbvb", "RequestHandler RequestHandler: ");
        mContext = context;
    }

    public void handle2(Socket socket) throws IOException {
        Log.d("vbvb", "RequestHandler handle: ");
        BufferedReader reader = null;
        PrintStream output = null;
        try {
            String route = "index.html";
            output = new PrintStream(socket.getOutputStream());
            String ipAddr = NetworkUtils.getIpAddr(mContext);
            String content = Utils.loadContentStr(route,mContext.getResources().getAssets());
            if (TextUtils.isEmpty(content)) {
                writeServerError(output);
                return;
            }
            content = content.replace("ipaddr",ipAddr);
            byte[]  bytes = content.getBytes("UTF-8");
            // Send out the content.
            output.println("HTTP/1.0 200 OK");
            output.println("Content-Type: " + Utils.detectMimeType(route));

            Log.d("vbvb", "RequestHandler handle 2【" + "HTTP/1.0 200 OK");
            Log.d("vbvb", "RequestHandler handle 2【" + "Content-Type: " + Utils.detectMimeType(route));

            output.println("Content-Length: " + bytes.length);
            Log.d("vbvb", "RequestHandler handle 2【" + "Content-Length: " + bytes.length);
            Log.d("vbvb", "RequestHandler handle 2【" + new String(bytes));
            output.println();
            output.write(bytes);
            output.flush();
        } finally {
            try {
                if (null != output) {
                    output.close();
                }
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void writeServerError(PrintStream output) {
        Log.d("vbvb", "RequestHandler writeServerError: ");
        output.println("HTTP/1.0 500 Internal Server Error");
        output.flush();
    }
}
