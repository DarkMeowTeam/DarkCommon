package net.darkmeow.common.sort;

@SuppressWarnings("DuplicatedCode")
public class IntHeapsort implements IntSort {
    private static void siftDown(int[] array, int p, int value, int from, int to) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || array[k] < array[k - 1]) {
                --k;
            }
            if (array[k] <= value) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(int[] array, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to);
        }
        while (--to > from) {
            int max = array[from];
            siftDown(array, from, array[to], from, to);
            array[to] = max;
        }
    }

    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    private static void siftDown(int[] array, int p, int value, int from, int to, int[] keys) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || keys[array[k]] < keys[array[k - 1]]) {
                --k;
            }
            if (keys[array[k]] <= keys[value]) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(int[] array, int from, int to, int[] keys) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, keys);
        }
        while (--to > from) {
            int max = array[from];
            siftDown(array, from, array[to], from, to, keys);
            array[to] = max;
        }
    }

    public static void sort(int[] array, int[] keys) {
        sort(array, 0, array.length, keys);
    }

    private static void siftDown(int[] array, int p, int value, int from, int to, float[] keys) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || keys[array[k]] < keys[array[k - 1]]) {
                --k;
            }
            if (keys[array[k]] <= keys[value]) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(int[] array, int from, int to, float[] keys) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, keys);
        }
        while (--to > from) {
            int max = array[from];
            siftDown(array, from, array[to], from, to, keys);
            array[to] = max;
        }
    }

    public static void sort(int[] array, float[] keys) {
        sort(array, 0, array.length, keys);
    }

    private static void siftDown(int[] array, int p, int value, int from, int to, IntComparator comp) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || comp.compare(array[k], array[k - 1]) < 0) {
                --k;
            }
            if (comp.compare(array[k], value) <= 0) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(int[] array, int from, int to, IntComparator comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, comp);
        }
        while (--to > from) {
            int max = array[from];
            siftDown(array, from, array[to], from, to, comp);
            array[to] = max;
        }
    }

    public static void sort(int[] array, IntComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private IntHeapsort() {}

    public static final IntHeapsort INSTANCE = new IntHeapsort();

    @Override
    public void iSort(int[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(int[] array) {
        sort(array);
    }

    @Override
    public void iSort(int[] array, int from, int to, int[] keys) {
        sort(array, from, to, keys);
    }

    @Override
    public void iSort(int[] array, int[] keys) {
        sort(array, keys);
    }

    @Override
    public void iSort(int[] array, int from, int to, float[] keys) {
        sort(array, from, to, keys);
    }

    @Override
    public void iSort(int[] array, float[] keys) {
        sort(array, keys);
    }

    @Override
    public void iSort(int[] array, int from, int to, IntComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(int[] array, IntComparator comp) {
        sort(array, comp);
    }
}
