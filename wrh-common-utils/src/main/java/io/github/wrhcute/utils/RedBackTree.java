package io.github.wrhcute.utils;

public class RedBackTree<S,D> {

    private final Node nil = new Node();

    private Node root;

    public Node getRoot() {
        return root;
    }


    public RedBackTree(S s ,D first){
        root = new Node(s,first,null);
        root.color = false;
    }

    public class Node {
        private boolean color = true; //颜色 红色:true ,黑色:false
        private S s;
        private D d;
        private Node left = nil;
        private Node right = nil;
        private Node parent = null;

        public Node(S s, D d,  Node parent) {
            this.s = s;
            this.d = d;
            this.parent = parent;
        }

        private Node(){};
    }

    public D put(S s ,D data){
        return null;
    }


}
