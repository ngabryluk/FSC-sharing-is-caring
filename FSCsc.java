// NAME: Noah Gabryluk
// ID: 1252318
// DATE: Thursday December 2, 2021
// EMAIL: ncgabryluk@gmail.com
// COURSE: CSC 3280 001
// Honor Code: I will practice academic and personal integrity and excellence
//             of character and expect the same from others.

// import
import java.util.*;

// class FSCsc
public class FSCsc {

    // Method to Process JOIN
    public static void join(Scanner in, FSCscBST tree) {

        // Variables to store inputs from the remainder of the line following the command:
        int MACnumber = in.nextInt(); // Integer (non-negative) representing the student's MAC
        String firstName = in.next(); // String representing the first name of the student
        String lastName = in.next(); // String representing the last name of the student
        int broadcastRange = in.nextInt(); // Integer (non-negative) representing the maximum broadcast range of the student's device
        int x = in.nextInt(); // Integer representing the initial X coordinate of the student's device
        int y = in.nextInt(); // Integer representing the initial Y coordinate of the student's device

        // Create a new FSCstudent object using the information above
        FSCstudent student = new FSCstudent(firstName, lastName, MACnumber, broadcastRange, x, y);

        // IF the student is NOT in the BST already
        if (!tree.search(MACnumber)) {
            tree.insert(student); // Add the previously created FSCstudent to the BST

            // Print a message saying that the student joined the system
            System.out.println(firstName + " " + lastName + ", MAC " + MACnumber + ", joined the FSC Sharing is Caring " +
                    "system.");
        }
        // ELSE (student is already in the BST)
        else {
            // Print an error saying that the student is already in the system
            System.out.println("Cannot Perform JOIN Command:");
            System.out.println("\tMAC " + MACnumber + ", " + firstName + " " + lastName + " - already a participant in " +
                    "the FSC Sharing is Caring system.");
        }

    }
    // END OF JOIN METHOD

    // Method to Process FINDMAC
    public static void findMAC(Scanner in, FSCscBST tree) {

        int MACnumber = in.nextInt(); // Store the MAC address given in the input

        FSCstudent student = tree.findNode(MACnumber);

        // IF we find the node that matches the stored MAC address is in the BST
        if (student != null) {
            // Print that node's MAC address, student name, X and Y coordinates, and the number of links
            System.out.print("Found:  MAC " + MACnumber + ", " + student.getFirstName() + " " + student.getLastName()
                    + ", currently at position (" + student.getX() + ", " + student.getY() + "), " + student.getNumLinks());

            // Says "Link" if just 1 link
            if (student.getNumLinks() == 1) {
                System.out.println(" Link");
            }
            // Says "Links" otherwise
            else {
                System.out.println(" Links");
            }
        }
        // ELSE (node is not found in the BST)
        else {
            // Print an error saying the MAC address was not found in the system
            System.out.println("MAC " + MACnumber + " not found in the FSC Sharing is Caring system.");
        }

    }
    // END OF FINDMAC METHOD

    // Method to Process LINK
    public static void link(Scanner in, FSCscBST tree) {

        int MAC1 = in.nextInt(); // Variable storing first MAC address given in the input
        int MAC2 = in.nextInt(); // Variable storing second MAC address given in the input

        // Create a FSCstudent, Student 1, that stores the found FSCstudent in the BST or null when searching for the first MAC
        FSCstudent student1 = tree.findNode(MAC1);

        // Create a second FSCstudent, Student 2, that stores the found FSCstudent in the BST or null when searching for the second MAC
        FSCstudent student2 = tree.findNode(MAC2);

        // IF both FSCstudents are in the BST
        if (student1 != null && student2 != null) {

            // IF the two MAC address are NOT the same
            if (student1 != student2) {
                // IF each MAC address is not found in the other's list of linked devices
                if (!student1.getAllowedMACs().search(MAC2) && !student2.getAllowedMACs().search(MAC1)) {

                    // Adding Student 2's device MAC to Student 1's list of linked devices:
                    student1.getAllowedMACs().insert(MAC2); // Insert Student 2's MAC address into the linked devices of Student 1
                    student1.setNumLinks(student1.getNumLinks() + 1); // Increases student 1's numLinks count by 1

                    // Adding Student 1's device MAC to Student 2's list of linked devices:
                    student2.getAllowedMACs().insert(MAC1);// Insert Student 1's MAC address into the linked devices of Student 2
                    student2.setNumLinks(student2.getNumLinks() + 1); // Increases student 2's numLinks count by 1

                    // Print a message saying that the two MAC address have been linked
                    System.out.println("MAC " + MAC1 + " and MAC " + MAC2 + " are now linked.");

                }
                // ELSE (The two devices are already linked)
                else {
                    System.out.println("Cannot Perform LINK Command:");
                    System.out.println("\tMAC " + MAC1 + " and MAC " + MAC2 + " are already linked.");
                }

            }
            // ELSE (The two MAC addresses are the same)
            else {
                // Print an error saying that the MAC cannot link to itself
                System.out.println("Cannot Perform LINK Command:");
                System.out.println("\tMAC " + MAC1 + " cannot link to itself.");
            }

        }
        // ELSE (either MAC is not in the system)
        else {
            // Print an error:

            System.out.println("Cannot Perform LINK Command:");

            // IF BOTH MAC address are not in the system
            if (student1 == null && student2 == null) {
                // Message saying that the first MAC address is not in system
                System.out.println("\tMAC " + MAC1 + " - This MAC Address is not in the FSC Sharing is Caring system.");
                // Message saying that the second MAC address is not in system
                System.out.println("\tMAC " + MAC2 + " - This MAC Address is not in the FSC Sharing is Caring system.");
            }
            // ELSE IF MAC1 is not in the system
            else if (student1 == null) {
                // Message saying that the first MAC address is not in system
                System.out.println("\tMAC " + MAC1 + " - This MAC Address is not in the FSC Sharing is Caring system.");
            }
            // ELSE (MAC2 is not in the system)
            else {
                // Message saying that the second MAC address is not in system
                System.out.println("\tMAC " + MAC2 + " - This MAC Address is not in the FSC Sharing is Caring system.");
            }
        }

    }
    // END OF LINK METHOD

