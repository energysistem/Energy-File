package com.energy.fileexplorer.File;

import java.io.File;

/**
 * Esta clase se utiliza unicamente para crear un buffer de todo el contenido de una carpeta
 */

public class Archive {
    private boolean directory;
    private Archive[] subFiles;
    private File file;

    public Archive (File file){
        directory = false;
        this.file = file;
    }

    public Archive(File file, Archive[] subFiles){
        directory = true;
        this.file = file;
        this.subFiles = subFiles;
    }

    public boolean isDirectory(){
        return directory;
    }
    
    public File getFile(){
        return file;
    }
    
    public Archive[] getSubFiles(){
        return subFiles;
    }
}
