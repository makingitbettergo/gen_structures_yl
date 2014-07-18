package structures.tree;

public class AVLTreeTest {
	public static void main(String[] args) {
		AVLTree<Integer> a = new AVLTree<Integer>();
		System.out.println("Integer Test: \n");
		a.insert(10);
		a.insert(13);
		a.insert(14);
		a.insert(11);
		a.insert(17);
		a.insert(9);
		a.insert(20);
		a.insert(19);
		a.insert(21);
		a.insert(15);
		a.insert(8);
		a.delete(17);
		a.delete(14);
		a.delete(13);
		a.insert(24);
		a.insert(23);
		a.insert(1);
		a.insert(2);
		a.insert(3);
		a.delete(10);
		a.print();

		System.out.println("\nString Test: \n");

		AVLTree<String> b = new AVLTree<String>();
		b.insert("lol");
		b.insert("hey");
		b.insert("test");
		b.insert("cool");
		b.insert("avl");
		b.print();
	}

}
