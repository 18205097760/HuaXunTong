package com.nju.chemtrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nju.chemtrack.FragmentHist.MyAdapter;
import com.nju.chemtrack.FragmentHist.ViewHolder;

import Api.HttpClientApi;
import Api.SQLiteApi;
import android.R.integer;
import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchCasActivity extends Activity {
	ImageView back,home;
	ListView listView;
	String cas;
    List<Map<String, String>> mData = null;
    Button searchButton;
    EditText editText;
    JSONObject jsonObject;
    JSONObject jsonObject2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        initView();
    }
    
    private void initView() {
    	back = (ImageView) findViewById(R.id.back);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SearchCasActivity.this.finish();
			}
		});
    	
    	home = (ImageView) findViewById(R.id.home);
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SearchCasActivity.this.finish();
			}
		});
    	
    	editText = (EditText) findViewById(R.id.edit);
    	searchButton = (Button) findViewById(R.id.searchcommit);
    	searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				cas =editText.getText().toString();
				new Thread(new Mythread()).start();
			}
		});
    	
    	listView = (ListView) findViewById(R.id.list);
    }
    
    Handler myHandler = new Handler() {  
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 1:   
					try {
						if(jsonObject!=null && jsonObject.getInt("key") == 1){
							JSONArray jsonArray = jsonObject.getJSONArray("List");
							mData = new ArrayList<Map<String, String>>();
							for(int i=0;i<jsonArray.length();i++){
								 Map<String, String> map = new HashMap<String, String>();
						        map.put("title", jsonArray.getJSONObject(i).getString("name"));
						        map.put("cas", "CAS±‡∫≈£∫"+jsonArray.getJSONObject(i).getString("cas"));
						        mData.add(map);
							}
							MyAdapter adapter = new MyAdapter(SearchCasActivity.this);
					        listView.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
										long arg3) {
									new Thread(new Mythreadmsg(arg2)).start();
								} 	
							});
					        listView.setAdapter(adapter);
					        if(jsonArray.length() == 0){
					        	Toast.makeText(getApplicationContext(), "≤È—ØΩ·π˚Œ™ø’",
					        			Toast.LENGTH_SHORT).show();
					        }
		         		}else{
		         			Toast.makeText(getApplicationContext(), "≤È—Ø ß∞‹",
		         					Toast.LENGTH_SHORT).show();
		         		}
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "≤È—Ø ß∞‹",
	         					Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
	               break;  
                  case 2:
					try {
						if(jsonObject2!=null && jsonObject2.getInt("key") == 1){
			         			Intent intent = new Intent();
				         		intent.putExtra("chem", jsonObject2.getJSONObject("chemBasic").toString());
				         		intent.setClass(SearchCasActivity.this, OneChemActivity.class);
				         		startActivity(intent);
			         		}else{
			         			Toast.makeText(getApplicationContext(), "≤È—Ø ß∞‹",
			         					Toast.LENGTH_SHORT).show();
			         		}
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "≤È—Ø ß∞‹",
	         					Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
                	break;
                  default:
            		Toast.makeText(getApplicationContext(), "≤È—Ø ß∞‹",
         					Toast.LENGTH_SHORT).show();
                	break;
             }   
             super.handleMessage(msg);   
        }   
   };  
    
    class Mythread implements Runnable{

		@Override
		public void run() {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("cas", cas);
			//jsonObject = HttpClientApi.getJsonObject("GetList", map);
			jsonObject = SQLiteApi.getJsonObject("GetList", map, SearchCasActivity.this);
			Message message = new Message();   
	        message.what = 1;      
	        myHandler.sendMessage(message);   
		}   
   }
    
    class Mythreadmsg implements Runnable{
    	int num;
    	Mythreadmsg(int t){
    		num = t;
    	}
		@Override
		public void run() {
			HashMap<String, String> map = new HashMap<String, String>();
			try {
				map.put("cas", jsonObject.getJSONArray("List").getJSONObject(num).getString("cas"));
				//jsonObject2 = HttpClientApi.getJsonObject("GetBasic", map);
				jsonObject2 = SQLiteApi.getJsonObject("GetBasic", map,SearchCasActivity.this);
				Message message = new Message();   
				message.what = 2;      
				myHandler.sendMessage(message);   
			} catch (JSONException e) {
				Message message = new Message();   
		        message.what = 5;      
		        myHandler.sendMessage(message);   
				e.printStackTrace();
			}
		}   
   }
    
    public final class ViewHolder{
        public TextView title;
        public TextView cas;
    }
     
     
    public class MyAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
         
        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }
 
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }
 
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             
            ViewHolder holder = null;
            if (convertView == null) {
                 
                holder=new ViewHolder();  
                 
                convertView = mInflater.inflate(R.layout.historyitem, null);
                holder.title = (TextView)convertView.findViewById(R.id.text1);
                holder.cas = (TextView)convertView.findViewById(R.id.text2);
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ViewHolder)convertView.getTag();
            }
             
             
            holder.title.setText((String)mData.get(position).get("title"));
            holder.cas.setText((String)mData.get(position).get("cas"));
                    
            return convertView;
        }
 
    }
   
}
