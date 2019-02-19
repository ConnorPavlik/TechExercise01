import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Add_Pavlik")
public class Add_Pavlik extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Add_Pavlik() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      search(request, response);
   }

   void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      String firstName = request.getParameter("firstName");
      String lastName = request.getParameter("lastName");
      String cellPhone = request.getParameter("cellPhone"); 
      String workPhone = request.getParameter("workPhone");
      String email = request.getParameter("email"); 
      String address = request.getParameter("address");
      String website = request.getParameter("website");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         
            String selectSQL = "Insert INTO contactList (FIRSTNAME,LASTNAME,CELLPHONE,WORKPHONE,EMAIL,ADDRESS,WEBSITE) VALUES (?,?,?,?,?,?,?)";
            
            preparedStatement = connection.prepareStatement(selectSQL);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, cellPhone);
            preparedStatement.setString(4, workPhone);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, website);
         
         preparedStatement.executeUpdate();
          
         out.println("Contact Added");
         out.println("<button onclick=\"window.location.href='./Search.html'\">Search</button>");
         out.println("<button onclick=\"window.location.href='./Add.html'\">Add Data</button>");
         out.println("<button onclick=\"window.location.href='./Home.html'\">Home</button> <br>");
         out.println("</body></html>");
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
