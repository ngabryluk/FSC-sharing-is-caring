// NAME: Noah Gabryluk
// ID: 1252318
// DATE: Thursday December 2, 2021
// EMAIL: ncgabryluk@gmail.com
// COURSE: CSC 3280 001
// Honor Code: I will practice academic and personal integrity and excellence
//             of character and expect the same from others.

public class FSCstudent {
    private String firstName;
    private String lastName;
    private int MACnumber;
    private int broadcastRange;
    private int x;
    private int y;
    private int numLinks;
    private FSCscLinkedDevices allowedMACs;
    private FSCstudent right;
    private FSCstudent left;

    // CONSTRUCTOR
    public FSCstudent(String firstName, String lastName, int MACnumber, int broadcastRange, int x, int y) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.MACnumber = MACnumber;
        this.broadcastRange = broadcastRange;
        this.x = x;
        this.y = y;
        this.numLinks = 0;
        this.allowedMACs = new FSCscLinkedDevices();
        left = right = null;
    }

    // ACCESSORS
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getMACnumber() {
        return MACnumber;
    }

    public int getBroadcastRange() {
        return broadcastRange;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumLinks() {
        return numLinks;
    }

    public FSCscLinkedDevices getAllowedMACs() {
        return allowedMACs;
    }

    public FSCstudent getLeft() {
        return left;
    }

    public FSCstudent getRight() {
        return right;
    }

    // MUTATORS
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMACnumber(int MACnumber) {
        this.MACnumber = MACnumber;
    }

    public void setBroadcastRange(int broadcastRange) {
        this.broadcastRange = broadcastRange;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNumLinks(int numLinks) {
        this.numLinks = numLinks;
    }

    public void setAllowedMACs(FSCscLinkedDevices allowedMACs) {
        this.allowedMACs = allowedMACs;
    }

    public void setLeft(FSCstudent left) {
        this.left = left;
    }

    public void setRight(FSCstudent right) {
        this.right = right;
    }
}
