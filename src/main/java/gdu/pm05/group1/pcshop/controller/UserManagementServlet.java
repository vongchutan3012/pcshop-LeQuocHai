package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
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

@WebServlet (name = "usermanagement", urlPatterns = "/usermanagement")
public class UserManagementServlet extends HttpServlet {
    // CONSTRUCTORS:
    public UserManagementServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validation failed case
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get action parameter from request
        String action = request.getParameter("action");

        // Action null case
        if (action == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Invalid action case
        if (
            !action.equals("new")
            &&
            !action.equals("detail")
        ) {
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

        // Action detail case
        if (action.equals("detail")) {
            // Get username parameter
            String username = request.getParameter("username");

            // Username null case
            if (username == null) {
                ServletUtil.showInputRequiredMessage(request, response);
                return;
            }

            // Get context
            ServletContext context = request.getServletContext();

            // Get DBHandler
            IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

            // Get User from given username
            User user = dbHandler.get(
                User.class,
                new HQLParameter("username", username)
            );

            // User null case
            if (user == null) {
                ServletUtil.showDataNotExistMessage(request, response);
                return;
            }

            // Set user attribute for request
            request.setAttribute("user", user);
        }

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/usermanagement.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validation failed case
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get action parameter
        String action = request.getParameter("action");

        // Endpoint definition
        String endpoint = null;
        if (action.equals("new")) {
            endpoint = "newuser";
        }
        else {
            endpoint = "edituser";
        }

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher(endpoint);

        // Forward
        dispatcher.forward(request, response);
    }
}
