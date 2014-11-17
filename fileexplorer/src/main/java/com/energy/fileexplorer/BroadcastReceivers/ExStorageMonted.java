package com.energy.fileexplorer.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by MFC on 17/07/2014.
 */
public class ExStorageMonted extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("pruebax","--------***********--------------");
        if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD Card mounted",Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
            Toast.makeText(context, "SD Card unmounted",Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_STARTED)) {
            Toast.makeText(context, "SD Card scanner started",Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
            Toast.makeText(context, "SD Card scanner finished",Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
            Toast.makeText(context, "SD Card eject", Toast.LENGTH_LONG).show();
        } else if(action.equals(Intent.ACTION_UMS_CONNECTED))
        {
            Toast.makeText(context, "connected",Toast.LENGTH_LONG).show();
        } else if(action.equals(Intent.ACTION_UMS_DISCONNECTED)) {
            Toast.makeText(context, "disconnected",Toast.LENGTH_LONG).show();
        }
    }
}
