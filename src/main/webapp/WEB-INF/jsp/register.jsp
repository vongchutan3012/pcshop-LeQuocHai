<!-- JSP Page Configurations -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Components configurations declaration -->
<c:set var="emailBoxRequired" scope="request" value="required"/>
<c:set var="passwordBoxRequired" scope="request" value="required"/>
<c:set var="fullNameBoxRequired" scope="request" value="required"/>
<c:set var="phoneNumberBoxRequired" scope="request" value="required"/>
<c:set var="addressBoxRequired" scope="request" value="required"/>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <!-- Title -->
        <title>Đăng ký</title>

        <!-- Css inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/areaboxstyle.jsp"/>
        <jsp:include page="styles/buttonstyle.jsp"/>
        <jsp:include page="styles/labelstyle.jsp"/>
        <jsp:include page="styles/textboxstyle.jsp"/>
        <jsp:include page="styles/radioselectorstyle.jsp"/>

        <!-- Css file linking -->
        <link rel="stylesheet" href="./css/formpage.css"/>

        <!-- Custom styles declaration -->
        <style>
            td {
                text-align: left;
            }
        </style>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">
            <!-- Content box -->
            <div class="contentBox" style="padding-left: 25px; padding-right: 25px;">
                <!-- Register form -->
                <form action="register" method="post">
                    <!-- Register table -->
                    <table cellpadding="10">
                        <!-- Header row -->
                        <tr>
                            <th colspan="2">
                                <p class="label" style="font-size: 35px;">
                                    Đăng ký
                                </p>
                            </th>
                        </tr>

                        <!-- Email row -->
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

                        <!-- Password row -->
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

                        <!-- Full name row -->
                        <tr>
                            <td>
                                <label for="lastName" class="label">
                                    Họ và tên:
                                </label>
                            </td>
                            <td>
                                <jsp:include page="components/fullnamebox.jsp"/>
                            </td>
                        </tr>

                        <!-- Gender row -->
                        <tr>
                            <td>
                                <label for="sex" class="label">
                                    Giới tính:
                                </label>
                            </td>
                            <td>
                                <jsp:include page="components/genderselector.jsp"/>
                            </td>
                        </tr>

                        <!-- Phone number row -->
                        <tr>
                            <td>
                                <label for="phoneNumber" class="label">
                                    Số điện thoại:
                                </label>
                            </td>
                            <td>
                                <jsp:include page="components/phonenumberbox.jsp"/>
                            </td>
                        </tr>

                        <!-- Address row -->
                        <tr>
                            <td>
                                <label for="address" class="label">
                                    Địa chỉ:
                                </label>
                            </td>
                            <td>
                                <jsp:include page="components/addressbox.jsp"/>
                            </td>
                        </tr>

                        <!-- Submit row -->
                        <tr>
                            <td colspan="2" style="text-align: center;">
                                <jsp:include page="components/registerbutton.jsp"/>
                            </td>
                        </tr>

                        <!-- Message row -->
                        <c:if test="${not empty message}">
                            <tr>
                                <td colspan="2" style="color: red;">
                                    ${message}
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