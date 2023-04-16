public class Stack {

    public GraphNode first;
    int sSize;

    public Stack() {
        this.first = null;
        this.sSize = 0;
    }

    public GraphNode getFirst(){return this.first;}

    public void push(GraphNode gn){
        if (this.sSize == 0){
            gn.sNext = null;
            gn.sPrev = null;
        }
        else {
            gn.sNext = this.first;
            this.first.sPrev = gn;
        }
        this.first = gn;
        this.sSize++;
    }

    public GraphNode pop(){
        if (this.sSize != 0){
            GraphNode toReturn = getFirst();
            this.first = toReturn.sNext;
            if (this.first != null) {
                this.first.sPrev = null;
            }
            toReturn.sNext=null;
            toReturn.sPrev=null;
            this.sSize--;
            return toReturn;
        }
        return null;
    }
}
