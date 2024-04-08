package consoleApplication;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
public class Inventory{
	Scanner sc=new Scanner(System.in);
	void show(String s)
	{
		System.out.print(s);
	}
	void showInventory(Statement st) throws Exception
	{
		 String query="select * from inventory";
		   ResultSet res=st.executeQuery(query);
		   while(res.next())
		   {
			   show(res.getString("name")+" "+
		            res.getString("description")+" "+
					res.getInt("unit_price")+" "+
		            res.getInt("stock"));
			   show("\n");
		   }
	}
	
	void deleteInventory(Statement st) throws Exception
	{
		String query;
		ArrayList<Integer> inv_id=getInventory(st);
		showInventory(st);
		show("CHOICE THE PRODUCT: ");
		int ch=sc.nextInt();
	    query="delete from inventory where id="+inv_id.get(ch-1);
	   try
	   {
		  //show(query);
		  st.execute(query)	;
		  show("\nDeleted SUcessfully\n");
	   }
	   catch(Exception e)
	   {
		   show("\nThe item was not present in the inventory could not deleted");
		   show(e.getMessage());
		}
	   }
	void addInventory(Statement st,int c_id)
	{
		String name,discription;
		int price,stock;
		show("       Enter the name  : ");
	    name=sc.next()	;
	    show("Enter the discription  : ");
	    discription=sc.next();
	    show(" Enter the unit price  : ");
	    price=sc.nextInt();
	    show("	 Enter the stock : ");
	    stock=sc.nextInt();
	    String query="insert into inventory(name,description,unit_price,stock,created_by,modified_by,created_at,updatedTime) values("+
	                   "\""+name+"\""+","+
	                   "\""+discription+"\""+","+
	                   price+","+
	                   stock+","+
	                   c_id+","+
	                   c_id+","+
	                   "current_date()" + ","+
	                   "current_timestamp()"+")";
	    show("product inserted successfully");
	    try {
	    st.execute(query);
	    }
	    catch(Exception e)
	    {
	    	show("Error Occur while inserting ");
	    	show("\n"+e.getMessage());
	    }
	}
	
	void updateInventory(Statement st,int c_id) throws Exception
	{
		String query;
		ArrayList<Integer> inv_id=getInventory(st);
		showInventory(st);
		show("CHOICE THE PRODUCT: ");
		int ch=sc.nextInt();
		show("1.UPDATE PRICE\n");
		show("2.UPDATE STOCK\n");
		show("3.UPDATE BOTH\n");
		show("ENTER YOUR CHOICE: ");
		int c=sc.nextInt();
		if(c==1)
		{
			show("ENTER UNIT PRICE :");
			int unit_price=sc.nextInt();
			query="update inventory set "+
		           "unit_price="+unit_price+","+
				   "modified_by="+c_id+","+
		           "updatedTime = current_timestamp where id="+inv_id.get(ch-1);
			st.execute(query);
		           
		}
		else if(c==2)
		{
			show("ENTER STOCK :");
			int stock=sc.nextInt();
			query="update inventory set "+
		           "stock="+stock+","+
				   "modified_by="+c_id+","+
		           "updatedTime = current_timestamp where id="+inv_id.get(ch-1);
			st.execute(query);
			
		}
		else if(c==3)
		{
			show("ENTER UNIT PRICE");
			int unit_price=sc.nextInt();
			show("ENTER UNIT stock :");
			int stock=sc.nextInt();
			query="update inventory set "+
		           "unit_price="+unit_price+","+
					"stock="+stock+","+
				   "modified_by="+c_id+","+
		           "updatedTime = current_timestamp where id="+inv_id.get(ch-1);
			st.execute(query);
			
		}
		else
		{
			show("INVALID OPTION");
		}
	
	}
	
	ArrayList getInventory(Statement st) throws Exception
	{
		ArrayList<Integer> inv_id=new ArrayList<Integer>();
		 String query="select * from inventory";
		   ResultSet res=st.executeQuery(query);
		   while(res.next())
		   {
			   inv_id.add(res.getInt("id"));
		   }
		   return inv_id;
	}
	

}