    // Method to Process UNLINK
    public static void unlink(FSCscBST tree, int MACnumber1, int MACnumber2, boolean removingLinks) {

        // Create a FSCstudent, Student 1, that stores the found FSCstudent in the BST or null when searching for the first MAC
        FSCstudent student1 = tree.findNode(MACnumber1);

        // Create a second FSCstudent, Student 2, that stores the found FSCstudent in the BST or null when searching for the second MAC
        FSCstudent student2 = tree.findNode(MACnumber2);

        // IF both FSCstudents are in the BST
        if (student1 != null && student2 != null) {

            // IF each MAC address is found in the other's list of linked devices
            if (student1.getAllowedMACs().search(MACnumber2) && student2.getAllowedMACs().search(MACnumber1)) {

                // Go into Student 1's list of linked devices and delete Student 2's device from that list
                student1.getAllowedMACs().delete(MACnumber2);
                student1.setNumLinks(student1.getNumLinks() - 1); // Decreases student 1's numLinks count by 1

                // Go into Student 2's list of linked devices and delete Student 1's device from that list
                student2.getAllowedMACs().delete(MACnumber1);
                student2.setNumLinks(student2.getNumLinks() - 1); // Decreases student 1's numLinks count by 1

                // Print a message saying that the two MAC addresses are no longer linked
                if (!removingLinks) {
                    System.out.println("MAC " + MACnumber1 + " and MAC " + MACnumber2 + " are no longer linked.");
                }

            }
            // ELSE (not in each other's linked devices list)
            else {
                // Print an error stating that the two MAC addresses are not linked
                if (!removingLinks) {
                    System.out.println("Cannot Perform UNLINK Command:");
                    System.out.println("\tMAC " + MACnumber1 + " and MAC " + MACnumber2 + " are not currently linked.");
                }
            }

        }
        // ELSE (either device not found in BST or each MAC address is not found in the other's linked devices)
        else {
            if (!removingLinks) {
                // Print an error:

                System.out.println("Cannot Perform UNLINK Command:");

                // IF BOTH MAC address are not in the system
                if (student1 == null && student2 == null) {
                    // Message saying that the first MAC address is not in system
                    System.out.println("\tMAC " + MACnumber1 + " - This MAC Address is not in the FSC Sharing is Caring system.");
                    // Message saying that the second MAC address is not in system
                    System.out.println("\tMAC " + MACnumber2 + " - This MAC Address is not in the FSC Sharing is Caring system.");
                }
                // ELSE IF MAC1 is not in the system
                else if (student1 == null) {
                    // Message saying that the first MAC address is not in system
                    System.out.println("\tMAC " + MACnumber1 + " - This MAC Address is not in the FSC Sharing is Caring system.");
                }
                // ELSE (MAC2 is not in the system)
                else {
                    // Message saying that the second MAC address is not in system
                    System.out.println("\tMAC " + MACnumber2 + " - This MAC Address is not in the FSC Sharing is Caring system.");
                }
            }
        }

    }
    // END OF UNLINK METHOD

    // Method to Process QUIT
    public static void quit(Scanner in, FSCscBST tree) {

        int MACnumber = in.nextInt(); // Variable storing the MAC address of the user intending to quit the FSCsc program

        // Create a FSCstudent that stores the found FSCstudent in the BST or null when searching for the given MAC address
        FSCstudent student = tree.findNode(MACnumber);

        // IF the FSCstudent is in the system
        if (student != null) {
            // Remove this device from any other laptop's list of linked devices:
            // Call this list's removeAllLinks method, which traverses through the list of linked devices and unlinks this
            // student's device from every device in the list
            student.getAllowedMACs().removeAllLinks(MACnumber, tree);

            // Delete the student from the BST
            tree.delete(MACnumber);

            // Print a message saying that the MAC has been removed from the system
            System.out.println("MAC " + MACnumber + " has been removed from the FSC Sharing is Caring system.");
        }
        // ELSE (not in the system)
        else {
            // Print an error stating that the MAC address is not found in the system
            System.out.println("Cannot Perform QUIT Command:");
            System.out.println("\tMAC " + MACnumber + " not found in the FSC Sharing is Caring system.");
        }

    }
    // END OF QUIT METHOD

