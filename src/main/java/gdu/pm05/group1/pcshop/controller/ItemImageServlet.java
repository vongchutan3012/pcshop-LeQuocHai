package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.io.OutputStream;

import gdu.pm05.group1.pcshop.model.ItemImage;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "itemimage", urlPatterns = "/itemimage")
public class ItemImageServlet extends HttpServlet {
    // CONSTRUCTORS:
    public ItemImageServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get request's id parameter
        String idStr = request.getParameter("id");

        // Id null case
        if (idStr == null) {
            this.accessFailed(response);
            return;
        }

        // Cast id into an integer
        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.accessFailed(response);
            return;
        }

        // Get servlet context
        ServletContext context = request.getServletContext();

        // Get dbHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get ItemImage entity from given id;
        ItemImage itemImage = dbHandler.get(
            ItemImage.class,
            new HQLParameter("id", id)
        );

        // Item image null case
        if (itemImage == null) {
            this.accessFailed(response);
            return;
        }

        // Get itemImage's content
        byte[] content = itemImage.getContent();

        // Get response's output stream
        OutputStream output = response.getOutputStream();

        // Write content to output
        output.write(content);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    protected void accessFailed(HttpServletResponse response) {
        response.setStatus(
            HttpServletResponse.SC_NOT_FOUND
        );
    }
}
