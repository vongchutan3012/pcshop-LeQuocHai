package gdu.pm05.group1.pcshop.model.holder;

import java.util.HashMap;
import java.util.Map;

import gdu.pm05.group1.pcshop.model.enums.OrderStatus;

public class OrderStatusHolder extends Holder<OrderStatus> {
    // STATIC FIELDS:
    private static Map<OrderStatus, String> stringMap = generateStringMap();

    // STATIC METHODS:
    private static Map<OrderStatus, String> generateStringMap() {
        // String map initialization
        Map<OrderStatus, String> stringMap = new HashMap<>();

        // String mapping
        stringMap.put(
            OrderStatus.AWAITING_CONFIRMATION, "Đang chờ xác nhận"
        );
        stringMap.put(
            OrderStatus.CANCELLED, "Đã hủy"
        );
        stringMap.put(
            OrderStatus.DELIVERED_SUCCESSFULLY, "Giao thành công"
        );
        stringMap.put(
            OrderStatus.DELIVERING, "Đang giao"
        );

        // Return string map
        return stringMap;
    }

    // CONSTRUCTORS:
    public OrderStatusHolder() {
        super();
    }

    public OrderStatusHolder(OrderStatus value) {
        super(value);
    }

    // METHODS:
    @Override
    public String toString() {
        // Result definition
        String result = stringMap.get(value);

        // Result null case
        if (result == null) {
            result = value.toString();
        }

        // Return result
        return result;
    }
}
