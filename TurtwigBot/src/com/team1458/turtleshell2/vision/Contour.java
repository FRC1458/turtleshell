package com.team1458.turtleshell2.vision;

/**
 * Immutable representation of a contour
 *
 * @author asinghani
 */
public class Contour implements Comparable<Contour> {
    private final double area;
    private final double centerX;
    private final double centerY;
    private final double width;
    private final double height;
    private final double solidity;

    /**
     * Create a contour object
     * @param area
     * @param centerX
     * @param centerY
     * @param width
     * @param height
     * @param solidity
     */
    public Contour(double area, double centerX, double centerY, double width, double height, double solidity) {
        this.area = area;
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.solidity = solidity;
    }

    public double getArea() {
        return area;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getSolidity() {
        return solidity;
    }

    @Override
    public int compareTo(Contour o) {
        if(o.getArea() > getArea()) return -1;
        if(o.getArea() < getArea()) return 1;
        return 0;
    }
}
