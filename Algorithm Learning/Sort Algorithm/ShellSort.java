/**
 * 插入排序在本身数量较少的时候效率很高，但是如果待排序的数量很大时，效率不理想。
 * 插入排序每次都将右边的数从后往前依次比较，找到位置时，其后的数向右移动一位。我们不希望它一步一步的移动，
 * 而是大步移动，这就是希尔排序。
 * Created by fred on 2018/6/8.
 */
public class ShellSort {
    public static void main(String[] args){
        Integer[] arr = {1,7,3,9,3,2,7,4};
        sort(arr);
        for(int k = 0; k < arr.length; k++){
            System.out.println(arr[k]);
        }
    }

    public static void sort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while(h < N/3){
            h = 3 * h + 1;
        }
        while(h >= 1){
            for(int i = h; i < N; i++){
                for(int j = i; j >= h; j -= h){
                    if(a[j].compareTo(a[j - h]) < 0){
                        Comparable temp = a[j];
                        a[j] = a[j - h];
                        a[j - h] = temp;
                    }
                }
            }
            h = h / 3;
        }
    }
}
