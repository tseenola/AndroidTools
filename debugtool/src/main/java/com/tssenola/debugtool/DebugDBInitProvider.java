package com.tssenola.debugtool;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.tssenola.debugtool.server.ClientServer;
import com.tssenola.debugtool.server.LogServer;
import com.tssenola.debugtool.utils.NetworkUtils;

/**
 * Created by amitshekhar on 16/11/16.
 */

public class DebugDBInitProvider extends ContentProvider {
    private static final int DEFAULT_PORT = 8080;
    private static ClientServer clientServer;
    private static String addressLog = "not available";
    public static final String TAG = "DebugDBInitProvider";
    @Override
    public boolean onCreate() {
        Context context = getContext();
        Log.d("vbvb", "DebugDBInitProvider onCreate: ");
        int portNumber = DEFAULT_PORT;
        LogServer.getIns().init();
        clientServer = new ClientServer(context, portNumber);
        clientServer.start();
        addressLog = NetworkUtils.getAddressLog(context, portNumber);
        Log.d(TAG, addressLog);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
