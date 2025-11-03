import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet{


public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    String heroName = request.getParameter("heroName");
    String password = request.getParameter("password");
    String reEnterPassword = request.getParameter("reEnterPassword");
    String email = request.getParameter("email");
    int userid =0;

if(heroName.length() < 25 && password.equals(reEnterPassword) && reEnterPassword != null && email.contains("@") == true){

 
     
  //   insertInto(heroName, password, email);
     
     Connection connection=null;
     try {
 			connection = DriverManager.getConnection(
 					"jdbc:mysql://localhost:3306/AQWorlds?serverTimezone=UTC","root", "root");
 	        String insert = "INSERT into users (username, password, email)  VALUES (?,?,?)";
 			PreparedStatement userentry = connection.prepareStatement(insert);
 					
 			userentry.setString(1, heroName);
             userentry.setString(2, password);
             userentry.setString(3, email);
             
             
             int rows = userentry.executeUpdate();

             if (rows > 0) {
                 System.out.println("User successfully registered: " + heroName);
             } else {
                 System.out.println("No rows inserted.");
             }
             
            
 			
 			Statement select = connection.createStatement();
 			ResultSet rs = select.executeQuery("SELECT * from users");

			while(rs.next()) {
				System.out.println("Column 1 in ResultSet : "+rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3));
			}
 			
			rs.close();
 			userentry.close();
 			//GET USERID
 			
 			PreparedStatement useridps= connection.prepareStatement("SELECT userid FROM users WHERE username = ?");
 	        useridps.setString(1,heroName);
 	        
 	        ResultSet useridrs = useridps.executeQuery();
 	        
 	        
 	        while(useridrs.next()) {
 	        	
 	        	userid = useridrs.getInt("userid");
 	        }
 	        
 	        useridrs.close();
 	        useridps.close();
 			
 	        // Create user balance
 			PreparedStatement userbalance = connection.prepareStatement("Insert into userbalance(userid) Values(?)" );
 			
 			userbalance.setInt(1, userid);
 			userbalance.executeUpdate();
 			
 			userbalance.close();
 		
 			
 		} catch (SQLException e1) {
 			e1.printStackTrace();
 		}

     response.sendRedirect("Login.html");

    }else {
    	
    	 RequestDispatcher rd = request.getRequestDispatcher("Register.html");
    	 	rd.forward(request, response);
    	
    }


}

}

