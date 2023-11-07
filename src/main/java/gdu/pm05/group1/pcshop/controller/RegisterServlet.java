package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.CartItem;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.UserInfo;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.enums.UserPermission;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet (name="register", urlPatterns="/register")
public class RegisterServlet extends HttpServlet {
    // CONSTRUCTORS:
    public RegisterServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get user validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // User logged-in case
        if (result == UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showMessage(
                request, response,
                "Hiện tại, bạn đang trong trạng thái đăng nhập!"
            );
            return;
        }

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get session without creation
        HttpSession session = request.getSession(false);

        // Get parameters from request
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String genderStr = request.getParameter("sex");
        Boolean gender = (
            (genderStr != null)
            ? genderStr.equals("Male")
            : true
        );
        String phoneNumbers = request.getParameter("phoneNumber");
        String address = request.getParameter("address");

        // Validate phoneNumbers
        if (!phoneNumbers.matches("^0{1}\\d{9}$")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ!");
            this.doGet(request, response);
            return;
        }
        
        // Get user from db with given username
        User user = dbHandler.get(
            User.class,
            new HQLParameter("username", username)
        );

        // User already exist case
        if (user != null) {
            request.setAttribute(
                "message",
                "Người dùng '@username' đã tồn tại!".replace("@username", username)
            );
            this.doGet(request, response);
            return;
        }

        // Create new user
        user = new User();

        // Create new user info
        UserInfo userInfo = new UserInfo();

        // Get or create a new cart
        Cart cart = null;
        Set<CartItem> cartItems = null;
        if (session != null) {
            cart = (Cart)session.getAttribute("cart");
        }

        if (cart == null) {
            // Create a new cart
            cart = new Cart();
        }
        else {
            // Connect cart items to cart
            cartItems = cart.getItems();
            if (cartItems.isEmpty()) {
                cartItems = null;
            }
            if (cartItems != null) {
                for (CartItem cartItem : cartItems) {
                    cartItem.setCart(cart);
                }
            }
        }

        // Set user's informations
        user.setUsername(username);
        user.setPassword(password);
        user.setPermission(UserPermission.CUSTOMER);
        user.setUserInfo(userInfo);
        user.setCart(cart);

        // Set userInfo's informations
        userInfo.setUsername(username);
        userInfo.setFullName(fullName);
        userInfo.setGender(gender);
        userInfo.setPhoneNumbers(phoneNumbers);
        userInfo.setAddress(address);
        userInfo.setUser(user);

        // Set cart's informations
        cart.setUsername(username);
        cart.setUser(user);

        // Save all data related to user to database
        dbHandler.save(user, userInfo, cart);
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                dbHandler.save(cartItem);
            }
        }

        // Set attributes for request
        ServletUtil.showMessage(
            request, response,
            "Đăng ký thành công"
        );

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");

        // Forward
        dispatcher.forward(request, response);
    }
}
