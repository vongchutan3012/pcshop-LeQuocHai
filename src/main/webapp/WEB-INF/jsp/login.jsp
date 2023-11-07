<!-- JSP Page Configuration -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Components configurations declaration -->
<c:set var="emailBoxRequired" value="${'required'}" scope="request"/>
<c:set var="passwordBoxRequired" value="${'required'}" scope="request"/>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <title>Đăng nhập</title>

        <!-- Css inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/textboxstyle.jsp"/>
        <jsp:include page="styles/buttonstyle.jsp"/>
        <jsp:include page="styles/labelstyle.jsp"/>

        <!-- Css files link -->
        <link rel="stylesheet" href="./css/formpage.css"/>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">
            <!-- Login box -->
            <div class="contentBox">
                <!-- Login form -->
                <form action="login" method="post">
                    <!-- Login table -->
                    <table cellpadding="10">
                        <!-- Title Row -->
                        <tr>
                            <th colspan="2">
                                <p class="label" style="font-size: 35px;">
                                    Đăng nhập
                                </p>
                            </th>
                        </tr>

                        <!-- Email Row -->
                        <tr>
                            <td>
                                <label for="email" class="label">
                                    Email:
                                </label>
                            </td>
                            <td>
                                <jsp:include page="components/emailbox.jsp"/>
                            </td>
                        </tr>

                        <!-- Password Row -->
                        <tr>
                            <td>
                                <label for="password" class="label">
                                    Mật khẩu:
                                </label>
                            </td>
                            <td>
                                <jsp:include page="components/passwordbox.jsp"/>
                            </td>
                        </tr>

                        <!-- Login Row -->
                        <tr>
                            <td colspan="2">
                                <jsp:include page="components/loginbutton.jsp"/>
                            </td>
                        </tr>

                        <!-- Message -->
                        <c:if test="${not empty message}">
                            <tr>
                                <td colspan="2">
                                    <p style="font-family: Arial; font-size: 18px; color: red">
                                        ${message}
                                    </p>
                                </td>
                            </tr>
                        </c:if>

                        <!-- Register URL -->
                        <tr>
                            <td colspan="2">
                                <a href="register" style="font-size: 15px;">
                                    Bạn chưa có tài khoản ? Nhấn vào đây để đăng ký!
                                </a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>