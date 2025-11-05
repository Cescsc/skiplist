package skiplist;

import java.util.*;

/**
 * Example usage of SkipList data type.
 */
public class SkipListExample {
    public static void main(String[] args) {
        SkipList list = new SkipList();

        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            list.insert(rand.nextInt());
        }

        System.out.println(list.toString());

        if (list.find(1000) == null) {
            System.out.println("1000 not in list.");
        } else {
            System.out.println("1000 found in list!");
        }
    }
}
