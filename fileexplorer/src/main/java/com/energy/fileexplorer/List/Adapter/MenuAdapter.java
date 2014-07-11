package com.energy.fileexplorer.List.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.energy.fileexplorer.List.Item.ShortCutItem;
import com.energy.fileexplorer.R;

import java.util.List;

/**
 * Created by sitron on 10/04/14.
 */
public class MenuAdapter extends ArrayAdapter<ShortCutItem> {

    Context mContext;
    int layoutResourceId;
    List<ShortCutItem> data = null;

    public MenuAdapter(Context mContext, int layoutResourceId, List<ShortCutItem> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        ShortCutItem folder = data.get(position);


        imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.name);

        return listItem;
    }

}

