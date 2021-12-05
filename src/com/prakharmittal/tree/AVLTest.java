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
public class AVLTest {

    private static final int TIMEOUT = 200;
    private AVL<Integer> tree;

    @Before
    public void setup() {
        tree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void t01_Initialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void t02_ConstructorNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree = new AVL<>(null);
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

        tree = new AVL<>(data);
        assertEquals(10, tree.size());

        validateNode(tree.getRoot(), 5, 3, 0);
        validateNode(tree.getRoot().getLeft(), 2, 2, -1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getLeft().getRight(), 3, 1, -1);
        validateNode(tree.getRoot().getLeft().getRight().getRight(), 4, 0, 0);
        validateNode(tree.getRoot().getRight(), 7, 2, -1);
        validateNode(tree.getRoot().getRight().getLeft(), 6, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 9, 1, 0);
        validateNode(tree.getRoot().getRight().getRight().getLeft(), 8, 0, 0);
        validateNode(tree.getRoot().getRight().getRight().getRight(), 10, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t04_AddNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.add(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t05_AddNoBalancing() {
        tree.add(5);
        tree.add(3);
        tree.add(9);
        tree.add(2);
        tree.add(7);
        tree.add(10);
        tree.add(6);
        tree.add(8);
        assertEquals(8, tree.size());

        tree.add(8);
        assertEquals(8, tree.size());

        validateNode(tree.getRoot(), 5, 3, -1);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 2, 0, 0);
        validateNode(tree.getRoot().getRight(), 9, 2, 1);
        validateNode(tree.getRoot().getRight().getLeft(), 7, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft().getLeft(), 6, 0, 0);
        validateNode(tree.getRoot().getRight().getLeft().getRight(), 8, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 10, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t06_AddLeft() {
        tree.add(1);
        tree.add(3);
        tree.add(5);
        tree.add(7);
        tree.add(9);
        assertEquals(5, tree.size());

        validateNode(tree.getRoot(), 3, 2, -1);
        validateNode(tree.getRoot().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 7, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft(), 5, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 9, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t07_AddRight() {
        tree.add(9);
        tree.add(7);
        tree.add(5);
        tree.add(3);
        tree.add(1);
        assertEquals(5, tree.size());

        validateNode(tree.getRoot(), 7, 2, 1);
        validateNode(tree.getRoot().getLeft(), 3, 1, 0);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getLeft().getRight(), 5, 0, 0);
        validateNode(tree.getRoot().getRight(), 9, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t08_AddRightLeft() {
        tree.add(1);
        tree.add(3);
        tree.add(5);
        tree.add(9);
        tree.add(7);
        assertEquals(5, tree.size());

        validateNode(tree.getRoot(), 3, 2, -1);
        validateNode(tree.getRoot().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 7, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft(), 5, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 9, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t09_AddLeftRight() {
        tree.add(9);
        tree.add(7);
        tree.add(5);
        tree.add(1);
        tree.add(3);
        assertEquals(5, tree.size());

        validateNode(tree.getRoot(), 7, 2, 1);
        validateNode(tree.getRoot().getLeft(), 3, 1, 0);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getLeft().getRight(), 5, 0, 0);
        validateNode(tree.getRoot().getRight(), 9, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t10_RemoveDataNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.remove(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t11_RemoveEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            tree.remove(5);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t12_RemoveSizeOneNotFound() {
        tree.add(6);
        assertEquals(1, tree.size());

        assertThrows(NoSuchElementException.class, () -> {
            tree.remove(5);
        });

        assertEquals(1, tree.size());

        validateNode(tree.getRoot(), 6, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t13_RemoveSizeOneFound() {
        Integer i = new Integer(129);

        tree.add(i);
        assertEquals(1, tree.size());

        assertSame(i, tree.remove(129));
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void t14_RemoveLeaf() {
        tree.add(3);
        tree.add(5);
        tree.add(7);
        assertEquals(3, tree.size());

        assertSame(3, tree.remove(3));
        assertEquals(2, tree.size());

        validateNode(tree.getRoot(), 5, 1, -1);
        validateNode(tree.getRoot().getRight(), 7, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t15_RemoveLeafLeft() {
        tree.add(1);
        tree.add(3);
        tree.add(5);
        tree.add(7);
        assertEquals(4, tree.size());

        assertSame(1, tree.remove(1));
        assertEquals(3, tree.size());

        validateNode(tree.getRoot(), 5, 1, 0);
        validateNode(tree.getRoot().getLeft(), 3, 0, 0);
        validateNode(tree.getRoot().getRight(), 7, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t16_RemoveLeafRight() {
        tree.add(7);
        tree.add(5);
        tree.add(3);
        tree.add(1);
        assertEquals(4, tree.size());

        assertSame(7, tree.remove(7));
        assertEquals(3, tree.size());

        validateNode(tree.getRoot(), 3, 1, 0);
        validateNode(tree.getRoot().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 5, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t17_RemoveLeafRightLeft() {
        tree.add(1);
        tree.add(3);
        tree.add(7);
        tree.add(5);
        assertEquals(4, tree.size());

        assertSame(1, tree.remove(1));
        assertEquals(3, tree.size());

        validateNode(tree.getRoot(), 5, 1, 0);
        validateNode(tree.getRoot().getLeft(), 3, 0, 0);
        validateNode(tree.getRoot().getRight(), 7, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t18_RemoveLeafLeftRight() {
        tree.add(7);
        tree.add(5);
        tree.add(1);
        tree.add(3);
        assertEquals(4, tree.size());

        assertSame(7, tree.remove(7));
        assertEquals(3, tree.size());

        validateNode(tree.getRoot(), 3, 1, 0);
        validateNode(tree.getRoot().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 5, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t19_RemoveOneChild() {
        tree.add(3);
        tree.add(5);
        tree.add(7);
        tree.add(1);
        tree.add(9);
        assertEquals(5, tree.size());

        assertSame(3, tree.remove(3));
        assertEquals(4, tree.size());

        validateNode(tree.getRoot(), 5, 2, -1);
        validateNode(tree.getRoot().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 7, 1, -1);
        validateNode(tree.getRoot().getRight().getRight(), 9, 0, 0);

        assertSame(9, tree.remove(9));
        assertEquals(3, tree.size());

        validateNode(tree.getRoot(), 5, 1, 0);
        validateNode(tree.getRoot().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 7, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t20_RemoveOneChildLeft() {
        tree.add(3);
        tree.add(5);
        tree.add(9);
        tree.add(7);
        tree.add(1);
        tree.add(11);
        tree.add(13);
        assertEquals(7, tree.size());

        assertSame(3, tree.remove(3));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 9, 2, 0);
        validateNode(tree.getRoot().getLeft(), 5, 1, 0);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getLeft().getRight(), 7, 0, 0);
        validateNode(tree.getRoot().getRight(), 11, 1, -1);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t21_RemoveOneChildRight() {
        tree.add(11);
        tree.add(9);
        tree.add(5);
        tree.add(7);
        tree.add(3);
        tree.add(13);
        tree.add(1);
        assertEquals(7, tree.size());

        assertSame(11, tree.remove(11));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 5, 2, 0);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 9, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft(), 7, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t22_RemoveOneChildRightLeft() {
        tree.add(3);
        tree.add(5);
        tree.add(11);
        tree.add(1);
        tree.add(9);
        tree.add(13);
        tree.add(7);
        assertEquals(7, tree.size());

        assertSame(3, tree.remove(3));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 9, 2, 0);
        validateNode(tree.getRoot().getLeft(), 5, 1, 0);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getLeft().getRight(), 7, 0, 0);
        validateNode(tree.getRoot().getRight(), 11, 1, -1);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t23_RemoveOneChildLeftRight() {
        tree.add(11);
        tree.add(9);
        tree.add(3);
        tree.add(13);
        tree.add(5);
        tree.add(1);
        tree.add(7);
        assertEquals(7, tree.size());

        assertSame(11, tree.remove(11));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 5, 2, 0);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 9, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft(), 7, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t24_RemoveTwoChildren() {
        tree.add(3);
        tree.add(5);
        tree.add(9);
        tree.add(1);
        tree.add(11);
        tree.add(7);
        assertEquals(6, tree.size());

        assertSame(5, tree.remove(5));
        assertEquals(5, tree.size());

        validateNode(tree.getRoot(), 7, 2, 0);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 9, 1, -1);
        validateNode(tree.getRoot().getRight().getRight(), 11, 0, 0);

        assertSame(7, tree.remove(7));
        assertEquals(4, tree.size());

        validateNode(tree.getRoot(), 9, 2, 1);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 11, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t25_RemoveTwoChildrenLeft() {
        tree.add(3);
        tree.add(5);
        tree.add(9);
        tree.add(7);
        tree.add(1);
        tree.add(11);
        tree.add(13);
        assertEquals(7, tree.size());

        assertSame(5, tree.remove(5));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 7, 2, 0);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 11, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft(), 9, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t26_RemoveTwoChildrenRight() {
        tree.add(11);
        tree.add(9);
        tree.add(5);
        tree.add(7);
        tree.add(3);
        tree.add(13);
        tree.add(1);
        assertEquals(7, tree.size());

        assertSame(9, tree.remove(9));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 5, 2, 0);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 11, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft(), 7, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t27_RemoveTwoChildrenRightLeft() {
        tree.add(3);
        tree.add(5);
        tree.add(9);
        tree.add(1);
        tree.add(7);
        tree.add(13);
        tree.add(11);
        assertEquals(7, tree.size());

        assertSame(5, tree.remove(5));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 7, 2, 0);
        validateNode(tree.getRoot().getLeft(), 3, 1, 1);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getRight(), 11, 1, 0);
        validateNode(tree.getRoot().getRight().getLeft(), 9, 0, 0);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t28_RemoveTwoChildrenLeftRight() {
        tree.add(11);
        tree.add(9);
        tree.add(3);
        tree.add(13);
        tree.add(1);
        tree.add(7);
        tree.add(5);
        assertEquals(7, tree.size());

        assertSame(9, tree.remove(9));
        assertEquals(6, tree.size());

        validateNode(tree.getRoot(), 7, 2, 0);
        validateNode(tree.getRoot().getLeft(), 3, 1, 0);
        validateNode(tree.getRoot().getLeft().getLeft(), 1, 0, 0);
        validateNode(tree.getRoot().getLeft().getRight(), 5, 0, 0);
        validateNode(tree.getRoot().getRight(), 11, 1, -1);
        validateNode(tree.getRoot().getRight().getRight(), 13, 0, 0);
    }

    @Test(timeout = TIMEOUT)
    public void t29_GetDataNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.get(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t30_GetEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            tree.get(5);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t31_Get() {
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
    public void t32_ContainsDataNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            tree.contains(null);
        });
    }

    @Test(timeout = TIMEOUT)
    public void t33_Contains() {
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
    public void t34_TraversalsEmpty() {
        ArrayList<Integer> output = new ArrayList<>();

        assertEquals(output, tree.preorder());
        assertEquals(output, tree.inorder());
        assertEquals(output, tree.postorder());
        assertEquals(output, tree.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void t35_Preorder() {
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
        preorder.addToBack(1);
        preorder.addToBack(0);
        preorder.addToBack(2);
        preorder.addToBack(6);
        preorder.addToBack(4);
        preorder.addToBack(5);
        preorder.addToBack(8);
        preorder.addToBack(7);

        assertEquals(preorder, tree.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void t36_Inorder() {
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
    public void t37_Postorder() {
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
        postorder.addToBack(0);
        postorder.addToBack(2);
        postorder.addToBack(1);
        postorder.addToBack(5);
        postorder.addToBack(4);
        postorder.addToBack(7);
        postorder.addToBack(8);
        postorder.addToBack(6);
        postorder.addToBack(3);

        assertEquals(postorder, tree.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void t38_Levelorder() {
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
        levelorder.addToBack(1);
        levelorder.addToBack(6);
        levelorder.addToBack(0);
        levelorder.addToBack(2);
        levelorder.addToBack(4);
        levelorder.addToBack(8);
        levelorder.addToBack(5);
        levelorder.addToBack(7);

        assertEquals(levelorder, tree.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void t39_EqualsNotAVL() {
        assertFalse(tree.equals(5));
    }

    @Test(timeout = TIMEOUT)
    public void t40_EqualsSame() {
        assertTrue(tree.equals(tree));
    }

    @Test(timeout = TIMEOUT)
    public void t41_EqualsEmpty() {
        AVL<Integer> tree2 = new AVL<>();
        assertTrue(tree.equals(tree2));
        assertTrue(tree2.equals(tree));
    }

    // TODO: Parent ?, Different T
    @Test(timeout = TIMEOUT)
    public void t42_Equals() {
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

        AVL<Integer> tree2 = new AVL<>();
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

        tree.getRoot().setHeight(0);

        assertFalse(tree.equals(tree2));
        assertFalse(tree2.equals(tree));

        tree.getRoot().setHeight(3);
        tree.getRoot().setBalanceFactor(0);

        assertFalse(tree.equals(tree2));
        assertFalse(tree2.equals(tree));
    }

    @Test(timeout = TIMEOUT)
    public void t43_IsEmpty() {
        assertTrue(tree.isEmpty());

        tree.add(0);
        assertEquals(1, tree.size());

        assertFalse(tree.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void t44_Clear() {
        tree.add(2);
        tree.add(0);
        tree.add(1);
        assertEquals(3, tree.size());

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void t45_Min() {
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
    public void t46_Max() {
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
    public void t47_IsAVL() {
        AVL.Node<Integer> root = createIllegalNode(20, 2, 0);
        root.setLeft(createIllegalNode(15, 1, 0));
        root.getLeft().setLeft(createIllegalNode(6, 0, 0));
        root.getLeft().setRight(createIllegalNode(21, 0, 0));
        root.setRight(createIllegalNode(38, 1, -1));
        root.getRight().setRight(createIllegalNode(50, 0, 0));
        assertFalse(tree.isAVL(root));

        root.getLeft().setRight(createIllegalNode(18, 1, 0));
        assertFalse(tree.isAVL(root));

        root.getLeft().setRight(createIllegalNode(18, 0, -1));
        assertFalse(tree.isAVL(root));

        root.getLeft().setRight(createIllegalNode(18, 0, 0));
        assertTrue(tree.isAVL(root));

        root.setHeight(3);
        assertFalse(tree.isAVL(root));

        root.setHeight(2);
        root.setBalanceFactor(1);
        assertFalse(tree.isAVL(root));

        root.getRight().getRight().setRight(createIllegalNode(51, 0, 0));
        root.getRight().getRight().setHeight(1);
        root.getRight().getRight().setBalanceFactor(-1);
        root.getRight().setHeight(2);
        root.getRight().setBalanceFactor(-2);
        root.setHeight(3);
        root.setBalanceFactor(-1);
        assertFalse(tree.isAVL(root));
    }

    private AVL.Node<Integer> createIllegalNode(int data, int height, int balanceFactor) {
        AVL.Node<Integer> node = new AVL.Node<>(data);
        node.setHeight(height);
        node.setBalanceFactor(balanceFactor);
        return node;
    }

    private void validateNode(AVL.Node<Integer> node, int data, int height, int balanceFactor) {
        assertEquals((Integer) data, node.getData());
        assertEquals(height, node.getHeight());
        assertEquals(balanceFactor, node.getBalanceFactor());
    }
}
