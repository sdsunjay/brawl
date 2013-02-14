import java.lang.Exception;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.File;

/*parses and outputs current cost of gas in SLO*/
public class parseLine
{
   /*main class*/
   public static void main(String[] args)
   {
      int size=0;
      String gname;
      String temp1="";
     
      /*individual station*/
      gasStation tempStation = new gasStation();    
      /*arraylist of gas stations*/
      ArrayList<gasStation> station = new ArrayList<gasStation>();
      /*arrayList to hold each price per gallon of gas*/
      ArrayList<Integer> gasp= new ArrayList<Integer>();
      /*flags to tell us when we have found
       * the name of the station
       * and when the price was last updated*/
      boolean flag=false;
      boolean flag1=false;
      /*temporarily stores each word in file*/
      String temp=new String();
      try{
	 
	 /*read the file from www.californiagasprices.com*/
	 /*wget -q -O index.html http://www.californiagasprices.com/index.aspx?area=San%20Luis%20Obispo*/
	 File f= new File("index.html");
	 /*file scanner*/
	 Scanner scan = new Scanner(f);
	 Scanner numscan;
	 int counter=0;
	 int counter1=0;
	 
	 /*parse file to the end*/
	 while(scan.hasNext()){

	    /*When was the price last updated*/
	    if(flag1)
	    {
	       counter1++;
	       if(counter1==1)
	       {
		  System.out.println();
		  System.out.print("   Updated: ");
		  System.out.print(temp.substring(7,temp.length())+" ");
	       }
	       else if(counter1==2 || counter1==3)
	       {
		  temp= new String(temp.replaceAll("\">",""));
		  System.out.print(temp+" ");
	       }

	       else if(counter1==4)
	       {
		  flag1=false;
		  counter1=0;
		  System.out.println();
		  System.out.println();
	       }

	    }
	    /*read word*/
	    temp=scan.next();
	    /*What is the name of the station*/
	    /*Where is it located*/
	    if(flag)
	    {
	       String address="";
	       counter++;
	       if(counter==3)
	       {
		  /*sanitize word*/
		  gname= new String(temp.replaceAll("_"," "));
		  gname=new String(gname.replaceAll("/"," "));
		  gname=new String(gname.replaceAll("href=\"",""));
		  numscan=new Scanner(gname);

		  System.out.print("   ");
		  while(numscan.hasNext()){
		     temp1=numscan.next();
		     System.out.print(temp1+" ");
		     /*so we know when we have reached the end of the line*/
		     if(temp1.equals("Obispo"))
			break;
		  }

		  temp=scan.next();
		  temp=scan.next();
		  /*build address of gas station*/
		  address=new String("   "+temp+" "+scan.next()+" "+scan.next()+" "+scan.next());
		  
		  /*sanitize address string*/  
		  temp= new String (address.replaceFirst("<dd>",""));
		  address= new String (temp.replaceFirst("</dt>",""));
		  temp= new String(address.replaceFirst("&amp;",""));
		  /*print address*/
		  address= new String(temp);
		  System.out.println();
		  System.out.print(address);
		  tempStation.setAddress(address);
		  flag=false;
		  counter=0;
	       }
	    }
	    /*this word contains a digit from the gas price*/
	    if(temp.startsWith("class=\"p") && temp.startsWith("</div><",11) && (!temp.startsWith("class=\"pd") )){
	       /*Sample temp outputs:
		*    class="p3"></div><div
		*    class="pd"></div><div
		*    class="p8"></div><div
		*    class="p9"></div></div>*/

		  //line with gas price numbers in it, usually one per line
		  /*overwrite the array each time with the unique 3 numbers
		   * from each specific gas station that make up 
		   * the price per gallon*/
		  gasp.add(size,new Integer(temp.substring(8,9)));
		  size++;
		  /*We have created gas price*/
		  if(size==3){
		     System.out.println("   Regular Gas: $"+gasp.get(0)+"."+gasp.get(1)+gasp.get(2));
		     tempStation.setPrice(0,gasp.get(0),gasp.get(1),gasp.get(2));
		     size=0;
		  }
	    }
	    /*set flag to true as we have found word with name and address
	     * of the gas station*/
	    if(temp.matches("class=\"address\">"))
	       flag=true;
	    if(temp.contains("title=")){
	       if(!temp.contains("title=\"Click"))
		  if(!temp.contains("title=\"Update"))
		  {
		     /*set flag to true as we have found word where price
		      * was last updated*/
		     flag1=true;
		  }
	    }
   
	 }

      }
      catch(FileNotFoundException NotFound)
      {
	 System.err.println("File not found");
      }
      catch(Exception ex)
      {
	 ex.getCause();
	 ex.printStackTrace();
      }
   }
}
