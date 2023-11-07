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
        <title>Đổi mật khẩu</title>

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
                <!-- Change password form -->
                <form action="changepassword" method="post">
                    <!-- Change password table -->
                    <table cellpadding="10">
                        <!-- Old password row -->
                        <tr>
                            <td>
                                <label for="oldPassword" class="label">
                                    Mật khẩu cũ:
                                </label>
                            </td>
                            <td>
                                <input type="password" id="oldPassword" name="oldPassword" class="textbox" required/>
                            </td>
                        </tr>

                        <!-- New password row -->
                        <tr>
                            <td>
                                <label for="newPassword" class="label">
                                    Mật khẩu mới:
                                </label>
                            </td>
                            <td>
                                <input type="password" id="newPassword" name="newPassword" class="textbox" required/>
                            </td>
                        </tr>

                        <!-- Submit row -->
                        <tr>
                            <td colspan="2" style="text-align: center;">
                                <input type="submit" value="Đổi mật khẩu" class="button"/>
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