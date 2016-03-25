package com.nju.chemtrack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;




public class DangerActivity extends Activity {
	ImageView back,home;
	TextView dangername,dangercode,dangercont,dangertype,ghs,dangerclass,msgword,content;
	ImageView imageView;
	JSONObject danger;
	String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danger);
        String tmp = getIntent().getStringExtra("danger");
        if(tmp!=null){
        	try {
        		danger = new JSONObject(tmp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        name = getIntent().getStringExtra("name");
        initView();
    }
    
    private void initView() {
    	back = (ImageView) findViewById(R.id.back);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DangerActivity.this.finish();
			}
		});
    	
    	home = (ImageView) findViewById(R.id.home);
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DangerActivity.this.finish();
			}
		});
    	dangername = (TextView) findViewById(R.id.dangername);
    	dangercode = (TextView) findViewById(R.id.dangercode);
    	dangercont = (TextView) findViewById(R.id.dangercont);
    	dangertype = (TextView) findViewById(R.id.dangertype);
    	ghs = (TextView)findViewById(R.id.ghs);
    	dangerclass = (TextView)findViewById(R.id.dangerclass);
    	msgword = (TextView)findViewById(R.id.msgword);
    	content = (TextView)findViewById(R.id.content);
    	imageView = (ImageView) findViewById(R.id.dangerimg);
    	try {
	    	if(danger!=null){
		    	String pic;
				pic = danger.getString("pic");
		    	if(pic.equals("explos")){
					imageView.setImageResource(R.drawable.explos);
				}
				else if(pic.equals("flamme")){
					imageView.setImageResource(R.drawable.flamme);
				} 
				else if(pic.equals("rondflam")){
					imageView.setImageResource(R.drawable.rondflam);				
				} 
				else if(pic.equals("acid")){
					imageView.setImageResource(R.drawable.acid);
				} 
				else if(pic.equals("bottle")){
					imageView.setImageResource(R.drawable.bottle);
				} 
				else if(pic.equals("skull")){
					imageView.setImageResource(R.drawable.skull);				
				} 
				else if(pic.equals("health")){
					imageView.setImageResource(R.drawable.health);
				} 
				else if(pic.equals("exclam")){
					imageView.setImageResource(R.drawable.exclam);
				} 
				else if(pic.equals("environment")){
					imageView.setImageResource(R.drawable.environment);
				} else{
					imageView.setVisibility(View.GONE);
				}
		    	dangername.setText(name);
		    	dangercode.setText(danger.getString("dangercode"));
		    	dangercont.setText(danger.getString("dangercont"));
		    	dangertype.setText(danger.getString("dangertype"));
		    	ghs.setText(danger.getString("ghs"));
		    	dangerclass.setText(danger.getString("dangerclass"));
		    	msgword.setText(danger.getString("msgword"));
		    	content.setText(danger.getString("content"));
	    	}
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
}
