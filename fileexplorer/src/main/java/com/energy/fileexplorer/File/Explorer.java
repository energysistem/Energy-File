package com.energy.fileexplorer.File;

import android.os.Environment;

import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by MFC on 15/05/2014.
 */
public class Explorer {
    private static String[] types = {"File","Audio","Video","Ereader","Image","Other"};
    private static int[] icons = {R.drawable.file_explorer,R.drawable.music,R.drawable.video_player,R.drawable.ereader,R.drawable.gallery,R.drawable.ic_launcher};

    public static ArrayList<MainItem> showArchives(){

        File sd = new File(Environment.getExternalStorageDirectory() + "");
        return showArchives(sd);
    }

    public static ArrayList<MainItem> showArchives(File sd){
        ArrayList<MainItem> result = new ArrayList<MainItem>();
        for(File aux : sd.listFiles()){
            if(!aux.isHidden())
                if(aux.isDirectory())
                    result.add(new MainItem(R.drawable.file_explorer, aux.getName(),"Número de Archivos: " + aux.listFiles().length, aux));
                else
                    result.add(new MainItem(R.drawable.music, aux.getName(),"", aux));
        }
        return result;
    }
}
