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
public class A3BSTree <E> implements Tree<E>{
	
	Node<E> root;
	private int size;
	
	/**
	 * Constructor for initialising BST trees to empty trees with no elements
	 */
	public A3BSTree(){
		root = null; 
		size = 0;
	}

	
	/**
	 * This method uses a recursive method to add specified element to the BST
	 * 
	 * @param element is the the element that's supposed to be added
	 * @return boolean true if the element is legal and added
	 * @throws NullPointerException if the element meant to be added is null
	 * You can't have null elements in a tree
	 */
	@Override
	public boolean add(E element) {
		if(element == null) throw new NullPointerException();
		root = addRecursive(root, element);
		return true;
	}

	
	/**
	 * Recursive method to insert elements to BST 
	 * 
	 * @param current its the node that is passed in a recursive call starting with the root
	 * @param element to be added
	 * @return a node that is either new or the current node
	 */
	@SuppressWarnings("unchecked")
	private Node<E> addRecursive(Node<E> current, E element) {
		if (current == null) {
			size++; //If there is empty space, we add the element there and increase size by 1
	        return new Node<E>(element, null, null);
	    }

	    if (((Comparable<? super E>) element).compareTo((E) current.item) < 0) {
	    	Node<E> leftChild = addRecursive(current.left, element);
	        current.left = leftChild; 
	        leftChild.parent = current; //update parent node
	    } else if (((Comparable<? super E>) element).compareTo((E) current.item) > 0) {
	    	Node<E> rightChild = addRecursive(current.right, element);
	        current.right = rightChild;
	        rightChild.parent = current; //update parent node
	    } else {
	        // value already exists
	        return current;
	    }

	    return current;
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
		for(E item : c){
			add(item);	
		}
		return true;
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
			size--; //size is decremented by one since one element is removed
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
		 
        if (node == null) // If the tree is empty 
        	throw new NullPointerException();
 
        // Traverse down the tree using recursion if tree is not empty
        if (((Comparable<? super E>) element).compareTo((E) node.item) < 0) {
            node.left = removeRecursive(node.left, element); 
            //Go left if element is smaller than current node
        }else if (((Comparable<? super E>) element).compareTo((E) node.item) > 0) {
            node.right = removeRecursive(node.right, element); 
            //Go right if element is bigger than current node
        }
        // Last case where it is the node to be deleted
        else {
            if (node.left == null) {
                return node.right; // If left side is a dead end we go right
            }else if (node.right == null) {
                return node.left; // If right side is a dead end we go left
            }
            node.item = minValue(node.right); // Next element in inorder traversal
            node.right = removeRecursive(node.right, node.item); // Delete the next inorder
        }
        return node;
        
	}

	
	/**
	 * This method traverses the tree to find and return the smallest (leftmost) element
	 * 
	 * @param root of tree
	 * @return smallest(leftmost) node value of the tree
	 */
	private E minValue(Node<E> root) {
		// TODO Auto-generated method stub
		E minv = root.item;
        while (root.left != null)
        {
            minv = root.left.item;
            root = root.left;
        }
        return minv;
	}

	
	/**
	 *This method checks if the tree contains element 0
	 *
	 * @param Object o, element of which we check presence in tree
	 * @return true if the element exists
	 * @throws NullPointerException if the element is null
	 * @throws ClassCastException if the element is not an object type
	 */
	@Override
	public boolean contains(Object o) {
		if(o == null) throw new NullPointerException();
		if(!(o instanceof Object)) throw new ClassCastException();
		return containsRecursive(root, o);
	}

	
	/**
	 * Checks presence of element specified in the tree
	 * 
	 * @param current node
	 * @param value is the element we're looking for
	 * @return true if element present, false if not
	 */
	@SuppressWarnings("unchecked")
	private boolean containsRecursive(Node<E> current, Object value) {
		if (current == null) {
	        return false;
	    } 
	    if (((Comparable<? super E>) value).compareTo((E) current.item) == 0) { // val == curr val
	        return true;
	    } 
	    return ((Comparable<? super E>) value).compareTo((E) current.item) < 0 // val < curr val
	      ? containsRecursive(current.left, value)
	      : containsRecursive(current.right, value);
	}

	
	/**
	 * Uses class A3BSTIterator to give an iterator with methods next() and hasNext()
	 * @return Iterator of type generic that iterates through BST inorder
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		//return new A3BSTIterator(root, size);
		return new A3BSTIterator<E>(root);
	}

	
	/**
	 * Returns the height of the tree (root included)
	 * @return int height
	 */
	@Override
	public int height() {
		return maximumDepth(root) + 1;
	}
	
	
	/**
	 * Depth First traversal to find the depth (height) of the tree whose root is passed to it
	 * @param node current node or root
	 * @return int height
	 */
	private int maximumDepth(Node<E> node)
    {
        if (node == null)
            return -1;
        else
        {
            // process height of left and right subtree
            int lDepth = maximumDepth(node.left);
            int rDepth = maximumDepth(node.right);
  
            //return the bigger height since that's the max height/depth
            if (lDepth > rDepth)
                return (lDepth + 1);
             else
                return (rDepth + 1);
        }
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


/**
 * Node class for defining nodes of the tree and for traversals
 * @author Daivakshi
 * @param <E>
 */
class Node<E>{
	E item;
	Node<E> left;
	Node<E> right;
	Node<E> parent;
	int height;
	
	/**
	 * Constructor to initialize/define a new node
	 * @param element: value
	 * @param left: left child
	 * @param right: right child
	 */
	Node(E element, Node<E> left, Node<E> right){
		this.item = element;
		this.left = left;
		this.right = right;
	}
	
}


/**
 * Iterator class for this BST implementation
 * @author Daivakshi
 * @param <E>
 */
class A3BSTIterator<E> implements Iterator<E>{
	
	Node<E> next; //for tracking next element
	
	/**
	 * Constructor to define an iterator
	 * @param root of tree
	 */
	public A3BSTIterator(Node<E> root) {
        next = root;
        if(next == null)
            return;

        while (next.left != null)
           next = next.left;
    }

	
	/**
	 * Returns true if the iteration has more elements
	 * @return boolean
	 */
    public boolean hasNext(){
        return next != null;
    }

    
    /**
     * Returns the next element in the iteration according to inorder traversal
     * @return element of generic type E
     */
	public E next(){
        if(!hasNext()) throw new NullPointerException();
        Node<E> r = next;

        if(next.right != null) {
            next = next.right;
            while (next.left != null)
                next = next.left;
            return (E) r.item;
        }

        while(true) {
            if(next.parent == null) {
                next = null;
                return (E) r.item;
            }
            if(next.parent.left == next) {
                next = next.parent;
               return (E) r.item;
            }
            next = next.parent;
        }
     }
	
}
