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

@WebServlet("/Clear_Pavlik")
public class Clear_Pavlik extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Clear_Pavlik() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      search(request, response);
   }

   void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      String title = "Database Clearing";
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

         
         String selectSQL = "Delete FROM contactList";
            
         preparedStatement = connection.prepareStatement(selectSQL);

         preparedStatement.executeUpdate();
          
         out.println("Database Cleared");
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
