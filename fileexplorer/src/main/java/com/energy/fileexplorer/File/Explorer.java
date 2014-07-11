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
import com.energy.fileexplorer.MainActivity;
import com.energy.fileexplorer.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que se utiliza como herramienta para la gestión de archivos
 */
public class Explorer {
    public static Context context;
    public static boolean canPast = false;
    private static boolean isCut = false;
    private static ArrayList<File> lastCache;

    public static ArrayList<MainItem> showArchives(File sd){
        if(sd.exists() && !sd.isDirectory()){
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
        ArrayList<MainItem> result = new ArrayList<MainItem>();
        File[] dirsList = sd.listFiles();
        Arrays.sort(dirsList);

        for (File aux : dirsList)
            if (!aux.isHidden())
                if (aux.isDirectory())
                    result.add(new MainItem(context.getResources().getDrawable(R.drawable.file_explorer), aux.getName(), "Archivos: " + aux.listFiles().length, aux));
                else {
                    String prueba = "";
                    try{
                        prueba = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getSingleton().getFileExtensionFromUrl(String.valueOf(aux.toURI())));
                        prueba += "  _  "+MimeTypeMap.getSingleton().getFileExtensionFromUrl(String.valueOf(aux.toURI()));
                    } catch (Exception e){}
                    result.add(new MainItem(getIcon(aux), aux.getName(), prueba, aux));
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

    public static void archiveCopy(ArrayList<File> file){
        lastCache = file;
        canPast = true;

        Toast.makeText(context, context.getText(R.string.eCopy), Toast.LENGTH_SHORT).show();

    }

    public static  void sendBackMessage(){
        Toast.makeText(context, context.getText(R.string.eBackFragment), Toast.LENGTH_SHORT).show();
    }

    public static void archiveCut(ArrayList<File> file){
        lastCache = file;
        canPast = true;
        isCut = true;

        Toast.makeText(context, context.getText(R.string.eCut), Toast.LENGTH_SHORT).show();

    }

    public static void archiveDelete(ArrayList<File> file){
        int numFiles = file.size();
        File[] aux = new File[numFiles];
        for(int i = 0; i< numFiles;i++)
            aux[i] = file.get(i);
        archiveDelete(aux);

        isCut = false;
        MainActivity.reloadFragmentMain();
        Toast.makeText(context, context.getText(R.string.eDelete), Toast.LENGTH_SHORT).show();
    }

    public static void archiveDelete(File file){
        if(file.isDirectory() && file.listFiles().length > 0)
            archiveDelete(file.listFiles());
        try {
            file.delete();
        } catch (Exception e){}
    }

    private static void archiveDelete(File[] file){
        for(File aux : file)
            if (file != null) {
                if (aux.isDirectory()) {
                    String[] children = aux.list();
                    for (int i = 0; i < children.length; i++) {
                        archiveDelete(new File(aux, children[i]));
                    }
                }
                aux.delete();
            }
    }

    public static void archivePaste(File file){
        canPast = false;
        File newFile;
        int numfiles = lastCache.size() -1;
        for(int i = numfiles;i>=0;i--) {
            newFile = lastCache.get(i);

            if(file.isDirectory() && newFile.getAbsolutePath().startsWith(file.getAbsolutePath())){
                //copyDirectory(newFile,new File(file + "/" + newFile.getName()));
                if (!copyArchives(file, toArchive(newFile)))
                    Toast.makeText(context, "Error al copiar.", Toast.LENGTH_SHORT).show();


            } else {
                //copyDirectory(newFile,new File(file + "/" + newFile.getName()));
                if (!copyArchives(file, newFile)) {
                    Toast.makeText(context, "Error al copiar.", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }

        if(isCut)
            archiveDelete(lastCache);

        MainActivity.reloadFragmentMain();
        Toast.makeText(context, context.getText(R.string.ePaste), Toast.LENGTH_SHORT).show();

    }

    private static Archive toArchive(File file){
        int num = file.listFiles().length;
        Archive[] subfiles = new Archive[num];
        for(int i = 0; i< num;i++) {
            if(file.listFiles()[i].isDirectory())
                subfiles[i] = toArchive(file.listFiles()[i]);
            else
                subfiles[i] = new Archive(file.listFiles()[i]);
        }
        return new Archive(file,subfiles);
    }

    private static boolean copyArchives(File newLocation, Archive file){
        File newFile = new File(newLocation + "/" + file.getFile().getName());
        if(newFile.exists())
            return false;

        Archive[] forCopy = file.getSubFiles();
        newFile.mkdir();
        for(int i = 0 ; i< forCopy.length;i++){
            if(forCopy[i].isDirectory()){
                if(!copyArchives(newFile,forCopy[i]))
                    return false;
            }else {
                if (!copyArchives(newFile, forCopy[i].getFile()))
                    return false;
            }


        }

        return true;

    }

    private static boolean copyArchives(File newLocation, File file){
        File newFile = new File(newLocation + "/" + file.getName());
        if(newFile.exists())
            return false;

        if(file.isDirectory()){
            File[] forCopy = file.listFiles();
            newFile.mkdir();
            for(int i = 0 ; i< forCopy.length;i++){
                if(!copyArchives(newFile,forCopy[i]))
                    return false;
            }

        } else {
            try {
                InputStream in = new FileInputStream(file);
                OutputStream out = new FileOutputStream(newFile);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e){
                return false;
            }
        }

        return true;

    }

    public static void createFolder(File file, String name){
        File newDir = new File(file.getAbsolutePath() + "/" + name);
        newDir.mkdir();

        MainActivity.reloadFragmentMain();
        Toast.makeText(context, context.getText(R.string.eNewFolder), Toast.LENGTH_SHORT).show();
    }

}
