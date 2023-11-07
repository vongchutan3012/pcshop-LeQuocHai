<!-- JSP Page Configuration -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>${user.userInfo.fullName}</title>

        <!-- CSS inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- CSS files linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>

        <!-- Custom styles declaration -->
        <style>
            .contentBox {
                /* Font/text */
                font-family: Arial;
                font-size: 25px;
                text-align: justify;
            }

            .contentBox > div {
                /* Margin */
                margin: 25px;
            }

            .contentBox .hello {
                /* Font/text */
                font-size: 35px;
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

                <!-- Hello -->
                <div class="hello">
                    Xin chào, ${user.userInfo.fullName}
                </div>

                <div>
                    <a href="userorders">
                        Đơn hàng đã đặt
                    </a>
                </div>

                <div>
                    <a href="changepassword">
                        Thay đổi mật khẩu
                    </a>
                </div>

                <div>
                    <a href="logout">
                        Đăng xuất
                    </a>
                </div>

            </div>

        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>