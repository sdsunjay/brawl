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
import java.util.HashMap;
import java.util.Map;

public final class Constants {
	//shown on first screen
	public static final String backgroundImage = "http://slimages.macys.com/is/image/MCY/products/2/optimized/1635042_fpx.tif?bgc=255,255,255&wid=700&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg";
	//headers for the top of the screen
	public static final String[] HEADERS = new String[]{
	"Dress It Up!","Top It Off!","Bottom Line","Short N' Sweet","Warmin' Up","Finish the Look"
	};
		
	public static final String[] categories = new String[] { "dresses", "tops",
			"bottoms", "shorts", "shoes" };
	public static final String width = "350";

	public static final String[] dressesIDs = new String[] { "852952",
			"852967", "851129", "833184", "815905", "833143", "821738",
			"936375", "830396", "779853", "856456", "796360", "854603" };
	public static final String[] dresses = new String[] {
			// 852952
			"http://slimages.macys.com/is/image/MCY/products/3/optimized/1616723_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 852967
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1616719_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 851129
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1609308_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 833184
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/1558384_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 815905
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1543328_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 833143
			"http://slimages.macys.com/is/image/MCY/products/1/optimized/1579151_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 821738
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1564119_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 936375
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/861404_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 830396
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1558278_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 779853
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1433019_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 856456
			"http://slimages.macys.com/is/image/MCY/products/2/optimized/1616552_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 796360
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1481728_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 854603
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/1623714_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg" };

	public static final String[] topsIDs = new String[] { "672364", "820032",
			"834615", "810506", "839829", "823224", "813659", "820027",
			"811389", "867638", "862190", "839866", "905759"

	};
	public static final String[] tops = new String[] {
			// 672364
			"http://slimages.macys.com/is/image/MCY/products/1/optimized/1199651_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 820032
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1547308_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 834615
			"http://slimages.macys.com/is/image/MCY/products/7/optimized/1579487_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 810506
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1514659_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 839829
			"http://slimages.macys.com/is/image/MCY/products/2/optimized/1584222_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 823224
			"http://slimages.macys.com/is/image/MCY/products/7/optimized/1579257_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 813659
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/1552254_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 820027
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1525438_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 811389
			"http://slimages.macys.com/is/image/MCY/products/5/optimized/1513615_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 867638
			"http://slimages.macys.com/is/image/MCY/products/7/optimized/1596677_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 862190
			"http://slimages.macys.com/is/image/MCY/products/1/optimized/1609251_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 839866
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/1584254_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 905759
			"http://slimages.macys.com/is/image/MCY/products/1/optimized/1627161_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg" };

	public static final String[] shortsIDs = new String[] { "811856", "824142",
			"813700", "830392", "779537", "826372", "806197", "831257",
			"872596", "802206", "831253", "778735", "834377" };
	public static final String[] shorts = new String[] {
			// 811856
			"http://slimages.macys.com/is/image/MCY/products/2/optimized/1514402_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 824142
			"http://slimages.macys.com/is/image/MCY/products/0/optimized/1578840_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 813700
			"http://slimages.macys.com/is/image/MCY/products/2/optimized/1528332_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 830392
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1558219_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 779537
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/1514464_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 826372
			"http://slimages.macys.com/is/image/MCY/products/0/optimized/1544070_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 806197
			"http://slimages.macys.com/is/image/MCY/products/6/optimized/1499246_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 831257
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1559728_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 872596
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1606388_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 802206
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1514428_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 831253
			"http://slimages.macys.com/is/image/MCY/products/1/optimized/1579121_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 778735
			"http://slimages.macys.com/is/image/MCY/products/3/optimized/1432033_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 834377
			"http://slimages.macys.com/is/image/MCY/products/2/optimized/1564162_fpx.tif?bgc=255,255,255&wid=350&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg" };
	public static final String[] outerwearIDs = new String[] { "805200",
			"799559", "715985", "830134", "805194", "810317", "799534",
			"806582", "840077", "860733", "833346", "804330", "794817" };
	public static final String[] outerwear = new String[] {
			// 805200
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1521199_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 799559
			"http://slimages.macys.com/is/image/MCY/products/6/optimized/1478546_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 715985
			"http://slimages.macys.com/is/image/MCY/products/3/optimized/1355633_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 830134
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1564508_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 805194
			"http://slimages.macys.com/is/image/MCY/products/6/optimized/1499146_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 810317
			"http://slimages.macys.com/is/image/MCY/products/6/optimized/1520556_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 799534
			"http://slimages.macys.com/is/image/MCY/products/3/optimized/1478543_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 806582
			"http://slimages.macys.com/is/image/MCY/products/6/optimized/1496956_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 840077
			"http://slimages.macys.com/is/image/MCY/products/0/optimized/1388860_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 860733
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1621758_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 833346
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/1558284_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 804330
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1490339_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 794817
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1500258_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg" };
	public static final String[] shoesIDs = new String[] { "696281", " 941524",
			"928326", "838595", "810982", "832396", "926985", "201394",
			"728766", "908552", "739870", "739868", "825716" };
	public static final String[] shoes = new String[] {
			// 696281
			"http://slimages.macys.com/is/image/MCY/products/1/optimized/854901_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 941524
			"http://slimages.macys.com/is/image/MCY/products/7/optimized/1664307_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 928326
			"http://slimages.macys.com/is/image/MCY/products/5/optimized/1646565_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 838595
			"http://slimages.macys.com/is/image/MCY/products/4/optimized/1597924_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 810982
			"http://slimages.macys.com/is/image/MCY/products/0/optimized/1506590_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 832396
			"http://slimages.macys.com/is/image/MCY/products/3/optimized/1571633_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 926985
			"http://slimages.macys.com/is/image/MCY/products/3/optimized/1639353_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 201394
			"http://slimages.macys.com/is/image/MCY/products/0/optimized/397830_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 728766
			"http://slimages.macys.com/is/image/MCY/products/3/optimized/1307663_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 908552
			"http://slimages.macys.com/is/image/MCY/products/1/optimized/1398791_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 739870
			"http://slimages.macys.com/is/image/MCY/products/8/optimized/1332978_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 739868
			"http://slimages.macys.com/is/image/MCY/products/7/optimized/1332967_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			// 825716
			"http://slimages.macys.com/is/image/MCY/products/9/optimized/1592659_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg" };
	public static final String[] bottomsIDs = new String[] { "778109",
			"582112", "765386", "782230", "823540", "804513", "839337",
			"816811", "689997", "821782", "811037", "778914", "808873", };
	public static final String[] bottoms = new String[] {
		//778109		
		"http://slimages.macys.com/is/image/MCY/products/0/optimized/1432060_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//582112
					"http://slimages.macys.com/is/image/MCY/products/2/optimized/1584312_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//765386
					"http://slimages.macys.com/is/image/MCY/products/6/optimized/1404496_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//782230
					"http://slimages.macys.com/is/image/MCY/products/7/optimized/1442237_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//823540
					"http://slimages.macys.com/is/image/MCY/products/6/optimized/1551076_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//804513
					"http://slimages.macys.com/is/image/MCY/products/2/optimized/1514162_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//839337
					"http://slimages.macys.com/is/image/MCY/products/8/optimized/1592258_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//816811
					"http://slimages.macys.com/is/image/MCY/products/8/optimized/1525418_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//689997
					"http://slimages.macys.com/is/image/MCY/products/4/optimized/1240024_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//821782
					"http://slimages.macys.com/is/image/MCY/products/4/optimized/1536304_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//811037
					"http://slimages.macys.com/is/image/MCY/products/7/optimized/1506837_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//778914
					"http://slimages.macys.com/is/image/MCY/products/1/optimized/1432231_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg",
			//808873
					"http://slimages.macys.com/is/image/MCY/products/8/optimized/1525378_fpx.tif?bgc=255,255,255&wid="
					+ width
					+ "&qlt=90&layer=comp&op_sharpen=0&resMode=bicub&op_usm=0.7,1.0,0.5,0&fmt=jpeg", };

	private Constants() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}
}
