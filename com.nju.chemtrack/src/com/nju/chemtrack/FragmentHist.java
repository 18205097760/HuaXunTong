package com.nju.chemtrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.nju.chemtrack.MainActivity.Mythread;
import com.zxing.activity.CaptureActivity;

import Api.HttpClientApi;
import Api.SQLiteApi;
import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentHist extends Fragment{
	private List<Map<String, String>> mData;
	private ListView listView;
	private TextView clear;
	JSONObject jsonObject;
	String cas;
	MyAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
 
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
    	View view = inflater.inflate(R.layout.historylist, container, false);
    	listView = (ListView) view.findViewById(R.id.histlist);
    	clear = (TextView) view.findViewById(R.id.clear);
    	
    	mData = getData();
        adapter = new MyAdapter(getActivity());
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				cas = mData.get(arg2).get("cas");
				new Thread(new Mythread()).start();
			}
        	
        	
		});
        listView.setAdapter(adapter);
        
        clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(getActivity()).setTitle("确认清空吗？")  
			    .setIcon(android.R.drawable.ic_dialog_info)  
			    .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			  
			        @Override  
			        public void onClick(DialogInterface dialog, int which) {  			
			        	 SQLiteDatabase db = getActivity().openOrCreateDatabase("hist.db", Context.MODE_PRIVATE, null);
			        	 db.execSQL("DELETE FROM chemname");
			        	 db.close();
			        	 mData = getData();
			        	 adapter.notifyDataSetChanged();
			        }  
			    })  
			    .setNegativeButton("返回", new DialogInterface.OnClickListener() {  
			  
			        @Override  
			        public void onClick(DialogInterface dialog, int which) {  
			        }  
			    }).show();  
				
			}
		});
        return view;  
    }  
    
    
    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        
        SQLiteDatabase db = getActivity().openOrCreateDatabase("hist.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE if not exists chemname (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, cas VARCHAR)");
        
        Cursor c = db.rawQuery("SELECT * FROM chemname group by name order by _id desc ", null);  
        while (c.moveToNext()) {  
        	Map<String, String> one = new HashMap<String,String>();  
            one.put ("title",c.getString(c.getColumnIndex("name")));  
            one.put ("cas",c.getString(c.getColumnIndex("cas")));  
            list.add(one);
        }  
        c.close();
        Log.d("num", list.size()+"");
        db.close();
        return list;
    }
    
    Handler myHandler = new Handler() {  
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 1:   
					try {
						if(jsonObject!=null && jsonObject.getInt("key") == 1){
		         				Toast.makeText(getActivity().getApplicationContext(), "查询成功",
			         					Toast.LENGTH_SHORT).show();
			         			Intent intent = new Intent();
				         		intent.putExtra("chem", jsonObject.getJSONObject("chemBasic").toString());
				         		intent.setClass(getActivity(), OneChemActivity.class);
				         		startActivity(intent);
		         		}else{
		         			Toast.makeText(getActivity().getApplicationContext(), "查询失败",
		         					Toast.LENGTH_SHORT).show();
		         		}
					} catch (JSONException e) {
						Toast.makeText(getActivity().getApplicationContext(), "查询失败",
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
			map.put("cas", cas);
			//jsonObject = HttpClientApi.getJsonObject("GetBasic", map);
			jsonObject = SQLiteApi.getJsonObject("GetBasic", map,getActivity());
			Message message = new Message();   
	        message.what = 1;      
	        myHandler.sendMessage(message);   
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