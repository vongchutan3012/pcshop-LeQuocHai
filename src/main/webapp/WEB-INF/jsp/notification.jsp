<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <title>Thông báo</title>

        <!-- CSS Inclusions -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>
        <jsp:include page="styles/notificationboxstyle.jsp"/>

        <!-- CSS file linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">
            <!-- Content box -->
            <div class="contentBox">
                <!-- Switch cases -->
                <c:choose>
                    <c:when test="${not empty notifications}" >
                        <c:forEach var="n" items="${notifications}">
                            <c:set var="notification" scope="request" value="${n}"/>
                            <jsp:include page="components/notificationbox.jsp"/>
                        </c:forEach>

                        <c:remove var="notification"/>
                    </c:when>

                    <c:otherwise>
                        <p class="label">
                            Không có bất kỳ thông báo nào để hiển thị!
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>