public class Planet {
    private final String name;
    private final String type;
    private double xPosition, yPosition;
    private double xSpeed, ySpeed;
    private final double weight;
    private final double r;

    /**
     * Tridni konstruktor definujici planetu
     * @param name nazev planety
     * @param type typ planety
     * @param xPosition pozice planety na ose x
     * @param yPosition pozice planety na ose y
     * @param xSpeed rychlost planety v ramci osy x
     * @param ySpeed rychlost planety v ramci osy x
     * @param weight vaha planety
     */
    public Planet(String name, String type, double xPosition, double yPosition, double xSpeed, double ySpeed, double weight) {
        this.name = name;
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.weight = weight;
        this.r = r();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public double getWeight() {
        return weight;
    }

    public double r() {
        double r = Math.cbrt(weight / (4.0/3.0) * Math.PI);

        if (r > 1E6) {
            return 1E11;
        } else {
            return r;
        }
    }

    public double getR() {
        return r;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

}
