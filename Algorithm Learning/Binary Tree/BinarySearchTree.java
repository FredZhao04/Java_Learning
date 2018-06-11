/**
 * Created by fred on 2018/6/11.
 */
public class BinarySearchTree {
    private Node root;

    private class Node{
        private Node left;
        private Node right;
        private int data;

        public Node(int data) {
            this.left = null;
            this.right = null;
            this.data = data;
        }
    }

    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * 创建二叉树
     */
    public void buildTree(Node node, int data){
        if(root == null){
            root = new Node(data);
        }else if(data < node.data){
            if(node.left == null){
                node.left = new Node(data);
            }else {
                buildTree(node.left, data);
            }
        }else {
            if(node.right == null){
                node.right = new Node(data);
            }else {
                buildTree(node.right, data);
            }
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder(Node node){
        if(node != null){
            System.out.println(node.data);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder(Node node){
        if(node != null){
            inOrder(node.left);
            System.out.println(node.data);
            inOrder(node.right);
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder(Node node){
        if(node != null){
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.data);
        }
    }

    public static void main(String[] args){
        int[] a = {2,4,12,45,21,6,111};
        BinarySearchTree bst = new BinarySearchTree();
        for(int i:a){
            bst.buildTree(bst.root, i);
        }
        bst.preOrder(bst.root);
        bst.inOrder(bst.root);
        bst.postOrder(bst.root);
    }
}
