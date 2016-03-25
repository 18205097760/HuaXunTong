package com.nju.chemtrack;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes.Name;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nju.chemtrack.SearchCasActivity.MyAdapter;
import com.nju.chemtrack.SearchCasActivity.Mythreadmsg;

import Api.HttpClientApi;
import Api.SQLiteApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;




public class UploadActivity extends Activity {
	ImageView back,home;
	JSONObject jsonObject = null;
	Spinner spinner; 
	TextView name,submit;
	EditText info,num;
	String nowstate,chemid;
	JSONObject result;
	private ArrayAdapter<String> adapter;
	private static final String[] m={"生产","储存","运输","经营","使用","废处"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
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
				UploadActivity.this.finish();
			}
		});
    	
    	home = (ImageView) findViewById(R.id.home);
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				UploadActivity.this.finish();
			}
		});
    	
    	spinner = (Spinner) findViewById(R.id.state);
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    	spinner.setVisibility(View.VISIBLE);
    	
    	name = (TextView) findViewById(R.id.name);
    	if(jsonObject!=null){
    		try {
				name.setText(jsonObject.getString("name"));
				chemid = jsonObject.getString("id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	info = (EditText) findViewById(R.id.info);
    	num = (EditText) findViewById(R.id.num);
    	submit = (TextView) findViewById(R.id.submit);
    	
    	submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new Thread(new Mythread()).start();
			}
		});
	}
    
    Handler myHandler = new Handler() {  
        public void handleMessage(Message msg) {   
           if(msg.what==1){
        	   try {
				if(result.has("key") && result.getInt("key") == 1){
					   Toast.makeText(getApplicationContext(), "上传成功",
								Toast.LENGTH_SHORT).show();
				   		UploadActivity.this.finish();
				   }else{
					   Toast.makeText(getApplicationContext(), "上传失败",
							Toast.LENGTH_SHORT).show();
				   }
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "上传失败",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
           }
           super.handleMessage(msg);   
        }   
   };  
    
    class Mythread implements Runnable{

		@Override
		public void run() {
			HashMap<String,String> map = new HashMap<String, String>();
			try {
				map.put("name", URLDecoder.decode(name.getText().toString(),"utf-8"));
				map.put("chemid", chemid);
				map.put("state", nowstate);
				map.put("info", URLDecoder.decode(info.getEditableText().toString(),"utf-8"));
				map.put("num",  URLDecoder.decode(num.getEditableText().toString(),"utf-8"));
				result  = HttpClientApi.getJsonObject("AddState", map);
				Message message = new Message();   
				message.what = 1;      
				myHandler.sendMessage(message);   
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}   
   }
    
    //使用数组形式操作
    class SpinnerSelectedListener implements OnItemSelectedListener{
 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
        	nowstate = m[arg2];
        }
 
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
   
}
