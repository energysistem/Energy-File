package com.energy.fileexplorer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**  ListAdapter
 * Created by sitron on 10/04/14.
 */

public class ListAdapter extends ArrayAdapter<ListItem> {

    Context mContext;
    int layoutResourceId;
    ListItem data[] = null;

    public ListAdapter(Context mContext, int layoutResourceId, ListItem[] data) {

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

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.listImage);
        TextView textViewName = (TextView) listItem.findViewById(R.id.listText);
        TextView subtextViewName = (TextView) listItem.findViewById(R.id.listSubText);

        ListItem folder = data[position];


        imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.text);
        subtextViewName.setText(folder.subText);

        return listItem;
    }

}
