/*
* Copyright (C) 2011 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.coolroy.widget;

import java.util.List;

import com.android.droidrecorder.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class RecordAdapter extends BaseAdapter{
	
	private Context cxt=null; 
	Boolean flag = true;
	Holder holder = null; 

	 private List<String> title;
	 private List<String> infos;
	 
	 public RecordAdapter(Context context) {
		// TODO Auto-generated constructor stub
		cxt=context;
	}

	 public RecordAdapter(Context context, List<String> title ,List<String> infos) {
			// TODO Auto-generated constructor stub
			cxt=context;
			this.title = title;
			this.infos = infos;
	}
	
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return title.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// TODO Auto-generated method stub
		if (convertView==null) {
			holder=new Holder();
			LayoutInflater inflater=LayoutInflater.from(cxt);
			convertView=inflater.inflate(R.layout.history_listview, null);
			
			holder.record_name=(TextView) convertView.findViewById(R.id.record_name);
			holder.record_info=(TextView) convertView.findViewById(R.id.record_info);
			holder.type_image=(ImageView) convertView.findViewById(R.id.type_image);
			
			convertView.setTag(holder);
		}
		holder = (Holder) convertView.getTag();
		holder.record_name.setText(title.get(position));
		holder.record_info.setText(infos.get(position));
		
		holder.type_image.setImageResource(R.drawable.ic_launcher_soundrecorder);
		
		return convertView;
	}
	
	static class Holder{
		ImageView type_image;
		TextView record_name,record_info;
	}
}