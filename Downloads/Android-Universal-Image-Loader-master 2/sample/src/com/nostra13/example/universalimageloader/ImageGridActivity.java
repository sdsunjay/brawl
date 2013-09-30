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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.example.universalimageloader.Constants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImageGridActivity extends AbsListViewBaseActivity {

	// the number of the screeen
	public static Integer number;

	// each category
	public static ArrayList<Item> items;

	// does stuff
	public Intent intent;

	// the text at the top
	public static TextView tx;

	// adapter
	public static ImageAdapter adapter; 
	public DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);
	

		// initialize stuff
		setup();

		// for the image library stuff
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		createNextButton();
	}

	private void setup() {
		
		adapter = new ImageAdapter();
		// Bundle bundle = getIntent().getExtras();
		number = Integer.valueOf(0);
		items = HomeActivity.map.get(number);
		tx = (TextView) findViewById(R.id.textView1);
		Log.d("tag", "Number is: " + number);
		tx.setTextColor(Color.RED);
		tx.setText("Dress It Up!");

		listView = (GridView) findViewById(R.id.gridview);

		((GridView) listView).setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// view.setHovered(true);
				Log.d("tag", "clicked grid picture");
				startImagePagerActivity(position);
				// selected_position = position;

			}
		});
		listView.setClickable(true);

		// m_vwJokeLayout.setLongClickable(true);
		listView.bringToFront();

	}

	private void createNextButton() {

		intent = new Intent(this, ImageListActivity.class);
		Button next = (Button) findViewById(R.id.next1);
		next.bringToFront();
		next.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				number = number + 1;
				if (number == 6) {
					startActivity(intent);
				}
				else
				{
					Log.d("tag", "Number is: " + number);
					items = HomeActivity.map.get(number);
					tx.setText(Constants.HEADERS[number]);
					adapter.notifyDataSetChanged();
				}
				// Perform action on click
			}
		});
	}

	public static Context getAppContext() {
        return ImageGridActivity.getAppContext();
    }
	private void startImagePagerActivity(int position) {
		intent = new Intent(this, ImagePagerActivity.class);
		// intent.putExtra(Extra.IMAGES, imageUrls);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter() {
		}

		public class ViewHolder {
			public CheckBox checkbox;
			public ImageView image;
		}

		@Override
		public int getCount() {
			return 12;
			// to be changed
			// return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = new ViewHolder();
			// holder = new ViewHolder();
			// final ImageView imageView;
			// the line below makes shit work..sometimes
			convertView = null;
			if (convertView == null) {
				// imageView = (ImageView)
				// getLayoutInflater().inflate(R.layout.item_grid_image, parent,
				// false);
				view = getLayoutInflater().inflate(R.layout.item_grid_image,
						parent, false);

				// holder.checkbox = (TextView)
				// view.findViewById(R.id.checkBox1);
				holder.image = (ImageView) view.findViewById(R.id.image);
				holder.checkbox = (CheckBox) view.findViewById(R.id.checkBox1);
				view.setTag(holder);
				// imageLoader.displayImage(imageUrls[position], holder.image,
				// options);

			}
			//else {
			  
			  //unimplemented logic to correctly set the checkboxes!!!
			  //if(state) //{ //holder.checkbox.setChecked(true); //} //else
			  //{
			  
			  //} holder = (ViewHolder) view.getTag();
			  
			  //when do we set it to false? True?
			  //holder.checkbox.setChecked(false); }
			 
			/*
			 * if(check[position]!=null && check[position].isChecked()) {
			 * holder.checkbox.setChecked(true); } else {
			 * holder.checkbox.setChecked(false); }
			 */
			// check[position]=holder.checkbox;
			imageLoader.displayImage(items.get(position).getUrl(),
					holder.image, options);
			// holder.checkbox.setSelected(getState());
			return view;
		}
	}
}
