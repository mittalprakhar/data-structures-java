package com.prakharmittal.algo;

import com.prakharmittal.graph.*;
import com.prakharmittal.hashing.DisjointSet;
import com.prakharmittal.hashing.HashMap;
import com.prakharmittal.hashing.HashSet;
import com.prakharmittal.linear.PriorityQueueMinHeap;
import com.prakharmittal.linear.QueueLinkedList;
import com.prakharmittal.list.ArrayList;

public class GraphAlgorithms {

    public static <T> ArrayList<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex must not be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph must not be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph.");
        }
        QueueLinkedList<Vertex<T>> queue = new QueueLinkedList<>();
        HashSet<Vertex<T>> visitedSet = new HashSet<>();
        ArrayList<Vertex<T>> searchResult = new ArrayList<>();
        visitedSet.add(start);
        queue.enqueue(start);
        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.dequeue();
            searchResult.addToBack(vertex);
            for (VertexDistance<T> neighbor: graph.getAdjList().get(vertex)) {
                if (!visitedSet.contains(neighbor.getVertex())) {
                    visitedSet.add(neighbor.getVertex());
                    queue.enqueue(neighbor.getVertex());
                }
            }
        }
        return searchResult;
    }

    public static <T> ArrayList<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex must not be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph must not be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph.");
        }
        HashSet<Vertex<T>> visitedSet = new HashSet<>();
        ArrayList<Vertex<T>> searchResult = new ArrayList<>();
        dfsHelper(start, graph, visitedSet, searchResult);
        return searchResult;
    }

    private static <T> void dfsHelper(Vertex<T> vertex, Graph<T> graph,
                                      HashSet<Vertex<T>> visitedSet,
                                      ArrayList<Vertex<T>> searchResult) {
        visitedSet.add(vertex);
        searchResult.addToBack(vertex);
        for (VertexDistance<T> neighbor: graph.getAdjList().get(vertex)) {
            if (!visitedSet.contains(neighbor.getVertex())) {
                dfsHelper(neighbor.getVertex(), graph, visitedSet, searchResult);
            }
        }
    }

    // Assumes non-negative weights
    public static <T> HashMap<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex must not be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph must not be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph.");
        }
        HashSet<Vertex<T>> visitedSet = new HashSet<>();
        HashMap<Vertex<T>, Integer> distanceMap = new HashMap<>();
        PriorityQueueMinHeap<VertexDistance<T>> priorityQueue = new PriorityQueueMinHeap<>();
        for (Vertex<T> vertex: graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        priorityQueue.add(new VertexDistance<>(start, 0));
        while (!priorityQueue.isEmpty() && visitedSet.size() < graph.getVertices().size()) {
            VertexDistance<T> current = priorityQueue.remove();
            if (!visitedSet.contains(current.getVertex())) {
                visitedSet.add(current.getVertex());
                distanceMap.put(current.getVertex(), current.getDistance());
                for (VertexDistance<T> neighbor: graph.getAdjList().get(current.getVertex())) {
                    if (!visitedSet.contains(neighbor.getVertex())) {
                        priorityQueue.add(new VertexDistance<>(neighbor.getVertex(),
                                current.getDistance() + neighbor.getDistance()));
                    }
                }
            }
        }
        return distanceMap;
    }

    public static <T> HashMap<Vertex<T>, Integer> bellmanFord(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex must not be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph must not be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph.");
        }
        HashMap<Vertex<T>, Integer> distanceMap = new HashMap<>();
        for (Vertex<T> vertex: graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        distanceMap.put(start, 0);
        for (int i = 0; i < graph.getVertices().size(); i++) {
            boolean changed = false;
            for (Edge<T> edge: graph.getEdges()) {
                if (distanceMap.get(edge.getU()) != Integer.MAX_VALUE) {
                    int newDistance = distanceMap.get(edge.getU()) + edge.getWeight();
                    if (newDistance < distanceMap.get(edge.getV())) {
                        distanceMap.put(edge.getV(), newDistance);
                        changed = true;
                    }
                }
            }
            if (!changed) {
                return distanceMap;
            }
        }
        for (Edge<T> edge: graph.getEdges()) {
            if (distanceMap.get(edge.getU()) != Integer.MAX_VALUE) {
                int newDistance = distanceMap.get(edge.getU()) + edge.getWeight();
                if (newDistance < distanceMap.get(edge.getV())) {
                    throw new IllegalArgumentException("The graph contains a negative cycle.");
                }
            }
        }
        return distanceMap;
    }

    // Assumes undirected graph
    public static <T> HashSet<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex must not be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The graph must not be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph.");
        }
        HashSet<Vertex<T>> visitedSet = new HashSet<>();
        HashSet<Edge<T>> edgeSet = new HashSet<>();
        PriorityQueueMinHeap<Edge<T>> priorityQueue = new PriorityQueueMinHeap<>();
        for (VertexDistance<T> neighbor: graph.getAdjList().get(start)) {
            priorityQueue.add(new Edge<>(start, neighbor.getVertex(), neighbor.getDistance()));
        }
        visitedSet.add(start);
        while (!priorityQueue.isEmpty() && visitedSet.size() < graph.getVertices().size()) {
            Edge<T> current = priorityQueue.remove();
            if (!visitedSet.contains(current.getV())) {
                visitedSet.add(current.getV());
                edgeSet.add(current);
                edgeSet.add(new Edge<>(current.getV(), current.getU(), current.getWeight()));
                for (VertexDistance<T> neighbor: graph.getAdjList().get(current.getV())) {
                    if (!visitedSet.contains(neighbor.getVertex())) {
                        priorityQueue.add(new Edge<>(current.getV(),
                                neighbor.getVertex(), neighbor.getDistance()));
                    }
                }
            }
        }
        if (edgeSet.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return edgeSet;
    }

    // Assumes undirected graph
    public static <T> HashSet<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph must not be null.");
        }
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>(graph.getVertices());
        HashSet<Edge<T>> edgeSet = new HashSet<>();
        PriorityQueueMinHeap<Edge<T>> priorityQueue = new PriorityQueueMinHeap<>();
        for (Edge<T> edge: graph.getEdges()) {
            priorityQueue.add(edge);
        }
        while (!priorityQueue.isEmpty() && edgeSet.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> current = priorityQueue.remove();
            if (!disjointSet.find(current.getU()).equals(disjointSet.find(current.getV()))) {
                edgeSet.add(current);
                edgeSet.add(new Edge<>(current.getV(), current.getU(), current.getWeight()));
                disjointSet.union(current.getU(), current.getV());
            }
        }
        return edgeSet;
    }
}
