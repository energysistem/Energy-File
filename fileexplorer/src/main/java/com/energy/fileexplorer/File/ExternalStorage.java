package com.energy.fileexplorer.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import com.energy.fileexplorer.List.Item.ShortCutItem;
import com.energy.fileexplorer.MainActivity;
import com.energy.fileexplorer.R;

import java.io.File;
import java.util.List;

/**
 * Created by MFC on 04/06/2014.
 */
public class ExternalStorage {
    private Context context;
    private File[] removeableList;
    private ShortCutItem[] removeableItemList;
    private int numRemovable= 0;


    public ExternalStorage(Context context){
        this.context = context;
        removeableList = new File("/storage").listFiles();
        numRemovable = removeableList.length;
        removeableItemList = new ShortCutItem[numRemovable];
        for(int i = 0; i< numRemovable;i++)
            removeableItemList[i] = new ShortCutItem(R.drawable.file_explorer,removeableList[i].getName());

        registerSDCardStateChangeListener();
    }

    private void registerSDCardStateChangeListener() {
        BroadcastReceiver mSDCardStateChangeListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                controlMediaShortCut();
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MEDIA_REMOVED");
        filter.addAction("android.intent.action.MEDIA_MOUNTED");
        filter.addDataScheme("file");
        context.registerReceiver(mSDCardStateChangeListener, filter);
    }



    public void controlMediaShortCut(){
        /*String aux="";
        aux = Environment.getStorageState(Environment.getExternalStorageDirectory());
        for(int i = 0; i< numRemovable;i++){
            try {
                aux = Environment.getStorageState(removeableList[i]);
                if (Environment.MEDIA_MOUNTED.equals(aux)) {
                    MainActivity.addRemovableItem(removeableItemList[i]);
                } else if (Environment.MEDIA_REMOVED.equals(aux)) {
                    MainActivity.delRemovableItem(removeableItemList[i]);
                }
            }catch (Exception e){}
        }*/

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}



