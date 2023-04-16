public class DynamicGraph
{
    public GraphNode lastGraphNode;
    public int time;
    public int gnNumber;

    public DynamicGraph(){
        this.lastGraphNode = null;
        this.time = 0;
        this.gnNumber = 0;

    }

    public GraphNode insertNode(int nodeKey){
        GraphNode graphNode = new GraphNode(nodeKey);
        if (this.gnNumber == 0) {
            graphNode.next = null;
            graphNode.prev = null;
        } else {
            this.lastGraphNode.next = graphNode;
            graphNode.prev = this.lastGraphNode;
        }
        this.lastGraphNode = graphNode;
        this.gnNumber++;
        return graphNode;
    }

    public void deleteNode(GraphNode node){
        if (node.getInDegree() == 0 && node.getOutDegree() == 0) {
            if (this.gnNumber == 1) {
                this.lastGraphNode = null;

            } else if (node.prev == null) {
                node.next.prev = null;

            } else if (node.next == null) {
                this.lastGraphNode = node.prev;
                node.prev.next = null;

            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }

            resetNode(node);
            node.key = -1;
            node.prev = null;
            node.next = null;
            node.sNext = null;
            node.sPrev = null;
            this.gnNumber--;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to){
        GraphEdge graphEdge = new GraphEdge(from,to);
        if(from.getOutDegree() == 0){
            graphEdge.prevOutEdge = null;
        }
        else{
            from.lastOutEdge.nextOutEdge = graphEdge;
            graphEdge.prevOutEdge=from.lastOutEdge;

        }
        graphEdge.nextOutEdge = null;
        from.lastOutEdge = graphEdge;
        from.outDegree++;

        if (to.getInDegree() == 0){
            graphEdge.prevInEdge = null;
        }
        else{
            to.lastInEdge.nextInEdge = graphEdge;
            graphEdge.prevInEdge = to.lastInEdge;
        }
        graphEdge.nextOutEdge = null;
        to.lastInEdge = graphEdge;
        to.inDegree++;

        return graphEdge;
    }

    public void deleteEdge(GraphEdge edge) {
        if (edge.from.getOutDegree() == 1){
            edge.from.lastOutEdge = null;
        }
        else if (edge.prevOutEdge == null) {
            edge.nextOutEdge.prevOutEdge = null;
        }
        else if (edge.nextOutEdge == null) {
            edge.prevOutEdge.nextOutEdge = null;
            edge.from.lastOutEdge = edge.prevOutEdge;
        }
        else {
            edge.prevOutEdge.nextOutEdge = edge.nextOutEdge;
            edge.nextOutEdge.prevOutEdge = edge.prevOutEdge;
        }

        if (edge.to.getInDegree() == 1){
            edge.to.lastInEdge = null;
        }
        else if (edge.prevInEdge == null) {
            edge.nextInEdge.prevInEdge = null;
        }
        else if (edge.nextInEdge == null) {
            edge.prevInEdge.nextInEdge = null;
            edge.to.lastInEdge = edge.prevInEdge;
        }
        else {
            edge.prevInEdge.nextInEdge = edge.nextInEdge;
            edge.nextInEdge.prevInEdge = edge.prevInEdge;
        }

        edge.from.outDegree--;
        edge.to.inDegree--;
        edge.nextInEdge = null;
        edge.nextOutEdge = null;
        edge.prevInEdge = null;
        edge.prevOutEdge = null;
        edge.from = null;
        edge.to = null;
    }


    public void dfs_visit(GraphNode u, Stack stack){
        this.time++;
        u.d = time;
        u.color = "gray";
        GraphEdge e = u.lastOutEdge;
        while (e != null){
            if (e.to.color.equals("white"))
            {
                e.to.parent=u;
                dfs_visit(e.to, stack);
            }
            e = e.prevOutEdge;

        }

        u.color="black";
        this.time++;
        u.f = this.time;
        stack.push(u);
    }


    public void dfs(Stack stack){
        GraphNode u = this.lastGraphNode;
        while (u != null){
            resetNode(u);
            u.sPrev = null;
            u.sNext = null;
            u=u.prev;
        }
        this.time = 0;
        u = this.lastGraphNode;
        while (u != null){
            if(u.color.equals("white")){
                dfs_visit(u, stack);
            }
            u = u.prev;

        }
    }


    public RootedTree scc() {
        GraphNode root = new GraphNode(0);
        RootedTree rt = new RootedTree(root.getKey());
        Stack stack = new Stack();
        dfs(stack);
        GraphNode gn = this.lastGraphNode;
        while (gn != null) {
            resetNode(gn);
            gn = gn.prev;
        }
        int level = 0;
        this.time = 0;
        gn = stack.getFirst();
        while (gn != null) {
            if (gn.color.equals("white")) {
                dfs_visit_reverse(gn, level);
            }
            gn = gn.sNext;
        }
        gn = stack.getFirst();
        GraphNode reverseRoot = gn;
        while (gn != null)  {
            if (gn.parent == null){
                if (rt.root.leftChild == null) {
                    rt.root.leftChild = gn;
                }
                else {
                    reverseRoot.rightSibling = gn ;
                }
                reverseRoot = gn;
            }
            gn = stack.pop();
        }
        return rt;
    }

    public void dfs_visit_reverse(GraphNode u, int level){
        this.time++;
        level++;
        u.level = level;
        u.d = time;
        u.color = "gray";
        GraphEdge e = u.lastInEdge;
        while (e != null){
            if (e.from.color.equals("white")){
                e.from.parent = u;
                if(e.to.leftChild == null){
                    e.to.leftChild = e.from;
                }
                else {
                    u.lastRChild.rightSibling = e.from;
                }
                u.lastRChild = e.from;
                dfs_visit_reverse(e.from, level);
            }
            e=e.prevInEdge;
        }

        u.color="black";
        this.time++;
        u.f = time;
    }

    public RootedTree bfs(GraphNode source) {
        GraphNode u;
        GraphNode p;
        GraphEdge e;
        Queue Q= new Queue();
        bfs_Initialisation();
        source.level = 0;
        source.d = 0;
        source.color = "gray";
        Q.enqueue(source);
        while (Q.qSize != 0) {
            p = Q.dequeue();
            e = p.lastOutEdge;
            while (e != null) {
                u = e.to;
                if (u.color.equals("white")) {
                    u.color = "gray";
                    u.level = p.level + 1;
                    u.d = p.d + 1;
                    u.parent = p;
                    Q.enqueue(u);
                    if (p.leftChild == null) {
                        p.leftChild = u;
                    }
                    else {
                        p.lastRChild.rightSibling = u;
                    }
                    p.lastRChild = u;
                }
                e = e.prevOutEdge;
            }
            p.color = "black";
        }
        RootedTree rt = new RootedTree();
        rt.root = source;
        return rt;
    }

    public void bfs_Initialisation() {
        GraphNode v = this.lastGraphNode;
        while (v != null) {
            resetNode(v);
            v = v.prev;
        }
    }

    public void resetNode(GraphNode v){
        v.color = "white";
        v.d = -1;
        v.parent = null;
        v.rightSibling = null;
        v.leftChild = null;
        v.level = -1;
        v.qNext = null;
        v.qPrev = null;
        v.lastRChild = null;
    }

}
