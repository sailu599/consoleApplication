package consoleApplication;
import java.sql.*;
import java.util.*;
public class Orders {
    Scanner sc=new Scanner(System.in);
    ArrayList<Integer> items=new ArrayList<>();	
    Orders(Statement st)
    {
    	String query="select * from inventory";
    	try {
		   ResultSet res=st.executeQuery(query);
		   while(res.next())
		   {
			    items.add(res.getInt("id"));   
		   }
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
	void addOrder(Statement st,int c_id,Inventory i) throws Exception
	{
		   i.show("select the product to add :\n");
		   i.showInventory(st);
		   int ch=sc.nextInt();
		   i.show("Enter the count: ");
		   int q=sc.nextInt();
		   i.show("\n");
		   if(ch>items.size())
		   {
			    i.show("invalid option\n");
			    return;
		   }
		   String query="insert into items(p_id,c_id,quantity) values ("+items.get(ch-1)+","+c_id+","+q+")";
		   st.execute(query);
		  i. show("Added Sucessfully\n");
	}
	
	
	void showOrders(Statement st,int c_id) throws Exception
	{ 
		String query="select inventory.name,items.quantity from items join inventory on items.p_id=inventory.id and  items.c_id="+c_id;
		ResultSet res=st.executeQuery(query);
		while(res.next())
		{
			System.out.println(res.getString("name")+" "+res.getInt("quantity"));
		}
	}
	
	void deleteOrder(Statement st,int c_id) throws Exception
	{
		//optimisation needed
		ArrayList<Integer> orders=new ArrayList<>();
		String query="select inventory.name,items.quantity,items.p_id from items join inventory on items.p_id=inventory.id and  items.c_id="+c_id;
		ResultSet res=st.executeQuery(query);
		System.out.println("YOUR ORDERS ");
		int c=1;
		while(res.next())
		{
			System.out.println(c+". "+res.getString("name")+" "+res.getInt("quantity"));
			orders.add(res.getInt("p_id"));
			c++;
			
		}
		System.out.print("Enter the product to delete: ");
		int del=sc.nextInt();
		query="delete from items where c_id="+c_id+" AND "+"p_id = "+ orders.get(del-1);
	    st.execute(query);
		System.out.print("The product deleted Sucessfully: ");
		
		
	}
	
	void purchase(Statement st,int c_id) throws Exception
	{
		
		String query="select inventory.stock,inventory.unit_price,inventory.name,items.quantity,items.p_id from items join inventory on items.p_id=inventory.id and  items.c_id="+c_id;
		ResultSet res=st.executeQuery(query);
		int total=0;
		ResultSet temp=res;
		ArrayList<String> p_name=new ArrayList<>();
		ArrayList<Integer> quantity=new ArrayList<>();
		ArrayList<Integer> price=new ArrayList<>();
		while(temp.next())
		{
			int n=price.size();
			price.add(temp.getInt("unit_price"));
			quantity.add(temp.getInt("quantity"));
			p_name.add(temp.getString("name"));
			if(quantity.get(n)>res.getInt("stock"))
			{
				System.out.println("YOUR ORDER IS HIGHER THAN AVAILAABLE STOCK ");
				System.out.println("AVAILABLE = "+res.getInt("stock")+"YOUR ORDER = "+quantity.get(n));
			}
			System.out.println(temp.getString("name")+" "+price.get(n)+" "+quantity.get(n)+"  "+price.get(n)*quantity.get(n));
			total+=(price.get(n)*quantity.get(n));
		}
	   System.out.println("TOTAL = "+total);
		System.out.println("1.PURCHASE\n"
							+ "2.CANCEL");
		System.out.println("Enter Your Choice: ");
		int ch=sc.nextInt();
		if(ch==1)
		{
			
			query="select amount,pin from wallet where c_id="+c_id;
			 res=st.executeQuery(query);
			 res.next();
			 System.out.print("Enter PIN: ");
			 int pin=sc.nextInt();
			 if(res.getInt(2)!=pin)
			 {
				System.out.print("WORNG PIN");
				return;
			 }
			 int amount=res.getInt(1);
			 if(amount>=total)
			 {		 
					query="insert into bill(c_id,total) values("+c_id+","+total+")";
					st.execute(query);
					ResultSet key=st.executeQuery("select last_insert_id()");
					key.next();			
					int bill_id=key.getInt(1);
					for(int i=0;i<p_name.size();i++)
					{
						System.out.println("comin");
						query="insert into bill_items values("
								+bill_id+","+"\""+
								p_name.get(i)+"\","+
								price.get(i)+","+
								quantity.get(i)+")";
					  st.execute(query);
					  query="update inventory set stock=stock-"+quantity.get(i)+" where name=\""+p_name.get(i)+"\"";
					  System.out.print(query);
					  st.execute(query);
					}
					query="update wallet set amount=amount-"+total+", credit="+total/10+" where c_id="+c_id;
					st.execute(query);
					System.out.println(query);
					System.out.println("Sucessfully Purchased");
			 }
			 else
			 {
				 System.out.print("INSUFFICIENT BALANCE "+amount);
			 }
			
		}
		
		}
	void showHistory(Statement st,int c_id)
	{
		try {
			String query="select bill.date,bill.bill_id,bill.total,bill_items.product_name,bill_items.price,bill_items.count \r\n"
					+ "    from bill\r\n"
					+ "    join bill_items \r\n"
					+ "    on bill.bill_id=bill_items.bill_id AND bill.c_id="+c_id;
			ResultSet res=st.executeQuery(query);
			int prev=0;
			while(res.next())
			{
				if(prev!=res.getInt("bill_id"))
				{
                   System.out.println("\n\nTOTAL AMOUNT = "+res.getInt("total"));
                   System.out.println("DATE OF PURCHASE = "+res.getTimestamp("date"));
                   prev=res.getInt("bill_id");
				}
			
					System.out.println(res.getString("product_name")+" "+
				                        res.getInt("price")+" "+
							            res.getInt("count")+" "+
				                        res.getInt("price")*res.getInt("count"));
			}
		}
		catch(Exception e)
		{
		    System.out.print(e.getMessage());	
		}
		
	}
	
}
