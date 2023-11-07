package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet (name = "logout", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    // CONSTRUCTORS:
    public LogoutServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get user validation result from path
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // Not logged in case
        if (result != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showMessage(
                request, response,
                "Hiện tại, bạn vẫn chưa đăng nhập!"
            );
            return;
        }

        // Get session from path
        HttpSession session = request.getSession(false);
        
        // Logging out
        if (session != null) {
            // Remove user attribute from session
            session.removeAttribute("user");
        }

        // Send redirect back to home page
        response.sendRedirect("home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
