package com.nju.chemtrack;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;




public class OneChemActivity extends Activity {
	ImageView back;
	LinearLayout ghslayout;
	LinearLayout measure;
	LinearLayout company;
	LinearLayout ref;
	LinearLayout fanghu;
	LinearLayout jijiu;
	TextView upload;
	JSONObject jsonObject = null;
	TextView name,othername,casNum,mol,molNum,msgword;
	ImageView[] imageView = new ImageView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onechem);
        String tmp = getIntent().getStringExtra("chem");
        if(tmp!=null){
        	try {
				jsonObject = new JSONObject(tmp);
				SQLiteDatabase db = openOrCreateDatabase("hist.db", Context.MODE_PRIVATE, null);  
				db.execSQL("CREATE TABLE if not exists chemname (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, cas VARCHAR)");
				db.execSQL("INSERT INTO chemname VALUES (NULL, ?, ?)", 
						new Object[]{jsonObject.getString("name"), jsonObject.getString("cas")}); 
				db.close();
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
				OneChemActivity.this.finish();
			}
		});
    	
    	upload = (TextView) findViewById(R.id.upload);
    	upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("chem", jsonObject.toString());
				intent.setClass(OneChemActivity.this, UploadActivity.class);
				startActivity(intent);
			}
		});
    	
    	ghslayout = (LinearLayout) findViewById(R.id.GHSclass);
    	ghslayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("chem", jsonObject.toString());
				intent.setClass(OneChemActivity.this, GHSClassActivity.class);
				startActivity(intent);
			}
		});
    	
    	measure = (LinearLayout) findViewById(R.id.measure);
    	measure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OneChemActivity.this, MeasureActivity.class);
				startActivity(intent);
			}
		});
    	
    	ref = (LinearLayout) findViewById(R.id.ref);
    	ref.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("chem", jsonObject.toString());
				intent.setClass(OneChemActivity.this, RefActivity.class);
				startActivity(intent);
			}
		});
    	
    	jijiu = (LinearLayout) findViewById(R.id.jijiu);
    	jijiu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OneChemActivity.this, Do2Activity.class);
				startActivity(intent);
			}
		});
    	
    	fanghu = (LinearLayout) findViewById(R.id.fanghu);
    	fanghu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OneChemActivity.this, Do1Activity.class);
				startActivity(intent);
			}
		});
    	
    	company = (LinearLayout) findViewById(R.id.company);
    	company.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OneChemActivity.this, CompanyActivity.class);
				startActivity(intent);
			}
		});
    	
    	name = (TextView) findViewById(R.id.name);
    	othername = (TextView) findViewById(R.id.otherName);
    	casNum = (TextView)findViewById(R.id.casNum);
    	mol = (TextView)findViewById(R.id.mol);
    	molNum = (TextView)findViewById(R.id.molNum);
    	msgword = (TextView) findViewById(R.id.msgword);
    	if(jsonObject != null){
    		try {
				name.setText(jsonObject.getString("name"));
				othername.setText(jsonObject.getString("othername"));
				casNum.setText(jsonObject.getString("cas"));
				mol.setText(jsonObject.getString("molfor"));
				molNum.setText(jsonObject.getString("molwei"));
				msgword.setText(jsonObject.getString("msgword"));
		
    	
		    	imageView[0] = (ImageView) findViewById(R.id.imgd1);
		    	imageView[1] = (ImageView) findViewById(R.id.imgd2);
		    	imageView[2] = (ImageView) findViewById(R.id.imgd3);
		    	imageView[3] = (ImageView) findViewById(R.id.imgd4);
		    	imageView[4] = (ImageView) findViewById(R.id.imgd5);
		    	
		    	String[] pics;
				pics = jsonObject.getString("pics").split(";");
				for(int i=0;i<pics.length && i<5;i++){
					imageView[i].setVisibility(View.VISIBLE);
					if(pics[i].equals("explos")){
						imageView[i].setImageResource(R.drawable.explos);
					}
					else if(pics[i].equals("flamme")){
						imageView[i].setImageResource(R.drawable.flamme);
					} 
					else if(pics[i].equals("rondflam")){
						imageView[i].setImageResource(R.drawable.rondflam);				
					} 
					else if(pics[i].equals("acid")){
						imageView[i].setImageResource(R.drawable.acid);
					} 
					else if(pics[i].equals("bottle")){
						imageView[i].setImageResource(R.drawable.bottle);
					} 
					else if(pics[i].equals("skull")){
						imageView[i].setImageResource(R.drawable.skull);				
					} 
					else if(pics[i].equals("health")){
						imageView[i].setImageResource(R.drawable.health);
					} 
					else if(pics[i].equals("exclam")){
						imageView[i].setImageResource(R.drawable.exclam);
					} 
					else if(pics[i].equals("environment")){
						imageView[i].setImageResource(R.drawable.environment);
					} 
				}
    		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	}
	}
   
}
