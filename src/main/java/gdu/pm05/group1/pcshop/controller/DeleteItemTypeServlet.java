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

@WebServlet (name = "deleteitemtype", urlPatterns = "/deleteitemtype")
public class DeleteItemTypeServlet extends HttpServlet {
    // CONSTRUCTORS:
    public DeleteItemTypeServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validatiom failed
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get id parameter
        String id = request.getParameter("id");

        // Get context
        ServletContext context = request.getServletContext();

        // Get dbHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get ItemType entity with given id
        ItemType type = dbHandler.get(
            ItemType.class,
            new HQLParameter("id", id)
        );

        // Type not exist case
        if (type == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Type that has at least one item belongs to
        if (!type.getItems().isEmpty()) {
            ServletUtil.showMessage(
                request, response,
                "Chỉ có thể xóa loại sản phẩm khi không có sản phẩm nào thuộc về loại sản phẩm này!"
            );
            return;
        }

        // Delete type
        dbHandler.remove(type);

        // Send redirect back to item types management page
        response.sendRedirect("itemtypesmanagement");
    }
}
