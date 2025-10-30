import java.io.IOException;
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

public class Register extends HttpServlet{


public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    String heroName = request.getParameter("heroName");
    String password = request.getParameter("password");
    String reEnterPassword = request.getParameter("reEnterPassword");
    String email = request.getParameter("email");

if(heroName.length() < 25 && password.equals(reEnterPassword) && reEnterPassword != null && email.contains("@") == true){

 
     
     insertInto(heroName, password, email);
     RequestDispatcher rs = request.getRequestDispatcher("Login.html");
 	rs.forward(request, response);
    }


}

private void insertInto(String heroName,String password,String email){
  
    Connection connection=null;
    try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/users?serverTimezone=UTC","root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			PreparedStatement userentry = connection.prepareStatement(
					"INSERT into users(username, password, email) "  +" VALUES (?),(?),(?)");
					
			userentry.setString(1, heroName);
            userentry.setString(2, password);
            userentry.setString(3, email);
            
           
			int rowsUpdated = userentry.executeUpdate();
			
			
			userentry.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}



        
    }
}

