

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
        
        
        
        Connection connection=null;

        try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/users?serverTimezone=UTC","root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    try{
        PreparedStatement ps = connection.prepareStatement("Select userid, username, password from users Where username = (?) ");
       
        ResultSet rs = ps.executeQuery();
        ps.setString(1,Name);
       
       
       
       
        
        while(rs.next()){
        String userVerify = rs.getString(1);
        String passVerify = rs.getString(2);
        
                if(Name == userVerify && pass == passVerify ){

                	  RequestDispatcher rd = request.getRequestDispatcher("Login.html");
                	 	rd.forward(request, response);
                      
                      rs.close();
                      ps.close();

                }
        }
          

        }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();



        }
     
    
    


}
}
