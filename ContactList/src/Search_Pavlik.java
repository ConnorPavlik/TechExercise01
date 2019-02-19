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

@WebServlet("/Search_Pavlik")
public class Search_Pavlik extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Search_Pavlik() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      String column = request.getParameter("column");
      search(column, keyword, response);
   }

   void search(String column, String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title;
      boolean filter;
      if((keyword!=null&&keyword!="")&&column!="All") {
    	  title = "Contacts With "+column +" like: "+keyword+"%";
      	  filter = true;
      }
      else {
    	  title = "All Contacts";
          filter = false;
      }
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

         if (!filter) {
            String selectSQL = "SELECT * FROM contactList";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
        	 String selectSQL = "SELECT * FROM contactList WHERE " + column + " LIKE ?";
            String theKeyword = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theKeyword);
         }
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
            int id = rs.getInt("id");
            String FIRSTNAME = rs.getString("FIRSTNAME").trim();
            String LASTNAME = rs.getString("LASTNAME").trim();
            String CELLPHONE = rs.getString("CELLPHONE").trim();
            String WORKPHONE = rs.getString("WORKPHONE").trim();
            String EMAIL = rs.getString("EMAIL").trim();
            String ADDRESS = rs.getString("ADDRESS").trim();
            String WEBSITE = rs.getString("WEBSITE").trim();

            
            out.println("ID: " + id + ", ");
            out.println("First Name: " + FIRSTNAME + ", ");
            out.println("Last Name: " + LASTNAME + ", ");
            out.println("Cell Phone: " + CELLPHONE + ", ");
            out.println("Work Phone: " + WORKPHONE + ", ");
            out.println("Email: " + EMAIL + ",");
            out.println("Address: " + ADDRESS + ", ");
            out.println("Website: " + WEBSITE + "<br>");
            
         }
         out.println("<button onclick=\"window.location.href='./Search.html'\">Search</button>");
         out.println("<button onclick=\"window.location.href='./Add.html'\">Add Data</button>");
         out.println("<button onclick=\"window.location.href='./Home.html'\">Home</button> <br>");
         out.println("</body></html>");
         rs.close();
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
