package com.prakharmittal.graph;

public class Edge<T> implements Comparable<Edge<? super T>> {

    private final Vertex<T> u;
    private final Vertex<T> v;
    private final int weight;

    public Edge(Vertex<T> u, Vertex<T> v, int weight) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("The vertices of an edge must not be null.");
        }
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public Vertex<T> getU() {
        return u;
    }

    public Vertex<T> getV() {
        return v;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<?> e = (Edge<?>) o;
            return u.equals(e.u) && v.equals(e.v) && weight == e.weight;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return u.hashCode() ^ v.hashCode() ^ weight;
    }

    @Override
    public int compareTo(Edge<? super T> e) {
        return weight - e.weight;
    }

    @Override
    public String toString() {
        return u + "-" + v + "-" + weight;
    }
}
