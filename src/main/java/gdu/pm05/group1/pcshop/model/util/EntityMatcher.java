package gdu.pm05.group1.pcshop.model.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityMatcher {
    // STATIC METHODS:
    public static <T> boolean matches(Object entity, String content) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
        // Get entity's class
        Class entityClass = entity.getClass();

        // Get entity's fields
        Field[] fields = entityClass.getDeclaredFields();

        // Fields reading
        for (Field field : fields) {
            // Get field's name
            String fieldName = field.getName();

            // Get getter method name
            String fieldGetterMethodName = "get" + fieldName.replaceFirst(
                ""+fieldName.charAt(0), ""+Character.toUpperCase(
                    fieldName.charAt(0)
                )
            );

            // Get getter method
            Method method = entityClass.getMethod(fieldGetterMethodName);

            // Get field value from method
            Object fieldValue = method.invoke(entity);

            // Convert field value to string
            String fieldValueStr = fieldValue.toString();

            // To lowercase
            fieldValueStr = fieldValueStr.toLowerCase();

            // Return true if fieldValueStr contains content
            if (fieldValueStr.contains(content.toLowerCase())) {
                return true;
            }
        }

        // Not matches at all
        return false;
    }

    // CONSTRUCTORS:
    private EntityMatcher() {

    }
}
