package consoleApplication;
import java.util.*;
import java.sql.*;
public class Main {
	
	public static void show(String s)
	{
		System.out.println(s);
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
	  	 Scanner sc=new Scanner(System.in);
	  	 Class.forName("com.mysql.cj.jdbc.Driver");
		  Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test2","root","root@123");
     
   
     int ch;
     ch=0;
     while(ch!=3)
     {
    	
    	  show("1.NewCoustomer\n2.Login\n3.Exit");
    	  Statement st=con.createStatement();
    	  show("Enter your Choice= ");
    	 ch=sc.nextInt();
    	  if(ch==1)
    	     {
    	       User user=new User();
    	       user.createUser(st, 3);
    	     }
    	     else if(ch==2)
    	     {
    	    	 User user=new User();
      	         
      	         if( user.authenticate(st))
      	         {
      	        	 show("Hii "+user.name+"!!!");
      	        	 boolean flag=true;
      	        	 while(flag)
      	        	     flag= user.getChoice(st);
      	         }
    	     }
    	     else if(ch==3)
    	     {
    	    	 show("Progaram ended");
    	    	 break;
    	     }
    	     else
    	     {
    	    	 show("INVALID CHOICE");
    	     }
    	 
    	 }
   
     
	}

}
