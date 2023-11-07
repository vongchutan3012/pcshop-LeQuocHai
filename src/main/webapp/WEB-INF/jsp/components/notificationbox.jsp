<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="notificationBox" seen="${notification.seen}">
    <div class="notificationTitle">
        <a href="notificationdetail?id=${notification.notification.id}">
            ${notification.notification.title}
        </a>
    </div>

    <div class="notificationDate">
        ${notification.notification.date}
    </div>
</div>