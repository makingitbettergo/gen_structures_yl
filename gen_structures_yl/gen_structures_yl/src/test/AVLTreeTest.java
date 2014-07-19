package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import structures.tree.AVLTree;

public class AVLTreeTest {

	private AVLTree<Integer> avlTree;

	@Before
	public void setUp() {
		avlTree = new AVLTree<>();
	}

	@Test
	public void testSearch() {
	}

	@Test
	public void testInsert() {
		avlTree.insert(10);
		avlTree.insert(13);
		avlTree.insert(14);
		avlTree.insert(11);
		avlTree.insert(17);
		avlTree.insert(9);
		avlTree.insert(20);
		avlTree.insert(19);
		avlTree.insert(21);
		avlTree.insert(15);
		avlTree.insert(8);
		avlTree.insert(24);
		avlTree.insert(23);
		avlTree.insert(1);
		avlTree.insert(2);
		avlTree.insert(3);
		avlTree.insert(10);

		assertEquals("Actual result is different from the expected result",
				"[1,2,3,8,9,10,11,13,14,15,17,19,20,21,23,24]",
				avlTree.toString());
	}

	@Test
	public void testDelete() {
		avlTree.insert(10);
		avlTree.insert(13);
		avlTree.insert(14);
		avlTree.insert(11);
		avlTree.insert(17);
		avlTree.insert(9);
		avlTree.insert(20);
		avlTree.insert(19);
		avlTree.insert(21);
		avlTree.insert(15);
		avlTree.insert(8);
		avlTree.delete(17);
		avlTree.delete(14);
		avlTree.delete(13);
		avlTree.insert(24);
		avlTree.insert(23);
		avlTree.insert(1);
		avlTree.insert(2);
		avlTree.insert(3);
		avlTree.insert(10);

		assertEquals("Actual result is different from the expected result",
				"[1,2,3,8,9,10,11,15,19,20,21,23,24]", avlTree.toString());
	}

}
