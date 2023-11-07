package gdu.pm05.group1.pcshop.listener;

import gdu.pm05.group1.pcshop.model.dbhandler.DBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    // CONSTRUCTORS:
    public ContextListener() {

    }

    // METHODS:
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Initializing DBHandler");

        // Get context from event
        ServletContext context = event.getServletContext();

        // Create new DBHandler
        DBHandler dbHandler = new DBHandler(context);

        // Add dbHandler to context's attribute
        context.setAttribute("dbHandler", dbHandler);

        System.out.println("DBHandler initialized successfully!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Destroying DBHandler object");

        // Get context from event
        ServletContext context = event.getServletContext();

        // Get DBHandler object from context's attribute
        DBHandler dbHandler = (DBHandler)context.getAttribute("dbHandler");

        // Destroy dbHandler
        dbHandler.destroy();

        System.out.println("DBHandler Destroyed successfully!");
    }
}
