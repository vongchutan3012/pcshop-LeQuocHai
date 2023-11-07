<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>PM05 Gear</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/homepage.css"/>
        <link rel="stylesheet" href="./css/formpage.css"/>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">

            <!-- Type filter box -->
            <div id="typeFilterBox" class="contentBox" style="width: 95%;">
                <form action="searchbytype" method="post">
                    <label for="id" class="label">Tìm kiếm theo loại: </label>
                    <select id="id" name="id" class="textbox">

                        <c:if test="${empty param.id}">
                            <option value="*" selected>Tất cả</option>
                        </c:if>
                        <c:if test="${not empty param.id}">
                            <option value="">Tất cả</option>
                        </c:if>

                        <c:forEach var="type" items="${types}">
                            <c:if test="${type.id == param.id}">
                                <option value="${type.id}" selected>${type.name}</option>
                            </c:if>
                            <c:if test="${type.id != param.id}">
                                <option value="${type.id}">${type.name}</option>
                            </c:if>
                        </c:forEach>

                    </select>
                    <input type="submit" class="button" value="Tìm kiếm"/>
                </form>
            </div>

            <!-- Items displaying box -->
            <div id="itemDisplayBox" class="contentBox" style="width: 95%; text-align: justify;">

                <!-- Items displaying -->
                <c:forEach var="item" items="${items}">

                    <div class="itemBox">
                        <div class="itemAvatar">
                            <img src="itemimage?id=${item.avatar.id}"/>
                        </div>

                        <div class="itemInfo">
                            <p class="itemName">
                                ${item.name}
                            </p>

                            <p class="itemAmount">
                                Số lượng còn: ${item.amount}
                            </p>

                            <p class="itemPrice">
                                Đơn giá: ${item.price}
                            </p>
                        </div>

                        <div class="viewItem">
                            <a href="itemdetail?id=${item.id}" class="button">
                                Xem sản phẩm
                            </a>
                        </div>

                        <div class="addItemToCart">
                            <a href="addcartitem?id=${item.id}&amount=1" class="button">
                                Thêm vào giỏ hàng
                            </a>
                        </div>
                    </div>

                </c:forEach>

            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>