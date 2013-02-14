public class gasStation
{
      public int date;
      public String name;
      public String address;
      public String city;
      public int regular;
      public int plus;
      public int premium;
      public int updated;


      public gasStation()
      {

      }
      public void setPrice(int flag,int first, int second, int third)
      {
	 if(flag==1)
	 {
	    regular=(first*100)+(second*10)+(third);
	 }
	 else if(flag==2)
	 {
	    plus=(first*100)+(second*10)+(third);
	 }
	 else if(flag==3)
	 {
	    premium=(first*100)+(second*10)+(third);
	 }
      }
      public void setAddress(String address)
      {
	 address = address;
      }
}
