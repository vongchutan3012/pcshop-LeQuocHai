package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet (name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    // CONSTRUCTORS:
    public LoginServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get User validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // Logged in case
        if (result == UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showMessage(
                request, response,
                "Bạn đã đăng nhập!"
            );
            return;
        }

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get servlet context
        ServletContext context = request.getServletContext();

        // Get dbHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get parameters
        String username = request.getParameter("email");
        String password = request.getParameter("password");

        // Get user from username
        User user = dbHandler.get(
            User.class,
            new HQLParameter("username", username)
        );

        // User not exist case
        if (user == null) {
            request.setAttribute(
                "message",
                "Tài khoản không tồn tại!"
            );
            this.doGet(request, response);
            return;
        }

        // Wrong password case
        if (!user.getPassword().equals(password)) {
            request.setAttribute(
                "message",
                "Sai mật khẩu!"
            );
            this.doGet(request, response);
            return;
        }

        // Login successfully
        // Get or create new session
        HttpSession session = request.getSession(true);
        
        // Set user as an attribute for session
        session.setAttribute("user", user);

        // Send redirect to home page
        response.sendRedirect("home");
    }
}
