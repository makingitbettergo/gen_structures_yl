package structures.tree;

/**
 * As SplayTree reshape itself when it comes to search. the frequent elements
 * are likely to close to the root.
 * 
 * @author go-mk01
 *
 * @param <T>
 */
public class SplayTree<T extends Comparable<? super T>> {

	private Node<T> root;

	public SplayTree() {
		root = null;
	}

	/**
	 * Rotates a given node with its left child.
	 *
	 * @param t
	 *            The parent node to rotate
	 * @return The new parent node
	 */
	private Node<T> leftChildRotate(Node<T> t) {
		Node<T> newT = t.left;
		t.left = newT.right;
		newT.right = t;
		return newT;
	}

	/**
	 * Rotates a given node with its right child.
	 *
	 * @param t
	 *            The parent node to rotate
	 * @return The new parent node
	 */
	private Node<T> rightChildRotate(Node<T> t) {
		Node<T> newT = t.right;
		t.right = newT.left;
		newT.left = t;
		return newT;
	}

	/**
	 * Performs a splay operation to bring the node matching the given key (or
	 * the last visited node on the access path) up to the root.
	 *
	 * @param element
	 *            The element of the node we want to splay
	 */
	private void splay(T element) {
		Node<T> t = root;
		Node<T> pops, gramps, gg;
		pops = gramps = gg = null;
		boolean checkGG = true;

		while (true) {
			if (t == null || element.compareTo(t.value) == 0)
				break;
			else if (t.left != null && element.compareTo(t.value) < 0) {
				if (element.compareTo(t.left.value) == 0) {
					t = leftChildRotate(t);
				} else if (t.left.left != null
						&& element.compareTo(t.left.left.value) == 0) {
					gramps = t;
					pops = t.left;
					t = leftChildRotate(gramps);
					t = leftChildRotate(pops);
					checkGG = true;
				} else if (t.left.right != null
						&& element.compareTo(t.left.right.value) == 0) {
					gramps = t;
					pops = t.left;
					gramps.left = rightChildRotate(pops);
					t = leftChildRotate(gramps);
					checkGG = true;
				} else if (element.compareTo(t.value) < 0) {
					gg = t;
					t = t.left;
				}
			} else if (t.right != null && element.compareTo(t.value) > 0) {
				if (element.compareTo(t.right.value) == 0) {
					t = rightChildRotate(t);
				} else if (t.right.right != null
						&& element.compareTo(t.right.right.value) == 0) {
					gramps = t;
					pops = t.right;
					t = rightChildRotate(gramps);
					t = rightChildRotate(pops);
					checkGG = true;
				} else if (t.right.left != null
						&& element.compareTo(t.right.left.value) == 0) {
					gramps = t;
					pops = t.right;
					gramps.right = leftChildRotate(pops);
					t = rightChildRotate(gramps);
					checkGG = true;
				} else if (element.compareTo(t.value) > 0) {
					gg = t;
					t = t.right;
				}
			}
			// If target not found
			else if ((t.left == null && element.compareTo(t.value) < 0)
					|| (t.right == null && element.compareTo(t.value) > 0)) {
				element = t.value;
				t = root;
				gg = null;
			}

			// Link t and its great-grandparent after a zig-zig or zig-zag
			// operation.
			// A new round of splaying is then begun by setting root to t.
			if (checkGG && gg != null) {
				int comp = t.value.compareTo(gg.value);
				if (comp < 0)
					gg.left = t;
				else if (comp > 0)
					gg.right = t;
				t = root;
				gg = null;
				checkGG = false;
			}
		}
		// The root is now that of the final tree
		root = t;
	}

	/**
	 * Checks if this tree is empty.
	 *
	 * @return true if nothing in tree, false otherwise
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the number of nodes in the tree.
	 *
	 * @return the number of nodes currently in the tree
	 */
	public int size() {
		return isEmpty() ? 0 : size(root);
	}

