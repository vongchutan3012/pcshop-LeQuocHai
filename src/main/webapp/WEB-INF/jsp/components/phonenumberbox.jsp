<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input 
id="phoneNumber" 
type="text" 
name="phoneNumber" 
value="${phoneNumber}" 
placeholder="Số điện thoại"
class="textbox" 
${phoneNumberBoxRequired}
/>