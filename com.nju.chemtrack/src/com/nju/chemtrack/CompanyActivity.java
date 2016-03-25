package com.nju.chemtrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nju.chemtrack.FragmentHist.MyAdapter;
import com.nju.chemtrack.FragmentHist.ViewHolder;

import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;




public class CompanyActivity extends Activity {
	ImageView back,home;
	private List<Map<String, String>> mData;
	private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companylist);
        initView();
    }
    
    private void initView() {
    	back = (ImageView) findViewById(R.id.back);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CompanyActivity.this.finish();
			}
		});
    	
    	home = (ImageView) findViewById(R.id.home);
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CompanyActivity.this.finish();
			}
		});
    	
    	listView = (ListView)findViewById(R.id.complist);
    	mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    	
	}
    
    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
 
        Map<String, String> map = new HashMap<String, String>();
        map.put("companyname", "昆山氰化工贸易有限公司");
        map.put("num", "8000");
        map.put("addr", "江苏昆山经济技术开发区中央街101号");
        map.put("web", "www.qinghua.com");
        map.put("name", "马振邦");
        map.put("phone", "0512-57301234");
        list.add(map);
 
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("companyname", "卡诺尔医药科技发展有限公司");
        map2.put("num", "1600");
        map2.put("addr", "中国南京六合区新华路78号");
        map2.put("web", "www.kano.com.cn");
        map2.put("name", "宋志雄");
        map2.put("phone", "025-86929290");
        list.add(map2);
         
        return list;
    }
    
    
    
    public final class ViewHolder{
        public TextView companyname;
        public TextView num;
        public TextView addr;
        public TextView web;
        public TextView name; 
        public TextView phone;
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
                 
                convertView = mInflater.inflate(R.layout.companyitem, null);
                holder.companyname = (TextView)convertView.findViewById(R.id.companyname);
                holder.num = (TextView)convertView.findViewById(R.id.num);
                holder.addr = (TextView)convertView.findViewById(R.id.addr);
                holder.name = (TextView)convertView.findViewById(R.id.name);
                holder.web = (TextView)convertView.findViewById(R.id.web);
                holder.phone = (TextView)convertView.findViewById(R.id.phone);
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ViewHolder)convertView.getTag();
            }
             
             
            holder.companyname.setText((String)mData.get(position).get("companyname"));
            holder.num.setText((String)mData.get(position).get("companyname"));
            holder.addr.setText((String)mData.get(position).get("addr"));
            holder.name.setText((String)mData.get(position).get("name"));
            holder.web.setText((String)mData.get(position).get("web"));
            holder.phone.setText((String)mData.get(position).get("phone"));
                    
            return convertView;
        }
 
    }
   
}
