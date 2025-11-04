import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AAshop extends HttpServlet{

	
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException ,NullPointerException{
		 
		 HttpSession session = request.getSession(false);
		

		 Connection connection=null;
		 try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/AQWorlds?serverTimezone=UTC","root", "root");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		
			 int shopid = 2; 
		
			
				int itemid = 0;
				
				
					String Name = (String) session.getAttribute("username");
				   int AC = (int) session.getAttribute("AdventureCoins");
				int	gold = (int) session.getAttribute("gold");
					int userid = (int) session.getAttribute("userid");
				
					try {
					
						
						PreparedStatement Shop = connection.prepareStatement("SELECT i.itemid, i.itemName, i.price, i.currencytype " +
							    "FROM shopitems si " +
							    "JOIN items i ON si.itemid = i.itemid " +
							    "WHERE si.shopid = ?" );
						Shop.setInt(1,shopid);
						
						ResultSet rs = Shop.executeQuery();
						
			 response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
				out.print(
						
						"<!DOCTYPE html>" +
						"<html>" +
						"<head><title>Abyssal Angel Shop!</title></head>" +
						"<body>" +

						"<h1>Abyssal Angel Shop!</h1> "+

						"<form action='AAshop'>" 
			          + "<h2>Gold: " + gold + " | AC: " + AC + "</h2>" 
				);
				
				
				while(rs.next()) {
					itemid = rs.getInt("itemid");
					String itemname = rs.getString("itemName");
	                int price = rs.getInt("price");
	                String currencytype = rs.getString("currencytype");
					
				
                out.print("<button style='width: 250px; height: 35px;' name='itemid' value ='" + itemid + "'>" + itemname + " - " + price + " " + currencytype + " </button><br>" );
                
				}
					out.print(	
						"<br><button style='width: 200px; height: 25px;' name='home'>Go back To Home!</button>" +
						"</form>" +
						"</body>" +
						"</html>"
						);
				
				rs.close();
				Shop.close();
				
				  PreparedStatement goldAC = connection.prepareStatement("SELECT gold, AdventureCoins FROM userbalance WHERE userid = ?");
			        
			        goldAC.setInt(1, userid);
					ResultSet result = goldAC.executeQuery();

					while(result.next()) {
						 gold = result.getInt("gold");
						 AC = result.getInt("AdventureCoins");
				
					}
					result.close();
					goldAC.close();
					
					
					
					String home = request.getParameter("home");
	                
	                 if (home != null) {
						
	                	response.sendRedirect("AQWorlds");
					  		return;
						
						
					}
					 String itemId = request.getParameter("itemid");
					 
					 
					
					
					if (itemId != null) {
						
						 itemid = Integer.parseInt(itemId);
						 
						 PreparedStatement item = connection.prepareStatement( "SELECT itemName, price, currencytype FROM items WHERE itemid = ?");
			                item.setInt(1, itemid);
			                ResultSet results = item.executeQuery();

			                if (results.next()) {
			                   String itemname = results.getString("itemname");
			                    int price = results.getInt("price");
			                    String currency = results.getString("currencytype");

			                   

			                    boolean canbuy = false;

			                    
			                    if (currency.equals("Gold")) {
			                    	
			                        if (gold >= price) {
			                            gold -= price;
			                            canbuy = true;
			                            response.setContentType("text/html");
				         				PrintWriter out2 = response.getWriter();
				         				
				         				out2.print("<html><body> <h3>You Just Bought " + itemname + "</h3> </body></html>");
			                            
			                        }else {
				                    	
				                    	 response.setContentType("text/html");
				         				PrintWriter out2 = response.getWriter();
				         				
				         				out2.print("<html><body> <h3>Broke.</h3> </body></html>");
				                    	
				                    }
			                        
			                        
			                    } else if (currency.equals("AdventureCoins")) {
			                    	
			                        if (AC >= price) {
			                            AC -= price;
			                            canbuy = true;
			                            
			                            response.setContentType("text/html");
				         				PrintWriter out2 = response.getWriter();
				         				
				         				out2.print("<html><body> <h3>You Just Bought " + itemname + "</h3> </body></html>");
			                            
			                        
			                       }else {
				                    	
				                    	 response.setContentType("text/html");
				         				PrintWriter out2 = response.getWriter();
				         				
				         				out2.print("<html><body> <h3>Broke.</h3> </body></html>");
				                    	
				                    }
			                       
			                    }

			                    if (canbuy) {
			                        PreparedStatement updateBalance = connection.prepareStatement("UPDATE userbalance SET gold = ?, AdventureCoins = ? WHERE userid = ?");
			                        updateBalance.setInt(1, gold);
			                        updateBalance.setInt(2, AC);
			                        updateBalance.setInt(3, userid);
			                        updateBalance.executeUpdate();
			                        
			                    }

			                    results.close();
			                    item.close();
			                    
			                    
			                    
			                }
			                
			                


					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				   
				   
				  
}
	 

