package com.energy.fileexplorer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sitron on 10/04/14.
 */
public class ReadFragment extends Fragment {

    public ReadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_read, container, false);

        return rootView;
    }

}