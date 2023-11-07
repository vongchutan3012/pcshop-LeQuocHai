package gdu.pm05.group1.pcshop.controller.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.controller.util.enums.GetCartResult;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.enums.OrderStatus;
import gdu.pm05.group1.pcshop.model.enums.UserPermission;
import gdu.pm05.group1.pcshop.model.holder.OrderStatusHolder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class ServletUtil {
    // STATIC METHODS:
    public static Map<String, Object> getCart(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        // Get cart
        return getCart(request, response, true);
    }

    public static Map<String, Object> getCart(
        HttpServletRequest request,
        HttpServletResponse response,
        boolean create
    ) {
        // Path initialization
        Map<String, Object> path = new HashMap<>();

        // Get cart
        getCart(request, response, create, path);

        // Return path
        return path;
    }

    public static void getCart(
        HttpServletRequest request,
        HttpServletResponse response,
        Map<String, Object> path
    ) {
        // Get cart
        getCart(request, response, true, path);
    }

    public static void getCart(
        HttpServletRequest request,
        HttpServletResponse response,
        boolean create,
        Map<String, Object> path
    ) {
        // Get session from request
        HttpSession session = request.getSession(create);
        path.put("session", session);

        // Session null case
        if (session == null) {
            path.put("getCartResult", GetCartResult.NOT_FOUND);
            return;
        }

        // Get user from session
        User user = (User)session.getAttribute("user");
        path.put("user", user);

        // Get cart
        Cart cart = null;
        if (user != null) {
            cart = user.getCart();
            path.put("getCartResult", GetCartResult.USER_CART_FOUND);
        }
        else {
            cart = (Cart)session.getAttribute("cart");
            if (cart == null) {
                if (create) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                    path.put("getCartResult", GetCartResult.NEW_CART_CREATED);
                }
            }
            else {
                path.put("getCartResult", GetCartResult.FOUND);
            }
        }

        if (cart == null) {
            path.put("getCartResult", GetCartResult.NOT_FOUND);
        }
        else {
            path.put("cart", cart);
        }
    }

    public static List<OrderStatusHolder> getAllStatusHolders() throws IllegalArgumentException, IllegalAccessException {
        // List initialization
        List<OrderStatusHolder> list = new ArrayList<>();

        // Getting
        for (Field field : OrderStatus.class.getFields()) {
            list.add(
                new OrderStatusHolder(
                    ((OrderStatus)field.get(null))
                )
            );
        }

        // Return list
        return list;
    }
    
    public static void showDataNotExistMessage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        // Show data not exist message
        showDataNotExistMessage(request, response, "black");
    }

    public static void showDataNotExistMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String color
    ) throws ServletException, IOException {
        // Show message
        showMessage(
            request, response,
            "Dữ liệu này không tồn tại, vui lòng thử lại!",
            color
        );
    }

    public static void showInvalidInputMessage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        // Show invalid input message
        showInvalidInputMessage(request, response, "black");
    }

    public static void showInvalidInputMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String color
    ) throws ServletException, IOException {
        // Show message
        showMessage(
            request, response,
            "Thông tin truy cập không hợp lệ!",
            color
        );
    }

    public static void showInputRequiredMessage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        // Show input required message
        showInputRequiredMessage(request, response, "black");
    }

    public static void showInputRequiredMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String color
    ) throws ServletException, IOException {
        // Show message
        showMessage(
            request, response,
            "Không đủ thông tin truy cập!",
            color
        );
    }

    public static void showPermissionRequiredMessage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        // Show permission required message
        showPermissionRequiredMessage(request, response, "black");
    }

    public static void showPermissionRequiredMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String color
    ) throws ServletException, IOException {
        // Show message
        showMessage(
            request, response,
            "Bạn không có quyền truy cập vào trang này!",
            color
        );
    }

    public static void showLoginRequiredMessage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        // Show login required message
        showLoginRequiredMessage(request, response, "black");
    }

    public static void showLoginRequiredMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String color
    ) throws ServletException, IOException {
        // Show message
        showMessage(
            request, response,
            "Vui lòng đăng nhập trước khi truy cập vào trang này!",
            color
        );
    }

    public static void showMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String message
    ) throws ServletException, IOException {
        // Show message
        showMessage(request, response, message, "black");
    }

    public static void showMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String message,
        String color
    ) throws ServletException, IOException {
        // Show message
        showMessage(request, response, message, color, "");
    }

    public static void showMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String message,
        String color,
        String style
    ) throws ServletException, IOException {
        // Show message
        showMessage(request, response, message, color, style, "message");
    }

    public static void showMessage(
        HttpServletRequest request,
        HttpServletResponse response,
        String message,
        String color,
        String style,
        String forwardTo
    ) throws ServletException, IOException {
        // Set message attribute for request
        request.setAttribute("message", message);

        // Set color attribute for request
        request.setAttribute("color", color);

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardTo);

        // Forward
        dispatcher.forward(request, response);
    }

    public static Map<String, Object> administratorValidate(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        return administratorValidate(request, response, true);
    }

    public static Map<String, Object> administratorValidate(
        HttpServletRequest request,
        HttpServletResponse response,
        boolean logout
    ) {
        // Path initialization
        Map<String, Object> path = new HashMap<>();

        // Administrator validation
        administratorValidate(request, response, logout, path);

        // Return path
        return path;
    }

    public static void administratorValidate(
        HttpServletRequest request,
        HttpServletResponse response,
        Map<String, Object> path
    ) {
        administratorValidate(request, response, true, path);
    }

    public static void administratorValidate(
        HttpServletRequest request,
        HttpServletResponse response,
        boolean logout,
        Map<String, Object> path
    ) {
        // User validation
        userValidate(request, response, logout, path);

        // Get userValidateResult from path
        UserValidationResult userValidateResult = (UserValidationResult)path.get("userValidateResult");

        // User validation failed case
        if (userValidateResult != UserValidationResult.SUCCESSFULLY) {
            path.put("administratorValidateResult", AdministratorValidationResult.USER_VALIDATION_FAILED);
            return;
        }

        // User validation successfully case
        // Get user entity from path
        User user = (User)path.get("user");

        // Check user's permission
        if (user.getPermission() != UserPermission.ADMIN) {
            path.put("administratorValidateResult", AdministratorValidationResult.NOT_ADMINISTRATOR);
            return;
        }

        // Administrator validation successfully
        path.put("administratorValidateResult", AdministratorValidationResult.IS_ADMINISTRATOR);
    }

    /**
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @return Path stores all objects and results during validation processing.
     */
    public static Map<String, Object> userValidate(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        return userValidate(request, response, true);
    }

    /**
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @param logout Logout if already logged-in but not valid
     * @return Path stores all objects and results during validation processing. 
     */
    public static Map<String, Object> userValidate(
        HttpServletRequest request,
        HttpServletResponse response,
        boolean logout
    ) {
        // Path initialization
        Map<String, Object> path = new HashMap<>();

        // User validate
        userValidate(request, response, logout, path);

        // Return path
        return path;
    }

    /**
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @param path Path stores all objects and results during validation processing. 
     */
    public static void userValidate(
        HttpServletRequest request,
        HttpServletResponse response,
        Map<String, Object> path
    ) {
        userValidate(request, response, true, path);
    }

    /**
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @param logout Logout if already logged-in but not valid
     * @param path Path stores all objects and results during validation processing. 
     */
    public static void userValidate(
        HttpServletRequest request,
        HttpServletResponse response,
        boolean logout,
        Map<String, Object> path
    ) {
        // Get session from request
        HttpSession session = request.getSession(false);

        // Session null case
        if (session == null) {
            path.put("userValidateResult", UserValidationResult.NOT_LOGGED_IN);
            return;
        }

        // Get user attribute from session
        User user = (User)session.getAttribute("user");

        // User null
        if (user == null) {
            path.put("userValidateResult", UserValidationResult.NOT_LOGGED_IN);
            return;
        }

        path.put("user", user);

        // Get context
        ServletContext context = request.getServletContext();
        path.put("context", context);

        // Get dbHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");
        path.put("dbHandler", dbHandler);

        // Store current password
        String password = user.getPassword();
        path.put("password", password);

        // Refresh user
        dbHandler.refresh(user);

        // Check password
        if (!user.getPassword().equals(password)) {
            // Logout or not depending on parameter
            if (logout) {
                session.removeAttribute("user");
            }
            // Set result for this validation
            path.put(
                "userValidateResult",
                UserValidationResult.PASSWORD_NOT_MATCH
            );
            return;
        }

        // Set userValidateResult as true for successfully
        path.put(
            "userValidateResult",
            UserValidationResult.SUCCESSFULLY
        );
    }

    public static String readPartAsString(Part part) {
        // Read part as bytes
        byte[] bContent = readPartAsBytes(part);

        // Convert byte content to string content
        String content = new String(bContent);

        // Return content
        return content;
    }

    public static byte[] readPartAsBytes(Part part) {
        // Get part's input stream
        InputStream input = null;
        try {
            input = part.getInputStream();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Read all bytes
        byte[] content = null;
        try {
            content = input.readAllBytes();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Return content
        return content;
    }

    // CONSTRUCTORS:
    private ServletUtil() {

    }
}
