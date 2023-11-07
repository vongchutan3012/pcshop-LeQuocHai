<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Item images box -->
<div class="itemImagesBox">
    <!-- Image display box -->
    <div class="imageDisplayBox">
        <img src="./assets/item.png" class="itemImage" />
    </div>

    <!-- Image selection box -->
    <div class="imageSelectionBox">
        <c:forEach var="i" begin="1" end="10">
            <div class="imageSelectionCell">
                <img src="./assets/item.png" class="itemImageSelection"/>
            </div>
        </c:forEach>
    </div>
</div>