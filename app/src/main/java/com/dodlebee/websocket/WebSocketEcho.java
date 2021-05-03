package com.dodlebee.websocket;

import android.util.Log;

import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.io.RealConnection;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.Buffer;
import okio.ByteString;

import static android.provider.Telephony.Mms.Part.TEXT;


public final class WebSocketEcho extends WebSocketListener {
    private WebSocket socket;
    private int pingIntervalMillis;

    public void run() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url("ws://daw.g72.biz:30909")
//                .addHeader("Sec-WebSocket-Key","SERVER_TO_CLIENT_EVENT_DAW")
                .build();
        Log.e("DDD",request.toString());


        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.e("connect socket", String.valueOf(response));

            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.e("onMessage", text);
                Log.e("OnMessage","@#!");
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Log.e("onMessage2", String.valueOf(bytes));
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.e("onClosing", "onClosing");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.e("onClosed", "onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Log.e("onFailure", "onFailure");
            }
        };
        socket = client.newWebSocket(request, webSocketListener);
//        client.dispatcher().executorService().shutdown();

    }



}