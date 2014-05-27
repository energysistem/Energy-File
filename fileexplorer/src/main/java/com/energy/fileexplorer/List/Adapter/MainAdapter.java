package com.energy.fileexplorer.List.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.R;

import java.util.List;


/**  ListAdapter
 * Created by sitron on 10/04/14.
 */

public class MainAdapter extends ArrayAdapter<MainItem> {

    Context mContext;
    int layoutResourceId;
    List<MainItem> data = null;

    public MainAdapter(Context mContext, int layoutResourceId, List<MainItem> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.listImage);
        TextView textViewName = (TextView) convertView.findViewById(R.id.listText);
        TextView subtextViewName = (TextView) convertView.findViewById(R.id.listSubText);

        MainItem folder = data.get(position);


        imageViewIcon.setImageDrawable(folder.icon);
        textViewName.setText(folder.text);
        subtextViewName.setText(folder.subText);

        return convertView;
    }

}
