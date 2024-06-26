package net.darkmeow.common.sort;

public class LongIntrosort implements LongSort {
    private static final int INSERTION_SORT_SIZE = 64;

    private static void sort(long[] array, int from, int to, int depth) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            LongInsertionSort.sort(array, from, to);
            return;
        }

        if (depth == 0) {
            LongHeapsort.sort(array, from, to);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        long s3v = array[s3];

        if (array[s5] < array[s2]) {
            Utils.swap(array, s5, s2);
        }
        if (array[s4] < array[s1]) {
            Utils.swap(array, s4, s1);
        }
        if (array[s5] < array[s4]) {
            Utils.swap(array, s5, s4);
        }
        if (array[s2] < array[s1]) {
            Utils.swap(array, s2, s1);
        }
        if (array[s4] < array[s2]) {
            Utils.swap(array, s4, s2);
        }

        if (s3v < array[s2]) {
            if (s3v < array[s1]) {
                array[s3] = array[s2];
                array[s2] = array[s1];
                array[s1] = s3v;
            } else {
                array[s3] = array[s2];
                array[s2] = s3v;
            }
        } else if (s3v > array[s4]) {
            if (s3v > array[s5]) {
                array[s3] = array[s4];
                array[s4] = array[s5];
                array[s5] = s3v;
            } else {
                array[s3] = array[s4];
                array[s4] = s3v;
            }
        }

        if (array[s1] < array[s2] && array[s2] < array[s3] && array[s3] < array[s4] && array[s4] < array[s5]) {
            Utils.swap(array, from, s1);
            Utils.swap(array, last, s5);
            long p = array[from];
            long q = array[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                long m = array[mid];
                if (m > q) {
                    long r;
                    do {
                        r = array[--right];
                    } while (r > q && right > mid);
                    if (r < p) {
                        array[mid] = array[++left];
                        array[left] = r;
                    } else {
                        array[mid] = r;
                    }
                    array[right] = m;
                } else if (m < p) {
                    array[mid] = array[++left];
                    array[left] = m;
                }
            }

            array[from] = array[left];
            array[left] = p;
            array[last] = array[right];
            array[right] = q;

            sort(array, from, left, depth - 1);
            sort(array, left + 1, right, depth - 1);
            sort(array, right + 1, to, depth - 1);
        } else {
            Utils.swap(array, from, s3);

            long p = array[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                long l = array[left];
                if (l > p) {
                    long r;
                    do {
                        r = array[--right];
                    } while (r >= p && right > left);
                    array[left] = r;
                    array[right] = l;
                }
            }

            array[from] = array[--right];
            array[right] = p;

            sort(array, from, right, depth - 1);
            sort(array, right + 1, to, depth - 1);
        }
    }

    public static void sort(long[] array, int from, int to) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth);
    }

    public static void sort(long[] array) {
        sort(array, 0, array.length);
    }

    private static void sort(long[] array, int from, int to, int depth, LongComparator comp) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            LongInsertionSort.sort(array, from, to, comp);
            return;
        }

        if (depth == 0) {
            LongHeapsort.sort(array, from, to, comp);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        long s3v = array[s3];

        if (comp.compare(array[s5], array[s2]) < 0) {
            Utils.swap(array, s5, s2);
        }
        if (comp.compare(array[s4], array[s1]) < 0) {
            Utils.swap(array, s4, s1);
        }
        if (comp.compare(array[s5], array[s4]) < 0) {
            Utils.swap(array, s5, s4);
        }
        if (comp.compare(array[s2], array[s1]) < 0) {
            Utils.swap(array, s2, s1);
        }
        if (comp.compare(array[s4], array[s2]) < 0) {
            Utils.swap(array, s4, s2);
        }

        if (comp.compare(s3v, array[s2]) < 0) {
            if (comp.compare(s3v, array[s1]) < 0) {
                array[s3] = array[s2];
                array[s2] = array[s1];
                array[s1] = s3v;
            } else {
                array[s3] = array[s2];
                array[s2] = s3v;
            }
        } else if (comp.compare(s3v, array[s4]) > 0) {
            if (comp.compare(s3v, array[s5]) > 0) {
                array[s3] = array[s4];
                array[s4] = array[s5];
                array[s5] = s3v;
            } else {
                array[s3] = array[s4];
                array[s4] = s3v;
            }
        }

        if (comp.compare(array[s1], array[s2]) < 0 && comp.compare(array[s2], array[s3]) < 0 && comp.compare(
            array[s3],
            array[s4]
        ) < 0 && comp.compare(array[s4], array[s5]) < 0) {
            Utils.swap(array, from, s1);
            Utils.swap(array, last, s5);
            long p = array[from];
            long q = array[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                long m = array[mid];
                if (comp.compare(m, q) > 0) {
                    long r;
                    do {
                        r = array[--right];
                    } while (comp.compare(r, q) > 0 && right > mid);
                    if (comp.compare(r, p) < 0) {
                        array[mid] = array[++left];
                        array[left] = r;
                    } else {
                        array[mid] = r;
                    }
                    array[right] = m;
                } else if (comp.compare(m, p) < 0) {
                    array[mid] = array[++left];
                    array[left] = m;
                }
            }

            array[from] = array[left];
            array[left] = p;
            array[last] = array[right];
            array[right] = q;

            sort(array, from, left, depth - 1, comp);
            sort(array, left + 1, right, depth - 1, comp);
            sort(array, right + 1, to, depth - 1, comp);
        } else {
            Utils.swap(array, from, s3);

            long p = array[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                long l = array[left];
                if (comp.compare(l, p) > 0) {
                    long r;
                    do {
                        r = array[--right];
                    } while (comp.compare(r, p) >= 0 && right > left);
                    array[left] = r;
                    array[right] = l;
                }
            }

            array[from] = array[--right];
            array[right] = p;

            sort(array, from, right, depth - 1, comp);
            sort(array, right + 1, to, depth - 1, comp);
        }
    }

    public static void sort(long[] array, int from, int to, LongComparator comp) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth, comp);
    }

    public static void sort(long[] array, LongComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private LongIntrosort() {}

    public static final LongIntrosort INSTANCE = new LongIntrosort();

    @Override
    public void iSort(long[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(long[] array) {
        sort(array);
    }

    @Override
    public void iSort(long[] array, int from, int to, LongComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(long[] array, LongComparator comp) {
        sort(array, comp);
    }
}
