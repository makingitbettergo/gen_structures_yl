package structures.tree;

//public class Node<E extends Comparable<? super E>> {
public class Node<T extends Comparable<? super T>> {
	public T value;
	public Node<T> parent;
	public Node<T> left;
	public Node<T> right;
	public int leftHeight = 0;
	public int rightHeight = 0;

	public int getBalanceFactor() {
		int leftHeight = 0;
		int rightHeight = 0;
		if (this.left != null) {
			leftHeight = this.left.getHeight() + 1;
		}
		if (this.right != null) {
			rightHeight = this.right.getHeight() + 1;
		}
		return leftHeight - rightHeight;
	}

	public int getLeftHeight() {
		return this.leftHeight;
	}

	public int getRightHeight() {
		return this.rightHeight;
	}

	public int getHeight() {
		return Math.max(rightHeight, leftHeight);
	}

	public Node(T value) {
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	public Node<T> getRight() {
		return this.right;
	}

	public Node<T> getLeft() {
		return this.left;
	}

	public Node<T> getParent() {
		return this.parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public void setLeft(Node<T> left) {
		this.left = left;

		// is this right?
		if (left == null) {
			this.leftHeight = 0;
		} else {
			this.leftHeight = Math.max(left.leftHeight, left.rightHeight) + 1;
		}
	}

	public void setRight(Node<T> right) {
		this.right = right;

		if (right == null) {
			this.rightHeight = 0;
		} else {
			this.rightHeight = Math.max(right.leftHeight, right.rightHeight) + 1;
		}
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isRightChild() {
		return this == this.getParent().getRight();
	}

	public boolean isLeftChild() {
		return this == this.getParent().getLeft();
	}

	// }
	// public E value;
	// public Node<E> left, right, parent;
	// protected int height;
	// private boolean inQueue;

	// public boolean isInQueue() {
	// return inQueue;
	// }
	//
	// public void setInQueue(boolean inQueue) {
	// this.inQueue = inQueue;
	// }

	// public Node() {
	// parent = null;
	// left = null;
	// right = null;
	// value = null;
	// height = 0;
	// }
	//
	// public Node(E val) {
	// this.value = val;
	// }
	//
	// public Node<E> getParent() {
	// return parent;
	// }
	//
	// public void setParent(Node<E> node) {
	// parent = node;
	// }
	//
	// public Node<E> getLeft() {
	// return left;
	// }
	//
	// public void setLeft(Node<E> node) {
	// left = node;
	// }
	//
	// public Node<E> getRight() {
	// return right;
	// }
	//
	// public void setRight(Node<E> node) {
	// right = node;
	// }
	//
	// public String toString() {
	// return this.value.toString();
	// }

}