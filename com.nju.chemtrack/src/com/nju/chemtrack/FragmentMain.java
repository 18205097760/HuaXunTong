package com.nju.chemtrack;

import com.zxing.activity.CaptureActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentMain extends Fragment{
	LinearLayout search1,search2,search3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
 
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
    	View view = inflater.inflate(R.layout.main, container, false);
    	search1 = (LinearLayout) view.findViewById(R.id.Search1);
    	search1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
//				Intent intent = new Intent();
//				intent.setClass(getActivity(), OneChemActivity.class);
//				startActivity(intent);
				Intent openCameraIntent = new Intent(getActivity(),CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
    	
    	search2 = (LinearLayout) view.findViewById(R.id.Search2);
    	search2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SearchNameActivity.class);
				startActivity(intent);
			}
		});
    	
    	search3 = (LinearLayout) view.findViewById(R.id.Search3);
    	search3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SearchCasActivity.class);
				startActivity(intent);
			}
		});
        return view;  
    }  
 
}