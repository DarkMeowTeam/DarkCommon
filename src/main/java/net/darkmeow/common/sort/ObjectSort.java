package net.darkmeow.common.sort;

import java.util.Comparator;

public interface ObjectSort {
    <T> void iSort(T[] array, int from, int to);

    <T> void iSort(T[] array);

    <T> void iSort(T[] array, int from, int to, Comparator<T> comparator);

    <T> void iSort(T[] array, Comparator<T> comparator);
}