    // Method to Process SHOWCONNECTIONS
    public static void showConnectionsMain(Scanner in, FSCscBST tree) {

        int MACnumber = in.nextInt(); // Variable storing the MAC address from the input

        // Create a FSCstudent that stores the found FSCstudent in the BST or null when searching for the given MAC address
        FSCstudent student = tree.findNode(MACnumber);

        // IF the FSCstudent is in the database
        if (student != null) {
            // IF the student's list of linked devices is NOT empty
            if (!student.getAllowedMACs().isEmpty()) {
                student.getAllowedMACs().showConnections(student, tree);
            }
            // ELSE (list of linked devices is empty)
            else {
                // Print a message saying that the MAC address has no links
                System.out.println("MAC " + student.getMACnumber() + " has no links.");
            }
        }
        // ELSE (student is not in system)
        else {
            // Print an error stating the missing MAC address and that it is not in the system
            System.out.println("Cannot Perform SHOWCONNECTIONS Command:");
            System.out.println("\tMAC " + MACnumber + " - This MAC Address is not in the FSC Sharing is Caring system.");
        }

    }
    // END OF SHOWCONNECTIONS METHOD


    // Method to Process PRINTMEMBERS
    public static void printMembersMain(FSCscBST tree) {

        // IF the FSCscBST is NOT empty
        if (!tree.isEmpty()) {
            System.out.println("Members of FSC Sharing is Caring System:");

            // Traverses the BST and prints the MAC address, student name, the position of the student's device, and
            // how many devices their device is currently linked to
            tree.printMembers();
        }
        // ELSE (BST is empty)
        else {
            // Print an error saying that there is no one in the system
            System.out.println("Cannot Perform PRINTMEMBERS Command:");
            System.out.println("\tThere are currently no participants in the FSC Sharing is Caring system.");
        }

    }
    // END OF PRINTMEMBERS METHOD

    // Method to Process MOVEDEVICES
    public static void moveDevicesMain(FSCscBST tree, Random rng, int sizeX, int sizeY) {

        // IF the FSCscBST is NOT empty
        if (!tree.isEmpty()) {

            // Call the moveDevices method on the tree that gives every student new random coordinates
            tree.moveDevices(rng, sizeX, sizeY);

            // Print a message saying that all devices have been moved
            System.out.println("All devices successfully moved.");

        }
        // ELSE (FSCscBST is empty)
        else {
            // Print an error saying that there is no one in the system
            System.out.println("Cannot Perform MOVEDEVICES Command:");
            System.out.println("\tThere are currently no participants in the FSC Sharing is Caring system.");
        }

    }
    // END OF MOVEDEVICES METHOD

    // MAIN METHOD
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); // Make the Scanner

        // First line of input
        int seed = in.nextInt(); // Integer that will be used to seed random numbers

        // Create and "seed" the random number generator using the Random class and the integer from the first input in
        // the main method (this will be passed to the moveDevicesMain() method
        Random rng = new Random(seed);

        // Second line of input
        int sizeX = in.nextInt(); // Integer representing the size of the x-plane of the 2D coordinate plane
        int sizeY = in.nextInt(); // Integer representing the size of the y-plane of the 2D coordinate plane

        // Create a FSCscBST that will be passed along to each necessary method
        FSCscBST tree = new FSCscBST();

        // Third line of input
        int numCommands = in.nextInt(); // Integer indicating the number of commands happening during the simulation

        // Loop that repeats the same amount of times as the number of commands specified above
        for (int i = 0; i < numCommands; i++) {

            String command = in.next(); // Takes in the command from the input

            // IF the command is JOIN
            if (command.equals("JOIN")) {
                // Call the join method
                join(in, tree);
            }

            // IF the command is FINDMAC
            else if (command.equals("FINDMAC")) {
                // Call the findMAC method
                findMAC(in, tree);
            }

            // IF the command is LINK
            else if (command.equals("LINK")) {
                // Call the link method
                link(in, tree);
            }

            // IF the command is UNLINK
            else if (command.equals("UNLINK")) {
                // Call the unlink method
                unlink(tree, in.nextInt(), in.nextInt(), false);
            }

            // IF the command is QUIT
            else if (command.equals("QUIT")) {
                // call the quit method
                quit(in, tree);
            }

            // IF the command is SHOWCONNECTIONS
            else if (command.equals("SHOWCONNECTIONS")) {
                // Call the showConnections method
                showConnectionsMain(in, tree);
            }

            // IF the command is PRINTMEMBERS
            else if (command.equals("PRINTMEMBERS")) {
                // Call the printMembers method
                printMembersMain(tree);
            }

            // IF the command is MOVEDEVICES
            else if (command.equals("MOVEDEVICES")) {
                // Call the moveDevices method
                moveDevicesMain(tree, rng, sizeX, sizeY);
            }

        }
        // END OF LOOP

        // Close the Scanner
        in.close();

    }
    // END OF MAIN METHOD
}
// END OF CLASS