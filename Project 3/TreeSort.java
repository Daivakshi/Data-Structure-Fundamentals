import java.util.Iterator;

public class TreeSort{
	/** Sorts an array using TreeSort with a balanced BST implementation 
	 * @param a an array to sort
	 */
	public static <E extends Comparable <? super E>> void sort( E[] a){
		Tree <E> tree = new A3AVLTree<>();
		sort(tree, a);
	}
	
	/**
	 * Sorts an array using TreeSort with a specified BST
	 * @param tree tree to use
	 * @param a an array to sort
	 */
	public static <E extends Comparable <? super E>> void sort(Tree <E> tree, E[] a){
		for(int i = 0; i < a.length; i++)
			tree.add(a[i]);
        int i = 0;
        Iterator<E> treeIterator = tree.iterator();
        while(treeIterator.hasNext()) {
	         E element = treeIterator.next();
	         a[i] = element;
	         i++;
	      }
	}
	
}
