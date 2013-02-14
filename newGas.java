import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.regex.*;

import java.sql.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.*;

import java.io.*;
import java.lang.*;

public class newGas
{
   public static void main(String[] args)
   {


      String url = "http://www.californiagasprices.com/index.aspx?area=San%20Luis%20Obispo";
      int size=0;
      byte s;
      ArrayList<Integer> gasp= new ArrayList<Integer>();
      ArrayList<String> name= new ArrayList<String>();
      //String str=" ";
      boolean flag=false;
      String temp=new String();
      try
      {
	 URL searchURL = new URL(url);

	 //TEST
	 System.err.println("URL: " + searchURL);

	 URLConnection searchConnection = searchURL.openConnection();

	 InputStream is = searchConnection.getInputStream();

	 byte[] data = new byte[100 + 1];
	 String total = "";
	 int read;
	 while ((read = is.read(data, 0, 100)) != -1)
	 {
	    data[read]=' ';
	    total += new String(data);
	 }
	 is.close();
	 //how many lines are in this bitch?
	 //System.out.println("Lines: "+total.length());
	 
	
      
	 Scanner scan = new Scanner(total);
	 Scanner numscan;
	 int counter=0;
	 //each time there is a space, new temp
	 while(scan.hasNext()){

	    temp=scan.next();
	    if(flag)
	    {
	       counter++;
	       if(counter==3)
	       {
		  //name.add(temp.substring(7,temp.length()-29));
		  flag=false;
		  counter=0;
	       }
	    }
	    if(temp.matches("class=\"p\\d.*"))
	    {
	    System.out.println(temp);
	       // System.out.println(temp);
	       //class="p4"></div><div
		
	   // if(temp.length()>19 && temp.length()<25 && temp.substring(7,8).equals("p"))
	   // {
	       //System.out.println(temp);
			
	       numscan=new Scanner(temp.substring(8,9));
	       //System.out.println(temp.substring(8,9));
	       if(numscan.hasNextInt())
	       {
		  //line with gas price numbers in it, usually one per line
		  //System.out.println(temp);
		  size++;
		  gasp.add(numscan.nextInt());
	       }
	    }
	    //class="address"> 
	    if(temp.matches("class=\"address\">"))
	       flag=true;
	 }
	 size=gasp.size();
	 System.out.println(gasp.toString());
	 int ii=0;
	 String gname;
	 for(int i=2;i<=size+4;i=i+3)
	 {
	    //gname= new String(name.get(ii++).replaceAll("_"," "));
	    
	    //System.out.println(ii+". "+gname.replaceAll("/"," "));
	    System.out.println("   Regular Gas: $"+gasp.get(i)+"."+gasp.get(i+1)+gasp.get(i+2));
	    System.out.print("   Hours:");
	    if(ii==1|| ii==3 || ii==7 || ii==4)
	       System.out.print("Open 24 hours\n");
	    else
	       System.out.println();
	    System.out.println();
	    if(i+4>=gasp.size())
	       break;
	 }
      }

      catch(Exception ex)
      {
	 ex.getCause();
	 ex.printStackTrace( );
	 System.out.println("ERROR");
      }
   }
}
