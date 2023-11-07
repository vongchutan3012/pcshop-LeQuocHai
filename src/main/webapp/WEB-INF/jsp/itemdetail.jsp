<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>Chi tiết sản phẩm</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
        <link rel="stylesheet" href="./css/itemdetail.css"/>

        <!-- Script -->
        <script type="text/javascript" lang="javascript">
            // Show image method
            function showImage(id) {
                const imageDisplay = document.getElementById("imageDisplay");
                imageDisplay.src = "itemimage?id=" + id;
            }
        </script>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">

            <!-- Content box -->
            <div class="contentBox" style="width: 90%;">
                
                <!-- Main table -->
                <table>
                    <tr>
                        <!-- Left side -->
                        <td>

                            <div class="leftArea">
                                <!-- Image display box -->
                                <div class="imageDisplayBox">
                                    <img id="imageDisplay" src="itemimage?id=${item.avatar.id}"/>
                                </div>

                                <!-- Image select box -->
                                <div class="imageSelectBar">
                                    <div>
                                        <img src="itemimage?id=${item.avatar.id}" onclick="showImage(${item.avatar.id});"/>
                                    </div>
                                    <c:forEach var="image" items="${item.images}">
                                        <div>
                                            <img src="itemimage?id=${image.id}" onclick="showImage(${image.id});"/>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                        </td>

                        <!-- Right side -->
                        <td>

                            <div class="rightArea">
                                <!-- Item name display area -->
                                <div class="itemName">
                                    ${item.name}
                                </div>

                                <!-- Item description display area -->
                                <div class="itemDescription">
                                    <!-- Title -->
                                    <div class="title">
                                        Mô tả:
                                    </div>

                                    <!-- Content -->
                                    <div class="content">
                                        ${item.description}
                                    </div>
                                </div>

                                <!-- Item type display area -->
                                <div class="itemType">
                                    <b>Loại sản phẩm:</b> ${item.type.name}
                                </div>

                                <!-- Item price display area -->
                                <div class="itemPrice">
                                    <!-- Title -->
                                    <div class="title">
                                        Đơn giá: 
                                    </div>

                                    <!-- Content -->
                                    <div class="content">
                                        ${item.price}
                                    </div>
                                </div>

                                <!-- Item amount display area -->
                                <div class="itemAmount">
                                    <!-- Title -->
                                    <div class="title">
                                        Số lượng còn: 
                                    </div>

                                    <!-- Content -->
                                    <div class="content">
                                        ${item.amount}
                                    </div>
                                </div>

                                <!-- Add to cart area -->
                                <div class="addToCart">
                                    <form action="addcartitem" method="post">
                                        <!-- Hidden ID input -->
                                        <input id="id" name="id" value="${item.id}" hidden/>

                                        <!-- Add to cart -->
                                        Số lượng: <input type="number" id="amount" name="amount" value="1" class="textbox" required/> <input type="submit" value="Thêm vào giỏ hàng" class="button"/>
                                    </form>
                                </div>
                            </div>

                        </td>
                    </tr>
                </table>

            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>