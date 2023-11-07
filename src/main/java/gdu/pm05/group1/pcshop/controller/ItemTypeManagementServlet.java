package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.ItemType;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "itemtypemanagement", urlPatterns = "/itemtypemanagement")
public class ItemTypeManagementServlet extends HttpServlet {
    // CONSTRUCTORS:
    public ItemTypeManagementServlet() {
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

        // Validation failed case
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get necessary parameters
        String action = request.getParameter("action");

        // Action null case
        if (action == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Action invalid case
        if (
            !action.equals("new")
            &&
            !action.equals("detail")
        ) {
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

        // External conditions for detail action case
        if (action.equals("detail")) {
            // Get Id parameter
            String id = request.getParameter("id");

            // ID null case
            if (id == null) {
                ServletUtil.showInputRequiredMessage(request, response);
                return;
            }

            // Get context
            ServletContext context = request.getServletContext();

            // Get DBHandler
            IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

            // Get item type with given id
            ItemType type = dbHandler.get(
                ItemType.class,
                new HQLParameter("id", id)
            );

            // Type not exist case
            if (type == null) {
                ServletUtil.showDataNotExistMessage(request, response);
                return;
            }

            // Set type attribute for request
            request.setAttribute("type", type);
        }

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/itemtypemanagement.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get action parameter
        String action = request.getParameter("action");

        // Get endpoint from action parameter
        String endpoint = null;
        if (action.equals("new")) {
            endpoint = "newitemtype";
        }
        else {
            endpoint = "edititemtype";
        }

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher(endpoint);

        // Forward
        dispatcher.forward(request, response);
    }
}
