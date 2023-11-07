<!-- JSP Page configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>Quản lý thông tin loại sản phẩm</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS Files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>

        <!-- Custom style declaration -->
        <style>
            a.button[isTypeButton="true"] {
                /* Margin */
                margin: 10px;
            }
        </style>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">
            
            <!-- Content box -->
            <div class="contentBox">
                
                <!-- New item type area -->
                <div style="margin-bottom: 25px;">
                    <a href="itemtypemanagement?action=new" class="button" style="font-size: 18px;">
                        Thêm mới
                    </a>
                </div>

                <!-- Item types displaying area -->
                <div>
                    <c:forEach var="type" items="${types}">
                        <a href="itemtypemanagement?action=detail&id=${type.id}" class="button" isTypeButton="true">
                            ${type.id} - ${type.name}
                        </a>
                    </c:forEach>
                </div>

            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>