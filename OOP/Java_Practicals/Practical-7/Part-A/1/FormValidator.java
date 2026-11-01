import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
// Custom annotation: checks whether a field is blank
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}
// Custom annotation: checks maximum length
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}
// Signup form class
class SignupForm {
    @NotBlank
    @MaxLength(20)
    String name;
    @NotBlank
    @MaxLength(30)
    String email;
    @NotBlank
    @MaxLength(10)
    String password;
    SignupForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
// Validator class
class Validator {
    public static List<String> validate(Object obj)
            throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        Class<?> cls = obj.getClass();
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            // Check @NotBlank
            if (field.isAnnotationPresent(NotBlank.class)) {
                if (value == null ||
                    value.toString().trim().isEmpty()) {
                    errors.add(field.getName() + " cannot be blank");
                }
            }
            // Check @MaxLength
            if (field.isAnnotationPresent(MaxLength.class)) {
                MaxLength max = field.getAnnotation(MaxLength.class);
                if (value != null &&
                    value.toString().length() > max.value()) {
                    errors.add(field.getName()
                            + " must not exceed "
                            + max.value() + " characters");
                }
            }
        }
        return errors;
    }
}
// Main class
public class FormValidator {
    public static void main(String[] args)
            throws IllegalAccessException {
        SignupForm form = new SignupForm(
                "",
                "student@example.com",
                "verylongpassword123"
        );
        List<String> errors = Validator.validate(form);
        if (errors.isEmpty()) {
            System.out.println("Form is valid");
        } else {
            System.out.println("Validation Errors:");

            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}
