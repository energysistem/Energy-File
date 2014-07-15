package com.energy.fileexplorer.List.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.R;

import java.util.List;


/**  ListAdapter
 * Created by sitron on 10/04/14.
 */

public class DefaultMainAdapter extends ArrayAdapter<MainItem> {

    Context mContext;
    int layoutResourceId;
    List<MainItem> data = null;
    private boolean[] dataSelected = null;

    public DefaultMainAdapter(Context mContext, int layoutResourceId, List<MainItem> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

        cleanSelected();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_list_file, parent, false);

        ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.listImage);
        TextView textViewName = (TextView) convertView.findViewById(R.id.listText);
        TextView subtextViewName = (TextView) convertView.findViewById(R.id.listSubText);
        RelativeLayout linearLayout = (RelativeLayout) convertView.findViewById(R.id.mainItemBackground);

        MainItem folder = data.get(position);


        imageViewIcon.setImageDrawable(folder.icon);
        textViewName.setText(folder.text);

        subtextViewName.setText(folder.subText);

        if(dataSelected[position])
            linearLayout.setBackgroundColor(convertView.getResources().getColor(R.color.EnergyGrey));

        return convertView;
    }

    public void setItemSelected(int pos){
        if(dataSelected[pos])
            dataSelected[pos] = false;
        else
            dataSelected[pos] = true;
    }

    public void cleanSelected(){
        int aux = data.size();
        dataSelected = new boolean[aux];
        for(int i = 0; i< aux;i++){
            dataSelected[i] = false;
        }
    }

    public boolean isItemSelected(int num){
        return dataSelected[num];
    }


}
