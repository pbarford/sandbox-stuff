package com.pjb.test;

public class MergeSort {

    public Integer[] sort(Integer[] numbers) {
        if(numbers.length <= 1)
            return numbers;

        int middle = numbers.length / 2;

        Integer[] left = slice(numbers, 0, middle);
        Integer[] right = slice(numbers, middle, numbers.length);

        left = sort(left);
        right = sort(right);

        return merge(left, right);
    }

    private Integer[] merge(Integer[] left, Integer[] right) {
        Integer[] res = new Integer[left.length + right.length];
        int pos, leftpos, rightpos;
        pos = leftpos = rightpos = 0;

        while(pos < res.length) {

            if ((leftpos < left.length) && (rightpos<right.length)) {
                if (left[leftpos] < right[rightpos]) {
                    res[pos] = left[leftpos];
                    pos++;
                    leftpos++;
                }
                else {
                    res[pos] = right[rightpos];
                    pos++;
                    rightpos++;
                }
            }
            else {
                if (leftpos >= left.length) {
                    while (rightpos < right.length) {
                        res[pos] = right[rightpos];
                        pos++;
                        rightpos++;
                    }
                }
                if (rightpos >= right.length) {
                    while (leftpos < left.length) {
                        res[pos] = left[leftpos];
                        leftpos++;
                        pos++;
                    }
                }
            }
        }
        return res;
    }


    private Integer[] slice(Integer[] array, int start, int end) {
        int size = end - start;
        Integer[] res = new Integer[size];
        int x=0;
        for(int p = start; p < end; p++) {
            res[x] = array[p];
            x++;
        }
        return res;

    }
}
