/**
    * A helper class to encapsulate all the data for a tweet returned from 
    *  Twitter.
    */
   public class TweetData
   {
      public int pointValue;
      public String geolocation;
      public String twitter_id;
      public int frame_id;
      public int published;
      public String content;
      public String ref_content;
      public String source;
      public String lang;
      public String author;
      public String link ="none";
      public String keyword;
      /**
       * All the attributes returned by toString().
       */
      public final String attributes = 
	 "(id, twitter_id, frame_id, published, content, ref_content, source, lang, author,link)";

      /**
       * A String that you can use for a prepared statement that matches up with
       *  both attributes and the toString().
       */
      public final String preparedStatement = 
	 "(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      /**
       * Get all the data encapsulated by this class, SQL style.
       * String values are quoted.
       *
       * @return The data for this Tweet in a nice string.
       */
      public String toString()
      {
	 return String.format("(NULL, 'ID %s', %d, %d, '%s', '%s', '%s', '%s',\n 'Author: %s', %d, '%s')",twitter_id, frame_id, published, content, ref_content, source, lang, author, pointValue, geolocation);
      }
      public String output()
      {
	 return "Author: "+author+"\nContent: "+content+"\nSource: "+source;
      }
      private int parseDateTimeNoMillis(long published)
      {
	 return (int) published;
	 //ex 1359369342
	       //yyyy-MM-dd'T'HH:mm:ssZZ
	 //return(new BaseDateTime(published*1000).getYear());
	 
      }
      
   }
