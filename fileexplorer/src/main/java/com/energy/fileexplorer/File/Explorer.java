package com.energy.fileexplorer.File;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.List.Item.MenuItem;
import com.energy.fileexplorer.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by MFC on 15/05/2014.
 */
public class Explorer {
    public static Context context;
    public static boolean canPast = false;
    private static boolean isCut = false;
    private static File lastCache;

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
            try{
                context.startActivity(intent);
            } catch (Exception e){
                Toast.makeText(context, "No se ha podido ejecutar este archivo", Toast.LENGTH_SHORT).show();
            }
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

    public static void archiveCopy(File file){
        lastCache = file;
        canPast = true;

        Toast.makeText(context, "Copiar", Toast.LENGTH_SHORT).show();

    }

    public static void archiveCut(File file){
        lastCache = file;
        canPast = true;
        isCut = true;

        Toast.makeText(context, "Cortar", Toast.LENGTH_SHORT).show();

    }

    public static void archivePaste(File file){
        canPast = false;
        File newFile = new File(file.getPath() + "/" + lastCache.getName());
        InputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(lastCache);
            out = new FileOutputStream(newFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e){
            try {
                in = new FileInputStream(lastCache);
                out = new FileOutputStream(file);
            } catch (Exception i){}

            }
        if(isCut) {
            lastCache.delete();
            isCut = false;

        }

        Toast.makeText(context, "Pegar", Toast.LENGTH_SHORT).show();

    }

}
