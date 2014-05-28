package com.energy.fileexplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.energy.fileexplorer.File.Explorer;
import com.energy.fileexplorer.Fragment.DefaultFragment;
import com.energy.fileexplorer.List.Adapter.FragmentAdapter;
import com.energy.fileexplorer.List.Adapter.MenuAdapter;
import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.List.Item.MenuItem;


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

    private static  List<Fragment> listaFragments;
    private static  FragmentAdapter fragmentAdapter;
    private static ArrayList<File> mainViews;
    private static ViewPager mViewPager;
    private boolean backButtonExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** Slide Navegation */
        mainViews = new ArrayList<File>();
        mainViews.add(Environment.getExternalStorageDirectory());

        Explorer.context = this;

        listaFragments = new ArrayList<Fragment>();
        listaFragments.add(new DefaultFragment(0));
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), listaFragments);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(fragmentAdapter);


        /** Navigation Drawer */
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        MenuItem[] menuItems = new MenuItem[5];

        menuItems[0] = new MenuItem(R.drawable.file_explorer, "Home");
        menuItems[1] = new MenuItem(R.drawable.music, "Music");
        menuItems[2] = new MenuItem(R.drawable.video_player, "Video");
        menuItems[3] = new MenuItem(R.drawable.gallery, "Gallery");
        menuItems[4] = new MenuItem(R.drawable.ereader, "Ebook");


        MenuAdapter adapter = new MenuAdapter(this, R.layout.listview_item_row, menuItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
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
        getActionBar().setTitle("File Explore");
        getActionBar().setSubtitle("Energy Sistem File Explore");
        getActionBar().setIcon(null);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);


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
        if(backButtonExit)
            super.onBackPressed();
        else
            backButtonExit = true;
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
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
        thread.run();
    }

    public static void addFragmentMain(File newFile, int pos){
        while(mainViews.size() > pos){
            mainViews.remove(pos);
            listaFragments.remove(pos);
        }

        mainViews.add(newFile);
        listaFragments.add(new DefaultFragment(pos));
        //getSupportFragmentManager().getFragments().clear();
        fragmentAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(pos);
    }

    public static File getFile(int pos){
        return mainViews.get(pos);
    }


    /**  ----------------------------------------------------------------------------------------------------   */
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
                    while(mainViews.size() > 1){
                        mainViews.remove(1);
                        listaFragments.remove(1);
                    }
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    fragmentAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(0);
                    break;
                case 1:
                    //fragment = new ReadFragment();
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
