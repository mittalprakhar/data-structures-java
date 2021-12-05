package com.prakharmittal.graph;

import com.prakharmittal.hashing.HashMap;
import com.prakharmittal.hashing.HashSet;
import com.prakharmittal.list.ArrayList;

public class Graph<T> {

    private HashSet<Vertex<T>> vertices;
    private HashSet<Edge<T>> edges;
    private HashMap<Vertex<T>, ArrayList<VertexDistance<T>>> adjList;

    public Graph(HashSet<Vertex<T>> vertices, HashSet<Edge<T>> edges) {
        if (vertices == null) {
            throw new IllegalArgumentException("Set of vertices must not be null.");
        } else if (edges == null) {
            throw new IllegalArgumentException("Set of edges must not be null.");
        }
        this.vertices = vertices;
        this.edges = edges;
        this.adjList = new HashMap<>();
        for (Vertex<T> vertex: vertices) {
            adjList.put(vertex, new ArrayList<>());
        }
        for (Edge<T> edge: edges) {
            if (!adjList.contains(edge.getU()) || !adjList.contains(edge.getV())) {
                throw new IllegalArgumentException("Edge with non-existent vertex found.");
            }
            adjList.get(edge.getU()).addToBack(new VertexDistance<>(edge.getV(), edge.getWeight()));
        }
    }

    public HashSet<Vertex<T>> getVertices() {
        return vertices;
    }

    public HashSet<Edge<T>> getEdges() {
        return edges;
    }

    public HashMap<Vertex<T>, ArrayList<VertexDistance<T>>> getAdjList() {
        return adjList;
    }
}