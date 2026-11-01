
package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.annotation.Id;

public class Repository<T> {

    private final HashMap<String, T> data = new HashMap<>();

    // Find the field marked with @Id
    private String getId(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        Class<?> type = entity.getClass();

        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(entity);

                        if (value == null) {
                            throw new IllegalArgumentException(
                                "@Id field cannot be null"
                            );
                        }

                        return value.toString();

                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(
                            "Cannot read the @Id field", e
                        );
                    }
                }
            }

            type = type.getSuperclass();
        }

        throw new IllegalArgumentException(
            "No field marked with @Id found in " +
            entity.getClass().getName()
        );
    }

    // Save one object
    public T save(T entity) {
        String id = getId(entity);
        data.put(id, entity);
        return entity;
    }

    // Find an object by ID
    public T findById(String id) {
        return data.get(id);
    }

    // Return all stored objects
    public ArrayList<T> findAll() {
        return new ArrayList<>(data.values());
    }

    // Delete an object by ID
    public boolean delete(String id) {
        return data.remove(id) != null;
    }

    // Save multiple objects
    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    // Bounded generic method: calculate the average
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException(
                "Array cannot be null or empty"
            );
        }

        double sum = 0;

        for (N value : values) {
            if (value == null) {
                throw new IllegalArgumentException(
                    "Array cannot contain null values"
                );
            }
            sum += value.doubleValue();
        }

        return sum / values.length;
    }
}
