<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>Xem thông báo</title>

        <!-- CSS Inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS Files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
        <link rel="stylesheet" href="./css/notificationdetail.css"/>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">
            <!-- Content box -->
            <div class="contentBox" style="text-align: left; width: 1250px; margin-bottom: 50px;">
                <c:choose>
                    <c:when test="${not empty notification}" >
                        <!-- Title -->
                        <div clas="contentDisplayer">
                            <p class="label">
                                ${notification.title}
                            </p>
                        </div>

                        <!-- Content -->
                        <div class="contentDisplayer">
                            <p class="label" style="font-size: 14px; font-weight: normal;">
                                ${notification.content}
                            </p>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <p class="label">
                            Không có thông báo nào để hiển thị.
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${not empty referer}">
                <div style="text-align: center;">
                    <a href="${referer}" class="button">
                        Quay lại
                    </a>
                </div>
            </c:if>
        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>