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




public class RefActivity extends Activity {
	ImageView back,home;
	LinearLayout hea1Layout,env1Layout;
	JSONObject jsonObject;
	TextView refcont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ref);
        String tmp = getIntent().getStringExtra("chem");
        if(tmp!=null){
        	try {
				jsonObject = new JSONObject(tmp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        initView();
    }
    
    private void initView() {
    	back = (ImageView) findViewById(R.id.back);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				RefActivity.this.finish();
			}
		});
    	
    	home = (ImageView) findViewById(R.id.home);
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				RefActivity.this.finish();
			}
		});
    	
    	refcont = (TextView) findViewById(R.id.refcont);
    	try {
			refcont.setText(jsonObject.getString("ref"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
   
}
