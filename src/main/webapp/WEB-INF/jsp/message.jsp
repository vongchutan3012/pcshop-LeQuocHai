<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <title>
            <c:if test="${not empty message}">
                ${message}
            </c:if>
        </title>

        <!-- CSS Inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS Files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>

        <!-- Custom styles definition -->
        <style>
            .contentBox p {
                /* Font */
                font-family: Arial;
                font-size: 25px;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">
            <div class="contentBox">
                <p style="color: ${color};">
                    ${message}
                </p>
                <p>
                    <a href="home" class="button" style="font-size: 18px; padding: 5px;">
                        Quay lại trang chủ
                    </a>
                </p>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>