	/**
	 * Recursive helper, returns the number of nodes in a given tree
	 *
	 * @param t
	 *            The tree to count through
	 * @return The number of nodes currently in the tree
	 */
	private int size(Node<T> t) {
		int count = 0;
		if (t != null) {
			count++;
			count += size(t.left);
			count += size(t.right);
		}
		return count;
	}

	/**
	 * Adds a node with given element to the tree and then splays it to the
	 * root.
	 *
	 * @param T
	 *            The new node's element
	 */
	public void insert(T item) {
		if (item != null) {
			root = insert(item, root);
			splay(item);
		}
	}

	/**
	 * Recursive helper that performs the node insertion into a given tree.
	 *
	 * @param T
	 *            The new node's element
	 * @param t
	 *            The root of the tree we want to insert into
	 * @return The resulting tree's root node
	 */
	private Node<T> insert(T item, Node<T> t) {
		if (t == null)
			return new Node<T>(item);
		else {
			int position = item.compareTo(t.value);
			if (position < 0)
				t.left = insert(item, t.left);
			else if (position > 0)
				t.right = insert(item, t.right);
			return t;
		}
	}

	/**
	 * Removes the node matching the given key from the tree.
	 *
	 * @param key
	 *            The target node's key
	 * @return true if the target was removed, false otherwise
	 */
	public boolean remove(T item) {
		// Splay target then delete by replacing the new root with
		// its predecessor or the right tree root.
		if (!isEmpty() && item != null) {
			splay(item);
			if (root != null && root.value.compareTo(item) == 0) {
				if (root.left != null) {
					Node<T> temp = root.right;
					root = root.left;
					splay(item);
					root.right = temp;
				} else
					root = root.right;
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds the item with the minimum value in the tree.
	 *
	 * A splay operation is performed on the minimum value node to bring it to
	 * the root.
	 *
	 * @return the item with a minimum value
	 */
	public T findMin() {
		Node<T> t = root;
		if (t != null) {
			while (t.left != null)
				t = t.left;
			splay(t.value);
			return root.value;
		}
		return null;
	}

	/**
	 * Finds the item with the maximum value in the tree.
	 *
	 * A splay operation is performed on the max value to bring it to the root.
	 *
	 * @return the item with a maximum value.
	 */
	public T findMax() {
		Node<T> t = root;
		if (t != null) {
			while (t.right != null)
				t = t.right;
			splay(t.value);
			return root.value;
		}
		return null;
	}

	/**
	 * Checks if the item is in the tree.
	 *
	 * @param item
	 *            the item to find
	 * @return true if item is present in tree, false otherwise.
	 */
	public boolean contains(T item) {
		if (isEmpty())
			return false;
		splay(item);
		return root.value.compareTo(item) == 0;
	}

	/**
	 * Return the root value of the tree. This is not a normal operation but is
	 * provided for test purposes. Just return the value of the node that is
	 * currently at the root. DO NOT DO A SPLAY OPERATION.
	 *
	 * @return the root value
	 */
	public T getRootValue() {
		return (root == null) ? null : root.value;
	}

	/**
	 * return the value of the leftmost child of the tree. if there is no
	 * leftmost child then return null. DO NOT DO A SPLAY FOR THIS OPERATION
	 *
	 * @return the value of leftmost child or null if not present.
	 */
	public T getLeftmostChild() {
		Node<T> current = root;
		if (current != null) {
			while (current.left != null)
				current = current.left;
			return current.value;
		}
		return null;
	}

	/**
	 * return the value of the rightmost child of the tree. if there is no
	 * rightmost child, then return null. DO NOT DO A SPLAY FOR THIS OPERATION
	 *
	 * @return the value of the rightmost child.
	 */
	public T getRightmostChild() {
		Node<T> current = root;
		if (current != null) {
			while (current.right != null)
				current = current.right;
			return current.value;
		}
		return null;
	}

}