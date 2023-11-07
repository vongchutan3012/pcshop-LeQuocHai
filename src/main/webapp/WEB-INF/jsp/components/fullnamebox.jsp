<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input 
    id="fullName" 
    type="text" 
    name="fullName" 
    value="${fullName}" 
    placeholder="Họ và tên" 
    class="textbox" 
    ${fullNameBoxRequired}
/>