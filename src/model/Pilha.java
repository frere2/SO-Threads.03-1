package model;

public class Pilha {
    No topo;

    public Pilha() {
        topo = null;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public int push(int n) {
        No ntopo = new No();
        ntopo.valor = n;
        ntopo.proximo = topo;
        topo = ntopo;
        return topo.valor;
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Pilha vazia!");
        }
        int topv = topo.valor;
        topo = topo.proximo;
        return topv;
    }

    public int top() {
        return topo.valor;
    }

    public int size() {
        int counter = 0;
        while (topo.proximo != null) {
            counter++;
        }
        return counter;
    }
}
