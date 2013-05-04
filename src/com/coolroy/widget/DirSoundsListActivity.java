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

import java.util.ArrayList;
import java.util.List;

import com.android.droidrecorder.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DirSoundsListActivity extends Activity {

	final String SOUNDS_PATH = "Sounds_Path";
	
//	private ArrayList<String> s_path;
	
	private List<String> records_name = new ArrayList<String>(); 
	private List<String> records_info = new ArrayList<String>(); 
	private List<String> records_root = new ArrayList<String>(); 
	int counter;
	
	protected static final String assetManager = null;

	String selection = MediaStore.Audio.Media.DATA + " like ?";

	String path = "/mnt/sdcard/Sound_Records";
	ListView listView;
	Button btn_return;
	Button btn_reflesh;
	RecordAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = getIntent();
//        s_path = intent.getStringArrayListExtra(SOUNDS_PATH);

        setContentView(R.layout.soundslist_layout);
        
		listView = (ListView) findViewById(R.id.list);
		btn_return = (Button) findViewById(R.id.record_return);
		btn_reflesh=(Button)findViewById(R.id.record_reflesh);


		btn_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DirSoundsListActivity.this.finish();
			}
		});
		btn_reflesh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		searchWithPath(path);
		listView.setOnItemClickListener(new ListItemClickListener());
    }
	
	
	private void searchWithPath(String path) {

		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE };
		
		Cursor cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				MediaStore.Audio.Media.DATA + " like '" + path + "%'", null,
				null);

		counter = cursor.getCount();

		records_info.clear();
		records_name.clear();
		records_root.clear();
		
		cursor.moveToFirst();

		for (int j = 0; j < counter; j++) {
			String nameString = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
			records_info.add(nameString);


			String root_path =  cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
			records_root.add(root_path);
			
			int dot = root_path.lastIndexOf("/");
			records_name.add(root_path.substring(dot+1));
			
			cursor.moveToNext();
            adapter = new RecordAdapter(DirSoundsListActivity.this, records_name,records_info);

			listView.setAdapter(adapter);
		}
	}
	
    class ListItemClickListener implements OnItemClickListener{
    	@Override
    	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

			Intent it = new Intent(Intent.ACTION_VIEW);
			it.setDataAndType(Uri.parse("file://" + records_root.get(position)),
					"audio/*");
			startActivity(it);
    	}
    }
}
