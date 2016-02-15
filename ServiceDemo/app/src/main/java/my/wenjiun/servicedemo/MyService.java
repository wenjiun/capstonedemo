package my.wenjiun.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private Handler mHandler;

    public MyService() {
    }

    // Only run once
    @Override
    public void onCreate() {
        mHandler = new Handler();
        super.onCreate();
        Log.d("Capstone", "Services created!");
    }



    // run whenever startService is called
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Capstone", "onStartCommand called!");
        int command = intent.getIntExtra("selection", 0);
        Log.d("Capstone", "selection:" + command);
        switch(command) {
            case 0:
                Log.d("Capstone", "Do nothing");
                Intent broadcast = new Intent("CONNECTED");
                broadcast.setPackage(getPackageName());
                sendBroadcast(broadcast);
                break;
            case 1:
                Log.d("Capstone", "Play Video 1");
                try {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent broadcast = new Intent("SUCCESS");
                            broadcast.setPackage(getPackageName());
                            broadcast.putExtra("message", "Video 1 played");
                            sendBroadcast(broadcast);
                        }
                    }, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                Log.d("Capstone", "Play Video 2");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent broadcast = new Intent("SUCCESS");
                        broadcast.setPackage(getPackageName());
                        broadcast.putExtra("message", "Video 2 played");
                        sendBroadcast(broadcast);
                    }
                }, 5000);
                break;
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
