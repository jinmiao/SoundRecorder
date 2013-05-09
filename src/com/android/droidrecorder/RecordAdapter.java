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

package com.android.droidrecorder;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecordAdapter extends BaseAdapter {

	private Context cxt = null;
	Boolean flag = true;
	Holder holder = null;
		
	//data source lists
	private List<String> title;
	private List<String> infos;
	
	public RecordAdapter(Context ct){
		cxt =ct;
	}
	
	public RecordAdapter(Context context, List<String>title, List<String>infos){
		this.cxt = context;
		this.title = title;
		this.infos = infos;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return title.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			holder = new Holder();
			LayoutInflater inflater = LayoutInflater.from(cxt);
			//use xml file to initiate the items' outlook
			convertView = inflater.inflate(R.layout.records_listview_item, null);

			holder.record_name = (TextView)convertView.findViewById(R.id.record_name);
			holder.record_info = (TextView)convertView.findViewById(R.id.record_info);
			
			convertView.setTag(holder);
		}
		holder = (Holder) convertView.getTag();
		
		holder.record_name.setText(title.get(position));
		holder.record_info.setText(infos.get(position));
		
		return convertView;
	}
	
	static class Holder{
		TextView record_name;
		TextView record_info;
	}
}
