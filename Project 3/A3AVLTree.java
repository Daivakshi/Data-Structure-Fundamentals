import java.util.Collection;
import java.util.Iterator; 

/**
 * 
 * @author Daivakshi Vaidya (217761016)
 *
 * @param <E> Generic value stored in nodes of the tree
 * 
 * The following sites were used as reference (Most provided by Professor Andriy):
 * 1. https://docs.oracle.com/javase/7/docs/api/java/util/Set.html
 * 2. https://en.wikipedia.org/wiki/Binary_search_tree
 * 3. https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html#hasNext--
 * 4. https://www.cs.usfca.edu/~galles/visualization/BST.html
 * 5. https://www.cs.usfca.edu/~galles/visualization/AVLtree.html
 * 
 */
public class A3AVLTree<E> implements Tree<E> { // consider extending A3BSTree

	private Node<E> root;
	private int size;

	/**
	 * Constructor for initialising AVL trees to empty trees with no elements
	 */
	public A3AVLTree() {
		root = null;
		size = 0;
	}

	
	/**
	 * This method uses a recursive method to add specified element to the AVL
	 * 
	 * @param element is the the element that's supposed to be added
	 * @return boolean true if the element is legal and added
	 * @throws NullPointerException if the element meant to be added is null
	 * You can't have null elements in a tree
	 */
	@Override
	public boolean add(E e) {
		if(e == null) throw new NullPointerException();
		root = addRecursive(root, e);
		return true;
	}


