package com.energy.fileexplorer;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.energy.fileexplorer.File.Explorer;
import com.energy.fileexplorer.File.ExternalStorage;
import com.energy.fileexplorer.Fragment.DefaultFragment;
import com.energy.fileexplorer.Fragment.GridFragment;
import com.energy.fileexplorer.List.Adapter.FragmentAdapter;
import com.energy.fileexplorer.List.Adapter.MenuAdapter;
import com.energy.fileexplorer.List.Item.ShortCutItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Actividad Principal
 */
public class MainActivity extends ActionBarActivity {

    /**
     * Navigation Drawer
     * This an example from http://www.codeofaninja.com/2014/02/android-navigation-drawer-example.html
     */
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    /**
     * Objetos principales
     */
    //Lista de fragmentos de la pantalla principal
    private static List<Fragment> listaFragments;
    //Adaptado de la lista de fragmentos
    private static FragmentAdapter fragmentAdapter;
    //Lista de las carpetas que estan abiertas
    private static ArrayList<File> mainViews;
    //ViewPager donde se carga el fragment
    private static ViewPager mViewPager;
    //Se utiliza para comprobar si el botón "Atras" del Navegation Bar tiene que cerrar la aplicación.
    private boolean backButtonExit = false;
    //Se utiliza para cuando la pantalla gire, saver en que fragmento nos encontramos.
    private static int lastFragment = 0;
    private static List<ShortCutItem> shortCutItems;
    private static MenuAdapter shortCutAdapter;
    //private List<ShortCutItem> Removeable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** Slide Navegation */
        mainViews = new ArrayList<File>();
        listaFragments = new ArrayList<Fragment>();
        //ExternalStorage externalStorage = new ExternalStorage();
        //registerSDCardStateChangeListener();
        ExternalStorage externalStorage = new ExternalStorage(this);

      //Guardar el estado anterior
        try {

            String[] savedFiles = savedInstanceState.getStringArray("keyFiles");
            lastFragment = savedInstanceState.getInt("keyPosition");
            for (int i = 0; i < savedFiles.length; i++) {
                mainViews.add(new File(savedFiles[i]));
                listaFragments.add(new DefaultFragment(i));
            }

        } catch (Exception e) {
            mainViews.add(Environment.getExternalStorageDirectory());
            listaFragments.add(new DefaultFragment(0));
        }

        Explorer.context = this;

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), listaFragments);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(fragmentAdapter);


        /** Navigation Drawer */
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        shortCutItems = new ArrayList<ShortCutItem>();

        shortCutItems.add(new ShortCutItem(R.drawable.file_explorer, "Home"));
        shortCutItems.add(new ShortCutItem(R.drawable.music, "Music"));
        shortCutItems.add(new ShortCutItem(R.drawable.video_player, "Video"));
        shortCutItems.add(new ShortCutItem(R.drawable.gallery, "Gallery"));
        shortCutItems.add(new ShortCutItem(R.drawable.ereader, "Ebook"));


        shortCutAdapter = new MenuAdapter(this, R.layout.listview_item_row, shortCutItems);
        mDrawerList.setAdapter(shortCutAdapter);
        externalStorage.controlMediaShortCut();

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.file_explorer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Energy Files");
        getActionBar().setIcon(null);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String[] files = new String[mainViews.size()];
        for (int i = 0; i < files.length; i++)
            files[i] = mainViews.get(i).getAbsolutePath();
        outState.putStringArray("keyFiles", files);
        outState.putInt("keyPosition", mViewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Changed by Navigation Drawer
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSelect:
                DefaultFragment aux = (DefaultFragment) listaFragments.get(mViewPager.getCurrentItem());
                aux.showMenu();
                break;
            case R.id.mNewFolder:
                createFolder();
                reloadFragmentMain();
                break;

            case R.id.mSettings:

                break;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);/*
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        //getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(((DefaultFragment )listaFragments.get(mViewPager.getCurrentItem())).isMenuHide()){
            ((DefaultFragment )listaFragments.get(mViewPager.getCurrentItem())).hideMenu();
            return;
        }

        if (mViewPager.getCurrentItem() != 0) {
            backFragmentMain();
            return;
        }

        if (backButtonExit)
            super.onBackPressed();
        else {
            backButtonExit = true;
            Explorer.sendBackMessage();
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        backButtonExit = false;
                    }
                });
            }
        };
        thread.start();
    }

    private void createFolder() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Crear Carpeta");


        alertDialog.setMessage("Introduce el nombre:");
        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        alertDialog.setIcon(R.drawable.file_explorer);

        alertDialog.setPositiveButton("Crear",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Explorer.createFolder(mainViews.get(mViewPager.getCurrentItem()), input.getText().toString());
                    }
                }
        );

        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                }
        );

        alertDialog.show();
    }

    public static void addFragmentMain(File newFile, int pos) {
        while (mainViews.size() > pos) {
            mainViews.remove(pos);
            listaFragments.remove(pos);
        }

        mainViews.add(newFile);
        listaFragments.add(new DefaultFragment(pos));
        //getSupportFragmentManager().getFragments().clear();
        fragmentAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(pos);
    }

    public static void reloadFragmentMain() {
        int pos = mViewPager.getCurrentItem();
        for (int i = mainViews.size() - 1; i >= 0; i--) {
            if (!mainViews.get(i).exists()) {
                mainViews.remove(i);
                listaFragments.remove(i);
            }
        }
        if (pos >= mainViews.size())
            pos = mainViews.size() - 1;

        listaFragments.set(pos, new DefaultFragment(pos));
        fragmentAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(pos);

    }

    public static void backFragmentMain() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public static File getFile(int pos) {
        return mainViews.get(pos);
    }

    public static int getLastFragment() {
        return lastFragment;
    }


    public static void addRemovableItem(ShortCutItem shortCutItem){
        if(!shortCutItems.contains(shortCutItem)) {
            shortCutItems.add(shortCutItem);
            shortCutAdapter.notifyDataSetChanged();
        }
    }

    public static void delRemovableItem(ShortCutItem shortCutItem){
        if(shortCutItems.contains(shortCutItem)) {
            shortCutItems.remove(shortCutItems.indexOf(shortCutItem));
            shortCutAdapter.notifyDataSetChanged();
        }
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }


        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void selectItem(int position) {

            android.app.Fragment fragment = null;
            mDrawerLayout.setSelected(false);

            switch (position) {
                case 0:
                    mViewPager.setCurrentItem(0);
                    while (mainViews.size() > 1) {
                        mainViews.remove(1);
                        listaFragments.remove(1);
                    }
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    fragmentAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(0);
                    break;
                case 1:
                    listaFragments.add(0,new GridFragment(0));
                    mainViews.add(0,mainViews.get(0));
                    mViewPager.setCurrentItem(0);
                    while (mainViews.size() > 1) {
                        mainViews.remove(1);
                        listaFragments.remove(1);
                    }
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    fragmentAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(0);

                    //fragment = new GridFragment(0);
                    break;
                case 2:
                    //fragment = new HelpFragment();
                    break;

                default:
                    //fragment = new HelpFragment();
                    break;
            }

            if (fragment != null) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                mDrawerLayout.closeDrawer(0);
                //getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }

}
