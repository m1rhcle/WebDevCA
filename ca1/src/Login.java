

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException ,NullPointerException{

        
        String Name = request.getParameter("Name");
        String pass = request.getParameter("pass");
        String userVerify = null;
        String passVerify= null;
     int gold = 0;
     int AC = 0;
    int userid = 0; 
        
        
        
        Connection connection=null;

        try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/AQWorlds?serverTimezone=UTC","root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    try{
    	// PASSWORD VERIFY
    	
    	
        PreparedStatement ps = connection.prepareStatement("Select * from users Where username = ? AND password = ?");
        
        
        
        ps.setString(1,Name);
        ps.setString(2,pass);
        
    
    	
        ResultSet rs = ps.executeQuery();
        
       
        
        while(rs.next()){
        userVerify = rs.getString("username");
         passVerify = rs.getString("password");
        }
        
        
        if(!Name.equals(userVerify) && !pass.equals(passVerify)){
        	RequestDispatcher rd = request.getRequestDispatcher("Login.html");
     	 	rd.forward(request, response);
        	
        	
        }else {
        	
        	
        	 rs.close();
             ps.close();
        	
        }
        
        //USER ID 
        
        
        PreparedStatement useridps= connection.prepareStatement("SELECT userid FROM users WHERE username = ?");
        useridps.setString(1,Name);
        
        ResultSet useridrs = useridps.executeQuery();
        
        
        while(useridrs.next()) {
        	
        	userid = useridrs.getInt("userid");
        }
        
        useridrs.close();
        useridps.close();
        
        
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
		
		
        }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

        }
    
    
    HttpSession session = request.getSession();
    session.setAttribute("username", Name);
    session.setAttribute("gold", gold);
	 session.setAttribute("AdventureCoins", AC);

    
    
    
  
	
	if (session != null && session.getAttribute("username") != null) {
		
		session.getAttribute("username");
	    session.getAttribute("AdventureCoins");
		 session.getAttribute("gold");
		
		
	}
	

	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	
	out.print(
			"<!DOCTYPE html>" +
			"<html>" +
			"<head><title>Adventure Quest Worlds!</title></head>" +
			"<body>" +

			"<h1>Welcome to Adventure Quest World " + Name + "!</h1>" + "<h2>Gold: " + gold + " | AC: " + AC + "</h2>" +

			"<form action='shop'>" +

			"    <button style='width: 200px; height: 50px;' name='shop1'>Mystical Yokai Warrior</button><br><br>" +
			"    <button style='width: 200px; height: 50px;' name='shop2'>Abyssal Angel</button><br><br>" +
			"    <button style='width: 200px; height: 50px;' name='ACshop'>Buy AC Now!</button>" +

			"</form>" +

			"</body>" +
			"</html>"
			);

	String shop1 = request.getParameter("shop1");
	String shop2 = request.getParameter("shop2");
	String ACshop = request.getParameter("ACshop");
	
	if(ACshop != null) {
    	RequestDispatcher rs = request.getRequestDispatcher("ACshop.html");
    	rs.forward(request, response);
     
    
	}
	
	if(shop1 != null) {
    	RequestDispatcher rs = request.getRequestDispatcher("shop1.html");
    	rs.forward(request, response);
     
    
	}
	
	if(shop2 != null) {
    	RequestDispatcher rs = request.getRequestDispatcher("shop2.html");
    	rs.forward(request, response);
     
    
	}
	


}
}
