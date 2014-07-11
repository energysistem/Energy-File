package com.energy.fileexplorer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.energy.fileexplorer.File.Explorer;
import com.energy.fileexplorer.List.Adapter.DefaultMainAdapter;
import com.energy.fileexplorer.List.Adapter.GridMainAdapter;
import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.MainActivity;
import com.energy.fileexplorer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MFC on 02/06/2014.
 */
public class GridFragment extends Fragment implements View.OnLongClickListener, View.OnClickListener{
    private List<MainItem> mainItems;
    private File file;
    private int pos;
    private LinearLayout toolMenu = null;
    private boolean isMenuVisible = false;
    private View rootView = null;
    private GridMainAdapter adapter = null;

    public GridFragment(int pos){
        this.file = MainActivity.getFile(pos);
        this.pos = pos;
    }

    public GridFragment(){
        this.file = MainActivity.getFile(MainActivity.getLastFragment());
        this.pos = MainActivity.getLastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_main_grid, container, false);
        final GridView listview = (GridView) rootView.findViewById(R.id.mainGrid);
        toolMenu = (LinearLayout) rootView.findViewById(R.id.menuLayout);
        mainItems = Explorer.showArchives(file);


        adapter = new GridMainAdapter(getActivity(),R.id.mainGrid, mainItems);
        listview.setAdapter(adapter);
   /*     listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        LinearLayout lexit = (LinearLayout) rootView.findViewById(R.id.menuExit);
        lexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu();
            }
        });
*/
        rootView.setOnLongClickListener(this);
        rootView.setOnClickListener(this);
        this.rootView = rootView;

        //Menu mainMenu = (Menu) rootView.findViewById(R.menu.main);

        return rootView;
    }

    @Override
    public boolean onLongClick(View v) {
        showMenu();

        return true;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        if(toolMenu != null && !menuVisible)
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
        toolMenu.setVisibility(View.GONE);

    }

    public void showMenu(){
        isMenuVisible = true;
        toolMenu.setVisibility(View.VISIBLE);
    }
}