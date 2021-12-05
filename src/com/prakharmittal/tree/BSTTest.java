package com.prakharmittal.tree;

import com.prakharmittal.list.ArrayList;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BSTTest {

    private static final int TIMEOUT = 200;
    private BST<Integer> tree;

    @Before
    public void setup() {
        tree = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void t02_ConstructorNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree = new BST<>(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t03_ConstructorValid() {
        ArrayList<Integer> data = new ArrayList<>();
        data.addToBack(5);
        data.addToBack(2);
        data.addToBack(7);
        data.addToBack(1);
        data.addToBack(3);
        data.addToBack(6);
        data.addToBack(8);
        data.addToBack(4);
        data.addToBack(9);
        data.addToBack(10);

        tree = new BST<>(data);
        assertEquals(10, tree.size());

        assertEquals((Integer) 5, tree.getRoot().getData());
        assertEquals((Integer) 2, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 7, tree.getRoot().getRight().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 3, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getRight().getRight().getData());
        assertEquals((Integer) 6, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 8, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 9, tree.getRoot().getRight().getRight().getRight().getData());
        assertEquals((Integer) 10, tree.getRoot().getRight().getRight().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.add(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t05_Add() {
        tree.add(6);
        assertEquals(1, tree.size());

        tree.add(6);
        assertEquals(1, tree.size());

        assertEquals((Integer) 6, tree.getRoot().getData());
        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getRight());

        tree.add(3);
        tree.add(8);
        tree.add(2);
        tree.add(4);
        tree.add(9);
        tree.add(5);
        assertEquals(7, tree.size());

        tree.add(9);
        assertEquals(7, tree.size());

        assertEquals((Integer) 6, tree.getRoot().getData());
        assertEquals((Integer) 3, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 8, tree.getRoot().getRight().getData());
        assertEquals((Integer) 2, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 9, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 5, tree.getRoot().getLeft().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void t06_RemoveDataNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.remove(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t07_RemoveEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            tree.remove(5);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t08_RemoveSizeOneNotFound() {
        tree.add(6);
        assertEquals(1, tree.size());

        assertThrows(NoSuchElementException.class, () -> {
            tree.remove(5);
        });

        assertEquals(1, tree.size());
        assertEquals((Integer) 6, tree.getRoot().getData());
    }

    @Test(timeout = TIMEOUT)
    public void t09_RemoveSizeOneFound() {
        Integer i = new Integer(129);

        tree.add(i);
        assertEquals(1, tree.size());

        assertSame(i, tree.remove(129));
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void t10_RemoveLeaf() {
        Integer i = new Integer(170);

        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(3);
        tree.add(7);
        tree.add(11);
        tree.add(16);
        tree.add(4);
        tree.add(6);
        tree.add(i);
        assertEquals(10, tree.size());

        assertSame(i, tree.remove(170));
        assertEquals(9, tree.size());

        assertEquals((Integer) 10, tree.getRoot().getData());
        assertEquals((Integer) 5, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 15, tree.getRoot().getRight().getData());
        assertEquals((Integer) 3, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 7, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 11, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 16, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 4, tree.getRoot().getLeft().getLeft().getRight().getData());
        assertEquals((Integer) 6, tree.getRoot().getLeft().getRight().getLeft().getData());
    }

    @Test(timeout = TIMEOUT)
    public void t11_RemoveOneChild() {
        Integer i = new Integer(160);

        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(3);
        tree.add(7);
        tree.add(11);
        tree.add(i);
        tree.add(170);
        tree.add(180);
        assertEquals(9, tree.size());

        assertSame(i, tree.remove(160));
        assertEquals(8, tree.size());

        assertEquals((Integer) 10, tree.getRoot().getData());
        assertEquals((Integer) 5, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 15, tree.getRoot().getRight().getData());
        assertEquals((Integer) 3, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 7, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 11, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 170, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 180, tree.getRoot().getRight().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void t12_RemoveTwoChildren() {
        Integer i = new Integer(250);
        Integer j = new Integer(210);
        Integer k = new Integer(150);

        tree.add(j);
        tree.add(k);
        tree.add(i);
        tree.add(30);
        tree.add(170);
        tree.add(220);
        tree.add(260);
        tree.add(270);
        tree.add(280);
        assertEquals(9, tree.size());

        assertSame(i, tree.remove(250));
        assertEquals(8, tree.size());

        assertEquals((Integer) 210, tree.getRoot().getData());
        assertEquals((Integer) 150, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 260, tree.getRoot().getRight().getData());
        assertEquals((Integer) 30, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 170, tree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 220, tree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 270, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 280, tree.getRoot().getRight().getRight().getRight().getData());

        assertSame(j, tree.remove(210));
        assertEquals(7, tree.size());

        assertEquals((Integer) 220, tree.getRoot().getData());
        assertEquals((Integer) 150, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 260, tree.getRoot().getRight().getData());
        assertEquals((Integer) 30, tree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 170, tree.getRoot().getLeft().getRight().getData());
        assertNull(tree.getRoot().getRight().getLeft());
        assertEquals((Integer) 270, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 280, tree.getRoot().getRight().getRight().getRight().getData());

        assertSame(k, tree.remove(150));
        assertEquals(6, tree.size());

        assertEquals((Integer) 220, tree.getRoot().getData());
        assertEquals((Integer) 170, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 260, tree.getRoot().getRight().getData());
        assertEquals((Integer) 30, tree.getRoot().getLeft().getLeft().getData());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals((Integer) 270, tree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 280, tree.getRoot().getRight().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void t13_GetDataNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.get(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t14_GetEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            tree.get(5);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t15_Get() {
        Integer i = 600;
        Integer j = 300;
        Integer k = 800;
        Integer l = 200;
        Integer m = 400;
        Integer n = 900;

        tree.add(i);
        tree.add(j);
        tree.add(k);
        tree.add(l);
        tree.add(m);
        tree.add(n);
        assertEquals(6, tree.size());

        assertSame(i, tree.get(600));
        assertSame(j, tree.get(300));
        assertSame(k, tree.get(800));
        assertSame(l, tree.get(200));
        assertSame(m, tree.get(400));
        assertSame(n, tree.get(900));

        assertThrows(NoSuchElementException.class, () -> {
            tree.get(1000);
        });

        assertThrows(NoSuchElementException.class, () -> {
            tree.get(-1000);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t16_ContainsDataNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.contains(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t17_Contains() {
        tree.add(6);
        tree.add(3);
        tree.add(8);
        tree.add(2);
        tree.add(4);
        tree.add(9);
        assertEquals(6, tree.size());

        assertTrue(tree.contains(6));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(8));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(9));

        assertFalse(tree.contains(1));
        assertFalse(tree.contains(5));
        assertFalse(tree.contains(10));
    }

    @Test(timeout = TIMEOUT)
    public void t18_TraversalsEmpty() {
        ArrayList<Integer> output = new ArrayList<>();

        assertEquals(output, tree.preorder());
        assertEquals(output, tree.inorder());
        assertEquals(output, tree.postorder());
        assertEquals(output, tree.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void t19_Preorder() {
        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        ArrayList<Integer> preorder = new ArrayList<>();
        preorder.addToBack(3);
        preorder.addToBack(0);
        preorder.addToBack(1);
        preorder.addToBack(2);
        preorder.addToBack(8);
        preorder.addToBack(4);
        preorder.addToBack(6);
        preorder.addToBack(5);
        preorder.addToBack(7);

        assertEquals(preorder, tree.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void t20_Inorder() {
        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        ArrayList<Integer> inorder = new ArrayList<>();
        inorder.addToBack(0);
        inorder.addToBack(1);
        inorder.addToBack(2);
        inorder.addToBack(3);
        inorder.addToBack(4);
        inorder.addToBack(5);
        inorder.addToBack(6);
        inorder.addToBack(7);
        inorder.addToBack(8);

        assertEquals(inorder, tree.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void t21_Postorder() {
        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        ArrayList<Integer> postorder = new ArrayList<>();
        postorder.addToBack(2);
        postorder.addToBack(1);
        postorder.addToBack(0);
        postorder.addToBack(5);
        postorder.addToBack(7);
        postorder.addToBack(6);
        postorder.addToBack(4);
        postorder.addToBack(8);
        postorder.addToBack(3);

        assertEquals(postorder, tree.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void t22_Levelorder() {
        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        ArrayList<Integer> levelorder = new ArrayList<>();
        levelorder.addToBack(3);
        levelorder.addToBack(0);
        levelorder.addToBack(8);
        levelorder.addToBack(1);
        levelorder.addToBack(4);
        levelorder.addToBack(2);
        levelorder.addToBack(6);
        levelorder.addToBack(5);
        levelorder.addToBack(7);

        assertEquals(levelorder, tree.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void t23_BalanceEmpty() {
        tree.balance();
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void t24_BalanceEvenN() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(10);
        assertEquals(10, tree.size());

        tree.balance();
        assertEquals(3, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void t25_BalanceOddN() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        assertEquals(7, tree.size());

        tree.balance();
        assertEquals(2, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void t26_EqualsNotBST() {
        assertFalse(tree.equals(5));
    }

    @Test(timeout = TIMEOUT)
    public void t27_EqualsSame() {
        assertTrue(tree.equals(tree));
    }

    @Test(timeout = TIMEOUT)
    public void t28_EqualsEmpty() {
        BST<Integer> tree2 = new BST<>();
        assertTrue(tree.equals(tree2));
        assertTrue(tree2.equals(tree));
    }

    // TODO: Parent ?, Different T
    @Test(timeout = TIMEOUT)
    public void t29_Equals() {
        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        BST<Integer> tree2 = new BST<>();
        tree2.add(3);
        tree2.add(0);
        tree2.add(8);
        tree2.add(1);
        tree2.add(4);
        tree2.add(2);
        tree2.add(6);
        tree2.add(5);
        assertEquals(8, tree2.size());

        assertFalse(tree.equals(tree2));
        assertFalse(tree2.equals(tree));

        tree2.add(7);
        assertEquals(9, tree2.size());

        assertTrue(tree.equals(tree2));
        assertTrue(tree2.equals(tree));

        tree2.remove(7);
        tree2.add(9);
        assertEquals(9, tree2.size());

        assertFalse(tree.equals(tree2));
        assertFalse(tree2.equals(tree));
    }

    @Test(timeout = TIMEOUT)
    public void t30_IsEmpty() {
        assertTrue(tree.isEmpty());

        tree.add(0);
        assertEquals(1, tree.size());

        assertFalse(tree.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t31_Clear() {
        tree.add(2);
        tree.add(0);
        tree.add(1);
        assertEquals(3, tree.size());

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void t32_Height() {
        assertEquals(-1, tree.height());

        tree.add(6);
        assertEquals(1, tree.size());
        assertEquals(0, tree.height());

        tree.add(3);
        assertEquals(2, tree.size());
        assertEquals(1, tree.height());

        tree.add(8);
        assertEquals(3, tree.size());
        assertEquals(1, tree.height());

        tree.add(10);
        assertEquals(4, tree.size());
        assertEquals(2, tree.height());

        tree.add(2);
        assertEquals(5, tree.size());
        assertEquals(2, tree.height());

        tree.add(4);
        assertEquals(6, tree.size());
        assertEquals(2, tree.height());

        tree.add(1);
        assertEquals(7, tree.size());
        assertEquals(3, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void t33_Min() {
        assertThrows(NoSuchElementException.class, () -> {
            tree.min();
        });

        Integer i = new Integer(200);
        Integer j = new Integer(150);

        tree.add(i);
        assertEquals(1, tree.size());
        assertSame(i, tree.min());

        tree.add(j);
        tree.add(175);
        assertEquals(3, tree.size());
        assertSame(j, tree.min());
    }

    @Test(timeout = TIMEOUT)
    public void t34_Max() {
        assertThrows(NoSuchElementException.class, () -> {
            tree.max();
        });

        Integer i = new Integer(200);
        Integer j = new Integer(250);

        tree.add(i);
        assertEquals(1, tree.size());
        assertSame(i, tree.max());

        tree.add(j);
        tree.add(150);
        assertEquals(3, tree.size());
        assertSame(j, tree.max());
    }

    @Test(timeout = TIMEOUT)
    public void t35_IsBST() {
        BST.Node<Integer> root = new BST.Node<>(50);
        root.setLeft(new BST.Node<>(25));
        root.setRight(new BST.Node<>(75));
        root.getLeft().setLeft(new BST.Node<>(12));
        root.getLeft().setRight(new BST.Node<>(37));
        root.getLeft().getLeft().setLeft(new BST.Node<>(10));
        root.getLeft().getLeft().setRight(new BST.Node<>(15));
        root.getLeft().getLeft().getRight().setLeft(new BST.Node<>(13));
        root.getLeft().getRight().setRight(new BST.Node<>(40));

        assertTrue(tree.isBST(root));

        root = new BST.Node<>(20);
        root.setLeft(new BST.Node<>(15));
        root.setRight(new BST.Node<>(38));
        root.getLeft().setLeft(new BST.Node<>(6));
        root.getRight().setRight(new BST.Node<>(50));
        root.getLeft().getLeft().setRight(new BST.Node<>(21));

        assertFalse(tree.isBST(root));
    }
}
