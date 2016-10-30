package com.example.ngupta1.parksafe;

/**
 * Created by ngupta1 on 8/10/16.
 */
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;




public abstract class Baseactivity extends AppCompatActivity {

    public FrameLayout container;
    public android.support.v7.widget.Toolbar toolbar;
    public RelativeLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        container = (FrameLayout) findViewById(R.id.container);
        mainlayout = (RelativeLayout) findViewById(R.id.fulllayout);

        //Set the custom toolbar
//        if (toolbar != null)
//        {
//            setSupportActionBar(toolbar);
//            toolbar.setTitle(R.string.app_name);
//            toolbar.setTitleTextColor(Color.WHITE);
//            //toolbar.setLogo(R.mipmap.ic_launcher);
//        }
    }

    public void setToolbarSubTittle(String header)
    {
        toolbar.setSubtitle(header);
    }

    public void setToolbarElevation(float value)
    {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(value);
        }
    }

    // Method to set xml object reference.
    public abstract void setReference();
}