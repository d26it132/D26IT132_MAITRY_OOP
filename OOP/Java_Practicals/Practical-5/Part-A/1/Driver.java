public class Driver {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(8, 5),
            new Circle(3)
        };
        double total = 0;
        double largest = 0;
        for (Shape s : shapes) {
            double a = s.area();
            System.out.println("Area: " + a);
            total += a;
            if (a > largest) {
                largest = a;
            }
        }
        System.out.println("Total area: " + total);
        System.out.println("Largest area: " + largest);
    }
}