package com.nju.chemtrack;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nju.chemtrack.SearchNameActivity.MyAdapter;
import com.nju.chemtrack.SearchNameActivity.ViewHolder;

import android.R.array;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;




public class GHSClassActivity extends Activity {
	ImageView back,home;
	ListView phylist,healist,envlist;
	JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ghsclass);
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
				GHSClassActivity.this.finish();
			}
		});
    	
    	home = (ImageView) findViewById(R.id.home);
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				GHSClassActivity.this.finish();
			}
		});	
    	
    	ArrayList<String> tmp;
    	JSONArray tmplist;
    	try {
	    	if(jsonObject!=null){
	    		//物理危害
	    		tmp = new ArrayList<String>();
		    	phylist = (ListView) findViewById(R.id.phylist);
		    	tmplist  = jsonObject.getJSONArray("physicals");
				for(int i=0;i<tmplist.length();i++){
					tmp.add(tmplist.getJSONObject(i).getString("dangercont"));
				}
				MyAdapter adapter;
				if(tmp.size() == 0){
					tmp.add("无");
					adapter = new MyAdapter(GHSClassActivity.this,tmp);					
			    	phylist.setAdapter(adapter);
				}else{
					adapter = new MyAdapter(GHSClassActivity.this,tmp);
					phylist.setAdapter(adapter);
			    	phylist.setOnItemClickListener(new OnItemClickListener() {	
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Intent intent = new Intent();
							try {
								intent.putExtra("danger", jsonObject.getJSONArray("physicals").getJSONObject(arg2).toString());
								intent.putExtra("name", "物理危害");
								intent.setClass(GHSClassActivity.this, DangerActivity.class);
								startActivity(intent);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
		    	//健康危害
		    	tmp = new ArrayList<String>();
		    	healist = (ListView) findViewById(R.id.healist);
		    	tmplist  = jsonObject.getJSONArray("healths");
				for(int i=0;i<tmplist.length();i++){
					tmp.add(tmplist.getJSONObject(i).getString("dangercont"));
				}
				MyAdapter adapter2;
				if(tmp.size() == 0){
					tmp.add("无");
					adapter2 = new MyAdapter(GHSClassActivity.this,tmp);
					healist.setAdapter(adapter2);
				}else{
					adapter2 = new MyAdapter(GHSClassActivity.this,tmp);
					healist.setAdapter(adapter2);
			    	healist.setOnItemClickListener(new OnItemClickListener() {
	
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Intent intent = new Intent();
							try {
								intent.putExtra("danger", jsonObject.getJSONArray("healths").getJSONObject(arg2).toString());
								intent.putExtra("name", "健康危害");
								intent.setClass(GHSClassActivity.this, DangerActivity.class);
								startActivity(intent);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
		    	//环境危害
		    	tmp = new ArrayList<String>();
		    	envlist = (ListView) findViewById(R.id.envlist);
		    	tmplist  = jsonObject.getJSONArray("environments");
				for(int i=0;i<tmplist.length();i++){
					tmp.add(tmplist.getJSONObject(i).getString("dangercont"));
				}
				MyAdapter adapter3;
				if(tmp.size() == 0){
					tmp.add("无");
					adapter3 = new MyAdapter(GHSClassActivity.this,tmp);
			    	envlist.setAdapter(adapter3);
				}else{
					adapter3 = new MyAdapter(GHSClassActivity.this,tmp);
			    	envlist.setAdapter(adapter3);
			    	envlist.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Intent intent = new Intent();
							try {
								intent.putExtra("danger", jsonObject.getJSONArray("environments").getJSONObject(arg2).toString());
								intent.putExtra("name", "环境危害");
								intent.setClass(GHSClassActivity.this, DangerActivity.class);
								startActivity(intent);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}    	
	    	}
    	} catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}
   
    
    public final class ViewHolder{
        public TextView dangercont;
    }
     
     
    public class MyAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
        private ArrayList<String> mData;
         
        public MyAdapter(Context context,ArrayList<String> mData){
            this.mInflater = LayoutInflater.from(context);
            this.mData = mData;
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
                 
                convertView = mInflater.inflate(R.layout.dangeritem, null);
                holder.dangercont = (TextView)convertView.findViewById(R.id.dangercont);
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ViewHolder)convertView.getTag();
            }
            holder.dangercont.setText((String)mData.get(position));          
            return convertView;
        }
 
    }
}
