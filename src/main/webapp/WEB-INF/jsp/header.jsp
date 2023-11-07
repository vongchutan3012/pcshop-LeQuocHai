<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="header">
    <img src="./assets/pcshop_icon.png" class="pcshop_icon"/>

    <!-- Search item -->
    <form action="search" method="post">
        <input class="kwinput" id="content" name="content" type="text" placeholder="Nhập từ khóa tìm kiếm" />
        <input type="submit" value="" class="searchBtn" title="Search" />
    </form>

    <!-- <button class="loginBtn"></button> -->
    <c:choose>
        <c:when test="${not empty user}" >
            <a href="user" class="loginUserUrl">
                ${user.userInfo.fullName}
            </a>
        </c:when>

        <c:otherwise>
            <a href="login" class="loginUserUrl">
                Đăng nhập
            </a>
        </c:otherwise>
    </c:choose>

    <c:if test="${not empty user}">
        <a href="notification" class="notificationUrl">
            Thông báo
        </a>
    </c:if>

    <a href="cart" class="cartUrl">
        Giỏ hàng
    </a>

    <c:if test="${not empty user}">
        <c:if test="${user.permission == 'ADMIN'}">
            <a href="administrator" class="administratorUrl">
                Quản lý
            </a>
        </c:if>
    </c:if>
</div>