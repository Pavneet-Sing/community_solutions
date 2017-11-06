package test.myapplicationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    TextView mytxtView; //Use to bind a TextView from UI layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytxtView = (TextView) findViewById(R.id.mytxtView);
        mytxtView.setText("Value 1");        
        Thread thread1 = new Thread(new Thread1());
        thread1.start();
        // inefficent creation onextra threads should use everywhere
        // new Thread1().start();
        //  or implement Runnable instead of entends thread
    }

    public void onResume() {
        super.onResume();
        registerReceiver(myReceiver, new IntentFilter("android.provider.Telephony.SOME_TELEPHONY_INTENT_ACTION"));
        // trigger the broadcast to to execute onReceive method using sendBroadcast(yourBroadcastIntent)
        sendBroadcast(new Intent("android.provider.Telephony.SOME_TELEPHONY_INTENT_ACTION"));
    }

    private class Thread1 extends Thread {
        @Override
        public void run() {
            Thread thread2 = new Thread(new Thread2());
            thread2.start();
        }
    }

    private class Thread2 extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            try {
                Thread.sleep(45000); //just to make sur myReceiver does its job
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), mytxtView.getText(), Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().
                    equals("android.provider.Telephony.SOME_TELEPHONY_INTENT_ACTION")) {
                //do some work
                        mytxtView.setText("Value 2");
            }
        }
    };
    
    @Override
    protected void onStop() {
        super.onStop();
        //  Don't forget to unregister the receiver
        unregisterReceiver(myReceiver);
    }

}
