package dijkstras;

import com.opencsv.CSVWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Vertex implements Comparable<Vertex> {
    //number of vertex of graph
    private final int pipeNumber;
    // MAX_VALUE assumed to be infinity
    private int dist = Integer.MAX_VALUE;
    // previous vertex
    private Vertex previous = null;
    // map for storing adjacent мукеуч and the distance to them
    private final Map<Vertex, Integer> neighbours = new HashMap<>();

    public Vertex(Integer pipeNumber) {
        this.pipeNumber = pipeNumber;
    }

    public Integer getPipeNumber() {
        return pipeNumber;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public Map<Vertex, Integer> getNeighbours() {
        return neighbours;
    }

    public int compareTo(Vertex other) {
        return Integer.compare(dist, other.dist);
    }

    public void writeCSV(final PrintWriter fileWriter) throws IOException {
        if (this.previous == null) {
            fileWriter.write("FALSE; \n");
        } else {
            fileWriter.write("TRUE; " + this.getDist() + "\n");
        }
    }
}
