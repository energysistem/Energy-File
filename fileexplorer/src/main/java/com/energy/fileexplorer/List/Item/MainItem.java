package com.energy.fileexplorer.List.Item;

import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by sitron on 10/04/14.
 */
public class MainItem {
    public Drawable icon;
    public String text;;
    public String subText;
    public File file;

    // Constructor.
    public MainItem(Drawable icon, String text, String subText, File file) {

        this.icon = icon;
        this.text = text;
        this.subText = subText;
        this.file = file;
    }
}
