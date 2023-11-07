package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

@WebServlet (name = "edititem", urlPatterns = "/edititem")
@MultipartConfig
public class EditItemServlet extends ItemManagementServlet {
    // CONSTRUCTORS:
    public EditItemServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // METHOD NOT ALLOWED
        response.setStatus(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validation failed
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get nullable part
        Part isEditAvatarPart = request.getPart("isEditAvatar");
        Part isEditImagesPart = request.getPart("isEditImages");

        // Get parameters from request
        String id = ServletUtil.readPartAsString(
            request.getPart("id")
        );
        String name = ServletUtil.readPartAsString(
            request.getPart("name")
        );
        String priceStr = ServletUtil.readPartAsString(
            request.getPart("price")
        );
        String amountStr = ServletUtil.readPartAsString(
            request.getPart("amount")
        );
        String description = ServletUtil.readPartAsString(
            request.getPart("description")
        );
        String typeId = ServletUtil.readPartAsString(
            request.getPart("type")
        );

        // Input processing
        double price = Double.parseDouble(priceStr);
        int amount = Integer.parseInt(amountStr);
        boolean isEditAvatar = (isEditAvatarPart != null);
        boolean isEditImages = (isEditImagesPart != null);
        byte[] avatarByte = null;
        List<byte[]> imagesBytes = new ArrayList<>();

        // Get necessary parts for input processing
        Part avatarPart = request.getPart("avatar");
        List<Part> imagesParts = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getName().equals("images")) {
                imagesParts.add(part);
            }
        }

        // Keep processing input values
        if (avatarPart != null) {
            avatarByte = ServletUtil.readPartAsBytes(avatarPart);
        }
        for (Part imagesPart : imagesParts) {
            byte[] imagesByte = ServletUtil.readPartAsBytes(imagesPart);
            if (imagesByte.length > 0) {
                imagesBytes.add(imagesByte);
            }
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get Item in DB with given id
        Item item = dbHandler.get(
            Item.class,
            new HQLParameter("id", id)
        );

        // Item not exist in db case
        if (item == null) {
            // Try refreshing item management page and will display error message if still not exist
            super.doGet(request, response);
            return;
        }

        // Get item type with given id
        ItemType type = dbHandler.get(
            ItemType.class,
            new HQLParameter("id", typeId)
        );

        // Type no longer exist case
        if (type == null) {
            // Set message for request
            request.setAttribute(
                "message",
                "Loại sản phẩm bạn vừa chọn không tồn tại! Vui lòng thử lại!"
            );

            // Forward back to itemmanagement page
            super.doGet(request, response);
            return;
        }

        // Assigning new values for item
        item.setName(name);
        item.setPrice(price);
        item.setAmount(amount);
        item.setDescription(description);
        item.setType(type);

        // Get necessary entities
        ItemImage avatar = item.getAvatar();
        List<ItemImage> images = new ArrayList<>(
            item.getImages()
        );

        // Edit avatar processing
        if (isEditAvatar) {
            // avatarByte null case
            if (avatarByte.length == 0) {
                // Set message for request
                request.setAttribute(
                    "message",
                    "Ảnh đại diện của sản phẩm chưa được chọn!"
                );

                // Forward back to item management page
                super.doGet(request, response);
                return;
            }

            // Set content for avatar
            avatar.setContent(avatarByte);
        }

        // Edit images processing
        if (isEditImages) {
            // imagesBytes empty case
            if (imagesBytes.isEmpty()) {
                // Set message for request
                request.setAttribute(
                    "message",
                    "Chưa có bất kỳ hình ảnh nào sản phẩm nào được chọn !"
                );

                // Forward back to item management page
                super.doGet(request, response);
                return;
            }

            // Get max size of both imagesBytes and images
            // Processing
            for (int i = 0;i<Math.max(imagesBytes.size(), images.size());i++) {
                // Declarations
                byte[] imageByte = null;
                ItemImage image = null;

                // Get imageByte size
                if (i < imagesBytes.size()) {
                    imageByte = imagesBytes.get(i);
                }

                // Get image
                if (i < images.size()) {
                    image = images.get(i);
                }

                // itemByte null case
                if (imageByte == null) {
                    dbHandler.remove(image);
                    images.remove(image);
                    i--;
                    continue;
                }

                // image null case
                if (image == null) {
                    image = new ItemImage();
                    image.setItem(item);
                }

                // Set content for image
                image.setContent(imageByte);

                // Save image to db
                dbHandler.save(image);
            }
        }

        // Save avatar and item
        dbHandler.save(avatar, item);

        // SUCCESSFULLY
        // Send redirect to itemsmanagement page
        response.sendRedirect("itemsmanagement");
    }
}
