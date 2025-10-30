

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException ,NullPointerException{

        
        String Name = request.getParameter("Name");
        String pass = request.getParameter("pass");
        String userVerify = null;
        String passVerify= null;
        
        
        
        Connection connection=null;

        try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/AQWorlds?serverTimezone=UTC","root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    try{
        PreparedStatement ps = connection.prepareStatement("Select * from users Where username = (?) AND password = (?)");
       
        ps.setString(1,Name);
        ps.setString(2,pass);
        
    
    	
        ResultSet rs = ps.executeQuery();
        
        
        while(rs.next()){
        userVerify = rs.getString("username");
         passVerify = rs.getString("password");
        }
        
        
        if(Name.equals(userVerify) && pass.equals(passVerify)){

        	
        	
        	 rs.close();
             ps.close();
             response.sendRedirect("AQWorld.html");
	 	
        }else {
        	
        	 response.sendRedirect("Login.html")
        	
        	
        }

        }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();



        }
     
    
    


}
}
