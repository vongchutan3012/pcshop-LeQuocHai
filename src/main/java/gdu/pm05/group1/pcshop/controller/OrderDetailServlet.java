package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.Order;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.holder.OrderStatusHolder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "orderdetail", urlPatterns = "/orderdetail")
public class OrderDetailServlet extends HttpServlet {
    // CONSTRUCTORS:
    public OrderDetailServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get id parameter from request
        String idStr = request.getParameter("id");

        // ID Null case
        if (idStr == null) {
            ServletUtil.showMessage(
                request, response,
                "Không đủ thông tin yêu cầu, vui lòng thử lại!",
                "red"
            );
            return;
        }

        // Get id as an integer
        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            ServletUtil.showMessage(
                request, response,
                "Thông tin không hợp lệ, vui lòng thử lại!",
                "red"
            );
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get Order entity with given id
        Order order = dbHandler.get(
            Order.class,
            new HQLParameter("id", id)
        );

        // Order not exist case
        if (order == null) {
            ServletUtil.showMessage(
                request, response,
                "Đơn hàng không tồn tại, vui lòng thử lại!",
                "red"
            );
            return;
        }

        // User, Administrator validate
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get user validate result from path
        UserValidationResult userValidateResult = (UserValidationResult)path.get("userValidateResult");

        // Check user validate result
        // User validate failed case
        if (userValidateResult != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showMessage(
                request, response,
                "Vui lòng đăng nhập trước khi truy cập vào trang này!"
            );
            return;
        }

        // User validate successfully case
        // Retrieve user from path
        User user = (User)path.get("user");

        // Request user not order's user case
        if (!order.getUser().getUsername().equals(user.getUsername())) {
            // User not administrator case
            AdministratorValidationResult administratorValidateResult = (AdministratorValidationResult)path.get("administratorValidateResult");
            if (administratorValidateResult != AdministratorValidationResult.IS_ADMINISTRATOR) {
                ServletUtil.showMessage(
                    request, response,
                    "Bạn không có quyền truy cập vào trang này!"
                );
                return;
            }
        }

        // Set order attribute for request
        request.setAttribute("order", order);

        // Set user attribute for request
        request.setAttribute("user", user);

        // Packing all order status
        List<OrderStatusHolder> orderStatuses;
        try {
            orderStatuses = ServletUtil.getAllStatusHolders();
        }
        catch (Exception e) {
            e.printStackTrace();
            ServletUtil.showMessage(request, response, e.toString(), "red");
            return;
        }

        // Add 'OrderStatus' attribute for request
        request.setAttribute("OrderStatus", orderStatuses);

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderdetail.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
