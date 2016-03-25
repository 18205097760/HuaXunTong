package com.nju.chemtrack;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.nju.chemtrack.R.color;

import Api.HttpClientApi;
import Api.SQLiteApi;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends FragmentActivity implements OnClickListener{
	Fragment mainFragment,histFragment,aboutFragment;
	TextView search,hist,about,title;
	JSONObject jsonObject;
	String scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    
    private void initView() {
    	 mainFragment = new FragmentMain();
    	 histFragment = new FragmentHist();
    	 aboutFragment = new FragmentAbout();
         getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();
         
         search = (TextView) findViewById(R.id.search);
         hist = (TextView) findViewById(R.id.hist);
         about = (TextView) findViewById(R.id.about);
         title = (TextView) findViewById(R.id.title);
         
         search.setOnClickListener(this);
         hist.setOnClickListener(this);
         about.setOnClickListener(this);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();     
            scanResult = bundle.getString("result");
            Log.d("scan", scanResult);
            new Thread(new Mythread()).start();
        }
    }
    Handler myHandler = new Handler() {  
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 1:   
					try {
						if(jsonObject!=null && jsonObject.getInt("key") == 1){
		         				Toast.makeText(getApplicationContext(), "查询成功",
			         					Toast.LENGTH_SHORT).show();
			         			Intent intent = new Intent();
				         		intent.putExtra("chem", jsonObject.getJSONObject("chemBasic").toString());
				         		intent.setClass(MainActivity.this, OneChemActivity.class);
				         		startActivity(intent);
		         		}else{
		         			Toast.makeText(getApplicationContext(), "查询失败",
		         					Toast.LENGTH_SHORT).show();
		         		}
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "查询失败",
	         					Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
	               break;   
             }   
             super.handleMessage(msg);   
        }   
   };  
   
   class Mythread implements Runnable{

		@Override
		public void run() {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("identification", scanResult);
			//jsonObject = HttpClientApi.getJsonObject("GetBasic", map);
			jsonObject = SQLiteApi.getJsonObject("GetBasic", map,MainActivity.this);
			Message message = new Message();   
	        message.what = 1;      
	        myHandler.sendMessage(message);   
		}   
   }
   

   
   
    
	@SuppressLint("NewApi") @Override
	public void onClick(View v) {
		Drawable drawable = getResources().getDrawable(R.drawable.btndown);
		switch (v.getId()) {
		case R.id.search:
			search.setBackground(drawable);
			hist.setBackground(null);
			about.setBackground(null);
			title.setText("化讯通");
			getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();
			break;
		case R.id.hist:
			search.setBackground(null);
			hist.setBackground(drawable);
			about.setBackground(null);
			title.setText("历史记录");
			getSupportFragmentManager().beginTransaction().replace(R.id.main_content, histFragment).commit();
			break;
		case R.id.about:
			search.setBackground(null);
			hist.setBackground(null);
			title.setText("关于");
			about.setBackground(drawable);
			getSupportFragmentManager().beginTransaction().replace(R.id.main_content, aboutFragment).commit();
			break;
		default:
			break;
		}
		
	}

}
