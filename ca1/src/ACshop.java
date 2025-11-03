import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ACshop extends HttpServlet {

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
		 
		 
	
		 
	
		 
		 
				
		 
		 response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.print(
					
					"<!DOCTYPE html>" +
					"<html>" +
					"<head><title>Adventure Coins shop!</title></head>" +
					"<body>" +

					"<h1>Adventure Coins Shop!</h1> "+

					"<form action='ACshop'>" +

					"   <button style='width: 200px; height: 50px;' name='500'>Buy 500 AC Coins</button><br><br>" +
					"   <button style='width: 200px; height: 50px;' name='1500'>Buy 1500 AC Coins</button><br><br>" +
					"   <button style='width: 200px; height: 50px;' name='3000'>Buy 3000 AC Coins</button>" +

					"</form>" +

					"</body>" +
					"</html>"
					);
			
			
			 System.out.println("Current session ID: " + session.getId());
			String	username = (String) session.getAttribute("username");
			   int AC = (int) session.getAttribute("AdventureCoins");
			   int userid = (int) session.getAttribute("userid");
				
			   
			   
			   
			   
			   String coins500 = request.getParameter("500");
				String coins1500 = request.getParameter("1500");
				String coins3000 = request.getParameter("3000");
				
				if(coins500 != null) {
					try {
						PreparedStatement insert = connection.prepareStatement("UPDATE userbalance SET AdventureCoins + 500 WHERE userid = ?");
						
						insert.setInt(1, userid);
						insert.executeUpdate();
						
						insert.close();
						
						 RequestDispatcher rd = request.getRequestDispatcher("AQWorlds");
					  		rd.forward(request, response);
							return;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
							
					
				else if(coins1500 != null) {
						try {
							PreparedStatement insert = connection.prepareStatement("UPDATE userbalance SET AdventureCoins + 1500 WHERE userid = ?");
							
							insert.setInt(1, userid);
							insert.executeUpdate();
							
							insert.close();
							 RequestDispatcher rd = request.getRequestDispatcher("AQWorlds");
						  		rd.forward(request, response);
								return;
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			    
					if(coins3000 != null) {
						try {
							PreparedStatement insert = connection.prepareStatement("UPDATE userbalance SET AdventureCoins + 3000 WHERE userid = ?");
							
							insert.setInt(1, userid);
							insert.executeUpdate();
							
							insert.close();
							
							
							 RequestDispatcher rd = request.getRequestDispatcher("AQWorlds");
						  		rd.forward(request, response);
								return;
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
			}
		 
	
  }
