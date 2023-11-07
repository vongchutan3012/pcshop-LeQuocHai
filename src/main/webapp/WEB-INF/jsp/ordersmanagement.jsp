<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>Quản lý đon hàng</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>

        <!-- Custom styles definition -->
        <style>
            a.button {
                /* Font/text */
                font-size: 18px;

                /* Padding */
                padding: 5px;
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

                <!-- Display all orders table -->
                <table cellpadding="5" style="font-family: Arial; font-size: 18px;">
                    <!-- Heading row -->
                    <tr>
                        <th>
                            Mã đơn
                        </th>
                        <th>
                            Ngày lập đơn
                        </th>
                        <th>
                            Thành tiền
                        </th>
                        <th>
                            Trạng thái
                        </th>
                        <th>
                            Hành động
                        </th>
                    </tr>

                    <!-- Displaying -->
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>
                                ${order.id}
                            </td>
                            <td>
                                <fmt:formatDate var="date" scope="page" pattern="dd/MM/yyyy HH:mm:ss" value="${order.date}" />
                                ${date}
                            </td>
                            <td>
                                ${order.totalPrice}
                            </td>
                            <td>
                                <c:forEach var="orderStatus" items="${OrderStatus}">
                                    <c:if test="${order.status == orderStatus.value}">
                                        ${orderStatus}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <a href="orderdetail?id=${order.id}" class="button">
                                    Xem chi tiết
                                </a>
                                <c:if test="${order.status == 'DELIVERED_SUCCESSFULLY' || order.status == 'CANCELLED'}">
                                    <a href="deleteorder?id=${order.id}" class="button">
                                        Xóa
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:remove var="date"/>
                </table>

            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>