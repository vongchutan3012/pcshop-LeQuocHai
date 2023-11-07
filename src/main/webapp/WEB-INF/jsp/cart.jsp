<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>Giỏ hàng</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
        <link rel="stylesheet" href="./css/cart.css"/>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">

            <!-- Cart item displaying -->
            <div class="contentBox">
                
                <!-- Order form -->
                <form action="orderrequest" method="post">

                    <!-- Cart items displaying table -->
                    <table cellpadding="10">
                        <!-- Heading row -->
                        <tr>
                            <th></th>
                            <th>Sản phẩm</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th>Tổng tiền</th>
                            <th>Hành động</th>
                        </tr>

                        <!-- Displaying each item per row -->
                        <c:forEach var="cartItem" items="${cartItems}">
                            <tr>
                                <td>
                                    <input type="checkbox" id="${cartItem.item.id}" name="${cartItem.item.id}"/>
                                </td>

                                <td style="text-align: left;">
                                    <img src="itemimage?id=${cartItem.item.avatar.id}" class="itemAvatar"/>
                                    <a href="itemdetail?id=${cartItem.item.id}">
                                        ${cartItem.item.name}
                                    </a>
                                </td>

                                <td>
                                    ${cartItem.item.price}
                                </td>

                                <td>
                                    <a href="editcartitemamount?id=${cartItem.item.id}&amount=-1" class="button" style="display: inline-block; width: 35px; height: 35px; padding: 0px;">
                                        -
                                    </a>
                                    ${cartItem.amount}
                                    <a href="editcartitemamount?id=${cartItem.item.id}&amount=1" class="button" style="display: inline-block; width: 35px; height: 35px; padding: 0px;">
                                        +
                                    </a>
                                </td>

                                <td>
                                    ${cartItem.totalPriceCalculate()}
                                </td>

                                <td>
                                    <a href="removecartitem?id=${cartItem.item.id}" class="button">
                                        Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>

                        <!-- Order request row -->
                        <tr>

                            <td colspan="6">

                                <c:if test="${not empty user}">
                                    <input type="submit" value="Đặt hàng" class="button"/>
                                </c:if>
                                <c:if test="${empty user}">
                                    <p style="font-size: 18px; color: darkgray;">
                                        Vui lòng đăng nhập hoặc đăng ký trước khi đặt hàng!
                                    </p>
                                </c:if>

                            </td>

                        </tr>

                        <!-- Message display row -->
                        <c:if test="${not empty message}">
                            <tr>
                                <td colspan="6">
                                    <p style="color: red; font-family: Arial;">
                                        ${message}
                                    </p>
                                </td>
                            </tr>
                        </c:if>
                    </table>

                </form>

            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>