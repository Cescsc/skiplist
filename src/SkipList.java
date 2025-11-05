package skiplist;

import java.util.Random;

/**
 * Package skiplist implements a probabilistic skip list data type for java.
 *
 * A skip list is an ordered linked list with efficient search, insertion and deletion. The list achieves
 * this by maintaining multiple layers of linked lists. This package implements a sorted skip list with integer
 * elements supporting basic insert, search and delete as well as a size and toString method. The list does not
 * support duplicate elements.
 *
 * The advantage of a skip list is that it has all the benefits of an ordered linked list, that is with
 * efficient insertion and deletion, while being able to search for elements on par with a sorted array.
 * Therefore, the skip list has an average time complexity of O(log n) for all these three operations, at
 * the cost of requiring slightly more memory space. The skip list works by keeping a multiple sequences of
 * linked lists, where each element is added to the lowest list but has a fifty percent chance of making it
 * to the next list and so on. When searching for an element, the class starts at the highest, most sparse
 * list and quickly makes its way through that sequence. At this point, the probabilistic nature of the skip
 * list assures (with greater success, the longer the list) that the elements will be somewhat evenly spaced
 * out, allowing the class to "skip" through the list, quickly finding values closer to the searched element.
 * The skip list is implemented with a head node at the beginning, a node that is always the first part of every
 * subsequence of linked lists, acting as a starting point for every one of them. The height of a node in the
 * list refers to how many subsequences it appears in, and the max height is the amount of subsequences that
 * exist in the skip list. The default max height value when the skip list is created is set to 2.
 *
 * Example:
 * ------------------------------------------
 * head ->           4            : height: 2
 * head ->      3 -> 4 ->      11 : height: 1
 * head -> 1 -> 3 -> 4 -> 7 -> 11 : height: 0
 * ------------------------------------------
 */
public class SkipList {
    /**
     * A single list element of the skip list that stores an integer value.
     */
    class Node {
        public Integer data;
        public int height;
        public Node[] next;

        /**
         * Initializes a ListElement.
         */
        public Node(int height, Integer data) {
            this.height = height;
            this.data = data;
            next = new Node[height+1];
        }
    }

    public int size;
    public int maxHeight;
    public Node head;

    /**
     * Initializes the skip list. Creates the list with a default height of two.
     */
    public SkipList() {
        maxHeight = 2;
        head = new Node(maxHeight, null);
        size = 0;
    }

    /**
     * If the given height is bigger than the current maximum height of the list
     * then updates the max height and extends the references of the head node following
     * the height increase.
     */
    private void updateHeight(int height) {
        if (height <= maxHeight) {
            return;
        }
        else {
            maxHeight = height;
            Node[] update = new Node[maxHeight+1];
            int i = 0;
            for (Node elem : head.next) {
                update[i] = elem;
                i++;
            }
            head.next = update;
        }
    }

    /**
     * Returns a random height.
     */
    private int giveHeight() {
        int height = 0;
        Random rand = new Random();
        while (true) {
            if (rand.nextInt(2) == 1) {
                height++;
            }
            else {
                break;
            }
        }

        return height;
    }

    /**
     * Inserts the given integer element into the list. If the element is already in the
     * list then does nothing. Assigns a random height to the new element, if that height is
     * bigger than max height then increases the max height to that value.
     */
    public void insert(int data) {
        int nodeHeight = giveHeight();
        Node newNode = new Node(nodeHeight, data);

        if (nodeHeight > maxHeight) {
            updateHeight(nodeHeight);
        }

        Node[] update = new Node[maxHeight+1];
        Node temp = head;
        for (int i = maxHeight; i >= 0; i--) {
            for (int j = 0; j < size+1; j++) {
                if (temp.next[i] == null) {
                    update[i] = temp;
                    break;
                }
                Integer nextElem = temp.next[i].data;
                if (nextElem < data) {
                    temp = temp.next[i];
                } else if (data == nextElem) {
                    return;
                } else {
                    update[i] = temp;
                    break;
                }
            }
        }

        for (int i = 0; i <= newNode.height; i++) {
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }

        size++;
    }

    /**
     * Deletes the given integer element from the list. Does nothing if the
     * element does not exist.
     */
    public void delete(int data) {
        boolean hasDeleted = false;
        Node temp = head;
        for (int i = maxHeight; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (temp.next[i] == null) {
                    break;
                }
                Integer nextElem = temp.next[i].data;
                if (nextElem < data) {
                    temp = temp.next[i];
                } else if (data == nextElem) {
                    temp.next[i] = temp.next[i].next[i];
                    hasDeleted = true;
                } else {
                    break;
                }
            }
        }

        if (hasDeleted) {
            size--;
        }
    }

    /**
     * Searches if the given element is in the list. Returns the element if it is found,
     * otherwise returns null.
     */
    public Integer find(int elem) {
        Node temp = head;
        for (int i = maxHeight; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (temp.next[i] == null) {
                    break;
                }
                Integer nextElem = temp.next[i].data;
                if (nextElem < elem) {
                    temp = temp.next[i];
                } else if (elem == nextElem) {
                    return elem;
                } else {
                    break;
                }
            }
        }

        return null;
    }

    /**
     * Returns string representation of list.
     * The elements are enclosed in square brackets ("[]"). Adjacent elements are separated by ", ".
     */
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder output = new StringBuilder("[");
        Node temp = head.next[0];
        for (int i=0; i < size-1; i++) {
            output.append(temp.data).append(", ");
            temp = temp.next[0];
        }
        output.append(temp.data).append("]");
        return output.toString();
    }

    /**
     * Returns the length of the list, that is the number of elements, not including the head.
     * O(1) time.
     */
    public int size() {
        return size;
    }

    /**
     * Removes all elements except the head from the list. Resets max height to default two.
     */
    public void clear() {
        maxHeight = 2;
        head = new Node(maxHeight, null);
        size = 0;
    }
}
