public class Planet {
    private String name;
    private String type;
    private double xPosition, yPosition;
    private double xSpeed, ySpeed;
    private double weight;
    private double r;

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
        return Math.cbrt(weight / (4.0/3.0) * Math.PI);
    }

    public double getR() {
        return r;
    }
}
