package com.prakharmittal.graph;

public class Vertex<T> {

    private final T data;

    public Vertex(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vertex<?>) {
            Vertex<?> v = (Vertex<?>) o;
            return data.equals(v.data);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
