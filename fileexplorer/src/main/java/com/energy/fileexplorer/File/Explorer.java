package com.energy.fileexplorer.File;

import android.os.Environment;

import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.R;

import java.io.File;

/**
 * Created by MFC on 15/05/2014.
 */
public class Explorer {
    private static String[] types = {"File","Audio","Video","Ereader","Image","Other"};
    private static int[] icons = {R.drawable.file_explorer,R.drawable.music,R.drawable.video_player,R.drawable.ereader,R.drawable.gallery,R.drawable.ic_launcher};

    public static MainItem[] showArchives(){

        File sd = new File(Environment.getExternalStorageDirectory() + "");
        return showArchives(sd);
    }

    public static MainItem[] showArchives(File sd){
        File[] sdDirList = sd.listFiles();
        MainItem[] archives = new MainItem[sdDirList.length];

        for(int i = 0; i < sdDirList.length;i++){
            try {
                if(!sdDirList[i].isHidden())
                    if(sdDirList[i].isDirectory())
                        archives[i] = new MainItem(R.drawable.file_explorer, sdDirList[i].getName(),sdDirList[i].getAbsolutePath());
                    else
                        archives[i] = new MainItem(R.drawable.music, sdDirList[i].getName(),"");
                else
                if(sdDirList[i].isDirectory())
                    archives[i] = new MainItem(R.drawable.file_explorer, sdDirList[i].getName(),"oculto");
                    else
                    archives[i] = new MainItem(R.drawable.music, sdDirList[i].getName(),"oculto");

            } catch (Exception e){

                if(sdDirList[i].isDirectory())
                    archives[i] = new MainItem(R.drawable.file_explorer, sdDirList[i].getName(),sdDirList[i].getAbsolutePath());
                else
                    archives[i] = new MainItem(R.drawable.music, sdDirList[i].getName(),"");
            }
            //if(sdDirList[i].isHidden())

        }

        return archives;
    }
}
