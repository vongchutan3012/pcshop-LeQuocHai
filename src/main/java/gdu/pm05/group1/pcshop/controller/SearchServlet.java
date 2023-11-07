package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.util.EntityMatcher;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "search", urlPatterns = "/search")
public class SearchServlet extends HttpServlet {
    // CONSTRUCTORS:
    public SearchServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get content parameter
        String content = request.getParameter("content");

        // Content null case
        if (content == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get all items
        List<Item> items = dbHandler.getAll(Item.class);

        // Filtering
        for (int i = 0;i<items.size();i++) {
            // Get item entity
            Item item = items.get(i);

            // Use matcher to check item's match
            try {
                if (!EntityMatcher.matches(item, content)) {
                    // Remove this entity from items
                    items.remove(i);
                    i--;
                }
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        // Set items attribute for request
        request.setAttribute("items", items);

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("home");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
