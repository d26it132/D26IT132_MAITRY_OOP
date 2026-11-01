import java.lang.annotation.*;
import java.lang.reflect.Method;
// Custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}
// Class containing test methods
class MyTests {
    @Run
    public void testAddition() {
        System.out.println("Test Addition passed");
    }
    @Run
    public void testSubtraction() {
        System.out.println("Test Subtraction passed");
    }
    public void normalMethod() {
        System.out.println("This method should not run");
    }
    @Run
    public void testMessage() {
        System.out.println("Test Message passed");
    }
}
// Mini test runner
class MiniTestRunner {
    public static int runTests(Object obj)
            throws Exception {
        int count = 0;
        // Get all methods of the class
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            // Find only methods with @Run
            if (method.isAnnotationPresent(Run.class)) {
                // Check that the method has no parameters
                if (method.getParameterCount() == 0) {
                    method.setAccessible(true);
                    method.invoke(obj);
                    count++;
                }
            }
        }
        return count;
    }
}
// Main class
public class MiniTest {
    public static void main(String[] args) throws Exception {
        MyTests tests = new MyTests();
        int total = MiniTestRunner.runTests(tests);
        System.out.println("Total tests run: " + total);
    }
}
