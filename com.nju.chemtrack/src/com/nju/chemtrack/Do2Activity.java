package com.nju.chemtrack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;




public class Do2Activity extends Activity {
	ImageView back,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dothing2);
        initView();
    }
    
    private void initView() {
    	back = (ImageView) findViewById(R.id.back);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Do2Activity.this.finish();
			}
		});
    	
    	home = (ImageView) findViewById(R.id.home);
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Do2Activity.this.finish();
			}
		});

	}
   
}
