package io.github.wrhcute.utils;

import java.util.function.Consumer;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName BiSortTree.java
 * @Description 搜索树
 * @createTime 2021年12月20日 14:54:00
 */
public class BiSortTree<S, D> {

    private BiSortTree<S, D> left;

    private BiSortTree<S, D> right;

    private final S s;
    private D data;
    private final int h;


    public BiSortTree(S s,D data){
        this(s,data,null,0);
    }

    private BiSortTree(S s,D data,BiSortTree<S,D> parent, int h){
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


    private static <T> int compare(T a , T b){
        if (a instanceof Comparable && b instanceof Comparable){
            return ((Comparable<T>) a).compareTo(b);
        }else{
            return Integer.compare(a.hashCode(),b.hashCode());
        }
    }

    /**
     *
     * @param s 比较键
     * @param data 值
     * @return 如果新建节点则返回null ，没有新建则更新节点,返回覆盖的旧值
     */
    public D put(S s,D data){
        Asserts.isTrue(h == 0,"根节点才允许put数据");
        int compare = compare(this.s,s);
        if (compare > 0){
            if (this.left == null){
                this.left = new BiSortTree<>(s,data,this,h + 1);
                return null;
            }else
                return this.left.put(s,data);
        }else if (compare < 0){
            if (this.right == null) {
                this.right = new BiSortTree<>(s, data, this, h + 1);
                return null;
            }else
                return this.right.put(s,data);
        }else{
            D old = this.data;
            this.data = data;
            return old;
        }
    }

    public BiSortTree<S,D> searchTree(S s){
        int compare = compare(this.s,s);
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



    public void traversePre(Consumer<BiSortTree<S,D>> consumer){
        consumer.accept(this);
        if (left != null)
            left.traversePre(consumer);
        if (right != null)
            right.traversePre(consumer);
    }

    public void traversePost(Consumer<BiSortTree<S,D>> consumer){
        if (left != null)
            left.traversePost(consumer);
        consumer.accept(this);
        if (right != null)
            right.traversePost(consumer);
    }

    public void traverseAfter(Consumer<BiSortTree<S,D>> consumer){
        if (left != null)
            left.traverseAfter(consumer);
        if (right != null)
            right.traverseAfter(consumer);
        consumer.accept(this);
    }

    public void traverseLevel(Consumer<BiSortTree<S,D>> consumer){
        CircularQueue<BiSortTree<S,D>> queue = new CircularQueue<>(5);
        queue.offer(this);
        while (queue.size() > 0){
            BiSortTree<S,D> pop = queue.poll();
            consumer.accept(pop);
            if(pop.left != null)
                queue.offer(pop.left);
            if (pop.right != null)
                queue.offer(pop.right);
        }
    }



    public D remove(S s){

        return null;
    }

    public BiSortTree<S,D> removeTree(S s){

        return null;
    }

}
