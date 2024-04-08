package consoleApplication;
import java.util.*;
import java.sql.*;

public class User {

	  Scanner sc=new Scanner(System.in);
	  String userName,name,password;
	  int id,role;
	  Inventory inventory=new Inventory();
	  void show(String s)
	  {
		  System.out.println(s);
	  }
	  
	  public  String encrypt(String key)
	    {
	    	StringBuilder res=new StringBuilder();
	    	
	    	for(int i=0;i<key.length();i++)
	    	{
	    	    char cur=key.charAt(i);
	    	    char en;
	    	    if(Character.isLowerCase(cur))
	    	    {
	    	    	en=(char)(((cur-'a'+1)%26)+'a');
	    	    }
	    	    else if(Character.isUpperCase(cur))
	    	    {
	    	    	en=(char)(((cur-'A'+1)%26)+'A');
	    	    }
	    	    else if(Character.isDigit(cur))
	    	    {
	    	    	en=(char)(char)(((cur-'0'+1)%10)+'0');
	    	    }
	    	    else
	    	    	 en=cur;
	    	    res.append(en);
	    	}
	      return res.toString();
	    
	    }
	  
	  boolean authenticate(Statement st) throws Exception
	  {
		  show("Enter your user_name ");
		  String name=sc.next();
		  show("Enter your password ");
		  String password=sc.next();
		  String query="select * from user where userName="+"\""+name+"\"";
		  ResultSet res=st.executeQuery(query);
		  res.next();
		  if(res.getString("password").equals(encrypt(password)))
		  {
			  show("login sucessfull");
			 this.userName=res.getString("userName");
     		 this.id=res.getInt("id");
			 this.role=res.getInt("role");
			 this.name=res.getString("name");
			  
			  return true;
		  }
		  else 
			    show("login Unsucessfull");
		  return false;
	  }
	  
	  boolean getChoice(Statement st)throws Exception
	  {
		  
		 if(role==1)
		 {
			return  getAdmin(st);
		 }
		 else if(role==2)
		 {
			 return getManager(st);
		 }
		 else if(role==3)
		 {
			return  getUser(st);
		 }
		 else
			  return true;
	  }
	  
	  boolean getAdmin(Statement st) throws Exception
	  {
		  show("\n1.Change Password\n"
		  		+ "2.Create Manager\n"
		  		+ "3.Update Manager\n"
		  		+ "4.Show inventory\n"
		  		+ "5.Add Inventory\n"
		  		+ "6.Update Inventory\n"
		  		+ "7.Delete Inventory\n"
		  		+"8.Delete Manager\n"
		  		+ "9.Exit\n");
		  System.out.print("ENTER YOUR CHOICE : ");		  
		  int ch=sc.nextInt();
		  if(ch==1)
		  {
			 changePassword(st,this.userName) ;
		  }
		  else if(ch==2)
		  {
			  createUser(st,2);
		  }
		  else if(ch==3)
		  {
			  show("Enter the name of the Manager: ");
			   String name=sc.next();
			   updateUser(st,name);
		  }
		  else if(ch==4)
		  {
			inventory.showInventory(st);
		  }
		  else if(ch==5)
		  {
			  inventory.addInventory(st,id);
		  }
		  else if(ch==6)
		  {
			  inventory.updateInventory(st,id);
		  }
		  else if(ch==7)
		  {
			  inventory.deleteInventory(st);
		  }
		  else if(ch==8)
		  {
			 show("Enter the name of the Manager ");
			 String name=sc.next();
			 deleteUser(st,name);
			 }
		  else if(ch==9)
		  {
			  return false;
		  }
		  else
		  {
			  show("Invalid Choice");
		  }
		  return true;
	  }
	  
	  boolean getManager(Statement st)throws Exception
	  {
		  show("\n1.Change Password\n"
			  		+ "2.Show Inventories\n"
			  		+ "3.Add Inventories\n"
			  		+ "4.Delete Inventories\n"
			  		+ "5.Update Inventories\n"
			  		+"6.EXIT/LOGOUT"
			  		);
		  System.out.print("ENTER YOUR CHOICE : ");		  
		  int ch=sc.nextInt();
		  if(ch==1)
		  {
			 changePassword(st,this.userName) ;
		  }
		  else if(ch==2)
		  {
			  inventory.showInventory(st);
		  }
		  else if(ch==3)
		  {
			  inventory.addInventory(st,id);
		  }
		  else if(ch==4)
		  {
			  inventory.deleteInventory(st);
		  }
		  else if(ch==5)
		  {
			  inventory.updateInventory(st, id);
		  }
		  else if(ch==6)
		  {
			  return false;
		  }
		  else
			   System.out.println("INVALID INPUT/CHOICE");
			  
		  return true;
		  
	  }
	 