	/**
	 * Recursive method to insert elements to AVL 
	 * 
	 * @param node: its the node that is passed in a recursive call starting with the root
	 * @param element to be added
	 * @return a node that is either new or the current node
	 */
	@SuppressWarnings("unchecked")
	private Node<E> addRecursive(Node<E> node, E element) {
		// Classic BST insertion
		if (node == null) {
			size++;
			return (new Node<E>(element, null, null));
		}

		if (((Comparable<? super E>) element).compareTo((E) node.item) < 0) { // key < root.key
			node.left = addRecursive(node.left, element);
		}else if (((Comparable<? super E>) element).compareTo((E) node.item) > 0) { // key > root.key
			node.right = addRecursive(node.right, element);
		}else // Duplicate keys not allowed
			return node;

		//Update height
		node.height = 1 + maxInt(heightNode(node.left), heightNode(node.right));

		int balance = getBalanceFactor(node); //Balance factor to verify if node is unbalanced or not

		// Cases for unbalanced node situation:-
		
		//Case 1: apply single right rotation at z
		if (balance > 1 && ((Comparable<? super E>) element).compareTo((E) (node.left).item) < 0)
			return rightRotate(node);

		//Case 2: apply single left rotation at z
		if (balance < -1 && ((Comparable<? super E>) element).compareTo((E) (node.right).item) > 0) 
			return leftRotate(node);

		// Case 3: apply double rotation left rotation at y followed by right rotation at z
		if (balance > 1 && ((Comparable<? super E>) element).compareTo((E) (node.left).item) > 0) { 
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		//  Case 4: apply double rotation right rotation at y followed by left rotation at z
		if (balance < -1 && ((Comparable<? super E>) element).compareTo((E) (node.right).item) < 0) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		return node;
	}
	
	
	/**
	 * This method adds all elements from a collection one by one into a tree using 
	 * the method add --> add recursive
	 * 
	 * @param collection with elements to be added
	 * @return true once all elements of the collection have been legally added
	 * @throws NullPointerException if the collection is null/empty
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if(c == null) throw new NullPointerException();
		for (E item : c) {
			add(item);
		}
		return true;
	}

	
	/**
	 * This method computes the balance factor and returns it
	 * @param node
	 * @return int balance factor
	 */
	private int getBalanceFactor(Node<E> node) {
		if (node == null)
			return 0;

		return heightNode(node.left) - heightNode(node.right);
	}

	
	/**
	 * Returns the bigger inetger out of the two passed as parameter
	 * @param a int1
	 * @param b int1
	 * @return a if a>b and b if b>a
	 */
	private int maxInt(int a, int b) {
		return (a > b) ? a : b;
	}

	
	/**
	 * This basically just returns the height of the node specified in the tree but 
	 * also checks if the node is null or not to get precise number and not count all null ones too
	 * @param node: node whose height needs to be returned
	 * @return Height of the node
	 */
	private int heightNode(Node<E> node) {
		if (node == null)
			return 0;

		return node.height;
	}

	
	/**
	 * Rotates right the tree where param y is the root
	 * @param y Node<E>
	 * @return the rotated (Balanced/helps balance) node x with it's rotated subtrees
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Node rightRotate(Node y) {

		Node x = y.left;
		Node temp = x.right;

		// Perform rotation
		x.right = y;
		y.left = temp;

		// Update heights
		y.height = maxInt(heightNode(y.left), heightNode(y.right)) + 1;
		x.height = maxInt(heightNode(x.left), heightNode(x.right)) + 1;

		// Return new root with subtrees
		return x;
	}

	
	/**
	 * Rotates left the tree where param x is the root
	 * @param x Node<E>
	 * @return the rotated (Balanced/helps balance) node y with it's rotated subtrees
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Node leftRotate(Node x) {

		Node y = x.right;
		Node temp = y.left;

		// Perform rotation
		y.left = x;
		x.right = temp;

		// Update heights
		x.height = maxInt(heightNode(x.left), heightNode(x.right)) + 1;
		y.height = maxInt(heightNode(y.left), heightNode(y.right)) + 1;

		// Return new root
		return y;
	}

	
	/**
	 * This method removes specified element using a recursive method
	 * 
	 * @param Object o is the element to be removed
	 * @return true if element is present in tree and removed
	 * @throws NullPointerException if the element specified is not present in the tree and
	 * 			if the element is a null element and trees don't have null elements
	 */
	@Override
	public boolean remove(Object o) {
		if(o == null) throw new NullPointerException();
		if(contains(o)) {
			size--;
			root = removeRecursive(root, o);
			return true;
		}
		else throw new NullPointerException("This element does not exist");
	}

	
	/**
	 * Removing element using recursion 
	 * 
	 * @param node the current node during traversal/ root in the beginning
	 * @param element to be removed
	 * @return current processed node
	 */
	@SuppressWarnings("unchecked")
	private Node<E> removeRecursive(Node<E> node, Object element) {
		//First we perform classic BST deletion 
        if (node == null)
        	throw new NullPointerException(); // If tree is null/empty
 
        // Traverse down the tree using recursion
        if (((Comparable<? super E>) element).compareTo((E) node.item) < 0)
            node.left = removeRecursive(node.left, element);
        	//Go left if element is smaller than current node
        else if (((Comparable<? super E>) element).compareTo((E) node.item) > 0)
            node.right = removeRecursive(node.right, element);
        	//Go right if element is bigger than current node
  
        // Last case where it is the node to be deleted
        else
        { 
  
            if ((node.left == null) || (node.right == null)) 
            { 
                Node<E> temp = null; 
                if (temp == node.left) 
                    temp = node.right; 
                else
                    temp = node.left; 
  
                // If no child/subtree
                if (temp == null) 
                { 
                    temp = node; 
                    node = null; 
                } 
                else
                    node = temp;
            } 
            else
            { 
                Node<E> temp = minValueNode(node.right); //smallest in the right subtree
                node.item = temp.item; 
                node.right = removeRecursive(node.right, temp.item); // Delete the next inorder 
            } 
        } 
  
        // If the tree had only one node then return 
        if (node == null) 
            return node; 
  
        // Update height 
        node.height = maxInt(heightNode(node.left), heightNode(node.right)) + 1; 
        
        int balance = getBalanceFactor(node); //Balance factor to verify if node is unbalanced or not
  
        // Cases for unbalanced node situation (SAME AS ADD RECURSIVE ABOVE):- 
        
        // Case 1 
        if (balance > 1 && getBalanceFactor(node.left) >= 0) 
            return rightRotate(node); 
  
        // Case 3
        if (balance > 1 && getBalanceFactor(node.left) < 0) 
        { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
        } 
  
        // Case 2
        if (balance < -1 && getBalanceFactor(node.right) <= 0) 
            return leftRotate(node); 
  
        // Case 4
        if (balance < -1 && getBalanceFactor(node.right) > 0) 
        { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 
  
        return node; 
	}

	
	/**
	 * This method traverses the tree to find and return the smallest (leftmost) element
	 * 
	 * @param root of tree
	 * @return smallest(leftmost) node value of the tree
	 */
	private Node<E> minValueNode(Node<E> node) {
		Node<E> current = node; 
        while (current.left != null) 
        current = current.left; 
        return current;
	}

	
	/**
	 *This method checks if the tree contains element 0
	 *
	 * @param Object o, element of which we check presence in tree
	 * @return true if the element exists
	 * @throws NullPointerException if the element is null
	 * @throws ClassCastException if the element is not an object type
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		if(o == null) throw new NullPointerException();
		if(!(o instanceof Object)) throw new ClassCastException();
		Node<E> current = root;
		while (current != null) {
			if (((Comparable<? super E>) o).compareTo((E) current.item) == 0) {
				return true;
			}
			current = ((Comparable<? super E>) o).compareTo((E) current.item) > 0 ? current.right : current.left;
		}
		return false;
	}

	
	/**
	 * Uses class A3BSTIterator to give an iterator with methods next() and hasNext()
	 * @return Iterator of type generic that iterates through AVL inorder
	 */
	@Override
	public Iterator<E> iterator() {
		return new A3BSTIterator<E>(root);
	}

	
	/**
	 * Returns the height of the tree (root included)
	 * @return int height
	 */
	@Override
	public int height() {
		return heightNode(root) + 1;
	}

	
	/**
	 * Size of tree (number of elements)
	 * @param size of tree
	 */
	@Override
	public int size() {
		return size;
	}

}

/*class A3AVLIterator<E> implements Iterator<E>{

        Node<E> next;
        Stack<Node<E>> stack;

        public A3AVLIterator(Node<E> root) {
            stack = new Stack<Node<E>>();
            next = root;

            while (next != null) {
                stack.push(next);
                next = next.left;
            }
        }


	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return !stack.empty();
	}

	@Override
	public E next() {
		// TODO Auto-generated method stub
		Node<E> node = stack.pop();
        E v = node.item;
        if (node.right != null) {
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return v;
	}
}*/
