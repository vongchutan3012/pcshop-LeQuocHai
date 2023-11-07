package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.List;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.ItemType;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "searchbytype", urlPatterns = "/searchbytype")
public class SearchByTypeServlet extends HttpServlet {
    // CONSTRUCTORS:
    public SearchByTypeServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get id parameter
        String id = request.getParameter("id");

        // ID null case
        if (id == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get ItemType entity with given id
        ItemType type = dbHandler.get(ItemType.class, new HQLParameter("id", id));

        // Get all items in db
        List<Item> items = dbHandler.getAll(Item.class);

        // Only filtering when type not null
        if (type != null) {
            // Filtering
            for (int i = 0;i<items.size();i++) {
                // Get item
                Item item = items.get(i);

                // Get item's type
                ItemType itemType = item.getType();

                // Not matches case
                if (!itemType.getId().equals(type.getId())) {
                    items.remove(i);
                    i--;
                }
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
