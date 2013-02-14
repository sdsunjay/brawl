import java.util.*;
import java.lang.*;

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

public class parseLine1
{
   public static void main(String[] args)
   {
      int size=0;
      int ii=0;
      String gname;
      String gnam="";
      String temp1="";
      ArrayList<Integer> gasp= new ArrayList<Integer>();
      ArrayList<String> name= new ArrayList<String>();
      ArrayList<String> update= new ArrayList<String>();
      //String str=" ";
      boolean flag=false;
      boolean flag1=false;
      String temp=new String();
      try{
	 File f= new File("index.html");
	 Scanner scan = new Scanner(f);
	 int counter=0;
	 int counter1=0;
	 Scanner numscan;
	 //each time there is a space, new temp
	 while(scan.hasNext()){

	    if(flag1)
	    {
	       counter1++;

	       if(counter1==1)
	       {
		  System.out.println();
		  System.out.print("   Updated: ");
		  if(temp.contains("See"))
		     flag1=false;
		  else if(temp.contains("Learn"))
		     flag1=false;
		  else
		  System.out.print(temp.substring(7,temp.length())+" ");
	       }
	       else
	       {
		  temp= new String(temp.replaceAll("\">",""));
		  temp= new String(temp.replaceAll(">",""));
		  System.out.print(temp+" ");
	       }
	       if(counter1==3)
	       {
		  flag1=false;
		  counter1=0;
		  System.out.println();
		  System.out.println();
	       }

	    }
	    temp=scan.next();
	    //if(temp.matches("class=\"p"))
	    if(flag)
	    {
	       counter++;
	       if(counter==3)
	       {
		  gname= new String(temp.replaceAll("_"," "));
		  gname=new String(gname.replaceAll("/"," "));
		  gname=new String(gname.replaceAll("href=\"",""));
		  numscan=new Scanner(gname);

		  System.out.print("   ");
		  while(numscan.hasNext()){
		     temp1=numscan.next();
		     System.out.print(temp1+" ");
		     if(temp1.equals("Francisco"))
			break;

		     if(numscan.hasNextInt())
			break;
		  }


		  String address="";
		  temp=scan.next();
		  temp=scan.next();
		  address=new String("   "+temp+" "+scan.next()+" "+scan.next()+" "+scan.next());

		  temp= new String (address.replaceFirst("<dd>",""));
		  address= new String (temp.replaceFirst("</dt>",""));
		  temp= new String(address.replaceFirst("&amp;",""));
		  address= new String(temp);
		  System.out.println();
		  System.out.print(address);





		  flag=false;
		  counter=0;

	       }
	    }
	    if(temp.length()>19 && temp.length()<25 && temp.charAt(0)=='c')
	    {
	       numscan=new Scanner(temp.substring(8,9));
	       //System.out.println(temp.substring(8,9));
	       if(numscan.hasNextInt())
	       {
		  //line with gas price numbers in it, usually one per line
		  gasp.add(size,numscan.nextInt());
		  size++;
		  if(size==3){
		     System.out.println("   Regular Gas: $"+gasp.get(0)+"."+gasp.get(1)+gasp.get(2));
		     size=0;
		  }
	       }
	    }
	    //class="address"> 
	    if(temp.matches("class=\"address\">"))
	       flag=true;
	    if(temp.contains("title="))
	       if(!temp.contains("title=\"Click"))
		  if(!temp.contains("title=\"Update"))
		  {
		     flag1=true;
		  }
	 }

	 //size=gasp.size();
	 //System.out.println(gasp.toString());
	 /* for(int i=0;i<=size-3;i=i+3)
	    {
	    gname= new String(name.get(ii).replaceAll("_"," "));

	    System.out.print((ii+1)+". ");
	    numscan=new Scanner(gname.replaceAll("/"," "));

	    while(numscan.hasNext()){
	    temp1=numscan.next();
	    System.out.print(temp1+" ");
	    if(temp1.equals("Obispo"))
	    break;
	    }
	    System.out.println();
	    System.out.println("   Regular Gas: $"+gasp.get(i+0)+"."+gasp.get(i+1)+gasp.get(i+2));
	    System.out.println("   Updated: "+update.get(ii)+" "+update.get(ii+1)+" "+update.get(ii+2));
	    ii++;
	    System.out.print("   Hours: ");
	    if(ii==1|| ii==3 || ii==7 || ii==4 || ii==8)
	    System.out.print("Open 24 hours\n");
	    else if(ii==2)
	    System.out.print("7AM-8PM\n");
	    else
	    System.out.println();
	    System.out.println();
	    }*/
      }

      catch(Exception ex)
      {
	 ex.getCause();
	 //ex.printStackTrace( );
	 //System.out.println("ERROR");
      }
   }
}
