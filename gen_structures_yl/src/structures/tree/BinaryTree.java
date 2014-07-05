package structures.tree;

import structures.linear.Queue;

/**
 * A binary tree implementation
 * 
 * @author Yue
 * 
 * @param <E>
 */
public class BinaryTree<E extends Comparable<? super E>> {
	protected Node<E> root;
	protected Queue<Node<E>> queue;

	public BinaryTree() {
		root = null;
		queue = new Queue<>();
	}

	public Node<E> root() {
		if (!isEmpty())
			return root;
		else
			return null;
	}

	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * worst case O(n)
	 * 
	 * @return how many nodes are in the tree
	 */
	public int size() {
		return this.size(this.root);
	}

	private int size(Node<E> current) {
		if (current == null) {
			return 0;
		} else {
			return this.size(current.getLeft()) + this.size(current.getRight())
					+ 1;
		}
	}

	public boolean isRoot(Node<E> v) {
		if (root == null)
			return false;
		else
			return (v == root);
	}

	public boolean hasParent(Node<E> v) {
		if (v.getParent() == null)
			return false;
		else
			return true;
	}

	public Node<E> parent(Node<E> v) {
		if (hasParent(v))
			return v.getParent();
		else
			return null;
	}

	public boolean isLeaf(Node<E> v) {
		return (!hasLeft(v) && !hasRight(v));
	}

	public boolean hasLeft(Node<E> v) {
		return (v.getLeft() != null);
	}

	public Node<E> left(Node<E> v) {
		if (hasLeft(v))
			return v.getLeft();
		else
			return null;
	}

	public boolean hasRight(Node<E> v) {
		return (v.getRight() != null);
	}

	public Node<E> right(Node<E> v) {
		if (hasRight(v))
			return v.getRight();
		else
			return null;
	}

	public boolean isFull(Node<E> v) {
		return (hasLeft(v) && hasRight(v));
	}

	public void addNode(E value) {
		this.root = insert(this.root, value);
	}

	private Node<E> insert(Node<E> current, E value) {
		if (current == null) {
			current = new Node<E>(value);
		} else if (value.compareTo(current.value) < 0) {
			current.left = insert(current.left, value);
			current.left.setParent(current);
		} else if (value.compareTo(current.value) > 0) {
			current.right = insert(current.right, value);
			current.right.setParent(current);
			// ***** If equal keys are NOT allowed, ERROR *****
		} else {
			System.err.println(value + " is already in.");
		}
		return current;
	}

	private Node<E> remove(E value) {
		Node<E> temp = locate(value);
		// check if it is root
		if (temp.equals(this.root)) {
			this.root = null;
			return temp;
		}
		// if not not root check if left
		if (temp.parent.left != null && temp.parent.left.equals(temp)) {
			temp.parent.left = null;
			temp.parent = null;
		} else if (temp.parent.right != null && temp.parent.right.equals(temp)) {
			temp.parent.right = null;
			temp.parent = null;

		}
		return temp;
	}

	private Node<E> findTheFutherestLeftNode(Node<E> current) {
		if (current.left == null)
			return current;
		else
			return findTheFutherestLeftNode(current.left);
	}

	private Node<E> findTheFutherestRightNode(Node<E> current) {
		if (current.right == null)
			return current;
		else
			return findTheFutherestRightNode(current.right);
	}

	private void collectAllRightChildren(Node<E> current) {
		this.queue.clear();
		while (current.right != null && !current.right.equals(current)) {
			Node<E> temp = findTheFutherestRightNode(current);
			if (!current.right.equals(current)) {
				remove(temp.value);
				this.queue.enqueue(temp);
			}
		}
	}

	private void collectAllLeftChildren(Node<E> current) {
		this.queue.clear();
		while (current.left != null && !current.left.equals(current)) {
			Node<E> temp = findTheFutherestLeftNode(current);
			if (!current.left.equals(current)) {
				remove(temp.value);
				this.queue.enqueue(temp);
			}
		}
	}

	public void rightRotate(Node<E> current) {
		// all the right children of the node are collected
		collectAllRightChildren(current);
		// parent is root
		if (current.parent == null) {
			System.err.println("root cannot be rotated");
		} else {
			// child must be the left child of the parent
			if (current.parent.left.equals(current)) {
				boolean toRoot = false;
				Node<E> wasParent = current.parent;
				current.parent = wasParent.parent;
				if (current.parent == null) {
					toRoot = true;
				} else {
					current.parent.left = current;
				}
				wasParent.parent = current;
				current.right = wasParent;
				wasParent.left = null;
				while (this.queue.size() > 0) {
					addNode(this.queue.dequeue().value);
				}
				if (toRoot) {
					this.root = current;
				}
			} else {
				System.err.println("current is not the left child");
			}
		}
	}

