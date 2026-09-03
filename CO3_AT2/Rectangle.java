public class Rectangle {
    private double length;
    private double width;

    // Constructor validates length and width before creating the object
    public Rectangle(double length, double width) throws InvalidDimensionException {
        if (length <= 0 || width <= 0) {
            throw new InvalidDimensionException(
                "Invalid dimensions: length and width must be positive. " +
                "Given length = " + length + ", width = " + width);
        }
        this.length = length;
        this.width = width;
    }

    public double getArea() {
        return length * width;
    }

    public double getPerimeter() {
        return 2 * (length + width);
    }

    @Override
    public String toString() {
        return "Rectangle[length=" + length + ", width=" + width + "]";
    }
}