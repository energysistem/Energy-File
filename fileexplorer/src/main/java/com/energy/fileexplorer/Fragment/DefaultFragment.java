package com.energy.fileexplorer.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.energy.fileexplorer.File.Explorer;
import com.energy.fileexplorer.List.Adapter.MainAdapter;
import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.MainActivity;
import com.energy.fileexplorer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MFC on 26/05/2014.
 */
public class DefaultFragment extends Fragment implements View.OnLongClickListener, View.OnClickListener{
    private List<MainItem> mainItems;
    private File file;
    private int pos;
    private LinearLayout menu = null;
    private boolean isMenuVisible = false;
    private View rootView = null;
    private MainAdapter adapter = null;

    public DefaultFragment(int pos){
        this.file = MainActivity.getFile(pos);
        this.pos = pos;
    }

    public DefaultFragment(){
        this.file = MainActivity.getFile(MainActivity.getLastFragment());
        this.pos = MainActivity.getLastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final ListView listview = (ListView) rootView.findViewById(R.id.listView);
        menu = (LinearLayout) rootView.findViewById(R.id.menuLayout);
        mainItems = Explorer.showArchives(file);

        adapter = new MainAdapter(getActivity(), R.layout.fragmentlist_file, mainItems);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isMenuVisible){
                    adapter.setItemSelected(position);
                    adapter.notifyDataSetChanged();

                }else {
                    if (mainItems.get(position).file.isDirectory())
                        MainActivity.addFragmentMain(mainItems.get(position).file, pos + 1);
                    else
                        Explorer.showArchives(mainItems.get(position).file);
                }
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showMenu();
                adapter.setItemSelected(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        LinearLayout lcopy = (LinearLayout) rootView.findViewById(R.id.menuCopy);
        lcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<File> aux = new ArrayList<File>();
                for(int i = 0; i< mainItems.size();i++)
                    if(adapter.isItemSelected(i))
                        aux.add(mainItems.get(i).file);

                if(aux.size() == 0)
                    return;

                Explorer.archiveCopy(aux);
                hideMenu();
            }
        });
        LinearLayout lcut = (LinearLayout) rootView.findViewById(R.id.menuCut);
        lcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<File> aux = new ArrayList<File>();
                for(int i = 0; i< mainItems.size();i++)
                    if(adapter.isItemSelected(i))
                        aux.add(mainItems.get(i).file);

                if(aux.size() == 0)
                    return;

                Explorer.archiveCut(aux);
                hideMenu();
            }
        });
        LinearLayout lpaste = (LinearLayout) rootView.findViewById(R.id.menuPaste);
        lpaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explorer.archivePaste(MainActivity.getFile(pos));
                hideMenu();
            }
        });
        LinearLayout ldelete = (LinearLayout) rootView.findViewById(R.id.menuDelete);
        ldelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<File> aux = new ArrayList<File>();
                for(int i = 0; i< mainItems.size();i++)
                    if(adapter.isItemSelected(i))
                        aux.add(mainItems.get(i).file);

                Explorer.archiveDelete(aux);
                hideMenu();
            }
        });

        rootView.setOnLongClickListener(this);
        rootView.setOnClickListener(this);
        this.rootView = rootView;

        return rootView;
    }

    @Override
    public boolean onLongClick(View v) {
        showMenu();

        /**
         if (item.getItemId() == R.id.mFileCopy)
         Explorer.archiveCopy(mainItems.get(position).file);
         else if (item.getItemId() == R.id.mFileCut)
         Explorer.archiveCut(mainItems.get(position).file);
         else if (item.getItemId() == R.id.mFilePast)
         Explorer.archivePaste(MainActivity.getFile(pos));
         else if (item.getItemId() == R.id.mFileDelete)
         Explorer.archiveDelete(mainItems.get(position).file);
         */

        return true;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        if(menu != null && !menuVisible)
            hideMenu();
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onClick(View v) {
        hideMenu();
    }

    private void hideMenu(){
        adapter.cleanSelected();
        adapter.notifyDataSetChanged();
        isMenuVisible = false;
        menu.setVisibility(View.GONE);

    }

    private void showMenu(){
        isMenuVisible = true;
        menu.setVisibility(View.VISIBLE);
    }
}
