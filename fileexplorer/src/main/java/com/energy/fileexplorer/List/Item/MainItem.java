package com.energy.fileexplorer.List.Item;

import java.io.File;

/**
 * Created by sitron on 10/04/14.
 */
public class MainItem {
    public int icon;
    public String text;;
    public String subText;
    public File file;

    // Constructor.
    public MainItem(int icon, String text, String subText, File file) {

        this.icon = icon;
        this.text = text;
        this.subText = subText;
        this.file = file;
    }
}
