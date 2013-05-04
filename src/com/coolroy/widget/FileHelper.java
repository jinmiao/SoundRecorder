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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class FileHelper {
	/**the root file*/
	private File parent;
	
	/**root file path*/
	private String parent_path = Environment.getExternalStorageDirectory() + "/Sound_Records";
	
	/**image files path under the root file path*/
	private List<String> sounds_path;
	
	/**initialize function with the special file path
	 * 
	 * @param parent_path
	 * */
	public FileHelper(String ppath){
		parent_path = ppath;
		parent = new File(ppath);
		sounds_path = new ArrayList<String>();
		getSoundsUnderParentPath(parent, 0);
	}
	
	/**initialize with the default path*/
	public FileHelper(){
		parent = new File(parent_path);
		sounds_path = new ArrayList<String>();
		getSoundsUnderParentPath(parent, 0);
	}
	
	/**initialize as the special file*/
	public FileHelper(File f){
		parent = f;
		sounds_path = new ArrayList<String>();
		getSoundsUnderParentPath(parent, 0);
	}
	
	/**get Sounds Path*/
	public List<String> getSoundsPathList(){
		return sounds_path;
	}
	
	/**find images of .3gpp .amr .mp3
	 * 
	 * root as the file path
	 * layer count for the sub file
	 * @param root 
	 * @param layer
	 */
	private void getSoundsUnderParentPath(File root,int layer){
		if(layer>2)
			return;
		final int this_layer = layer+1;
		
		File[] files = root.listFiles();
		if (files == null) {
			return ;
		}
		for(int i = 0; i < files.length; i++) {
			final File f = files[i];
			if(f.isFile()) {
				try{
					int idx = f.getPath().lastIndexOf(".");
					if (idx <= 0) {
						continue;
					}
					String suffix = f.getPath().substring(idx);
					if (suffix.toLowerCase().equals(".3gpp") ||
							suffix.toLowerCase().equals(".mp3")||
							suffix.toLowerCase().equals(".amr") )
					{
						sounds_path.add(f.getPath());
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}else{
				getSoundsUnderParentPath(f,this_layer);
			}
		}
	}
}
