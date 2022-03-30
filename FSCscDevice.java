// NAME: Noah Gabryluk
// ID: 1252318
// DATE: Thursday December 2, 2021
// EMAIL: ncgabryluk@gmail.com
// COURSE: CSC 3280 001
// Honor Code: I will practice academic and personal integrity and excellence
//             of character and expect the same from others.

public class FSCscDevice {
    private int MACnumber;
    private FSCscDevice next;

    // CONSTRUCTOR
    public FSCscDevice(int MACnumber, FSCscDevice next) {
        this.MACnumber = MACnumber;
        this.next = next;
    }


    // ACCESSORS
    public int getMACnumber() {
        return MACnumber;
    }

    public FSCscDevice getNext() {
        return next;
    }


    // MUTATORS
    public void setNext(FSCscDevice next) {
        this.next = next;
    }
}
