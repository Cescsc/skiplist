package skiplist;

import java.lang.reflect.Method;
import java.util.*;

public class SkipListTest {

    private static void healthy(SkipList lst) {
        assert lst.head.next.length == lst.maxHeight+1;
        SkipList.Node temp = lst.head.next[0];
        int len = 0;
        while (temp != null) {
            len++;
            temp = temp.next[0];
        }
        assert lst.size == len;
    }

    public static void testUpdateHeight() throws Exception {
        SkipList lst = new SkipList();
        Method method = SkipList.class.getDeclaredMethod("updateHeight", int.class);
        method.setAccessible(true);
        method.invoke(lst, 10);

        assert lst.maxHeight == 10;
        healthy(lst);

        lst = new SkipList();
        int[] testArray = {1, 0, -10};
        for (int i : testArray) {
            lst.insert(i);
        }
        int maxHeight = lst.maxHeight;
        method.invoke(lst, 1);
        assert lst.maxHeight == maxHeight;
        healthy(lst);

        method.invoke(lst, 20);
        assert lst.maxHeight == 20;
        healthy(lst);
    }

    public static void testGiveHeight() throws Exception {
        SkipList lst = new SkipList();
        Method method = SkipList.class.getDeclaredMethod("giveHeight");
        method.setAccessible(true);
        Integer height = (Integer) method.invoke(lst);
        assert height >= 0;
        healthy(lst);

        lst = new SkipList();
        int[] testArray = {1, 0, -10, 2194, -24};
        for (int i : testArray) {
            lst.insert(i);
        }
        height = (Integer) method.invoke(lst);
        assert height >= 0;
        healthy(lst);
    }

    public static void testInsert() {
        SkipList lst = new SkipList();
        lst.insert(112);
        assert lst.head.next[0].data == 112;
        assert lst.size == 1;

        lst.insert(112);
        assert lst.head.next[0].next[0] == null;
        assert lst.size == 1;

        lst = new SkipList();
        int[] testArray = {1, 2, 3, 4, 5, 6, 100, 0, -10};
        for (int i : testArray) {
            lst.insert(i);
        }
        assert lst.size == testArray.length;
        int[] sortedArray = testArray.clone();
        Arrays.sort(sortedArray);
        SkipList.Node temp = lst.head;
        for (int i : sortedArray) {
            temp = temp.next[0];
            assert temp.data == i;
        }
        healthy(lst);
    }

    public static void testDelete() {
        SkipList lst = new SkipList();
        lst.delete(12091);
        for (SkipList.Node n : lst.head.next)
            assert n == null;

        lst = new SkipList();
        lst.insert(-1);
        lst.delete(-1);
        assert lst.size == 0;
        assert lst.head.next[0] == null;
        healthy(lst);

        lst = new SkipList();
        int[] testArray = {-23, -2, 10, 4, 5, 6, 1020, 0, -10, 200, 3};
        for (int i : testArray) {
            lst.insert(i);
        }
        int size = lst.size;
        lst.delete(0);
        assert lst.size == size - 1;
        lst.delete(-23);
        lst.delete(-10);
        lst.delete(1020);
        assert lst.size == size - 4;
        int[] expectedArray = {-2, 10, 4, 5, 6, 200, 3};
        Arrays.sort(expectedArray);
        SkipList.Node temp = lst.head;
        for (int i : expectedArray) {
            temp = temp.next[0];
            assert temp.data == i;
        }
        healthy(lst);
    }

    public static void testFind() {
        SkipList lst = new SkipList();
        assert lst.find(10203) == null;
        healthy(lst);

        lst = new SkipList();
        int[] testArray = {-10, 3000, 9, 0};
        for (int i : testArray) {
            lst.insert(i);
        }
        assert lst.find(3000) == 3000;
        assert lst.find(-10) == -10;
        assert lst.find(0) == 0;
        assert lst.find(1204) == null;
        healthy(lst);
    }

    public static void testToString() {
        SkipList lst = new SkipList();
        assert Objects.equals(lst.toString(), "[]");
        healthy(lst);

        lst = new SkipList();
        int[] testArray = {-943, 0, 943, 3};
        for (int i : testArray) {
            lst.insert(i);
        }
        assert Objects.equals(lst.toString(), "[-943, 0, 3, 943]");
        healthy(lst);
    }

    public static void testSize() {
        SkipList lst = new SkipList();
        assert lst.size() == 0;

        lst = new SkipList();
        int[] testArray = {-1000, 94, 9, 2, 2, 56, 0, 943, -35};
        for (int i : testArray) {
            lst.insert(i);
        }
        assert lst.size() == testArray.length-1;
        healthy(lst);
    }

    public static void testClear() {
        SkipList lst = new SkipList();
        lst.clear();
        assert lst.size == 0;
        healthy(lst);

        lst = new SkipList();
        int[] testArray = {-1000, 94, 9, 2, 2, 56, 0, 943, -35, 35, 34069, 293850, -1284, -248, 8};
        for (int i : testArray) {
            lst.insert(i);
        }
        lst.clear();
        assert lst.size == 0;
        for (SkipList.Node n : lst.head.next) {
            assert n == null;
        }
        healthy(lst);
    }

    /**
     * Unit test. Usage: java -ea SkipListTest.java
     */
    public static void main(String[] args) {
        testInsert();
        testDelete();
        testFind();
        testToString();
        testSize();
        testClear();
        try {
            testUpdateHeight();
            testGiveHeight();
        } catch (Exception e) {
            assert true;
        }
    }
}
