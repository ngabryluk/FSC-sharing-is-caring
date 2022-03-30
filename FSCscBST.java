// NAME: Noah Gabryluk
// ID: 1252318
// DATE: Thursday December 2, 2021
// EMAIL: ncgabryluk@gmail.com
// COURSE: CSC 3280 001
// Honor Code: I will practice academic and personal integrity and excellence
//             of character and expect the same from others.

import java.util.*;

public class FSCscBST {
    private FSCstudent root;

    // CONSTRUCTORS
    public FSCscBST() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }


    public boolean search(int MACnumber) {
        return search(root, MACnumber);
    }
    private boolean search(FSCstudent p, int MACnumber) {
        if (p == null)
            return false;
        else {
            // if the data we are searching for is found at p (at the current root)
            if (MACnumber == p.getMACnumber())
                return true;
            else if (MACnumber < p.getMACnumber())
                return search(p.getLeft(), MACnumber);
            else
                return search(p.getRight(), MACnumber);
        }
    }

    public FSCstudent findNode(int MACnumber) {
        return findNode(root, MACnumber);
    }
    private FSCstudent findNode(FSCstudent p, int MACnumber) {
        if (p == null)
            return null;
        else {
            // if the data we are searching for is found at p (at the current root)
            if (MACnumber == p.getMACnumber())
                return p;
            else if (MACnumber < p.getMACnumber())
                return findNode(p.getLeft(), MACnumber);
            else
                return findNode(p.getRight(), MACnumber);
        }
    }


    public void printMembers() {
        printMembers(root);
    }
    private void printMembers(FSCstudent p) {
        if (p != null) {
            printMembers(p.getLeft());

            // Print that node's MAC address, student name, X and Y coordinates, and the number of links
            System.out.print("\tMAC " + p.getMACnumber() + ", " + p.getFirstName() + " " + p.getLastName() +
                    ", currently at position (" + p.getX() + ", " + p.getY() + "), " + p.getNumLinks());

            // Says "Link" if just 1 link
            if (p.getNumLinks() == 1) {
                System.out.println(" Link");
            }
            // Says "Links" otherwise
            else {
                System.out.println(" Links");
            }

            printMembers(p.getRight());
        }
    }


    public void moveDevices(Random rng, int sizeX, int sizeY) {
        moveDevices(root, rng, sizeX, sizeY);
    }
    private void moveDevices(FSCstudent p, Random rng, int sizeX, int sizeY) {

        // Traverse the FSCscBST (in order) and set the x and y of the current node to a random number in the coordinate plane
        if (p != null) {
            moveDevices(p.getLeft(), rng, sizeX, sizeY);
            p.setX(rng.nextInt(sizeX)); // Set the X coordinate of the current node to a random integer within the x-plane
            p.setY(rng.nextInt(sizeY)); // Set the Y coordinate of the current node to a random integer within the y-plane
            moveDevices(p.getRight(), rng, sizeX, sizeY);
        }

    }


    public void insert(FSCstudent newNode) {
        root = insert(root, newNode);
    }
    private FSCstudent insert(FSCstudent p, FSCstudent newNode) {
        // IF there is no tree, newNode will be the root, so just return it
        if (p == null)
            return newNode;

            // ELSE, we have a tree. Insert to the right or the left
        else {
            // Insert to the RIGHT of root
            if (newNode.getMACnumber() > p.getMACnumber()) {
                // Recursively insert into the right subtree
                // The result of insertion is an updated root of the right subtree
                FSCstudent temp = insert(p.getRight(), newNode);
                // Save this newly updated root of right subtree into p.right
                p.setRight(temp);
            }
            // Insert to the LEFT of root
            else {
                // Recursively insert into the left subtree
                // The result of insertion is an updated root of the left subtree
                FSCstudent temp = insert(p.getLeft(), newNode);
                // Save this newly updated root of left subtree into p.left
                p.setLeft(temp);
            }
        }
        // Return root of updated tree
        return p;
    }


    public void delete(int MACnumber) {
        root = delete(root, MACnumber);
    }
    private FSCstudent delete(FSCstudent p, int MACnumber) {
        FSCstudent node2delete, newnode2delete, node2save, parent;
        int saveMAC, saveRange, saveX, saveY, saveNumLinks;
        String saveFirstName, saveLastName;
        FSCscLinkedDevices saveLinkedDevices;

                // Step 1: Find the node we want to delete
        node2delete = findNode(p, MACnumber);
        // If node is not found (does not exist in tree), we clearly cannot delete it.
        if (node2delete == null)
            return root;

        // Step 2: Find the parent of the node we want to delete
        parent = parent(p, node2delete);

        // Step 3: Perform Deletion based on three possibilities

        // **1** :  node2delete is a leaf node
        if (isLeaf(node2delete)) {
            // if parent is null, this means that node2delete is the ONLY node in the tree
            if (parent == null)
                return null; // we return null as the updated root of the tree

            // Delete node if it is a left child
            if (MACnumber < parent.getMACnumber())
                parent.setLeft(null);
                // Delete node if it is a right child
            else
                parent.setRight(null);

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **2** : node2delete has only a left child
        if (hasOnlyLeftChild(node2delete)) {
            // if node2delete is the root
            if (parent == null)
                return node2delete.getLeft();

            // If node2delete is not the root,
            // it must the left OR the right child of some node

            // IF it is the left child of some node
            if (MACnumber < parent.getMACnumber())
                parent.setLeft(parent.getLeft().getLeft());
                // ELSE it is the right child of some node
            else
                parent.setRight(parent.getRight().getLeft());

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **3** :  node2delete has only a right child
        if (hasOnlyRightChild(node2delete)) {
            // if node2delete is the root
            if (parent == null)
                return node2delete.getRight();

            // If node2delete is not the root,
            // it must the left OR the right child of some node

            // IF it is the left child of some node
            if (MACnumber < parent.getMACnumber())
                parent.setLeft(parent.getLeft().getRight());
                // ELSE it is the right child of some node
            else
                parent.setRight(parent.getRight().getRight());

            // Finally, return the root of the tree (in case the root got updated)
            return p;
        }

        // **4** :  node2delete has TWO children.
        // Remember, we have two choices: the minVal from the right subtree (of the deleted node)
        // or the maxVal from the left subtree (of the deleted node)
        // We choose to find the minVal from the right subtree.
        newnode2delete = minNode(node2delete.getRight());
        // Now we need to temporarily save the data value(s) from this node
        saveFirstName = newnode2delete.getFirstName();
        saveLastName = newnode2delete.getLastName();
        saveMAC = newnode2delete.getMACnumber();
        saveRange = newnode2delete.getBroadcastRange();
        saveX = newnode2delete.getX();
        saveY = newnode2delete.getY();
        saveNumLinks = newnode2delete.getNumLinks();
        saveLinkedDevices = newnode2delete.getAllowedMACs();

        // Now, we recursively call our delete method to actually delete this node that we just copied the data from
        p = delete(p, saveMAC);

        // Now, put the saved data (in saveValue) into the correct place (the original node we wanted to delete)
        node2delete.setFirstName(saveFirstName);
        node2delete.setLastName(saveLastName);
        node2delete.setMACnumber(saveMAC);
        node2delete.setBroadcastRange(saveRange);
        node2delete.setX(saveX);
        node2delete.setY(saveY);
        node2delete.setNumLinks(saveNumLinks);
        node2delete.setAllowedMACs(saveLinkedDevices);

        // Finally, return the root of the tree (in case the root got updated)
        return p;
    }


    public FSCstudent minNode(FSCstudent root) {
        if (root == null) {
            return null;
        }
        else {
            if (root.getLeft() == null) {
                return root;
            }
            else {
                return minNode(root.getLeft());
            }
        }
    }


    private FSCstudent parent(FSCstudent root, FSCstudent p) {
        // Take care of NULL cases
        if (root == null || root == p)
            return null; // because there is on parent

        // If root is the actual parent of node p
        if (root.getLeft()==p || root.getRight()==p)
            return root; // because root is the parent of p

        // Look for p's parent in the left side of root
        if (p.getMACnumber() < root.getMACnumber())
            return parent(root.getLeft(), p);

            // Look for p's parent in the right side of root
        else if (p.getMACnumber() > root.getMACnumber())
            return parent(root.getRight(), p);

            // Take care of any other cases
        else
            return null;
    }


    public boolean isLeaf(FSCstudent p) {
        return (p.getLeft()==null && p.getRight()==null);
    }


    public boolean hasOnlyLeftChild(FSCstudent p) {
        return (p.getLeft()!=null && p.getRight()==null);
    }


    public boolean hasOnlyRightChild(FSCstudent p) {
        return (p.getLeft() == null && p.getRight() != null);
    }

}
