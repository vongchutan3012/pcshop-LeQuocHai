package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.List;
import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.ItemType;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "home", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    // CONSTRUCTORS:
    public HomeServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        ServletUtil.userValidate(request, response);

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // If request's items property is null
        if (request.getAttribute("items") == null) {
            // Get all Item entities in db
            List<Item> items = dbHandler.getAll(Item.class);

            // Set items attribute for request
            request.setAttribute("items", items);
        }

        // Get all item types
        List<ItemType> types = dbHandler.getAll(ItemType.class);

        // Set types attribute for request
        request.setAttribute("types", types);

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/homepage.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
