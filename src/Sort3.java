import java.util.*;

public class Sort3 {

    public static int[] quick_sort (int[] array, int p, int r) {
        if (p < r) {
            int q = partition(array, p, r);
            quick_sort(array, p, q - 1);
            quick_sort(array, q + 1, r);
        }
        return array;
    }

    public static int partition (int[] array, int p, int r) {
        int x = array[r]; //last index is the pivot value
        int i = p - 1; // index that has been checked, start at -1 because we have not checked anything yet.

        //move elements that are smaller than the partition value to the left or equal to i
        for (int j = p; j < r; j++) {
            if (array[j] <= x) {
                i++;

                //swapping array[j] and array[i] values
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        //swap the partitioned index with the indices to place in it's correct position.
        int temp = array[i + 1];
        array[i + 1] = array[r];
        array[r] = temp;

        //return new partitioned index
        return i + 1;
    }

    public static int[] counting_sort (int[] array, int k) {

        /*
        In Java arrays elements values by default are 0 so no need for
        the first loop to initialize elements to zero. If using other
        languages then must have initialize all values to zero
         */
        int [] b = new int [array.length];
        int [] c = new int [k + 1];


        /*
        populate auxiliary array with frequencies of elements in
        a[] corresponding to the index of c[]
         */
        for (int j = 0; j < array.length; j++) {
            c[array[j]] = c[array[j]] + 1;
        }

        /*
        contains the number of values in array that is less than
        j
         */
        for (int i = 1; i <= k; i++) {
            c[i] = c[i] + c[i - 1];
        }
        /*
        Places the elements in correct order from starting from the right to the left.
         */

        for (int j = array.length - 1; j >= 0 ; j--) {
            b[c[array[j]] - 1] = array[j];
            c[array[j]] = c[array[j]] - 1;
        }
        return b;
    }

    public static void heap_sort(int array[]) {

        //creates a maxHeap from a given list of numbers
        buildMaxHeap(array);

        /*
             swap array[0] (largest element in the array) with
             array[size) (last element in heap) then decrease size
             by 1 in the for loop to "remove" the max number from heap
         */
        for (int i=array.length -1; i>=0; i--) {

            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            /*
                call heapify again to ensure heap property is maintained
                once the largest number is swapped
             */
            heapify(array, i, 0);
        }
    }

    public static void buildMaxHeap (int array[]) {
        int size = array.length;
        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(array, size, i);
    }
    public static int getLeftChildIndex (int i) {return 2*i + 1;}
    public static int getRightChildIndex (int i) {return 2*i + 2;}
    public static void heapify(int array[], int heapSize, int i) {

        int largest = i; //assumes the parent is larger than the children
        int leftChildIndex = getLeftChildIndex(i);
        int rightChildIndex  = getRightChildIndex(i);

        // If left child is larger than parent
        if (leftChildIndex < heapSize && array[leftChildIndex] > array[largest])
            largest = leftChildIndex;

        // If right child is larger than largest so far
        if (rightChildIndex  < heapSize && array[rightChildIndex ] > array[largest])
            largest = rightChildIndex ;

        // If largest is not parent meaning that it is now either children swap their respective positions
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            // Recursive call to  heapify the sub-tree to maintain heap property
            heapify(array, heapSize, largest);
        }
    }


    public static int[] generate_random_array (int n, int k) {
        List<Integer> list;
        int[] array;
        Random rnd;

        rnd = new Random(System.currentTimeMillis());

        list = new ArrayList<Integer> ();
        for (int i = 1; i <= n; i++)
            list.add(new Integer(rnd.nextInt(k+1)));

        Collections.shuffle(list, rnd);

        array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = list.get(i).intValue();

        return array;
    }

    public static int[] generate_random_array (int n) {
        List<Integer> list;
        int[] array;

        list = new ArrayList<Integer> ();
        for (int i = 1; i <= n; i++)
            list.add(new Integer(i));

        Collections.shuffle(list, new Random(System.currentTimeMillis()));

        array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = list.get(i).intValue();

        return array;
    }

    /*
     * Input: an integer array
     * Output: true if the array is acsendingly sorted, otherwise return false
     */
    public static boolean check_sorted (int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i-1] > array[i])
                return false;
        }
        return true;
    }

    public static void print_array (int[] array) {
        for (int i = 0; i < array.length; i++)
            System.out.print(array[i] + ", ");
        System.out.println();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int k = 10000;

        System.out.println("Quick sort starts ------------------");
        for (int n = 100000; n <= 1000000; n=n+100000) {
            int[] array = Sort3.generate_random_array(n);
            long t1 = System.currentTimeMillis();
            array = Sort3.quick_sort(array, 0, n-1);
            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            boolean flag = Sort3.check_sorted(array);
            System.out.println(n + "," + t + "," + flag);
        }
        System.out.println("Quick sort ends ------------------");

        System.out.println("Counting sort starts ------------------");
        for (int n = 100000; n <= 1000000; n=n+100000) {
            int[] array = Sort3.generate_random_array(n, k);
            long t1 = System.currentTimeMillis();
            array = Sort3.counting_sort(array, k);
            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            boolean flag = Sort3.check_sorted(array);
            System.out.println(n + "," + t + "," + flag);
        }
        System.out.println("Counting sort ends ------------------");

        System.out.println("Heap sort starts ------------------");
        for (int n = 100000; n <= 1000000; n=n+100000) {
            int[] array = Sort3.generate_random_array(n);
            long t1 = System.currentTimeMillis();
            Sort3.heap_sort(array);
            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            boolean flag = Sort3.check_sorted(array);
            System.out.println(n + "," + t + "," + flag);
        }
        System.out.println("Heap sort ends ------------------");
    }


}
