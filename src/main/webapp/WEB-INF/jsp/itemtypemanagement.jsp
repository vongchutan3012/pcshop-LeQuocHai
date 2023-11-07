<!-- JSP Page configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>
            <c:if test="${not empty param.action}">
                <c:if test="${param.action == 'new'}">
                    Thêm loại sản phẩm
                </c:if>
                <c:if test="${param.action == 'detail'}">
                    <c:if test="${not empty type}">
                        ${type.name}
                    </c:if>
                </c:if>
            </c:if>
        </title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS Files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">

            <!-- Content box -->
            <div class="contentBox">

                <!-- Item type management form -->
                <form action="itemtypemanagement" method="post">
                    <!-- Hidden inputs -->
                    <input id="action" name="action" value="${param.action}" hidden/>
                    <c:if test="${not empty type}">
                        <input id="id" name="id" value="${type.id}" hidden/>
                    </c:if>

                    <!-- Item type management table -->
                    <table cellpadding="10">
                        <!-- ID Row -->
                        <c:if test="${empty type}">
                            <tr>
                                <td>
                                    <label for="id" class="label">
                                        Mã loại sản phẩm:
                                    </label>
                                </td>

                                <td>
                                    <input type="text" id="id" name="id" class="textbox" required/>
                                </td>
                            </tr>
                        </c:if>

                        <!-- Name row -->
                        <tr>
                            <td>
                                <label for="name" class="label">
                                    Tên loại sản phẩm:
                                </label>
                            </td>

                            <td>
                                <input type="text" id="name" name="name" value="${type.name}" class="textbox" required/>
                            </td>
                        </tr>

                        <!-- Action row -->
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Lưu" class="button"/>
                                <c:if test="${not empty type}">
                                    <a href="deleteitemtype?id=${type.id}" class="button">
                                        Xóa
                                    </a>
                                </c:if>
                            </td>
                        </tr>

                        <!-- Message row -->
                        <c:if test="${not empty message}">
                            <tr>
                                <td colspan="2">
                                    <p style="color: red;">
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