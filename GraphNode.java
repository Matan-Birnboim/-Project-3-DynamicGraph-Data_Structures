public class GraphNode {

    public int key;
    public GraphNode prev;
    public GraphNode next;
    public GraphNode parent;
    public GraphNode leftChild;
    public GraphNode rightSibling;
    public GraphNode qNext;
    public GraphNode qPrev;
    public GraphNode sNext;
    public GraphNode sPrev;
    public GraphEdge lastInEdge;
    public GraphEdge lastOutEdge;
    public String color;
    public int inDegree;
    public int outDegree;
    public int d;
    public int f;
    public int level;
    public GraphNode lastRChild;

    public GraphNode(int key){
        this.parent = null;
        this.leftChild = null;
        this.rightSibling = null;
        this.lastInEdge = null;
        this.lastOutEdge = null;
        this.qPrev = null;
        this.qNext = null;
        this.sPrev = null;
        this.sNext = null;
        this.key = key;
        this.inDegree = 0;
        this.outDegree = 0;
        this.prev = null;
        this.next = null;
        this.color = "white";
        this.d = 0;
        this.f = 0;
        this.level = -1;
        this.lastRChild = null;

    }

    public int getKey(){
        return this.key;
    }

    public int getOutDegree(){
        return this.outDegree;
    }

    public int getInDegree(){
        return this.inDegree;
    }


}
