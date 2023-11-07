package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.CartItem;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.ItemImage;
import gdu.pm05.group1.pcshop.model.OrderItem;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "deleteitem", urlPatterns = "/deleteitem")
public class DeleteItemServlet extends HttpServlet {
    // CONSTRUCTORS
    public DeleteItemServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get user administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Validation failed case
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get id parameter from request
        String id = request.getParameter("id");

        // Id null case
        if (id == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get dbHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get Item from given id
        Item item = dbHandler.get(
            Item.class,
            new HQLParameter("id", id)
        );

        // Item not exist case
        if (item == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Get related entities
        ItemImage avatar = item.getAvatar();
        Set<ItemImage> images = item.getImages();
        Set<CartItem> carts = item.getCarts();
        Set<OrderItem> orders = item.getOrders();

        // Check relationship conditions
        // Orders
        if (orders.size() > 0) {
            ServletUtil.showMessage(
                request, response,
                "Vật phẩm này hiện đang thuộc một hoặc một số đơn hàng bất kỳ! Không thể xóa!"
            );
            return;
        }

        // Carts
        if (carts.size() > 0) {
            ServletUtil.showMessage(
                request, response,
                "Vật phẩm này hiện đang thuộc một hoặc một số giỏ hàng! Không thể xóa!"
            );
            return;
        }

        // Delete all images
        for (ItemImage image : images) {
            dbHandler.remove(image);
        }

        // Deleting
        dbHandler.remove(item, avatar);

        // Send redirect to items management page
        response.sendRedirect("itemsmanagement");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
