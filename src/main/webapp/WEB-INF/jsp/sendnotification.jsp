<!-- JSP Page Configuration -->
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HTML -->
<!DOCTYPE html>

<html>
    <head>
        <title>Gửi thông báo</title>

        <!-- Css inclusion -->
        <jsp:include page="css_inclusion/general.xml"/>

        <!-- Styles inclusion -->
        <jsp:include page="styles/general.jsp"/>

        <!-- Css files link -->
        <link rel="stylesheet" href="./css/formpage.css"/>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <!-- Body -->
        <div class="body">
            <!-- Send notification box -->
            <div class="contentBox">
                <!-- Send notification form -->
                <form action="sendnotification" method="post">
                    <!-- Send notification table -->
                    <table cellpadding="10">
                        <!-- Send To Row -->
                        <tr>
                            <td>
                                <label for="to" class="label">
                                    Gửi đến:
                                </label>
                            </td>
                            <td>
                                <input type="text" id="to" name="to" class="textbox" placeholder="Email người dùng, * cho tất cả" style="width: 500px;" required/>
                            </td>
                        </tr>

                        <!-- Title Row -->
                        <tr>
                            <td>
                                <label for="title" class="label">
                                    Tiêu đề:
                                </label>
                            </td>
                            <td>
                                <input type="text" id="title" name="title" class="textbox" required/>
                            </td>
                        </tr>

                        <!-- Content row -->
                        <tr>
                            <td>
                                <label for="content" class="label">
                                    Nội dung:
                                </label>
                            </td>
                            <td>
                                <textarea id="content" name="content" class="areabox" required></textarea>
                            </td>
                        </tr>

                        <!-- Send Row -->
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Gửi" class="button"/>
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
                    </table>
                </form>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>