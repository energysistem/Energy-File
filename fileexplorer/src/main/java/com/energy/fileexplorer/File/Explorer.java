package com.energy.fileexplorer.File;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by MFC on 15/05/2014.
 */
public class Explorer {
    public static Context context;
    //private static String[] types = {"File","Audio","Video","Ereader","Image","Other"};
    //private static int[] icons = {R.drawable.file_explorer,R.drawable.music,R.drawable.video_player,R.drawable.ereader,R.drawable.gallery,R.drawable.ic_launcher};

    public static ArrayList<MainItem> showArchives(File sd){
        ArrayList<MainItem> result = new ArrayList<MainItem>();
        File[] dirsList = sd.listFiles();
        if(dirsList != null) {
            for (File aux : dirsList)
                if (!aux.isHidden())
                    if (aux.isDirectory())
                        result.add(new MainItem(context.getResources().getDrawable(R.drawable.file_explorer), aux.getName(), "Archivos: " + aux.listFiles().length, aux));
                    else {
                        String prueba = "";
                        try{
                            prueba = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getSingleton().getFileExtensionFromUrl(String.valueOf(aux.toURI())));
                            prueba += "    ____    "+MimeTypeMap.getSingleton().getFileExtensionFromUrl(String.valueOf(aux.toURI()));
                        } catch (Exception e){}
                        result.add(new MainItem(getIcon(aux), aux.getName(), prueba, aux));
                    }
        } else {
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(sd),MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getSingleton().getFileExtensionFromUrl(String.valueOf(sd.toURI()))));
            context.startActivity(intent);
            return null;
        }
        return result;
    }

    private static Drawable getIcon(File file){
        String prueba = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getSingleton().getFileExtensionFromUrl(String.valueOf(file.toURI())));
        if(prueba == null)
            return context.getResources().getDrawable(R.drawable.ic_launcher);
        else if(prueba.contains("image"))
            return context.getResources().getDrawable(R.drawable.gallery);
        else if(prueba.contains("audio"))
            return context.getResources().getDrawable(R.drawable.music);
        else if(prueba.contains("video"))
            return context.getResources().getDrawable(R.drawable.video_player);
        else if(prueba.contains("text"))
            return context.getResources().getDrawable(R.drawable.ereader);
        else if(prueba.contains("application"))
            if (file.getPath().endsWith(".apk")) {
                String APKFilePath = file.getPath(); //For example...
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageArchiveInfo(APKFilePath, 0);

                // the secret are these two lines....
                pi.applicationInfo.sourceDir = APKFilePath;
                pi.applicationInfo.publicSourceDir = APKFilePath;
                //
                Drawable APKicon = pi.applicationInfo.loadIcon(pm);


                return APKicon;
            }
                else
            return context.getResources().getDrawable(R.drawable.browser);


        return context.getResources().getDrawable(R.drawable.ic_launcher);
    }

}
