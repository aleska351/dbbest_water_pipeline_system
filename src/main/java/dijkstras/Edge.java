package dijkstras;

public class Edge {
    //start point of vertex
    private final int startPoint;
    //endpoint of vertex
    private final int endPoint;
    //length of edge
    private final int length;

    public Edge(int startPoint, int endPoint, int length) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
    }

    public Integer getStartPoint() {
        return startPoint;
    }

    public Integer getEndPoint() {
        return endPoint;
    }

    public int getLength() {
        return length;
    }
}
