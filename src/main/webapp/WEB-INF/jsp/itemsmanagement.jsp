<!-- JSP Page configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <title>Quản lý thông tin sản phẩm</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
        <link rel="stylesheet" href="./css/itemsmanagement.css"/>
    </head>

    <body>
        <!-- Heder -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">

            <!-- Content box -->
            <div class="contentBox">
                <div style="margin-bottom: 25px;">
                    <a href="itemmanagement?action=new" class="url" style="font-family: Arial; font-size: 25px; font-weight: bold;">
                        Thêm mới sản phẩm
                    </a>
                </div>
                <div style="width: 100%; text-align: justify;">
                    <!-- Displaying all items as item boxes -->
                    <c:forEach var="item" items="${items}">
                        <div class="itemBox">
                            <div class="itemImageBox">
                                <img src="itemimage?id=${item.avatar.id}" class="itemImage"/>
                            </div>

                            <div class="itemNameBox">
                                ${item.name}
                            </div>

                            <div class="itemPriceBox">
                                <b>Giá: </b> ${item.price}
                            </div>

                            <div class="urlBox">
                                <a href="itemmanagement?action=detail&id=${item.id}" class="url">
                                    Xem sản phẩm
                                </a>
                            </div>

                            <div class="urlBox">
                                <a href="deleteitem?id=${item.id}" class="url">
                                    Xóa sản phẩm
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>