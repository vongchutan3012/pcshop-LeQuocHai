<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <title>Chi tiết đơn hàng</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
        <link rel="stylesheet" href="./css/orderdetail.css"/>

        <!-- Custom style definition -->
        <style>
            .button {
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
            <div class="contentBox" style="text-align: justify; font-family: Arial; font-size: 25px;">
                <!-- Order ID area -->
                <div class="orderInfoArea">
                    <p>
                        Mã đơn hàng: ${order.id}
                    </p>
                </div>

                <!-- Order date area -->
                <div class="orderDateArea">
                    <p>
                        Ngày lập đơn: ${order.date}
                    </p>
                </div>

                <!-- Items area -->
                <div class="itemsArea">
                    <!-- Title -->
                    <div class="title">
                        Sản phẩm trong giỏ hàng:
                    </div>

                    <!-- Items -->
                    <div class="items">
                        <!-- Items table -->
                        <table cellpadding="10">
                            <!-- Heading row -->
                            <tr>
                                <th>
                                    Mã sẩn phẩm
                                </th>

                                <th>
                                    Tên sản phẩm
                                </th>

                                <th>
                                    Số lượng
                                </th>

                                <th>
                                    Đơn giá
                                </th>

                                <th>
                                    Tổng
                                </th>

                                <th>
                                    Hành động
                                </th>
                            </tr>

                            <!-- Adding each item as a row -->
                            <c:forEach var="item" items="${order.items}">
                                <tr>
                                    <td>
                                        ${item.item.id}
                                    </td>

                                    <td>
                                        ${item.item.name}
                                    </td>

                                    <td>
                                        ${item.amount}
                                    </td>

                                    <td>
                                        ${item.item.price}
                                    </td>

                                    <td>
                                        ${item.totalPriceCalculate()}
                                    </td>

                                    <td>
                                        <a href="itemdetail?id=${item.item.id}" class="button">
                                            Xem chi tiết
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>

                <!-- Total price area -->
                <div class="totalPriceArea">
                    <p>
                        Tổng giá trị đơn hàng: ${order.totalPrice}
                    </p>
                </div>

                <!-- Order status area -->
                <div class="orderStatusArea">
                    <p>
                        Trạng thái đơn hàng: 
                        <!-- Non-Admin case -->
                        <c:if test="${user.permission != 'ADMIN'}">
                            <c:forEach var="orderStatus" items="${OrderStatus}">
                                <c:if test="${orderStatus.value == order.status}">
                                    ${orderStatus}
                                </c:if>
                            </c:forEach>
                        </c:if>

                        <!-- Admin case -->
                        <c:if test="${user.permission == 'ADMIN'}">
                            <form action="editorderstatus" method="post">
                                <input id="id" name="id" value="${order.id}" hidden/>

                                <select id="status" name="status" class="textbox">

                                    <c:forEach var="orderStatus" items="${OrderStatus}">

                                        <c:if test="${orderStatus.value != order.status}">
                                            <option value="${orderStatus.value}">${orderStatus}</option>
                                        </c:if>

                                        <c:if test="${orderStatus.value == order.status}">
                                            <option value="${orderStatus.value}" selected>${orderStatus}</option>
                                        </c:if>

                                    </c:forEach>

                                </select>

                                <input type="submit" value="Cập nhật" class="button"/>
                            </form>
                        </c:if>
                    </p>
                </div>

                <!-- Action area -->
                <div class="actionArea">
                    <p>
                        <!-- Home page button -->
                        <a href="home" class="button">Quay lại trang chủ</a>

                        <!-- Cancel button -->
                        <c:if test="${user.username == order.user.username}">
                            <c:if test="${order.status == 'AWAITING_CONFIRMATION'}">
                                <a href="cancelorder?id=${order.id}" class="button">Hủy đơn hàng</a>
                            </c:if>
                        </c:if>

                        <!-- Delete button -->
                        <c:if test="${user.permission == 'ADMIN'}">
                            <c:if test="${order.status == 'DELIVERED_SUCCESSFULLY' || order.status == 'CANCELLED'}">
                                <a href="deleteorder?id=${order.id}" class="button">Xóa đơn hàng</a>
                            </c:if>
                        </c:if>
                    </p>
                </div>
            </div>
        </div>
        
        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>