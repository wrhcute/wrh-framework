package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName BiSortTreeTest.java
 * @Description TODO
 * @createTime 2021年12月20日 15:22:00
 */
public class BiSortTreeTest {



    @Test
    public void testPutAndSearch(){
        BiSortTree<Integer,Integer> biSortTree = new BiSortTree<>(5,5);
        biSortTree.put(1,1);
        biSortTree.put(6,1);
        biSortTree.put(3,3);
        final BiSortTree<Integer, Integer>.Node node = biSortTree.searchNode(3);
        assert node.getS() == 3;
    }

    @Test
    public void testPrintln(){
        BiSortTree<Integer,Integer> biSortTree = new BiSortTree<>(5,5);
        biSortTree.put(1,1);
        biSortTree.put(6,1);
        biSortTree.put(3,3);
        //biSortTree.println();
    }

    @Test
    public void testRemove(){
        BiSortTree<Integer,String> biSortTree = new BiSortTree<>(10, "10");
        biSortTree.put(1,"1");
        biSortTree.put(6,"6");
        biSortTree.put(3,"3");
        assert biSortTree.remove(6).equals("6");
        System.out.println(biSortTree);
    }
}
