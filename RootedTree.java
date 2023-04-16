import java.io.DataOutputStream;
import java.io.IOException;

public class RootedTree{

    public GraphNode root;
    public int level;

    public RootedTree(){
        this.root= new GraphNode (0);
        this.root.leftChild=null;
        this.root.rightSibling=null;
    }
    public RootedTree(int key){
        this.root = new GraphNode(key);
        this.root.leftChild = null;
        this.root.rightSibling = null;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        out.writeBytes(String.valueOf(this.root.key));
        Queue Q = new Queue();
        Q.enqueue(this.root);
        this.level = 0;
        while (Q.getFirst() != null){
            GraphNode current = Q.dequeue();
            current.qNext = null;
            current.qPrev = null;
            current = current.leftChild;
            if (current == null){
                continue;
            }
            else if (current.level > this.level){
                out.writeBytes(System.lineSeparator());
                this.level++;
            }
            else {
                out.writeBytes(",");
            }
            while (current != null) {
                Q.enqueue(current);
                out.writeBytes(String.valueOf(current.key));
                if (current.rightSibling != null) {
                    out.writeBytes(",");
                }
                current = current.rightSibling;
            }
        }
    }

    public void preorderPrint(DataOutputStream out) throws IOException {
        boolean sourceNotChild = true;
        GraphNode x = this.root;
        out.writeBytes(""+x.getKey());
        x = x.leftChild;
        while (x != null) {
            if (sourceNotChild) {
                out.writeBytes(","+ x.getKey());
                if (x.leftChild != null) {
                    x = x.leftChild;
                }
                else {
                    if (x.rightSibling != null) {
                        x = x.rightSibling;
                    }
                    else {
                        sourceNotChild = false;
                        x = x.parent;
                    }
                }
            }
            else {
                if (x.rightSibling != null) {
                    sourceNotChild = true;
                    x = x.rightSibling;
                }
                else {
                    x = x.parent;
                }
            }
        }
    }

}

