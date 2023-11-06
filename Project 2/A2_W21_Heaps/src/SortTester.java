import java.util.Arrays;

public class SortTester {

	public static void main(String[] args) {
		test(10);
		test(100);
		test(1000);
		test(10000);
		test(100000);
		test(1000000);
	}

	/**
	 * This method tests the efficiency of heapsort by comparing it to quicksort
	 * 
	 * @param N is the number of elements
	 */
	public static void test(int N) {
		int[] arraysQuickSort = new int[N];
		Integer[] arraysMergeSort = new Integer[N];
		Integer[] arrayHeapSort = new Integer[N];
		Integer[] arrayHeapSort2011 = new Integer[N];

		System.out.println("n = " + N + ":");

		for (int i = 0; i < N; i++) {
			int value = (int) (Math.random() * N * 10 + 0);
			arraysQuickSort[i] = value;
			arraysMergeSort[i] = value;
			arrayHeapSort[i] = value;
			arrayHeapSort2011[i] = value;
		}

		long begin = System.currentTimeMillis();
		Arrays.sort(arraysQuickSort);
		System.out.print("Arrays.Qsort\t" + (System.currentTimeMillis() - begin) + " ms\n");
		
		begin = System.currentTimeMillis();
		Arrays.sort(arraysMergeSort);
		System.out.print("Arrays.Msort\t" + (System.currentTimeMillis() - begin) + " ms\n");

		begin = System.currentTimeMillis();
		PQSort.heapSort(arrayHeapSort);
		System.out.print("heapsort\t" + (System.currentTimeMillis() - begin) + " ms\n");

		begin = System.currentTimeMillis();
		PQSort.heapSort2011(arrayHeapSort2011);
		System.out.print("heapsort2011\t" + (System.currentTimeMillis() - begin) + " ms\n");
	}
}