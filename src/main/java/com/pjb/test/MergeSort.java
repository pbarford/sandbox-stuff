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

            //check if merge has reached the end of either left/right
            if ((leftpos < left.length) && (rightpos<right.length)) {
                if (left[leftpos] < right[rightpos]) {
                    //merge left
                    res[pos] = left[leftpos];
                    pos++;
                    leftpos++;
                }
                else {
                    //merge right
                    res[pos] = right[rightpos];
                    pos++;
                    rightpos++;
                }
            }
            else {
                //merge remaining from left/right
                if (leftpos >= left.length) {
                    //merge any remaining on the the right
                    while (rightpos < right.length) {
                        res[pos] = right[rightpos];
                        pos++;
                        rightpos++;
                    }
                }
                if (rightpos >= right.length) {
                    //merge any remaining on the left
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
