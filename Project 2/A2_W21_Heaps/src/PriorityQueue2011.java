import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueue2011<E> extends AbstractQueue<E>{
	private static final int INITIAL_CAPACITY = 11; 
	private E[] storage; //position 0 is NOT used
	private int size;

	public PriorityQueue2011() {
		this.storage = (E[]) new Object [INITIAL_CAPACITY];
	}

	@Override
	public boolean offer(E e) {
		if (this.size == storage.length - 1)
			resize();
		this.size++;
		this.storage[size] = e;
		bubbleUp(size);
		return true;
	}

	@Override
	public boolean add(E e) {
		return offer(e);
	}

	@Override
	public E poll() {
		if (size == 0)
			return null;
		E result = (E) storage[1];
		storage[1] = (E) storage[size--];
		if (size != 1)
			siftDown(1);
		return result;
	}

	@Override
	public E remove() {
		if (this.size == 0)
			throw new NoSuchElementException();
		else
			return poll();
	}

	@Override
	public E peek() {
		if (this.size == 0)
			return null;
		else
			return this.storage[1];
	}

	@Override
	public E element() {
		if (size == 0)
			throw new NoSuchElementException();
		else
			return peek();
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	void bubbleUp(int index)
	{
		while (index > 1)
		{
			int parent = index / 2;
			if (((Comparable<? super E>) storage[parent]).compareTo((E) storage[index]) < 1)
			{
				break;
			}

			E temp = storage[index];
			storage[index] = storage[parent];
			storage[parent] = temp;

			index = parent;
		}
	}

	@SuppressWarnings("unchecked")
	private void siftDown(int index) {
		Comparable<? super E> p = (Comparable<? super E>) storage[index];
		int half = size /2; //alternative >>>1
		while (index <= half) //as long as there are children at index
		{
			int child = index * 2; 		//the smaller child
			Object c = storage[child]; 	//and its value
			int right = child + 1;
			if (right <= size && ((Comparable<? super E>) c).compareTo((E) storage[right]) > 0)
				c = storage[child = right];

			if (p.compareTo((E) c) <= 0){
				break;
			}

			storage[index] = (E) c;
			index = child;
		}
		storage[index] = (E) p; //at the end put the old root value in the last accessed position
	}

	
	@SuppressWarnings("unchecked")
	private void resize() {
		E[] new_data = (E[]) new Object[2 * storage.length];
		System.arraycopy(storage, 0, new_data, 0, storage.length);
		storage = new_data;
	}

	@Override
	public String toString() {
		return Arrays.toString(Arrays.copyOf(storage, size + 1));
	}

	public String toTree(){
		StringBuffer sb = new StringBuffer();
		int maxLevel = (int) Math.floor(Math.log(size) / Math.log(2)); //log2 of size
		int spacing = (int) (Math.round((Math.pow(2, maxLevel) + 1)/2*4));
		int gap = (spacing - 2) * 2;
		for (int row = 0, inCurrentLevel = 1, current = 1; 
				row <= maxLevel; 
				row++, inCurrentLevel*=2){
			for (int spaces = 0; spaces < spacing-4; spaces++) sb.append(' ');
			for (int j = 0; j < inCurrentLevel && current <= size; j++, current++){
				sb.append(String.format("%4s", storage[current]));//
				if (j < inCurrentLevel - 1)
					for (int spaces = 0; spaces < gap-4; spaces++) sb.append(' ');
			}	
			sb.append("\n");
			spacing = spacing/2 + 1;
			gap = (spacing - 2) * 2;
		}
		return sb.toString();
	}

}
