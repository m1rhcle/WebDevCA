

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
			
			
			
			 
			    	// PASSWORD VERIFY
			    	
			    	
			        PreparedStatement ps = connection.prepareStatement("Select * from users Where username = ? AND password = ?");
			        
			        
			        
			        ps.setString(1,Name);
			        ps.setString(2,pass);
			        
			    
			    	
			        ResultSet rs = ps.executeQuery();
			        
			       
			        
			        if(rs.next()){
			        userVerify = rs.getString("username");
			         passVerify = rs.getString("password");
			        }
			        
			        
			        if (userVerify == null || passVerify == null) {
			    	   
			    	    RequestDispatcher rd = request.getRequestDispatcher("Login.html");
			    	    rd.forward(request, response);
			    	    return; 
			    	
			        	
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
					
					
			      
			    
			    
			    
			    
			    HttpSession session = request.getSession();
			    session.setAttribute("username", Name);
			    session.setAttribute("gold", gold);
				 session.setAttribute("AdventureCoins", AC);
			    session.setAttribute("userid", userid);
			    session.setAttribute("password", pass);
			    session.setAttribute("VerifyName", userVerify);
			    session.setAttribute("VerifyName", passVerify);
			    
			    
			    
			  
				
				if (session != null && session.getAttribute("username") != null) {
					 
					response.sendRedirect("AQWorlds");
			  	
					return;
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
   
	
	
	

	
	}
    }


    
	
    
