
package util;

import java.lang.reflect.Field;
import java.util.ArrayList;

import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator {

    public static String[] validate(Object obj) {
        ArrayList<String> errors = new ArrayList<>();

        if (obj == null) {
            errors.add("Object cannot be null");
            return errors.toArray(new String[0]);
        }

        Class<?> type = obj.getClass();

        // Check fields declared in the class and its superclasses
        while (type != null) {
            Field[] fields = type.getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive annotation =
                                field.getAnnotation(Positive.class);

                        if (value instanceof Number) {
                            double number =
                                    ((Number) value).doubleValue();

                            if (number <= 0) {
                                errors.add(field.getName() + " "
                                        + annotation.message());
                            }
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength annotation =
                                field.getAnnotation(MaxLength.class);

                        if (value instanceof String) {
                            String text = (String) value;

                            if (text.length() > annotation.value()) {
                                errors.add(field.getName()
                                        + " must not exceed "
                                        + annotation.value()
                                        + " characters");
                            }
                        }
                    }

                } catch (IllegalAccessException e) {
                    errors.add("Cannot validate field: "
                            + field.getName());
                }
            }

            type = type.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
