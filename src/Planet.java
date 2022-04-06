public class Planet {
    private String name;
    private String type;
    private int xPosition, yPosition;
    private double xSpeed, ySpeed;
    private double weight;

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
    public Planet(String name, String type, int xPosition, int yPosition, double xSpeed, double ySpeed, double weight) {
        this.name = name;
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.weight = weight;
    }
}
