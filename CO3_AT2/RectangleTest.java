public class RectangleTest {
    public static void main(String[] args) {

        // ---- Test Case 1: Valid rectangle ----
        System.out.println("=== Test Case 1: Valid Rectangle ===");
        try {
            Rectangle r1 = new Rectangle(10, 5);
            System.out.println("Created: " + r1);
            System.out.println("Area = " + r1.getArea());
            System.out.println("Perimeter = " + r1.getPerimeter());
        } catch (InvalidDimensionException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ---- Test Case 2: Invalid - negative width ----
        System.out.println("\n=== Test Case 2: Negative Width ===");
        try {
            Rectangle r2 = new Rectangle(8, -3);
            System.out.println("Created: " + r2);
        } catch (InvalidDimensionException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ---- Test Case 3: Invalid - zero length ----
        System.out.println("\n=== Test Case 3: Zero Length ===");
        try {
            Rectangle r3 = new Rectangle(0, 4);
            System.out.println("Created: " + r3);
        } catch (InvalidDimensionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}