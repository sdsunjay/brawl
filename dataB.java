import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;
import java.lang.System;
import java.lang.Exception;
import java.sql.*;
public class dataB{
	
	//private final String tableName = new String("TweetTable");
	//private final String databaseName = new String("Tweets");
      private String databaseName;
      private String tableName;
      public Connection connect(String databaseName, String tableName ) {
      Connection conn =null;
      this.databaseName=databaseName;
      this.tableName = tableName;
      Console console = System.console();
      //this is what we should really do, DON'T DELETE
      //String id = console.readLine("Enter your Oracle userID: "); 
      //char[] pword = console.readPassword("Enter Oracle password: ");

      try {
	 String password = new String("toor");
	 String id = new String("root");
	 String url= "jdbc:mysql://localhost:3306/"+databaseName+"?user="+id+"&password="+password;
	 
	 Class.forName("com.mysql.jdbc.Driver").newInstance();
	 conn = DriverManager.getConnection(url);


      } catch (Exception e) {

	 System.err.println("Trouble connecting to database server, quitting");
	 System.exit(-1);
      }
   
      return conn;

   }





   public int insert(ArrayList<gasStation> data,Connection conn) 
   {
      int insertCount=0;
      PreparedStatement pstmt = null;
      String query = null;
      int i = 0;
      try {
	 conn.setAutoCommit(false);
	 conn. setTransactionIsolation(
	       Connection.TRANSACTION_READ_COMMITTED);

	 // Create a reusable parameterized prepared statement.  Once 
	 // prepared, the query can be executed repeatedly with new 
	 // values without preparation.
	 for(i=0;i<data.size();i++)
	 {
	    //data.get(i).pointValue=0;
	    query = "insert into "+tableName+" (date,name,address,city,regular,plus,premium,updated)" +" values (?,?,?,?,?,?,?,?)";
	    pstmt = conn.prepareStatement(query);
	    pstmt.setInt(1, data.get(i).date);  
	    pstmt.setString(2, data.get(i).name);  
	    pstmt.setString(3, data.get(i).address);  
	    pstmt.setString(4, data.get(i).city);              
	    pstmt.setInt(5, data.get(i).regular);  
	    pstmt.setInt(6, data.get(i).plus);             
	    pstmt.setInt(7, data.get(i).premium);             
	    pstmt.setInt(8, data.get(i).updated);             
	    pstmt.executeUpdate();
	    //IMPORTANT: NO PARAMETER
	 }		
      } //end try

      catch (SQLException e){
	 /*	if (e.getErrorCode() == INEXISTENT_COLUMN_ERROR)
		System.err.println("User friendly error message caused by column " + this.matchPattern(e.getMessage(), this.INEXISTENT_COLUMN_PATTERN));
		else if (e.getErrorCode() == DUPLICATE_DATA_ERROR)
		System.err.println("User friendly error message caused by duplicate data " + this.matchPattern(e.getMessage(), this.DUPLICATE_DATA_PATTERN));
		else */

	 if(e.getSQLState().equals("23000"))
	 {
	    System.err.println("ER_DUP_KEY");
	 }
	 else
	 {
	    System.err.println("Error Code: " +(e.getErrorCode()));
	    System.err.println("SQL State: " +(e.getSQLState()));
	 }

	 e.printStackTrace( );    
	 System.exit(-1);
      }

      catch (Exception ex)
      {
	 ex.printStackTrace( );    
	 // for debugging
	 System.exit(-1);
      }
      finally{
	 try{
	    conn.commit( );
	    pstmt.close( );
	    //conn.close( );
	 }
	 catch (Exception ex)
	 {
	    ex.printStackTrace( );    // for debugging
	    System.err.println("Error");
	    System.exit(-1);
	 }
      } // end finally          
      return i;
   } //end method


   public void deleteAll(Connection conn,String delword,String column ) 
   {

      PreparedStatement pstmt = null;                                           

      String query = "DELETE from "+tableName+" where "+column+" = '"+delword+"';";
      int i = 0;
      System.err.println("WARNING DELETING TABLE!");
      System.out.println("Are you sure you want to do this?");
      System.out.println("Enter 1 for yes and 0 for no (exit)");
      Scanner scan = new Scanner(System.in);
      if(scan.hasNextInt())
      {
	 i=scan.nextInt();
	 if(i==0)
	 {
	    throw new IllegalArgumentException("Final ");
	    //throw new SQLExeception("Delete Error");
	 }
	 else if(i==1)
	 {
	    try 
	    {
	       conn.setAutoCommit(false);
	       conn. setTransactionIsolation(
		     Connection.TRANSACTION_READ_COMMITTED);


	       // Create a reusable parameterized prepared statement.  Once 
	       // prepared, the query can be executed repeatedly with new 
	       // values without preparation.


	       pstmt = conn.prepareStatement(query);                                  
	       pstmt.executeUpdate();                                                 
	       conn.commit( );                                                        
	       pstmt.close( );                                                        
	       //conn.close( );                                                         

	    }
	    catch (Exception ex)
	    {
	       System.err.println("Error deleting");	
	       ex.printStackTrace( );    // for debugging
	    }
	 }
      }
      else
      {
	 System.err.println("No number entered, will not delete");

      }
   }
   //closes class	
}
