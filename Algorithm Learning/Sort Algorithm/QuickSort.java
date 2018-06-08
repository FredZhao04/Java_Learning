/**
 *所谓快速排序：首先在数组中选择一个基准点并把基准点放于序列的开头（该基准点的选取可能影响快速排序的效率），
 * 然后分别从数组的两端扫描数组，设两个指示标志（lo指向起始位置，hi指向末尾)，首先从后半部分开始，
 * 如果发现有元素比该基准点的值小，就交换lo和hi位置的值，然后从前半部分开始扫秒，发现有元素大于基准点的值，
 * 就交换lo和hi位置的值，如此往复循环，直到lo>=hi,然后把基准点的值放到hi这个位置，一次排序就完成了。
 * 之后再采用递归的方式分别对前半部分和后半部分排序，当前半部分和后半部分均有序时该数组自然也就有序了。
 * Created by fred on 2018/6/8.
 */
public class QuickSort {
    public static void main(String[] args){
        int[] a = {1,9,3,12,7,8,3,4,65,22, 33};
        quickSort(a, 0, a.length - 1);
        for(int i:a){
            System.out.println(i);
        }
    }

    public static void quickSort(int[] a, int low, int high){
        if(low >= high){
            return;
        }
        //进行第一轮排序获取分割点
        int index = partition(a, low, high);
        //前半部分排序
        quickSort(a, low, index - 1);
        //后半部分排序
        quickSort(a, index + 1, high);
    }

    public static int partition(int[] a, int low, int high){
        int key = a[low];
        while(low < high){
            //先从后半部分查找小于key的数
            while(a[high] >= key && low < high){
                high--;
            }
            a[low] = a[high];
            //再从前半部分查找大于key的数
            while(a[low] <= key && low < high){
                low++;
            }
            a[high] = a[low];
        }
        a[low] = key;//最后把基准数存入
        return high;
    }
}
