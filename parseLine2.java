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

public class parseLine2
{
   public static void main(String[] args)
   {
      int size=0;
      int ii=0;
      ArrayList<Integer> gasp= new ArrayList<Integer>();
      String temp=new String();
      try{
	 File f= new File("index.html");
	 Scanner scan = new Scanner(f);
	 int counter=0;
	 Scanner numscan;
	 //each time there is a space, new temp
	 while(scan.hasNext()){
	    temp=scan.next();
	    //if(temp.matches("class=\"p"))
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
		     counter++;
		     System.out.print("<div>");
		     System.out.print(+gasp.get(0)+"."+gasp.get(1)+gasp.get(2));
		     System.out.print("</div>");
		     System.out.println();
		     size=0;
		  }
	       }
	    }
	    if(counter==10)
	       break;
	 }
      }
      catch(Exception ex)
      {
	 ex.getCause();
	 //ex.printStackTrace( );
	 //System.out.println("ERROR");
      }
   }
}
