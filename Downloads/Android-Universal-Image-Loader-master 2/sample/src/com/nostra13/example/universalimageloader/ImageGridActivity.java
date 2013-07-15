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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
import com.nostra13.example.universalimageloader.ImageListActivity.ItemAdapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImageGridActivity extends AbsListViewBaseActivity {

	//url of images
	String[] imageUrls;

	//does stuff
	Intent intent;
	//the number of the screeen
	String number;
	//the text at the top
	TextView tx;
	ImageAdapter[] adapter = new ImageAdapter[100];

	boolean[] checkStatus= new boolean[1000];
    CheckBox[] check = new CheckBox[100];
	DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);
		setup();
		setIntentStuff();
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();


		createNextButton();
		

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//view.setHovered(true);
				Log.d("tag","clicked grid picture");
				
				
			
				int color;
			
				color=-16776961;
				
				//image1.bringToFront();
				
				//view.setBackgroundColor(color);
				//shows image in its own screen
				startImagePagerActivity(position);
			}
		});
		
		
	
		
	}

	private void setup()
	{

		for(int i=0;i<100;i++)
		{
			adapter[i] = new ImageAdapter(false);
		}
		Bundle bundle = getIntent().getExtras();
		imageUrls = bundle.getStringArray(Extra.IMAGES);
		number = bundle.getString("number");
		 tx= (TextView) findViewById(R.id.textView1);
		 tx.setTextColor(Color.RED);
		listView = (GridView) findViewById(R.id.gridview);
		((GridView) listView).setAdapter(new ImageAdapter(false));

		listView.bringToFront();
	}
	
	private void setIntentStuff()
	{
	
		intent = new Intent(this, ImageGridActivity.class);
		if(number.equals("0"))
		{
			tx.setText("Dress It Up!");
			intent.putExtra(Extra.IMAGES, Constants.tops);
			intent.putExtra("number", "1");
		}
		else if(number.equals("1"))
		{
			tx.setText("Top It Off!");
			intent.putExtra(Extra.IMAGES, Constants.bottoms);
			intent.putExtra("number", "2");
		}
		else if(number.equals("2"))
		{
			tx.setText("Bottom Line");
			
			intent.putExtra(Extra.IMAGES, Constants.shorts);
			intent.putExtra("number", "3");
		}
		else if(number.equals("3"))
		{
			tx.setText("Short N' Sweet");
			intent.putExtra(Extra.IMAGES, Constants.jackets);
			intent.putExtra("number", "4");
		}
		else if(number.equals("4"))
		{
			tx.setText("Warmin' Up");
		
			intent.putExtra(Extra.IMAGES, Constants.shoes);
			intent.putExtra("number", "5");
		}
		else if(number.equals("5"))
		{
			tx.setText("Finish the Look");
		}
	}
	private void createNextButton()
	{
	
		if (number.equals("5"))
		{
					intent = new Intent(this, ImageListActivity.class);
		}
	
		Button next = (Button) findViewById(R.id.next1);
		next.bringToFront();
		next.setOnClickListener(new View.OnClickListener() {
		
			
			public void onClick(View v) {
			
				startActivity(intent);
            	
                // Perform action on click
            }
        });
	}
	private void startImagePagerActivity(int position) {
		intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra(Extra.IMAGES, imageUrls);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}
	

	

	public class ImageAdapter extends BaseAdapter {
		//private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		private boolean state;
		public ImageAdapter(boolean state)
		{
			this.state=state;
		}
		
		public class ViewHolder {
			public CheckBox checkbox;
			public ImageView image;
		}

		@Override
		public int getCount() {
			return imageUrls.length;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = new ViewHolder();
			//holder = new ViewHolder();
			//final ImageView imageView;
			//the line below makes shit work
			//convertView=null;
			if (convertView == null) {
				//imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
				view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
		
				//holder.checkbox = (TextView) view.findViewById(R.id.checkBox1);
				holder.image = (ImageView) view.findViewById(R.id.image);				
				holder.checkbox = (CheckBox) view.findViewById(R.id.checkBox1);
				if(state)
				{
					holder.checkbox.setChecked(true);
				}
				else
				{
					holder.checkbox.setChecked(false);
				}
				view.setTag(holder);	
				//imageLoader.displayImage(imageUrls[position], holder.image, options);

			} 
			else {
			
				//unimplemented logic to correctly set the checkboxes!!!
				//if(state)
				//{
					//holder.checkbox.setChecked(true);
				//}
				//else
				//{
	
				//}
				holder = (ViewHolder) view.getTag();
				
				//when do we set it to false? True?
				holder.checkbox.setChecked(false);
			}
			imageLoader.displayImage(imageUrls[position], holder.image, options);
			//holder.checkbox.setSelected(getState());
		  return view;
		}
		 public boolean getState() {
		        return state;
		    } 
		 public void setState(boolean state) {
	        this.state = state;
	    } 
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
