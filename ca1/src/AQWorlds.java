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

public class AQWorlds  extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		HttpSession session = request.getSession(false);
		 Connection connection=null;
		 try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/AQWorlds?serverTimezone=UTC","root", "root");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		
		
		
		
		String Name = "";
		int AC = 0;
		int gold = 0;
		int userid = 0;
		String pass = "";
		String userVerify = "";
		String passVerify = "";
		
if (session != null && session.getAttribute("username") != null) {
			
	System.out.println("Current session ID: " + session.getId());
			Name = (String) session.getAttribute("username");
		    AC = (int) session.getAttribute("AdventureCoins");
			gold = (int) session.getAttribute("gold");
			userid = (int) session.getAttribute("userid");
			pass = (String) session.getAttribute("password");
			userVerify = (String) session.getAttribute("userVerify");
			passVerify = (String) session.getAttribute("userVerify");
			
			
			
			try {
				
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
				out.print(
						
						"<!DOCTYPE html>" +
						"<html>" +
						"<head><title>Adventure Quest Worlds!</title></head>" +
						"<body>" +

						"<h1>Welcome to Adventure Quest World " + Name + "!</h1>" + "<h2>Gold: " + gold + " | AC: " + AC + "</h2>" +

						"<form action='AQWorlds'>" +

						"    <button style='width: 200px; height: 50px;' name='MywShop'>Mystical Yokai Warrior</button><br><br>" +
						"    <button style='width: 200px; height: 50px;' name='AAshop'>Abyssal Angel</button><br><br>" +
						"    <button style='width: 200px; height: 50px;' name='ACshop'>Buy AC Now!</button>" +

						"</form>" +

						"</body>" +
						"</html>"
						);
				
				
			        
			        //GOLD AND AC
			        
			        PreparedStatement goldAC = connection.prepareStatement("SELECT gold, AdventureCoins FROM userbalance WHERE userid = ?");
			        
			        goldAC.setInt(1, userid);
					ResultSet result = goldAC.executeQuery();

					while(result.next()) {
						 gold = result.getInt("gold");
						 AC = result.getInt("AdventureCoins");
				
					}
					result.close();
					goldAC.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

					String shop1 = request.getParameter("MywShop");
					String shop2 = request.getParameter("AAshop");
					String ACshop = request.getParameter("ACshop");
					
					if(ACshop != null) {
				    	
						
						
						response.sendRedirect("ACshop");
				 	 	return;
				 	 	
				 	 
						
							
						}
									
							
						
				  
					
					if(shop1 != null) {
						response.sendRedirect("MywShop");
				    	
				     return;
				    
					}
					
					if(shop2 != null) {
				    	 response.sendRedirect("AAshop");
				    	return;
					}
		}
		
		
		
		
		
		
		

}
	}
