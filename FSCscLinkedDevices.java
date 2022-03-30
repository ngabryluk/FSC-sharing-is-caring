// NAME: Noah Gabryluk
// ID: 1252318
// DATE: Thursday December 2, 2021
// EMAIL: ncgabryluk@gmail.com
// COURSE: CSC 3280 001
// Honor Code: I will practice academic and personal integrity and excellence
//             of character and expect the same from others.

import java.util.*;

public class FSCscLinkedDevices {
    private FSCscDevice head;

    // CONSTRUCTORS
    public FSCscLinkedDevices() {
        head = null;
    }


    public boolean isEmpty() {
        return head == null;
    }


    public boolean search(int MACnumber) {
        return search(head, MACnumber);
    }
    private boolean search(FSCscDevice p, int MACnumber) {
        FSCscDevice helpPtr = p;
        while (helpPtr != null) {
            if (helpPtr.getMACnumber() == MACnumber)
                return true;
            helpPtr = helpPtr.getNext();
        }
        return false;
    }


    public void insert(int MACnumber) {
        head = insert(head, MACnumber);
    }
    private FSCscDevice insert(FSCscDevice head, int MACnumber) {
        // IF there is no list, newNode will be the first node, so just return it
        if (head == null || head.getMACnumber() > MACnumber) {
            head = new FSCscDevice(MACnumber, head);
            return head;
        }

        // ELSE, we have a list. Insert the new node at the correct location
        else {
            // We need to traverse to the correct insertion location...so we need a help ptr
            FSCscDevice helpPtr = head;
            // Traverse to correct insertion point
            while (helpPtr.getNext() != null) {
                if (helpPtr.getNext().getMACnumber() > MACnumber)
                    break; // we found our spot and should break out of the while loop
                helpPtr = helpPtr.getNext();
            }
            // Now make the new node. Set its next to point to the successor node.
            // And then make the predecessor node point to the new node
            FSCscDevice newNode = new FSCscDevice(MACnumber, helpPtr.getNext());
            helpPtr.setNext(newNode);
        }
        // Return head
        return head;
    }


    public void delete(int MACnumber) {
        head = delete(head, MACnumber);
    }
    private FSCscDevice delete(FSCscDevice head, int MACnumber) {
        // We can only delete if the list has nodes (is not empty)
        if (!isEmpty()) {
            // IF the first node (at the head) has the MACnumber of the device we are wanting to delete
            // we found it. Delete by skipping the node and making head point to the next node.
            if (head.getMACnumber() == MACnumber) {
                head = head.getNext();
            }
            // ELSE, the MACnumber is perhaps somewhere else in the list...so we must traverse and look for it
            else {
                // We need to traverse to find the MACnumber of the device we want to delete...so we need a help ptr
                FSCscDevice helpPtr = head;
                // Traverse to correct deletion point
                while (helpPtr.getNext() != null) {
                    if (helpPtr.getNext().getMACnumber() == MACnumber) {
                        helpPtr.setNext(helpPtr.getNext().getNext());
                        break; // we deleted the node and should break out of the while loop
                    }
                    helpPtr = helpPtr.getNext();
                }
            }
            // return the possibly updated head of the list
            return head;
        }
        return head;
    }


    public void showConnections(FSCstudent student, FSCscBST tree) {
        showConnections(head, student, tree);
    }
    private void showConnections(FSCscDevice p, FSCstudent student, FSCscBST tree) {

        // Print the student's MAC address, name, and the position of their device
        System.out.println("Connections for MAC " + student.getMACnumber() + ", " + student.getFirstName() + " " +
                student.getLastName() + ", currently at position (" + student.getX() + ", " + student.getY() + "):");
        // Print the amount of links that device has
        System.out.println("\tThere are a total of " + student.getNumLinks() + " link(s).");

        // Calculating the amount of active links (Linked AND in range):
        int activeLinks = 0; // Variable for counter of active links

        // Create an array of FSCstudent objects (with size of the number of links) to store all active links
        ArrayList<FSCstudent> studentsInRange = new ArrayList<>();

        // Traverse through the saved FSCstudent's list of linked devices
        FSCscDevice helpPtr = p;
        while (helpPtr != null) {
            FSCstudent studentLinkedTo = tree.findNode(helpPtr.getMACnumber());
            // IF the current node in the list is in range of the FSCstudent (inRange)
            if (withinRange(student.getX(), student.getY(), studentLinkedTo.getX(), studentLinkedTo.getY(), student.getBroadcastRange())) {
                studentsInRange.add(studentLinkedTo); // Add node to the array at the current index
                activeLinks++; // Add 1 to active links counter
            }
            helpPtr = helpPtr.getNext();
        }

        // Print how many active links within the FSCstudent's broadcast range
        if (activeLinks == 0) {
            System.out.println("\tThere are NO active links within the broadcast range of " +
                    student.getBroadcastRange() + ".");
        }
        else {
            System.out.println("\tThere are " + activeLinks + " active link(s) within the broadcast range of " +
                    student.getBroadcastRange() + ":");
        }

        // For each FSCstudent in the array
        for (FSCstudent stud : studentsInRange) {
            // Print the MAC address, name, and current position
            System.out.println("\t\tMAC " + stud.getMACnumber() + ", " + stud.getFirstName() + " " + stud.getLastName() +
                    ", currently at position (" + stud.getX() + ", " + stud.getY() + ")");
        }

    }


    // withinRange method (boolean) for use in showConnections (returns true if Student B is within Student A's broadcast range)
    private boolean withinRange(int x1, int y1, int x2, int y2, int broadcastRange) {

        // Calculate the distance between the two devices using the 2D Euclidean distance formula
        int distance = (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

        // IF that calculated distance is less than student's broadcast range
        if (distance <= broadcastRange) {
            return true; // return true
        }
        // ELSE (distance is greater than broadcast range)
        else {
            return false; // return false
        }
    }


    public void removeAllLinks(int quittingMAC, FSCscBST tree) {
        removeAllLinks(head, quittingMAC, tree);
    }
    private void removeAllLinks(FSCscDevice head, int quittingMAC, FSCscBST tree) {
        // We can only remove links if the list has nodes (is not empty)
        while (!isEmpty()) {
            // Unlink each device from quittingMAC's list of linked devices
            FSCsc.unlink(tree, quittingMAC, head.getMACnumber(), true);
            head = head.getNext(); // Move to the next node
        }
    }

}
