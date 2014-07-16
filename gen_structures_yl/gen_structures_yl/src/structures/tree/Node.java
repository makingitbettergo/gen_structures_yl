package structures.tree;

public class Node<E extends Comparable<? super E>> {
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