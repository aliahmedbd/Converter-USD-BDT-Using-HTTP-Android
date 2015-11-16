package com.example.aliahmed.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.methods.HttpGet;

/**
 * Created by Ali Ahmed on 11/12/2015.
 */
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpService extends IntentService {
    private static final String TAG="com.example.aliahmed.myapplication";
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";
   // String a="m.facebook.com/messages";
    public HttpService() {
        super("HttpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       String responseString = "http://www.hrhafiz.com/converter" + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());
       // String responseString = a+"" + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());
        String responseMessage = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet=new HttpGet("http://www.hrhafiz.com/converter");
            //HttpGet httpGet=new HttpGet(a);
            HttpResponse response = httpclient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseMessage = out.toString();

            }

            else{
                //Closes the connection.
                Log.w("HTTP1:",statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        }
        catch (Exception e) {
            //Log.w("HTTP2:",e );
            //responseMessage = e.getMessage();
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.MyWebRequestReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_STRING, responseString);
        broadcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);

        sendBroadcast(broadcastIntent);

        Log.i(TAG, "Service is stared now");

    }
}
