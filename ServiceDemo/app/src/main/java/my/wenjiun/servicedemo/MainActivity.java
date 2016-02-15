package my.wenjiun.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Intent i;
    private BroadcastReceiver successReceiver;
    private TextView textView;
    private BroadcastReceiver connectedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        i = new Intent(MainActivity.this, MyService.class);
        i.putExtra("selection", 0);
        startService(i);

        // to stop it call stopService(i);

        successReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Capstone", intent.toString());
                String message = intent.getStringExtra("message");
                textView.setText(message);
            }
        };

        connectedReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Capstone", intent.toString());
                textView.setText("Connected");
            }
        };

        IntentFilter filter = new IntentFilter("SUCCESS");
        registerReceiver(successReceiver, filter);

        IntentFilter filter2 = new IntentFilter("CONNECTED");
        registerReceiver(connectedReceiver, filter2);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("selection", 1);
                startService(i);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("selection", 2);
                startService(i);
            }
        });

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(successReceiver);
        super.onDestroy();
    }
}
