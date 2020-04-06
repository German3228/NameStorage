/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jsoncreator.BookJsonBuilder;
import org.omg.CORBA.TIMEOUT;
import session.BookFacade;
import session.UserFacade;

/**
 *
 * @author user
 */
@WebServlet(name = "UserController", loadOnStartup = 1, urlPatterns = {
    "/getListNewBooks",
    "/getListCustomers",
    "/createBook",
    "/createUser",
    "/authorizaton",
    "/logout",
    "/editUser"
    
})
public class UserController extends HttpServlet {
    @EJB private BookFacade bookFacade;
    @EJB private UserFacade userFacade;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String json = "";
        String path = request.getServletPath();
        switch (path) {
            case "/getListNewBooks":
                List<Book> listNewBooks = bookFacade.findNewBooks();
                BookJsonBuilder bookJsonBuilder = new BookJsonBuilder();
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                for (Book book : listNewBooks) {
                    arrayBuilder.add(bookJsonBuilder.createJsonObject(book));
                }
                JsonObjectBuilder jsonBooksBuilder = Json.createObjectBuilder();
                jsonBooksBuilder.add("books", arrayBuilder);
                try (Writer writer = new StringWriter()) {
                    Json.createWriter(writer).write(jsonBooksBuilder.build());
                    json = writer.toString();
                }
                break;
            case "/createBook":
                String caption = request.getParameter("caption");
                String author = request.getParameter("author");
                String publishedYear = request.getParameter("publishedYear");
                String cover = request.getParameter("cover");
                String text = request.getParameter("text");
                String active = request.getParameter("active");
                Integer year23 = new Integer(publishedYear);
                Book book = new Book(
                        caption,
                        author,
                        year23,
                        cover,
                        text
                );
                bookFacade.create(book);
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;

            case "/editUser":
                String fname2 = request.getParameter("fname");
                String lname2 = request.getParameter("lname");
                String pnumber2 = request.getParameter("pnumber");
                String login2 = request.getParameter("login");
                String password2 = request.getParameter("password");
                User userCheck = userFacade.findByLogin(login2);
                if (!userCheck.getFname().equals(fname2)) {
                    userCheck.setFname(fname2);
                }
                if (!userCheck.getLname().equals(lname2)) {
                    userCheck.setLname(lname2);
                }
                if (!userCheck.getPnumber().equals(pnumber2)) {
                    userCheck.setPnumber(pnumber2);
                }
                if (!userCheck.getLogin().equals(login2)) {
                    userCheck.setLogin(login2);
                }
                if (!userCheck.getPassword().equals(password2)) {
                    userCheck.setPassword(password2);
                }
                userFacade.edit(userCheck);
                request.setAttribute("info", "<div class=\"alert alert-success\" role=\"alert\">\n"
                        + "Данные пользователя изменены!\n"
                        + "</div>");
                request.getRequestDispatcher("/redirect.jsp")
                        .forward(request, response);
                break;

            case "/createUser":
                String fname = request.getParameter("fname");
                String lname = request.getParameter("lname");
                String pnumber = request.getParameter("pnumber");
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String salts = request.getParameter("salts");
                User user = new User(
                        fname,
                        lname,
                        pnumber,
                        login,
                        password,
                        salts,
                        true
                );
                userFacade.create(user);

                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;
            case "/logout":
                Cookie cookie = new Cookie("user", "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                request.getRequestDispatcher("/redirect.jsp")
                        .forward(request, response);
                break;
            case "/authorizaton":
                String login1 = request.getParameter("login");
                String password1 = request.getParameter("password");
                List<User> abc = userFacade.findAll();
                boolean checker = true;
                List<String> users = new ArrayList<String>();
                for (int i = 0; i < abc.size(); i++) {
                    User item = abc.get(i);
                    String log = item.getLogin();
                    String pass = item.getPassword();

                    if (log.equals(login1) && pass.equals(password1)) {
                        users.add(item.getFname() + ":" + item.getLname() + ":" + item.getPnumber() + ":" + item.getLogin() + ":" + item.getPassword());
                        System.out.println("YEP");
                    }
                }

                if (users.size() != 0) {
                    Cookie loginCookie = new Cookie("user", users.get(0));
                    loginCookie.setMaxAge(30 * 60);
                    response.addCookie(loginCookie);
                    request.getRequestDispatcher("/redirect.jsp")
                            .forward(request, response);
                    break;
                }
                request.setAttribute("info", "<div class=\"alert alert-danger\" role=\"alert\">\n"
                        + "Неверный логин или пароль!!\n"
                        + "</div>");
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;

        }
        
        try (PrintWriter out = response.getWriter()) {
          out.println(json);        
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
