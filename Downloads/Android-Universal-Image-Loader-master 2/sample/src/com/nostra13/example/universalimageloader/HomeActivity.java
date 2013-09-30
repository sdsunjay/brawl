/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nostra13.example.universalimageloader;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.utils.L;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class HomeActivity extends BaseActivity {

	private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";
	//public static Map<String, ArrayList<Item>> map = new HashMap<String, ArrayList<String>>();
	public static Map<Integer, ArrayList<Item>> map = new HashMap<Integer, ArrayList<Item>>();
	public static final Map<Integer, String[]> URLS = new HashMap<Integer, String[]>();
	public static final Map<Integer, String[]> IDS = new HashMap<Integer, String[]>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);

		File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
		if (!testImageOnSdCard.exists()) {
			copyTestImageToSdCard(testImageOnSdCard);
		}
		
		createItems();
		
		Intent intent; 
		//Intent intent = new Intent(this, ImagePagerActivity.class);
		intent= new Intent(this, ImageGridActivity.class);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		imageLoader.stop();
		if(ImageGridActivity.number!=0)
		{
			ImageGridActivity.number--;
			Log.d("tag", "Number is: " + ImageGridActivity.number);
			ImageGridActivity.items = HomeActivity.map.get(ImageGridActivity.number);
			ImageGridActivity.tx.setText(Constants.HEADERS[ImageGridActivity.number]);
			ImageGridActivity.adapter.notifyDataSetChanged();
		}
		else
		{
			super.onBackPressed();
		}
			
	}

	private void createItems()
	{

		String[] tempUrls;
		String[] tempIds;
		
		helpCreateItems();
		
		//loop through all categories
		for(int i=0;i<6;i++)
		{
			ArrayList<Item> temp = new ArrayList<Item>();
			tempUrls=URLS.get(Integer.valueOf(i));
			tempIds=IDS.get(Integer.valueOf(i));
			//loop through each of the 13 items in each category
			for(int ii=0;ii<13;ii++)
			{
				//Item(String url, String id, int category)
				temp.add(new Item(tempUrls[ii],tempIds[ii],i));
				
			}
			map.put(Integer.valueOf(i), new ArrayList<Item>(temp));
		}
	}
	private void helpCreateItems()
	{
		//urls
		URLS.put(Integer.valueOf(0),Constants.dresses);
		URLS.put(Integer.valueOf(1),Constants.tops);
		URLS.put(Integer.valueOf(2),Constants.bottoms);
		URLS.put(Integer.valueOf(3),Constants.shorts);
		URLS.put(Integer.valueOf(4),Constants.outerwear);
		URLS.put(Integer.valueOf(5),Constants.shoes);
		//product ids
		IDS.put(Integer.valueOf(0),Constants.dressesIDs);
		IDS.put(Integer.valueOf(1),Constants.topsIDs);
		IDS.put(Integer.valueOf(2),Constants.bottomsIDs);
		IDS.put(Integer.valueOf(3),Constants.shortsIDs);
		IDS.put(Integer.valueOf(4),Constants.outerwearIDs);
		IDS.put(Integer.valueOf(5),Constants.shoesIDs);
		
	}
	private void copyTestImageToSdCard(final File testImageOnSdCard) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream is = getAssets().open(TEST_FILE_NAME);
					FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
					byte[] buffer = new byte[9216];
					int read;
					try {
						while ((read = is.read(buffer)) != -1) {
							fos.write(buffer, 0, read);
						}
					} finally {
						fos.flush();
						fos.close();
						is.close();
					}
				} catch (IOException e) {
					L.w("Can't copy test image onto SD card");
					System.exit(1);
				}
			}
		}).start();
	}
}