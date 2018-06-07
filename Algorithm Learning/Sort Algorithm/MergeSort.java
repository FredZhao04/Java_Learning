/**
 * Created by fred on 2018/6/7.
 */
public class MergeSort {
    private static Comparable[] temp;//用作merge

    public static void main(String[] args){
        Integer[] arr = {5, 2, 76, 32, 14, 67};
        sort(arr);
        for(int k = 0; k < arr.length; k++){
            System.out.println(arr[k]);
        }
    }
    public static void sort(Comparable[] a){
        temp = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    public static void sort(Comparable[] a, int low, int high){
        if(high <= low){
            return;
        }
        int mid = (low + high) / 2;
        sort(a, low, mid);
        sort(a, mid + 1, high);
        merge(a, low, mid, high);
    }

    private static void merge(Comparable[] a, int low, int mid, int high) {
        int i = low, j = mid + 1;
        for(int k = low; k <= high; k++){
            temp[k] = a[k];
        }
        for(int k = low; k <= high; k++){
            if(i > mid){
                a[k] = temp[j++];
            }else if(j > high){
                a[k] = temp[i++];
            }else if(a[j].compareTo(a[i]) < 0){
                a[k] = temp[j++];
            }else {
                a[k] = temp[i++];
            }
        }
    }
}
