// package com.gradescope.cs201;
// import com.gradescope.cs201.UnsortedArrayBlackbox;
// import com.gradescope.cs201.SortedArrayBlackbox;

public class Sorting_and_searching_hw3 {

    public Sorting_and_searching_hw3() {
        // Constructor with no parameters
    }

    // get_median_index() returns the index of the median element of the array
    public int get_median_index(UnsortedArrayBlackbox unsorted_arr_bb) {
        int length = unsorted_arr_bb.get_length();
        int[] indices = new int[length];
        for (int i = 0; i < length; i++) {
            indices[i] = i;
        }

        int medianIndex = findMedianIndex(unsorted_arr_bb, 0, length - 1, indices);

        return indices[medianIndex];
    }
        // findMedianIndex() returns the index of the median element of the array
    private int findMedianIndex(UnsortedArrayBlackbox unsorted_arr_bb, int first, int last, int[] indices) {
        if (first == last) {
            return first;
        }

        int pivotIndex = partition(unsorted_arr_bb, first, last, indices);
        int medianIndex = unsorted_arr_bb.get_length() / 2;

        if (pivotIndex == medianIndex) {
            return pivotIndex;
        } else if (pivotIndex < medianIndex) {
            return findMedianIndex(unsorted_arr_bb, pivotIndex + 1, last, indices);
        } else {
            return findMedianIndex(unsorted_arr_bb, first, pivotIndex - 1, indices);
        }
    }
        // partition() returns the index of the pivot element of the array
    private int partition(UnsortedArrayBlackbox unsorted_arr_bb, int first, int last, int[] indices) {
        int pivotIndex = (first + last) / 2;
        int pivotValue = unsorted_arr_bb.compare(indices[first], indices[pivotIndex]);

        swap(indices, pivotIndex, last);

        int i = first;

        for (int j = first; j < last; j++) {
            if (unsorted_arr_bb.compare(indices[j], indices[last]) <= 0) {
                swap(indices, i, j);
                i++;
            }
        }

        swap(indices, i, last);

        return i;
    }
        // swap() swaps the elements at indices i and j in the array
    private void swap(int[] indices, int i, int j) {
        int temp = indices[i];
        indices[i] = indices[j];
        indices[j] = temp;
    }

    // get_index() returns the index of the element x in the array
    public int get_index(SortedArrayBlackbox sorted_arr_bb, int x) {
        int low = 0;
        int high = sorted_arr_bb.get_length() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparisonResult = sorted_arr_bb.compare(mid, x);

            if (comparisonResult == 0) {
                return mid; // Element found
            } else if (comparisonResult < 0) {
                high = mid - 1; // Search in the right half
            } else {
                low = mid + 1; // Search in the left half
            }
        }

        return -1; // Element not found
    }

    public static void main(String[] args) {
        Sorting_and_searching_hw3 sort_and_search = new Sorting_and_searching_hw3();
        /* test the median */
        //
        int[] unsorted_arr_1 = {1, 49, 3, 54, 29};
        UnsortedArrayBlackbox unsorted_arr_bb_1 = new UnsortedArrayBlackbox(unsorted_arr_1);
        System.out.println(sort_and_search.get_median_index(unsorted_arr_bb_1) + " must be 4");
        System.out.println(unsorted_arr_bb_1.get_comparison_num() + " must be <= 9"); 
        System.out.println("-----");
        //
        int[] unsorted_arr_2 = {1, 49, 29, 54, 3, 11, 20, 35, 40, 9, 67};
        UnsortedArrayBlackbox unsorted_arr_bb_2 = new UnsortedArrayBlackbox(unsorted_arr_2);
        System.out.println(sort_and_search.get_median_index(unsorted_arr_bb_2) + " must be 2");
        System.out.println(unsorted_arr_bb_2.get_comparison_num() + " must be <= 45");
        System.out.println("-----");
        //      
        /* test the searching */
        //
        int[] sorted_arr_1 = {1, 3, 9, 11, 20, 29, 35, 40, 49, 54, 67, 70};
        SortedArrayBlackbox sorted_arr_bb_1 = new SortedArrayBlackbox(sorted_arr_1);
        System.out.println(sort_and_search.get_index(sorted_arr_bb_1, 67) + " must be 10");
        System.out.println(sorted_arr_bb_1.get_comparison_num() + " must be <= 4");
        System.out.println("-----");
        //
        int[] sorted_arr_2 = {1, 3, 9, 11, 20, 29, 35, 40, 49, 54, 67, 70};
        SortedArrayBlackbox sorted_arr_bb_2 = new SortedArrayBlackbox(sorted_arr_2);
        System.out.println(sort_and_search.get_index(sorted_arr_bb_2, 35) + " must be 6");
        System.out.println(sorted_arr_bb_2.get_comparison_num() + " must be <= 4");
        System.out.println("-----");
        //
        //
        int[] sorted_arr_3 = {1, 3, 9, 11, 20, 29, 35, 40, 49, 54, 67, 70};
        SortedArrayBlackbox sorted_arr_bb_3 = new SortedArrayBlackbox(sorted_arr_3);
        System.out.println(sort_and_search.get_index(sorted_arr_bb_3, 12) + " must be -1");
        System.out.println(sorted_arr_bb_3.get_comparison_num() + " must be <= 4");         
    }
}