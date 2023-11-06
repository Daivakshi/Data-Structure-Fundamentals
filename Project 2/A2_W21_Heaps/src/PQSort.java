import java.util.PriorityQueue;

public class PQSort {
	public static <E extends Comparable <? super E>> void heapSort(E[] a){
		PriorityQueue<E> pq = new PriorityQueue<>();
		for (E e:a) pq.offer(e);
		for (int i = 0; i < a.length; i++) a[i] = pq.remove();
	}

	public static <E extends Comparable <? super E>> void heapSort2011(E[] a){
		PriorityQueue2011<E> pq = new PriorityQueue2011<>();
		for (E e:a) pq.offer(e);
		for (int i = 0; i < a.length; i++) a[i] = pq.remove();
	}
}
