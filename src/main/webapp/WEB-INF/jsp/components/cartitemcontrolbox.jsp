<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="cartItemControlBox">
    <table cellpadding="25">
        <tr>
            <td>
                <!-- All cart items selector -->
                <div class="allCartItemsSelectorBox">
                    <input id="allCartItemsSelector" type="checkbox"/>
                    <label for="allCartItemsSelector">
                        Chọn tất cả
                    </label>
                </div>
            </td>

            <td>
                <!-- Selected cart items remove -->
                <div class="removeSelectedCartItemsBox">
                    <a href="home">
                        Xóa
                    </a>
                </div>
            </td>

            <td>
                <!-- Selected cart items order -->
                <div class="orderSelectedCartItemsBox">
                    <input type="submit" value="Thanh toán" class="button"/>
                </div>
            </td>
        </tr>
    </table>
</div>