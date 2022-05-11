import java.util.ArrayList;

/**
 * Třída držící hlavní informace o planetě
 *
 * @author Samuel Vítek
 */
public class Planet {
    /** Název planety */
    private final String name;
    /** Typ planety */
    private final String type;
    /** Pozice planety */
    private double xPosition, yPosition;
    /** Rychlost planety */
    private double xSpeed, ySpeed;
    /** Hmotnost planety */
    private final double weight;
    /** Poloměr planety */
    private final double r;
    private ArrayList<Double> speeds = new ArrayList<>();
    private ArrayList<Double> times = new ArrayList<>();

    /**
     * Tridni konstruktor definujici planetu
     *
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

    /**
     * Vrátí název planety
     *
     * @return název planety
     */
    public String getName() {
        return name;
    }

    /**
     * Vrátí typ planety
     *
     * @return typ planety
     */
    public String getType() {
        return type;
    }

    /**
     * Vrátí x pozici planety
     *
     * @return x-ova souradnice
     */
    public double getxPosition() {
        return xPosition;
    }

    /**
     * Vrátí y pozici planety
     *
     * @return y-ova souradnice
     */
    public double getyPosition() {
        return yPosition;
    }

    /**
     * Vrátí x pozici rychlosti planety
     *
     * @return x-ova souradnice
     */
    public double getxSpeed() {
        return xSpeed;
    }

    /**
     * Vrátí y pozici rychlosti planety
     *
     * @return y-ova souradnice
     */
    public double getySpeed() {
        return ySpeed;
    }

    /**
     * Vrátí hmotnost planety
     *
     * @return hmotnost planety
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Vypočte a vrátí poloměr planety
     *
     * @return poloměr planety
     */
    public double r() {
        return Math.cbrt(weight / (4.0/3.0) * Math.PI);
    }

    /**
     * Vrátí poloměr planety
     *
     * @return poloměr planety
     */
    public double getR() {
        return r;
    }

    /**
     * Nastaví pozici x planety
     *
     * @param xPosition x pozice
     */
    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Nastaví pozici y planety
     *
     * @param yPosition y pozice
     */
    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Nastaví rychlost x planety
     *
     * @param xSpeed x rychlost
     */
    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Nastaví rychlost y planety
     *
     * @param ySpeed y rychlost
     */
    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setSpeed(double speed) {
        this.speeds.add(speed);
    }

    public double getSpeed(int i) {
        return speeds.get(i);
    }

    public double getTime(int i) {
        return times.get(i);
    }

    public void setTime(double time) {
        this.times.add(time);
    }

    public int arrayLength() {
        return this.speeds.size();
    }
}
