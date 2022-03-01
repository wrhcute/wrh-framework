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


    public class Node {
        private final S s;
        private D data;
        private boolean del;

        private Node left;

        private Node right;

        private Node parent;

        public S getS() {
            return s;
        }

        public D getData() {
            return data;
        }

        private Node(S s, D data, Node parent) {
            this.s = s;
            this.data = data;
            this.parent = parent;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public Node getParent() {
            return parent;
        }

        public void traversePre(Consumer<Node> consumer) {
            consumer.accept(this);
            if (left != null)
                left.traversePre(consumer);
            if (right != null)
                right.traversePre(consumer);
        }

        public void traversePost(Consumer<Node> consumer) {
            if (left != null)
                left.traversePost(consumer);
            consumer.accept(this);
            if (right != null)
                right.traversePost(consumer);
        }

        public void traverseAfter(Consumer<Node> consumer) {
            if (left != null)
                left.traverseAfter(consumer);
            if (right != null)
                right.traverseAfter(consumer);
            consumer.accept(this);
        }

        public void traverseLevel(Consumer<Node> consumer) {
            CircularQueue<Node> queue = new CircularQueue<>(5);
            queue.offer(this);
            while (queue.size() > 0) {
                Node pop = queue.poll();
                consumer.accept(pop);
                if (pop.left != null)
                    queue.offer(pop.left);
                if (pop.right != null)
                    queue.offer(pop.right);
            }
        }

        public void remove(){
            if (this == root) {
                //删除跟节点
                if (left == null && right == null){
                    root = null;
                }else if (left != null && right != null){
                    //叶子节点都不为空
                    Node successor = searchSuccessor();//找到后继节点,一定有,后继节点成为根节点
                    successor.remove();
                    successor.left = left;
                    successor.right = right;
                    root = successor;
                }else{
                    //左右 节点有一个不为空
                    if (left != null) {
                        root = left;
                    }else{
                        root = right;
                    }
                }
            } else {
                if (left == null && right == null) {
                    //根节点都为空,直接删
                    if (parent.left == this)
                        parent.left = null;
                    else
                        parent.right = null;
                }else if ( left != null && right != null){
                    //叶子节点都不为空
                    Node successor = this.searchSuccessor();//找到后继节点,一定有
                    successor.remove();
                    if (parent.left == this){
                        parent.left = successor;
                    }else{
                        parent.right = successor;
                    }
                    successor.parent = parent;
                    successor.left = left;
                    successor.right = right;
                }else{
                    //左右 节点有一个不为空
                    if (parent.left == this)
                        parent.left = left == null ? right : left;
                    else
                        parent.right = left == null ? right : left;
                }
            }
            parent = null;
            del = true;
        }

        //搜索后继节点: 右边第一个比自己大的节点
        private Node searchSuccessor(){
            if (this.right == null) {
                return null;
            }else {
                Node left = this.right;
                while (left.left != null)
                    left = left.left;
                return left;
            }
        }

        public Node removeTree(S s) {
            Node delTree = searchNode(s);
            if (delTree == root){
                root = null;
            }else if (delTree != null) {
                Node parent = delTree.parent;
                if (parent.left == delTree)
                    parent.left = null;
                else
                    parent.right = null;
            }
            return delTree;
        }



        public boolean isDel() {
            return del;
        }

        public Node search(S s){
            int compare = compare(this.s, s);
            if (compare > 0) {
                return left == null ? null : left.search(s);
            } else if (compare < 0) {
                return right == null ? null : right.search(s);
            } else {
                return this;
            }
        }


    }


    private Node root;


    public BiSortTree(S s, D first) {
        root = new Node(s,first,null);
    }


    public Node getRoot() {
        return root;
    }

    private static <T> int compare(T a, T b) {
        if (a instanceof Comparable && b instanceof Comparable) {
            return ((Comparable<T>) a).compareTo(b);
        } else {
            return Integer.compare(a.hashCode(), b.hashCode());
        }
    }

    /**
     * @param s    比较键
     * @param data 值
     * @return 如果新建节点则返回null ，没有新建则更新节点,返回覆盖的旧值
     */
    public D put(S s, D data) {
        return put0(root,s,data);
    }

    private D put0(Node node , S s, D data){
        int compare = compare(node.s, s);
        if (compare > 0) {
            if (node.left == null) {
                node.left = new Node(s, data, node);
                return null;
            } else
                return put0(node.left,s,data);
        } else if (compare < 0) {
            if (node.right == null) {
                node.right = new Node(s, data, node);
                return null;
            } else
                return put0(node.right,s,data);
        } else {
            D old = node.data;
            node.data = data;
            return old;
        }
    }

    public Node searchNode(S s) {
        return root.search(s);
    }



    public D search(S s) {
        Node node = searchNode(s);
        return node == null ? null : node.data;
    }


    public void traversePre(Consumer<Node> consumer) {
        root.traversePre(consumer);
    }

    public void traversePost(Consumer<Node> consumer) {
       root.traversePost(consumer);
    }

    public void traverseAfter(Consumer<Node> consumer) {
       root.traverseAfter(consumer);
    }

    public void traverseLevel(Consumer<Node> consumer) {
       root.traverseLevel(consumer);
    }

    public Node removeTree(S s){
        return root.removeTree(s);
    }

    public D remove(S s) {
        Node del = searchNode(s);
        if (del != null){
            del.remove();
            return del.data;
        }
        return null;
    }

}
