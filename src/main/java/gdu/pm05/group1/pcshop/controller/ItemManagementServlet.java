package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.ItemType;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "itemmanagement", urlPatterns = "/itemmanagement")
@MultipartConfig
public class ItemManagementServlet extends HttpServlet {
    // CONSTRUCTORS:
    public ItemManagementServlet() {
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

        // ADMINISTRATOR VALIDATION FAILED CASE
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // VALIDATION SUCCESSFULLY
        // Get action parameter
        String action = request.getParameter("action");

        // Action null case
        if (action == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Invalid action case
        if (
            !action.equals("new") &&
            !action.equals("detail")
        ) {
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

        // Get dbHandler
        ServletContext context = request.getServletContext();
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Action detail case
        if (action.equals("detail")) {
            // Get ID parameter
            String id = request.getParameter("id");

            // Id null case
            if (id == null) {
                ServletUtil.showInputRequiredMessage(request, response);
                return;
            }

            // Get item with given id
            Item item = dbHandler.get(
                Item.class,
                new HQLParameter("id", id)
            );

            // Item null case
            if (item == null) {
                ServletUtil.showDataNotExistMessage(request, response);
                return;
            }

            // Set item as an attribute to request
            request.setAttribute("item", item);
        }

        // Get all item types
        List<ItemType> itemTypes = dbHandler.getAll(ItemType.class);

        // Set itemTypes attribute for request
        request.setAttribute("itemTypes", itemTypes);

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher(
            "WEB-INF/jsp/itemmanagement.jsp"
        );

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get action parameter
        String action = ServletUtil.readPartAsString(
            request.getPart("action")
        );

        // Endpoint location definition
        String endpoint = null;

        // Action new case
        if (action.equals("new")) {
            endpoint = "newitem";
        }
        // Action detail case
        else if (action.equals("detail")) {
            endpoint = "edititem";
        }

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher(endpoint);

        // Forward
        dispatcher.forward(request, response);
    }
}
