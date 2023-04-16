public class Queue {

    public GraphNode first;
    public GraphNode last;
    public int qSize;


    public Queue() {
        this.first = null;
        this.last = null;
        this.qSize = 0;
    }


    public void enqueue(GraphNode gn) {
        if (this.qSize == 0) {
            this.first = gn;
            gn.qPrev = null;
            gn.qNext = null;
        } else {
            this.last.qNext = gn;
            gn.qPrev = this.last;
        }
        this.last = gn;
        this.qSize++;
    }

    public GraphNode dequeue() {
        GraphNode toReturn = getFirst();
        removeFirst();
        toReturn.qPrev = null;
        toReturn.qNext = null;
        return toReturn;
    }

    public void removeFirst() {
        if (this.first == null)
            return;
        this.first = this.first.qNext;
        if (this.first != null) {
            this.first.qPrev = null;
        }
        this.qSize--;
    }

    public GraphNode findKey(int key) {
        GraphNode current = this.first;
        while(current!= null) {
            if (current.getKey()==key){return current;}
            current = current.qNext;
        }
        return null;
    }

    public GraphNode getFirst() {
        return this.first;
    }

}