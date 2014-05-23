package com.energy.fileexplorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.energy.fileexplorer.File.Explorer;
import com.energy.fileexplorer.Fragment.CreateFragment;
import com.energy.fileexplorer.Fragment.HelpFragment;
import com.energy.fileexplorer.Fragment.ReadFragment;
import com.energy.fileexplorer.List.Adapter.MainAdapter;
import com.energy.fileexplorer.List.Adapter.MenuAdapter;
import com.energy.fileexplorer.List.Item.MainItem;
import com.energy.fileexplorer.List.Item.MenuItem;


public class MainActivity extends ActionBarActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Navigation Drawer
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener {
        List<MainItem> mainItems = new ArrayList<MainItem>();
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            int num = getArguments().getInt(ARG_SECTION_NUMBER);

            //This list is from http://www.vogella.com/tutorials/AndroidListView/article.html#androidlists
            ListView listview = (ListView) rootView.findViewById(R.id.listView);
            mainItems = new ArrayList<MainItem>();
            MainItem[] list = new MainItem[5];

            switch (num) {
                case 1:
                    mainItems = Explorer.showArchives();

                    /*mainItems.add(new MainItem(R.drawable.file_explorer, "Alarms","Contents 3 items"));
                    mainItems.add(new MainItem(R.drawable.file_explorer, "Android","Contents 3 items"));
                    mainItems.add(new MainItem(R.drawable.file_explorer, "Download","Contents 3 items"));
                    mainItems.add(new MainItem(R.drawable.file_explorer, "Movies","Contents 3 items"));
                    mainItems.add(new MainItem(R.drawable.gallery, "DMC0122212","Image file"));*/
                    break;
                case 2:
                    mainItems.add(new MainItem(R.drawable.music, "Purple Haze","Jimi Jendrix - Are you Expirienced?",null));
                    mainItems.add(new MainItem(R.drawable.music, "More Than A Feeling","Boston - Boston",null));
                    mainItems.add(new MainItem(R.drawable.music, "Butterfly bleu","Iron Butterfly - Metamorphosis",null));
                    mainItems.add(new MainItem(R.drawable.music, "Fortune Son","Creedence Clearwater Revival - Willy and the Poor Boys",null));
                    mainItems.add(new MainItem(R.drawable.music, "The End","The Door - The Doors",null));
                    break;
                case 3:
                    mainItems.add(new MainItem(R.drawable.video_player, "Gone with the Wind","Video file",null));
                    mainItems.add(new MainItem(R.drawable.video_player, "Casablanca","Video file",null));
                    mainItems.add(new MainItem(R.drawable.video_player, "Seven Samurai","Video file",null));
                    mainItems.add(new MainItem(R.drawable.video_player, "The Godfather","Video file",null));
                    mainItems.add(new MainItem(R.drawable.video_player, "The Lord of the Rings","Video file",null));
                    break;
                default:
                    mainItems.add(new MainItem(R.drawable.file_explorer, "Alarms","Contents 3 items",null));
                    mainItems.add(new MainItem(R.drawable.file_explorer, "Android","Contents 3 items",null));
                    mainItems.add(new MainItem(R.drawable.file_explorer, "Download","Contents 3 items",null));
                    mainItems.add(new MainItem(R.drawable.file_explorer, "Movies","Contents 3 items",null));
                    mainItems.add(new MainItem(R.drawable.gallery, "DMC0122212","Image file",null));
            }



            MainAdapter adapter = new MainAdapter(getActivity(), R.layout.fragmentlist_file, mainItems);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(this);
            //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
            return rootView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainItem item = mainItems.get(position);

        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }


        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void selectItem(int position) {

            android.app.Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new CreateFragment();
                    break;
                case 1:
                    fragment = new ReadFragment();
                    break;
                case 2:
                    fragment = new HelpFragment();
                    break;

                default:
                    fragment = new HelpFragment();
                    break;
            }

            if (fragment != null) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                //getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }

}
