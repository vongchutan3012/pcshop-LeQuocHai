<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
    <head>
        <title>This is title</title>

        <style>
            p[seen="true"] {
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <p>Hello World! This is not seen!</p>
        <p seen="true">Hello World! This is seen!</p>
    </body>
</html>