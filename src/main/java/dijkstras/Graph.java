package dijkstras;

import java.io.*;
import java.util.*;

public class Graph {
    private final Map<Integer, Vertex> graph;


    public Graph(List<Edge> edges) {
        graph = new HashMap<>(edges.size());

        //one pass to find all  vertices
        for (Edge e : edges) {
            if (!graph.containsKey(e.getStartPoint())) graph.put(e.getStartPoint(), new Vertex(e.getStartPoint()));
            if (!graph.containsKey(e.getEndPoint())) graph.put(e.getEndPoint(), new Vertex(e.getEndPoint()));
        }

        //another pass to set neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.getStartPoint()).getNeighbours().put(graph.get(e.getEndPoint()), e.getLength());
        }
    }

    /**
     * Runs dijkstra using a specified source vertex
     */
    public void dijkstra(final int startPoint, final int endPoint) throws IOException {
        if (!graph.containsKey(startPoint)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startPoint);
            return;
        }
        final Vertex source = graph.get(startPoint);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.setPrevious(v == source ? source : null);
            v.setDist(v == source ? 0 : Integer.MAX_VALUE);
            q.add(v);
        }
        dijkstra(q);
    }

    /**
     * Implementation of dijkstra's algorithm using a binary heap.
     */
    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex start;
        Vertex second;
        while (!q.isEmpty()) {
            // vertex with shortest distance (first iteration will return source)
            start = q.pollFirst();
            if (start.getDist() == Integer.MAX_VALUE)
                break; // we can ignore start (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : start.getNeighbours().entrySet()) {
                second = a.getKey(); //the neighbour in this iteration

                final int alternateDist = start.getDist() + a.getValue();
                if (alternateDist < second.getDist()) { // shorter path to neighbour found
                    q.remove(second);
                    second.setDist(alternateDist);
                    second.setPrevious(start);
                    q.add(second);
                }
            }
        }
    }

    /**
     * Prints a path from the source to the specified vertex
     */
    public void writeCSV(final int endPoint, PrintWriter fileWriter) throws IOException {
        graph.get(endPoint).writeCSV(fileWriter);

    }
}

