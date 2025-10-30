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

            
            
 			
 			 Statement select = connection.createStatement();
 			 ResultSet rs = select.executeQuery("SELECT * from users");

			while(rs.next()) {
				System.out.println("Column 1 in ResultSet : "+rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3));
			}
 			
			rs.close();
 			userentry.close();
 		
 			
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

