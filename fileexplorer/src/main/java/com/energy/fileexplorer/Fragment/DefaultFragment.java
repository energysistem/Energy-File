package com.energy.fileexplorer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.energy.fileexplorer.File.Explorer;
import com.energy.fileexplorer.List.Adapter.MainAdapter;
import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.MainActivity;
import com.energy.fileexplorer.R;

import java.io.File;
import java.util.List;

/**
 * Created by MFC on 26/05/2014.
 */
public class DefaultFragment extends Fragment {
    private List<MainItem> mainItems;
    private File file;
    private int pos;

    public DefaultFragment(int pos){
        this.file = MainActivity.getFile(pos);
        this.pos = pos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listview = (ListView) rootView.findViewById(R.id.listView);

        mainItems = Explorer.showArchives(file);

        MainAdapter adapter = new MainAdapter(getActivity(), R.layout.fragmentlist_file, mainItems);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mainItems.get(position).file.isDirectory())
                    MainActivity.addFragmentMain(mainItems.get(position).file, pos +1);
                else
                    Explorer.showArchives(mainItems.get(position).file);
            }
        });
        return rootView;
    }
}
