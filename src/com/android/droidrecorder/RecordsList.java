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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class RecordsList extends Activity {
	
	final String SOUNDS_PATH = "Sounds_Path";
	
	private List<String> records_name = new ArrayList<String>(); 
	private List<String> records_info = new ArrayList<String>(); 
	private List<String> records_root = new ArrayList<String>();
	private List<String> records_id = new ArrayList<String>();
	
	int counter;
	
	protected static final String assetManager = null;
	// Specify the query condition
	String selection = MediaStore.Audio.Media.DATA + " like ?";

	// Set the query directory
	String path = "/mnt/sdcard/";
	
	ListView listView;
	RecordAdapter adapter;
	
	ImageButton btn_previous;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.records_listview_layout);
        
		listView = (ListView) findViewById(R.id.recordlist);

		searchWithPath(path);
		listView.setOnItemClickListener(new ListItemClickListener());
		
		listView.setDividerHeight(1);//set the height of the divider line 
		
		btn_previous = (ImageButton)findViewById(R.id.header_back_btn);
		btn_previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RecordsList.this.finish();
			}
		});
    }

    private void searchWithPath(String path) {
		/**
		 * Load audio files from the specified folder
		 */
		String[] projection = new String[] { MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE };
		
		Cursor cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				MediaStore.Audio.Media.DATA + " like '" + path + "%'", null,
				null);

		counter = cursor.getCount();

		records_info.clear();
		records_name.clear();
		records_root.clear();
		records_id.clear();
		
		cursor.moveToFirst();

		for (int j = 0; j < counter; j++) {
			
			String id_t = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
			records_id.add(id_t);

			String nameString = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
			records_name.add(nameString);

			String root_url =  cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
			records_root.add(root_url);
			
//			String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
			int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//			long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

			records_info.add(album+"\n"+new SimpleDateFormat("mm:ss").format(duration));
			//			int dot = root_path.lastIndexOf("/");
//			records_name.add(root_path.substring(dot+1));
//			File f = new File(root_url);
//			records_info.add(((new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(f.lastModified())));
//			records_name.add(f.getName());
			
			cursor.moveToNext();
            adapter = new RecordAdapter(RecordsList.this, records_name,records_info);

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
    
    class ListItemLongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			return false;
		}
    }
}