	public void leftRotate(Node<E> current) {
		// all the left children of the node are collected
		collectAllLeftChildren(current);
		// parent is root
		if (current.parent == null) {
			System.err.println("root cannot be rotated");
			// height 2
		} else {
			// child must be the left child of the parent
			if (current.parent.right.equals(current)) {
				boolean toRoot = false;
				Node<E> wasParent = current.parent;
				current.parent = wasParent.parent;
				if (current.parent == null) {
					toRoot = true;
				} else {
					current.parent.right = current;
				}
				wasParent.parent = current;
				current.left = wasParent;
				wasParent.right = null;
				while (this.queue.size() > 0) {
					addNode(this.queue.dequeue().value);
				}
				if (toRoot) {
					this.root = current;
				}

			} else {
				System.err.println("current is not the left child");
			}
		}
	}

	public Node<E> locate(E value) {
		Node<E> finger = root;
		while (finger != null && !finger.value.equals(value)) {
			if (value.compareTo(finger.value) < 0)
				finger = finger.left;
			else
				finger = finger.right;
		}
		if (finger == null) {
			return null;
		} else {
			return finger;
		}
	}

	private int updateHeight(Node<E> node) {
		Node<E> lt = node.left, rt = node.right;

		if (lt == null && rt == null) { // Leaf node is height zero
			return 0;
		} else if (lt == null) { // Half node cases
			return 1 + rt.height;
		} else if (rt == null) {
			return 1 + lt.height;
		} else {
			return 1 + Math.max(lt.height, rt.height);
		}
	}

	private int getLevel() {
		return (int) (Math.log(size()) / Math.log(2));
	}

	private int height(Node<E> p) {
		if (p == null)
			return -1;
		else
			return 1 + Math.max(height(p.left), height(p.right));
	}

	public String toString() {
		if (this.root == null) {
			return "[]";
		}
		// String recStr = this.toStringPreOrder(this.root);
		// String recStr = this.toStringPostOrder(this.root);
		String recStr = this.toStringInOrder(this.root);
		return "[" + recStr.substring(0, recStr.length() - 1) + "]";
	}

	public String toStringInOrder(Node<E> current) {
		if (current == null)
			return "";
		return this.toStringInOrder(current.left) + current.value.toString()
				+ "," + this.toStringInOrder(current.right);

	}

	public String toStringPostOrder(Node<E> current) {
		if (current == null)
			return "";
		return toStringPostOrder(current.left)
				+ toStringPostOrder(current.right) + current.value.toString()
				+ ",";
	}

	public String toStringPreOrder(Node<E> current) {
		if (current == null)
			return "";
		return current.value.toString() + "," + toStringPreOrder(current.left)
				+ toStringPreOrder(current.right);
	}

	public static void main(String[] args) {
		BinaryTree<Integer> bt = new BinaryTree<>();
		bt.addNode(7);
		bt.addNode(4);
		bt.addNode(17);
		bt.addNode(2);
		bt.addNode(6);
		bt.addNode(5);
		bt.addNode(10);
		bt.addNode(20);
		// System.out.println(bt);
		// for (int i = 0; i < 20; i++) {
		// // for (int i = 20; i > 0; i--) {
		// bt.addNode(i);
		// }
		System.out.println(bt.root().value);
		// bt.remove(15);
		// bt.collectAllRightChildren(bt.locate(0));
		// bt.collectAllLeftChildren(bt.locate(17));
		// while (bt.queue.size() > 0) {
		// bt.addNode(bt.queue.dequeue().value);
		// }
		// bt.rightRotate(bt.locate(2));
		bt.leftRotate(bt.locate(17));
		// System.out.println(bt.height(bt.locate(19)));
		// System.out.println(bt.height(bt.locate(19)));
		System.out.println(bt.height(bt.locate(2)) == bt.height(bt.root()));
		System.out.println(bt.toStringPreOrder(bt.root));
		bt.rightRotate(bt.locate(7));
		System.out.println(bt.toStringPreOrder(bt.locate(7)));
	}
}

class Node<E extends Comparable<? super E>> {
	public E value;
	public Node<E> left, right, parent;
	protected int height;
	private boolean inQueue;

	public boolean isInQueue() {
		return inQueue;
	}

	public void setInQueue(boolean inQueue) {
		this.inQueue = inQueue;
	}

	public Node() {
		parent = null;
		left = null;
		right = null;
		value = null;
		height = 0;
	}

	public Node(E val) {
		this.value = val;
	}

	public Node<E> getParent() {
		return parent;
	}

	public void setParent(Node<E> node) {
		parent = node;
	}

	public Node<E> getLeft() {
		return left;
	}

	public void setLeft(Node<E> node) {
		left = node;
	}

	public Node<E> getRight() {
		return right;
	}

	public void setRight(Node<E> node) {
		right = node;
	}

	public String toString() {
		return this.value.toString();
	}

}