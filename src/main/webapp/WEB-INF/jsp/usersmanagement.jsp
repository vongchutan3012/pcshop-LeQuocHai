<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>Quản lý thông tin người dùng</title>

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
                <!-- New users area -->
                <div style="margin-bottom: 25px;">
                    <a href="usermanagement?action=new" class="button">
                        Thêm người dùng
                    </a>
                </div>

                <!-- All users displaying area -->
                <div>
                    <!-- Users table -->
                    <table cellspacing="10" style="font-family: Arial; font-size: 18px;">
                        <!-- Heading row -->
                        <tr>
                            <th>
                                Tên người dùng
                            </th>

                            <th>
                                Họ và tên
                            </th>

                            <th>
                                Số điện thoại
                            </th>

                            <th colspan="2">
                                Hành động
                            </th>
                        </tr>

                        <!-- Displaying all users -->
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>
                                    ${user.username}
                                </td>

                                <td>
                                    ${user.userInfo.fullName}
                                </td>

                                <td>
                                    ${user.userInfo.phoneNumbers}
                                </td>

                                <td colspan="2">
                                    <a href="usermanagement?action=detail&username=${user.username}" class="button">
                                        Xem thông tin
                                    </a>
                                    <a href="deleteuser?username=${user.username}" class="button">
                                        Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>