	  boolean getUser(Statement st) throws Exception
	  {
		  show("\n1.Change Password\n"
			  		+ "2.Show Inventories\n"
			  		+ "3.Show Orders\n"
			  		+ "4.Add to order\n"
			  		+ "5.Delete Order\n"
			  		+ "6.Purchase\n"
			  		+ "7.Purchase History\n"
			  		+"8.Reedem\n"
			  		+"9.show wallet\n"
			  		+ "10.Exit\n");
		  System.out.print("ENTER YOUR CHOICE : ");	
		  int ch=sc.nextInt();
		  Orders order=new Orders(st);
		  if(ch==1)
		  {
			  changePassword(st,this.userName);
		  }
		  else if(ch==2)
		  {
			  inventory.showInventory(st);
		  }
		  else if(ch==3)
		  {
			 //show order
			  order.showOrders(st, this.id);
		  }
		  else if(ch==4)
		  {
			  //delete Order
			 order.addOrder(st, id, inventory);
		  }
		  else if(ch==5)
		  {
			  //delete orderworking
			  order.deleteOrder(st, id);
		  }
		  else if(ch==6)
		  {
			  //purchase
			  order.purchase(st, id);
		  }
		  else if(ch==7)
		  {
			  //purchase history
			  order.showHistory(st,id);
			  
		  }
		  else if(ch==8)
		  {
			reedem(st,id);
		  }
		  else if(ch==9)
		  {
			  showWallet(st,id);
		  }
		  else if(ch==10)
		  {
			  return false;
		  }
		  else
			  show("UnSucessful");
		  return true;
	  }
	  
	  void changePassword(Statement st,String userName) throws Exception
	  {
		  
		  
          show("Enter your new password ");
		  String password=sc.next();
      	  password=encrypt(password);
		  String query="update user set password = \""+password+"\" where userName = \""+userName+"\"";
		  st.execute(query);
		  show("password has been changed sucessfully");
		  
	  }
	  void createUser(Statement st,int pos) throws Exception
	  {
		    show("Enter user_name");
			String userName=sc.next();
			show("Enter password");
			String  password=sc.next();
			show("Enter name");
			String name=sc.next();
			int position=pos;
			password=encrypt(password);
			String query="insert into user(name,password,role,userName) values"+"("+"\""+name+"\""+","+"\""+password+"\","+"\""+position+"\","+"\""+userName+"\""+")";
			st.execute(query);
			if(pos==3)
			{
			  query="select LAST_INSERT_ID()";
			  ResultSet res=st.executeQuery(query);
			  res.next();
			  int c_id=res.getInt(1);
			  show("ENTER PIN FOR WALLET");
			  int pin=sc.nextInt();
			  query="insert into wallet(c_id,pin) value("+c_id+","+pin+ ")";
			  st.execute(query);
			}
			System.out.println(pos+" entered sucessfully");
	  }
	  
	  void updateUser(Statement st,String name) throws Exception
	  {
		  int ch;
		  show("1.change password");
		  show("2.Delete Manager");
		   ch=sc.nextInt() ;
		   if(ch==1)
		   {
			   changePassword(st,name);
		   }
		   else if(ch==3)
		   {
			   deleteUser(st,name);
		   }
	  }
	   
	  void deleteUser(Statement st,String userName) throws Exception
	  {
		  String query="delete from user where userName =\""+userName+"\"";
		  show(query);
		  st.execute(query);
		  show(userName+" deleted sucessfully");
	  }
	  
	  void reedem(Statement st,int id) throws Exception
	  {
		 String query="upadate wallet set amount=amount+(credit/10),credit=credit%10 where c_id ="+id;
		 st.execute(query);
		 show("Reedemed Sucessfully");
	  }
	  
	  void showWallet(Statement st,int id) throws Exception
	  {
		  String query="select amount,credit from wallet where c_id = "+id;
		  ResultSet res=st.executeQuery(query);
		  while(res.next())
		  {
		  show("BALANCE = "+res.getInt(1));
		  show("CREDIT = "+res.getInt(2));
		  }
	  }
	
	
	
}
