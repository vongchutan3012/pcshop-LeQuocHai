<!-- JSP Page configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <title>
            <c:choose>
                <c:when test="${param.action == 'new'}" >
                    Thêm mới sản phẩm
                </c:when>
                <c:when test="${param.action == 'detail'}" >
                    ${item.name}
                </c:when>
            </c:choose>
        </title>

        <!-- CSS Inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
        <link rel="stylesheet" href="./css/itemmanagement.css"/>

        <!-- Custom styles definition -->
        <style>
            td.labelCol {
                /* Text align */
                text-align: justify;
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

                <!-- Item management form -->
                <form action="itemmanagement" method="post" enctype="multipart/form-data">
                    <!-- Hidden action input -->
                    <input name="action" value="${param.action}" hidden/>

                    <!-- Hidden ID input for detail action -->
                    <c:if test="${param.action == 'detail'}">
                        <input name="id" value="${item.id}" hidden/>
                    </c:if>

                    <!-- Item management table -->
                    <table cellpadding="5">
                        <!-- ID row -->
                        <c:if test="${param.action == 'new'}">
                            <tr>
                                <td class="labelCol">
                                    <label for="id" class="label">Mã sản phẩm:</label>
                                </td>
    
                                <td>
                                    <input type="text" id="id" name="id" class="textbox" required />
                                </td>
                            </tr>
                        </c:if>

                        <!-- Name row -->
                        <tr>
                            <td class="labelCol">
                                <label for="name" class="label">Tên sản phẩm:</label>
                            </td>

                            <td>
                                <input type="text" id="name" name="name" value="${item.name}" class="textbox" required />
                            </td>
                        </tr>

                        <!-- Price row -->
                        <tr>
                            <td class="labelCol">
                                <label for="price" class="label">Đơn giá:</label>
                            </td>

                            <td>
                                <c:if test="${not empty item}">
                                    <input type="number" id="price" name="price" value="${item.price}" class="textbox" required/>
                                </c:if>
                                <c:if test="${empty item}">
                                    <input type="number" id="price" name="price" value="0" class="textbox" required/>
                                </c:if>
                            </td>
                        </tr>

                        <!-- Amount row -->
                        <tr>
                            <td class="labelCol">
                                <label for="amount" class="label">Số lượng:</label>
                            </td>

                            <td>
                                <c:if test="${not empty item}">
                                    <input type="number" id="amount" name="amount" value="${item.amount}" class="textbox" required/>
                                </c:if>
                                <c:if test="${empty item}">
                                    <input type="number" id="amount" name="amount" value="0" class="textbox" required/>
                                </c:if>
                            </td>
                        </tr>

                        <!-- Description row -->
                        <tr>
                            <td class="labelCol">
                                <label for="description" class="label">Mô tả sản phẩm:</label>
                            </td>

                            <td>
                                <textarea id="description" name="description" class="areabox" style="font-size: 18px;" maxlength="1000" required>${item.description}</textarea>
                            </td>
                        </tr>

                        <!-- Type row -->
                        <tr>
                            <td class="labelCol">
                                <label for="type" class="label">Loại sản phẩm:</label>
                            </td>

                            <td>
                                <select id="type" name="type" class="textbox" style="width: fit-content;">
                                    <c:forEach var="itemType" items="${itemTypes}">
                                        <c:if test="${not empty item}">
                                            <c:if test="${item.type.id == itemType.id}">
                                                <option value="${itemType.id}" selected>${itemType.id} - ${itemType.name}</option>
                                            </c:if>
                                            <c:if test="${item.type.id != itemType.id}">
                                                <option value="${itemType.id}">${itemType.id} - ${itemType.name}</option>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${empty item}">
                                            <option value="${itemType.id}">${itemType.id} - ${itemType.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <!-- Avatar row -->
                        <tr>
                            <td class="labelCol">
                                <c:if test="${not empty item}">
                                    <input type="checkbox" id="isEditAvatar" name="isEditAvatar" />
                                </c:if>
                                <label for="isEditAvatar" class="label">Ảnh đại diện:</label>
                            </td>

                            <td>
                                <c:if test="${not empty item}">
                                    <input type="file" id="avatar" name="avatar" accept="image/*" />
                                </c:if>
                                <c:if test="${empty item}">
                                    <input type="file" id="avatar" name="avatar" accept="image/*" required />
                                </c:if>
                            </td>
                        </tr>

                        <!-- Images row -->
                        <tr>
                            <td class="labelCol">
                                <c:if test="${not empty item}">
                                    <input type="checkbox" id="isEditImages" name="isEditImages" />
                                </c:if>
                                <label for="isEditImages" class="label">Hình ảnh sản phẩm:</label>
                            </td>

                            <td>
                                <c:if test="${not empty item}">
                                    <input type="file" id="images" name="images" accept="image/*" multiple />
                                </c:if>
                                <c:if test="${empty item}">
                                    <input type="file" id="images" name="images" accept="image/*" multiple required />
                                </c:if>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Lưu" class="button" style="font-size: 20px;"/>
                                <c:if test="${not empty item}">
                                    <a href="deleteitem?id=${item.id}" class="button" style="font-size: 20px;">
                                        Xóa
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </form>

                <!-- Message displaying -->
                <c:if test="${not empty message}">
                    <p class="label" style="font-size: 15px; color: red;">
                        ${message}
                    </p>
                </c:if>

            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>