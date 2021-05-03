package com.dodlebee.websocket;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dodlebee.firebasetest.R;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SocketActivity extends AppCompatActivity {
    private Socket mSocket;
    private WebSocketEcho webSocketEcho;
    private WebSocketClient mWebSocketClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

//        setSocket();

        webSocketEcho = new WebSocketEcho();
        try {
            webSocketEcho.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        connectWebSocket();    }
    }

//    private void setSocket() {
//        try {
//            mSocket = IO.socket("http://daw.g72.biz");
//        } catch (URISyntaxException ue) {
//            ue.printStackTrace();
//        }
//        mSocket.on("SERVER_TO_CLIENT_EVENT_DAW", onNewMessage);
//        mSocket.connect();
//
//        Log.e("SOCKET", "Connection success : " +  mSocket.connected());
//    }
//
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    Log.e("Doodle","Soket?222222");
////                    String username;
////                    String message;
////                    try {
////                        username = data.getString("username");
////                        message = data.getString("message");
////                    } catch (JSONException e) {
////                        return;
////                    }
////
////                    // add the message to view
////                    addMessage(username, message);
//                    Log.e("Doodle","Soket?");
//                }
//            });
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSocket.disconnect();
//        mSocket.off("종료", onNewMessage);
//    }

}
