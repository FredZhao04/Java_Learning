/**
 * Created by fred on 2018/6/7.
 */
public class MySortLib {
    /**
     * 选择排序：首先，找到数组中最小的那个元素，其次，将它和数组的第一个元素交换未知，
     * 再次，在剩下的元素中找到最小的元素，将它与数组的第二个元素交换未知。
     * 如此反复，直到将整个数组排序。
     *
     * 本程序按照升序排列
     * Created by fred on 2018/6/7.
     */
    public static void selectionSort(int[] arr){
        for(int i = 0; i < arr.length - 1; i++){
            int min = i;
            for(int j = i + 1; j < arr.length; j++){
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }
}
