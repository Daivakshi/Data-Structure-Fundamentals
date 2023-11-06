import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PQ2011Test {
	private static Random rng = new Random();

	@Test (timeout = 2000)
	public void pqSortFieldsMethodsTester_5pts() {
		Field[] fields = PQSort.class.getDeclaredFields();
		for (Field f: fields){
			assertTrue("PQSort contains a public field", 
					!Modifier.isPublic(f.getModifiers()));
		}

		Method[] methods = PQSort.class.getDeclaredMethods();
		int publicMethodCounter = 0;
		for (Method m: methods){
			if(Modifier.isPublic(m.getModifiers()))
				publicMethodCounter++;
		}
		assertTrue("PQSort contains a wrong number of public methods", publicMethodCounter == 2);
	}

	@Test (timeout = 500)
	public void pqAPIExceptionsNulls_10pts() {
		PriorityQueue2011<Integer> pq = new PriorityQueue2011<>();
		assertTrue("poll(): null expected", pq.poll() == null);
		assertTrue("peek(): null expected", pq.peek() == null);

		try{
			pq.remove();
			fail("remove(): Exception was to be thrown");
		}catch (NoSuchElementException e){
			//OK
		}catch (Exception ex) {
			fail("remove(): Wrong type of exception");
		}

		try{
			pq.element();
			fail("element(): Exception was to be thrown");
		}catch (NoSuchElementException e){
			//OK
		}catch (Exception ex) {
			fail("element(): Wrong type of exception");
		}

	}

	@Test (timeout = 500)
	public void pqAPIAddRemove_25pts() {
		Queue<Integer> pq = new PriorityQueue2011<>();
		pq.add(2); pq.offer(2); pq.offer(2); pq.add(2);
		assertTrue("add(2 2 2 2): peek 2 expected", pq.peek() == 2);
		pq.add(1);
		assertTrue("add(1): element 1 expected", pq.element() == 1);
		pq.offer(1);
		assertTrue("add(_ 1); poll: 1 expected", pq.poll() == 1);
		assertTrue("remove: 1 expected", pq.remove() == 1);
		assertTrue("another remove: 2 expected", pq.remove() == 2);
	}

	
	@Test (timeout = 500)
	public void pqtoStringTester1_5_pts() {
		Queue<Integer> pq = new PriorityQueue2011<>();
		assertTrue(pq.toString().equals("[]") || pq.toString().equals("[null]"));
	}

	@Test (timeout = 500)
	public void pqtoStringTester2_5_pts() {
		Queue<String> pq = new PriorityQueue2011<>();
		pq.add("2"); pq.add("1"); pq.add("4"); pq.add("3");
		assertTrue(pq.toString().equals("[1, 2, 4, 3]") || pq.toString().equals("[null, 1, 2, 4, 3]"));
	}

	@Test (timeout = 500)
	public void pqtoTreeTester2_15_pts() {
		PriorityQueue2011<Integer> pq = new PriorityQueue2011<>();
		for (int i = 21; i >= 1; i--) pq.offer(i);
		System.out.println("Expected:");
		System.out.println("                                 1");
		System.out.println("                 2                               8");
		System.out.println("         5               3              11               9");
		System.out.println("    12       6       4      14      20      16      17      10");
		System.out.println("  21  15  18   7  19  13");
		System.out.println("\nActual:");
		System.out.println(pq.toTree());
	}

	@Test (timeout = 2000)
	public void sorting_HeapsortTest_10pts() {
		int n = 87;
		for (int trial = 0; trial < 100; trial++) {
			Integer[] array = createRandomArray(n);
			Integer[] sortedArray = createRandomSortedArray(n);
			PQSort.heapSort(array);
			assertTrue("Not sorted!", isSorted(array));
			assertTrue("Not sorted!", Arrays.equals(array, sortedArray));
		}
	}

	@Test (timeout = 20000)
	public void sorting_HeapsortTest2011_25pts() {
		int n = 89;
		long timeMS, timeHS;
		
		for (int trial = 0; trial < 100; trial++) {
			Integer[] array = createRandomArray(n);
			Integer[] sortedArray = createRandomSortedArray(n);
			PQSort.heapSort2011(array);
			assertTrue("Not sorted!", isSorted(array));
			assertTrue("Not sorted!", Arrays.equals(array, sortedArray));
		}
		
		Integer[] array = createRandomArray(500000);
		long begin = System.currentTimeMillis();
		Arrays.sort(array);
		timeMS = System.currentTimeMillis() - begin;
		System.out.print("Arrays.Msort\t" + timeMS + " ms\n");


		array = createRandomArray(500000);
		begin = System.currentTimeMillis();
		PQSort.heapSort2011(array);
		timeHS = System.currentTimeMillis() - begin;
		System.out.print("heapsort2011\t" + timeHS + " ms\n");
		
		assertTrue("> 50x slower than mergesort", timeHS < 50 * timeMS);

	}

	private static Integer [] createRandomArray (int size){
		rng.setSeed(8); //each time, the random sequence will be the same
		Integer [] array = new Integer [size];

		for (int i = 0; i < size; i++) array[i] = rng.nextInt();

		return array;
	}

	private static Integer [] createRandomArray (int size, int max){
		rng.setSeed(8); //each time, the random sequence will be the same
		Integer [] array = new Integer [size];

		for (int i = 0; i < size; i++) array[i] = rng.nextInt(max);

		return array;
	}

	private static Integer [] createRandomSortedArray (int size){
		rng.setSeed(8); //each time, the random sequence will be the same
		Integer [] array = new Integer [size];

		for (int i = 0; i < size; i++) array[i] = rng.nextInt();

		Arrays.sort(array);
		return array;
	}

	private static boolean isSorted(Integer [] array){
		if (array.length <= 1) return true;
		for (int i = 1; i < array.length; i++){
			if (array[i] < array [i-1]) return false;
		}
		return true;
	}

}