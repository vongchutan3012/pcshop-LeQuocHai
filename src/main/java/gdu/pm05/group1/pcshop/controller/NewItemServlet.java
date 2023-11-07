package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.ItemImage;
import gdu.pm05.group1.pcshop.model.ItemType;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet (name = "newitem", urlPatterns = "/newitem")
@MultipartConfig
public class NewItemServlet extends ItemManagementServlet {
    // CONSTRUCTORS:
    public NewItemServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Method not allowed
        response.setStatus(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validation failed
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get parts
        Part idPart = request.getPart("id");
        Part namePart = request.getPart("name");
        Part pricePart = request.getPart("price");
        Part amountPart = request.getPart("amount");
        Part descriptionPart = request.getPart("description");
        Part typePart = request.getPart("type");
        Part avatarPart = request.getPart("avatar");
        List<Part> imagesParts = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getName().equals("images")) {
                imagesParts.add(part);
            }
        }
        
        // Get parameters from parts
        String id = ServletUtil.readPartAsString(idPart);
        String name = ServletUtil.readPartAsString(namePart);
        String priceStr = ServletUtil.readPartAsString(pricePart);
        double price = Double.parseDouble(priceStr);
        String amountStr = ServletUtil.readPartAsString(amountPart);
        int amount = Integer.parseInt(amountStr);
        String description = ServletUtil.readPartAsString(descriptionPart);
        String typeId = ServletUtil.readPartAsString(typePart);
        byte[] avatarByte = ServletUtil.readPartAsBytes(avatarPart);
        List<byte[]> imagesBytes = new ArrayList<>();
        for (Part imagesPart : imagesParts) {
            imagesBytes.add(
                ServletUtil.readPartAsBytes(imagesPart)
            );
        }

        // Get dbHandler
        ServletContext context = request.getServletContext();
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Try get item with given id from database
        Item item = dbHandler.get(
            Item.class,
            new HQLParameter("id", id)
        );

        // Item already exist case
        if (item != null) {
            request.setAttribute(
                "message",
                "Đã tồn tại vật phẩm với id '@id' !".replace("@id", id)
            );
            super.doGet(request, response);
            return;
        }

        // Get item type
        ItemType type = dbHandler.get(
            ItemType.class,
            new HQLParameter("id", typeId)
        );

        // Type not exist case
        if (type == null) {
            request.setAttribute(
                "message",
                "Loại sản phẩm này không còn tồn tại! Vui lòng thử lại!"
            );
            super.doGet(request, response);
            return;
        }

        // Create new Item
        item = new Item();

        // Create new avatar
        ItemImage avatar = new ItemImage();

        // Create a list of ItemImage named images
        Set<ItemImage> images = new HashSet<>();

        // Item properties assigning
        item.setId(id);
        item.setName(name);
        item.setPrice(price);
        item.setAmount(amount);
        item.setDescription(description);
        item.setType(type);
        item.setAvatar(avatar);

        // Avatar properties assigning
        avatar.setContent(avatarByte);

        // Images properties assigning
        for (byte[] imageByte : imagesBytes) {
            ItemImage image = new ItemImage();
            image.setContent(imageByte);
            image.setItem(item);
            images.add(image);
        }

        // Save all related entities
        dbHandler.save(
            avatar, item
        );

        for (ItemImage image : images) {
            dbHandler.save(image);
        }

        // Send redirect to itemsmanagement page
        response.sendRedirect("itemsmanagement");
    }
}
