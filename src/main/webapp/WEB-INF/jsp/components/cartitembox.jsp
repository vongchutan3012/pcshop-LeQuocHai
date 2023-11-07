<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Cart item box -->
<div class="cartItemBox">
    <!-- Vertical alignment ruler -->
    <div class="verticalAlignRuler"></div>

    <!-- Selection box -->
    <div class="cartItemSelectorBox">
        <input id="cartItemSelector" type="checkbox"/>
        <label for="cartItemSelector"></label>
    </div>

    <!-- Item image -->
    <!-- <img src="itemicon?id=${cartItem.item.id}" class="cartItemIcon"/> -->
    <img src="./assets/item.png" class="cartItemIcon"/>

    <!-- Item name -->
    <div class="cartItemNameBox">
        <a href="itemdetail?id=${cartItem.item.id}">
            <!-- ${cartItem.item.name} -->
            GOGO A15 SUPER FAST PC
        </a>
    </div>

    <!-- Item price box -->
    <div class="cartItemPriceBox">
        <p>
            <!-- Đơn giá: ${cartItem.item.price} -->
            <b>Đơn giá:</b> 25000
        </p>
    </div>

    <!-- Item amount -->
    <div class="cartItemAmountBox">
        <p class="cartItemAmountTitle">
            Số lượng: 
        </p>
        <a class="cartItemAmountSettingBtn" href="reducecartitemamount?id=${cartItem.item.id}">
            -
        </a>
        <p class="cartItemAmount">
            <!-- ${cartItem.amount} -->
            26
        </p>
        <a class="cartItemAmountSettingBtn" href="addcartitemamount?id=${cartItem.item.id}">
            +
        </a>
    </div>

    <!-- Total price -->
    <div class="cartItemTotalPriceBox">
        <p class="cartItemTotalPrice">
            <!-- Tổng giá: ${cartItem.item.price * cartItem.amount} -->
            <b>Tổng giá:</b> 90000
        </p>
    </div>

    <!-- Remove item from cart -->
    <div class="removeCartItemBox">
        <a class="removeCartItemUrl" href="removecartitem?id=${cartItem.item.id}">
            Xóa
        </a>
    </div>
</div>