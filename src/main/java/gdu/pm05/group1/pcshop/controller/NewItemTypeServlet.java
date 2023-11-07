package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.ItemType;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "newitemtype", urlPatterns = "/newitemtype")
public class NewItemTypeServlet extends HttpServlet {
    // CONSTRUCTORS:
    public NewItemTypeServlet() {
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

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validation failed
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get necessary parameters
        String id = request.getParameter("id");
        String name = request.getParameter("name");

        // Get context
        ServletContext context = request.getServletContext();

        // Get dbHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get item type with given id
        ItemType type = dbHandler.get(
            ItemType.class,
            new HQLParameter("id", id)
        );

        // Item type already exist case
        if (type != null) {
            ServletUtil.showMessage(
                request, response,
                "Đã tồn tại một loại sản phẩm với mã '@id', vui lòng thử lại!".replace(
                    "@id", id
                )
            );
            return;
        }

        // Create new type
        type = new ItemType();

        // Type's values assigning
        type.setId(id);
        type.setName(name);

        // Save type to DB
        dbHandler.save(type);

        // Send redirect
        response.sendRedirect("itemtypesmanagement");
    }
}
