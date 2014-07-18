package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import structures.tree.SplayTree;

public class SplayTreeTest {
	SplayTree<String> testTree;

	@Before
	public void setUp() {
		testTree = new SplayTree<String>();
	}

	@Test
	public void testSplayTreeEmpty() {
		assertTrue("something in the tree!", testTree.isEmpty());
	}

	@Test
	public void testNotEmptyAfterInsert() {
		testTree.insert("A");
		assertFalse("Tree shows something there after insert",
				testTree.isEmpty());
	}

	@Test
	public void testEmptyAfterLastRemove() {
		testTree.insert("G");
		testTree.remove("G");
		assertTrue("Tree not empty after last remove", testTree.isEmpty());
	}

	@Test
	public void testSizeZeroAtStart() {
		assertEquals("New tree not at size 0", 0, testTree.size());
	}

	@Test
	public void testSizeAfterAdd() {
		testTree.insert("A");
		assertEquals("Tree after insert not at 1", 1, testTree.size());
	}

	@Test
	public void testSizeAfterRemove() {
		testTree.insert("A");
		testTree.insert("B");
		testTree.insert("C");
		testTree.remove("B");

		assertEquals("Failed to have correct size after a remove", 2,
				testTree.size());
	}

	@Test
	public void testInsertOneItem() {
		testTree.insert("A");
		assertEquals("Root not set to item", "A", testTree.getRootValue());
		assertEquals("Bad single element left tree", "A",
				testTree.getLeftmostChild());
		assertEquals("Bad single element right tree", "A",
				testTree.getRightmostChild());
	}

	@Test
	public void testInsertTwoItemLeft() {
		testTree.insert("C");
		testTree.insert("A");
		assertEquals("Didn't rotate insert to root", "A",
				testTree.getRootValue());
		assertEquals("Right child wrong on rotate", "C",
				testTree.getRightmostChild());
		assertEquals("Left child not null", "A", testTree.getLeftmostChild());
	}

	@Test
	public void testThreeInsertsToLeft() {
		testTree.insert("C");
		testTree.insert("B");
		testTree.insert("A");
		assertEquals("Didn't rotate to the root", "A", testTree.getRootValue());
		assertEquals("Right Child wrong on rotate", "C",
				testTree.getRightmostChild());
		assertEquals("Left Child wrong on rotate", "A",
				testTree.getLeftmostChild());
	}

	@Test
	public void testThreeInsertsToRight() {
		testTree.insert("A");
		testTree.insert("B");
		testTree.insert("C");
		assertEquals("Didn't rotate to the root", "C", testTree.getRootValue());
		assertEquals("Right Child wrong on rotate", "C",
				testTree.getRightmostChild());
		assertEquals("Left Child wrong on rotate", "A",
				testTree.getLeftmostChild());
	}

	@Test
	public void testFindMinEmpty() {
		assertNull("Find min did not return null if empty", testTree.findMin());
	}

	@Test
	public void testFindMaxEmpty() {
		assertNull("Find max did not return null if empty", testTree.findMax());
	}

	@Test
	public void testFindMin() {
		testTree.insert("A");
		testTree.insert("B");
		testTree.insert("C");
		testTree.insert("D");
		testTree.insert("E");
		testTree.insert("F");

		assertEquals("Returned wrong minimum", "A", testTree.findMin());
		assertEquals("Min not splayed to root", "A", testTree.getRootValue());
		assertEquals("After min splay, root should not have left child", "A",
				testTree.getLeftmostChild());
	}

	@Test
	public void testFindMax() {
		testTree.insert("F");
		testTree.insert("E");
		testTree.insert("D");
		testTree.insert("C");
		testTree.insert("B");
		testTree.insert("A");

		assertEquals("Returned wrong maximum", "F", testTree.findMax());
		assertEquals("Min not splayed to root", "F", testTree.getRootValue());
		assertEquals("After max splay, root should not have right child", "F",
				testTree.getRightmostChild());
	}

	@Test
	public void testContainsEmpty() {
		assertFalse("Thinks it contains wrong thing", testTree.contains("F"));
	}

	@Test
	public void testContainsOne() {
		testTree.insert("F");
		assertFalse("Thinks it contains wrong thing", testTree.contains("A"));
		assertTrue("Can't find only item", testTree.contains("F"));
	}

	@Test
	public void testContainsMany() {
		System.out.println("\nTrace testContainsMany");
		testTree.insert("X");
		testTree.insert("B");
		trace("After second insert");
		testTree.insert("M");
		trace("After third insert");
		testTree.insert("D");
		trace("After fourth insert");
		testTree.insert("E");
		trace("After fifth insert");
		testTree.insert("Y");
		trace("After sixth insert");

		assertFalse("Thinks it has something", testTree.contains("R"));
		assertEquals("Fail node on search should be at root", "M",
				testTree.getRootValue());
		assertTrue("Couldn't find an item after splay of found",
				testTree.contains("B"));
		assertEquals("Found node was not spayed to root", "B",
				testTree.getRootValue());
		assertEquals("After B splayed to root, should not have left child",
				"B", testTree.getLeftmostChild());

	}

	@Test
	public void testRemoveFromEmpty() {
		assertFalse("Failed to remove from empty tree", testTree.remove("G"));
	}

	@Test
	public void testRemoveOnlyItem() {
		testTree.insert("G");
		assertTrue("Failed to remove only item", testTree.remove("G"));
		assertNull("Root was not null after last remove",
				testTree.getRootValue());
	}

	@Test
	public void testRemoveOfNonexistentItem() {
		System.out.println("Trace testRemoveOfNonexistentItem");
		testTree.insert("G");
		testTree.insert("C");
		trace("After second insert");
		testTree.insert("A");
		trace("After third insert");
		testTree.insert("B");
		trace("After fourth insert");
		testTree.insert("R");
		trace("After fifth insert");

		assertFalse("Thinks it deleted a nonexistent item",
				testTree.remove("D"));
		assertEquals("Did not rotate last fail node to root", "C",
				testTree.getRootValue());
		trace("After remove of D");
	}

	/**
	 * @param string
	 */
	private void trace(String caption) {
		System.out.println(caption + " " + "Root: " + testTree.getRootValue()
				+ " Left: " + testTree.getLeftmostChild() + " Right: "
				+ testTree.getRightmostChild());

	}

}
