package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.UserInfo;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.enums.UserPermission;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "newuser", urlPatterns = "/newuser")
public class NewUserServlet extends UserManagementServlet {
    // CONSTRUCTORS:
    public NewUserServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // METHOD NOT ALLOWED
        response.setStatus(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get Administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validation failed
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get necessary parameter
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        boolean gender = request.getParameter("gender").equals("Male");
        String phoneNumbers = request.getParameter("phoneNumbers");
        String address = request.getParameter("address");

        // Check input
        if (!phoneNumbers.matches("^0\\d{9}$")) {
            request.setAttribute(
                "message",
                "Số điện thoại không hợp lệ!"
            );
            super.doGet(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get user with given username
        User user = dbHandler.get(
            User.class,
            new HQLParameter("username", username)
        );

        // User already exist case
        if (user != null) {
            request.setAttribute(
                "message",
                "Đã tồn tại tài khoản với email '@username'".replace(
                    "@username", username
                )
            );
            super.doGet(request, response);
            return;
        }

        // Create new user
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPermission(UserPermission.CUSTOMER);

        // Create new UserInfo for user
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setFullName(fullName);
        userInfo.setGender(gender);
        userInfo.setPhoneNumbers(phoneNumbers);
        userInfo.setAddress(address);

        // Create new Cart for user
        Cart cart = new Cart();
        cart.setUsername(username);

        // Set relationships for entities
        user.setUserInfo(userInfo);
        userInfo.setUser(user);
        cart.setUser(user);

        // Save entities
        dbHandler.save(user, userInfo, cart);

        // Send redirect to usersmanagement
        response.sendRedirect("usersmanagement");
    }
}
