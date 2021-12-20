package io.github.wrhcute.utils;

import java.util.function.Consumer;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName BiSortTree.java
 * @Description
 * @createTime 2021年12月20日 14:54:00
 */
public class BiSortTree<S extends Comparable<S>, D> {

    private BiSortTree<S,D> parent;

    private BiSortTree<S, D> left;

    private BiSortTree<S, D> right;

    private final S s;
    private D data;
    private final int h;


    public BiSortTree(S s,D data){
        this(s,data,null,0);
    }

    private BiSortTree(S s,D data,BiSortTree parent, int h){
        this.s = s;
        this.data = data;
        this.h = h;
    }


    public BiSortTree<S,D> getLeft() {
        return left;
    }

    public BiSortTree<S,D> getRight() {
        return right;
    }

    public S getS() {
        return s;
    }

    public D getData() {
        return data;
    }

    public void put(S leaf,D data){
        int compare = this.s.compareTo(leaf);
        if (compare >= 0){
            if (this.left == null)
                this.left = new BiSortTree<>(leaf,data,this,h + 1);
            else
                this.left.put(leaf,data,this);
        }else{
            if (this.right == null)
                this.right = new BiSortTree<>(leaf,data,this, h + 1);
            else
                this.right.put(leaf,data,this);
        }

    }

    protected void put(S leaf,D data,BiSortTree<S,D> parent){
        this.parent = parent;
        put(leaf,data);
    }

    public BiSortTree<S,D> searchTree(S s){
        int compare = this.s.compareTo(s);
        if (compare > 0){
            return this.left == null ? null : this.left.searchTree(s);
        }else if (compare < 0){
            return this.right == null ? null : this.right.searchTree(s);
        }else{
            return this;
        }
    }

    public D search(S s){
        BiSortTree<S, D> tree = searchTree(s);
        return tree == null ? null : tree.data;
    }

    public BiSortTree<S,D> removeTree(S s){
        BiSortTree<S, D> tree = searchTree(s);
        if (tree != null){
            Asserts.isFalse(tree.h == 0,"root节点不允许移除");
            BiSortTree<S, D> parent = tree.parent;
            if (parent.left == tree){
                parent.left = null;
            }else{
                parent.right = null;
            }
            return tree;
        }
        return null;
    }

    public void traverse(Consumer<BiSortTree<S,D>> consumer){
        consumer.accept(this);
        if (left != null){
            left.traverse(consumer);
        }
        if (right != null)
            right.traverse(consumer);
    }

    public D remove(S s){
        BiSortTree<S, D> tree = searchTree(s);
        if (tree != null){
            Asserts.isFalse(tree.h == 0,"root节点不允许移除");
            BiSortTree<S, D> parent = tree.parent , left = tree.left , right = tree.right;
           if (tree == parent.left){
               parent.left = left;
               right.traverse(b -> parent.put(b.s,b.data));
           }else{
               parent.right = right;
               left.traverse(b -> parent.put(b.s,b.data));
           }
        }
        return tree != null ? tree.data : null;
    }

}
