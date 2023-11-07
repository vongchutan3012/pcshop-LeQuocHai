package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.ItemType;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "itemtypesmanagement", urlPatterns = "/itemtypesmanagement")
public class ItemTypesManagementServlet extends HttpServlet {
    // CONSTRUCTORS:
    public ItemTypesManagementServlet() {
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

        // Get context
        ServletContext context = request.getServletContext();

        // Get dbHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get all item types
        List<ItemType> types = dbHandler.getAll(ItemType.class);

        // Set types attribute for request
        request.setAttribute("types", types);

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/itemtypesmanagement.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
