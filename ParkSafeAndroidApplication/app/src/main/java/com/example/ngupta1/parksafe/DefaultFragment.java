package com.example.ngupta1.parksafe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultFragment extends Fragment {
    String s;

    public DefaultFragment(String s) {
        // Required empty public constructor
        this.s=s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("calling again");
        int a = getResources().getIdentifier(s, "layout",container.getContext().getPackageName());
        return inflater.inflate(a, container, false);
    }


}
