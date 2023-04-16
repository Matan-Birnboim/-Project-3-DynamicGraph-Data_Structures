public class GraphEdge {
    public GraphNode from;
    public GraphNode to;
    public  GraphEdge prevInEdge;
    public  GraphEdge nextInEdge;
    public  GraphEdge prevOutEdge;
    public  GraphEdge nextOutEdge;

    public GraphEdge(GraphNode from, GraphNode to){
        this.from = from;
        this.to = to;
        this.nextInEdge = null;
        this.prevInEdge = null;
        this.prevOutEdge = null;
        this.nextOutEdge = null;
    }

}
