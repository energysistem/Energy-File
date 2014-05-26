package com.energy.fileexplorer.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by MFC on 15/05/2014.
 */
public class Explorer {
    //private static String[] types = {"File","Audio","Video","Ereader","Image","Other"};
    //private static int[] icons = {R.drawable.file_explorer,R.drawable.music,R.drawable.video_player,R.drawable.ereader,R.drawable.gallery,R.drawable.ic_launcher};

    public static ArrayList<MainItem> showArchives(File sd){
        ArrayList<MainItem> result = new ArrayList<MainItem>();
        File[] dirsList = sd.listFiles();
        if(dirsList != null) {
            for (File aux : dirsList)
                if (!aux.isHidden())
                    if (aux.isDirectory())
                        result.add(new MainItem(R.drawable.file_explorer, aux.getName(), "Archivos: " + aux.listFiles().length, aux));
                    else
                        result.add(new MainItem(R.drawable.music, aux.getName(), "", aux));
        } else {

        }
        return result;
    }

}
