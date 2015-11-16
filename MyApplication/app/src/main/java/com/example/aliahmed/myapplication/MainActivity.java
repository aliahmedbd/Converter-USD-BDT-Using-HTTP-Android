package com.example.aliahmed.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity {
    private MyWebRequestReceiver receiver;
    public String USD,BDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter(MyWebRequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MyWebRequestReceiver();
        registerReceiver(receiver, filter);
        //Button btn1=(Button)findViewById((R.id.);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v){

       // txt.setText("Hello");
        Intent i=new Intent(MainActivity.this,HttpService.class);
        startService(i);



    }
    @Override
    public void onDestroy() {
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }
    public  class MyWebRequestReceiver extends BroadcastReceiver {
        public static final String PROCESS_RESPONSE = "com.example.aliahmed.myapplication.intent.action.PROCESS_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {
            String responseString = intent.getStringExtra(HttpService.RESPONSE_STRING);
            String reponseMessage = intent.getStringExtra(HttpService.RESPONSE_MESSAGE);
            String a="";
            //String responseString = intent.getStringExtra(HttpService.RESPONSE_STRING)
            WebView myWebView = (WebView) findViewById(R.id.myWebView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            try {
                myWebView.loadData(URLEncoder.encode(reponseMessage, "utf-8").replaceAll("\\+"," "), "text/html", "UTF-8");
                a=reponseMessage;
            } catch (Exception e) {
                e.printStackTrace();
            }
            EditText txt=(EditText)findViewById(R.id.editText);
            TextView view=(TextView)findViewById(R.id.textView);
            USD=a.substring(9,14)+"";
            BDT=a.substring(25,30)+"";
           // String parts1,parts2,parts3,parts4;
           // String [] part=a.split(":");
           // String USD,BDT;

           // parts2=part[1];

           // parts4=part[2];
            //String [] secondTime=parts2.split(",");
            //USD=secondTime[0];
           // part=parts2.split("}");
           // parts2=part[0];
          //  part=parts4.split("}");
          //  parts4=part[0];
            //double value;
            //String b=txt.getText()+"";
           // value=Double.parseDouble(b);
            //value=value*(Double.parseDouble(USD));
            float dollar,taka;
            float value;


            String b="";
            b=txt.getText()+"";

            value=Float.parseFloat(b);
            float result=0;
            dollar=Float.parseFloat(USD);
            taka=Float.parseFloat(BDT);
            RadioGroup group=(RadioGroup)findViewById(R.id.radioGroup);
            RadioButton b1=(RadioButton)findViewById(R.id.radioButtonUsd);
            RadioButton b2=(RadioButton)findViewById(R.id.radioButtonBdt);
            if(b1.isChecked()){
                result = value * taka;
                view.setText("USD:"+result + "$");
            }
            else if(b2.isChecked()){
                result = value * dollar;
                view.setText("BDT:"+result + "tk");
            }
            else
            {
                view.setText("Please check BDT or USD.");
            }

            //if()
           // txt.setText(b+"value");


        }
    }
}
