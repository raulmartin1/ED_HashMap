package cat.urv.deim;

import java.util.Iterator;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;

public class LlistaNoOrdenada<E extends Comparable<E>> implements ILlistaGenerica<E> {

    private Node<E> fantasma;

    public LlistaNoOrdenada() {
        this.fantasma=new Node<>(null);
        this.fantasma.seguent=null;
    }

        private static class Node<E> {
            private E dada;
            private Node<E> seguent;

            public Node(E dada) {
                this.dada=dada;
                this.seguent=null;
            }
        }

        public void inserir(E e) {
            Node<E> actual = fantasma;
            Node<E> nouNode = new Node<>(e);
            while (actual.seguent != null) {
                actual = actual.seguent;
            }
            actual.seguent= nouNode;

        }

        public void esborrar(E e) throws ElementNoTrobat {
            Node<E> actual = fantasma;
            while (actual.seguent != null && actual.seguent.dada.compareTo(e) != 0) {
                actual = actual.seguent;
            }
            if (actual.seguent==null) {
                throw new ElementNoTrobat();
            }
            actual.seguent=actual.seguent.seguent;
        }

        public E consultar(int pos) throws PosicioForaRang {
            Node<E> actual = fantasma.seguent;
            if (pos <0) {
                throw new PosicioForaRang();
            }
            for (int i=0; actual != null && i<pos; i++) {
                actual=actual.seguent;
            }
            if(actual==null) {
                throw new PosicioForaRang();
            }
            return actual.dada;
        }

        public int buscar(E e) throws ElementNoTrobat {
            Node<E> actual = fantasma.seguent;
            int i;
            for(i=0; actual != null && actual.dada.compareTo(e) != 0; i++ ) {
                actual=actual.seguent;
            }
            if(actual==null) {
                throw new ElementNoTrobat();
            }
            return i;
        }

        public boolean existeix(E e) {
            try {
                buscar(e);
                return true;
            } catch (ElementNoTrobat e1) {
                return false;
            }
        }

        public boolean esBuida() {
            return fantasma.seguent == null;
        }

        public int numElements() {
        Node<E> actual=fantasma.seguent;
        int numElements=0;
        while(actual!=null) {
            numElements++;
            actual=actual.seguent;
        }
        return numElements;
        }

        public Iterator<E> iterator() {
        @SuppressWarnings("rawtypes")
        final Node primer = fantasma; // final y asi no se podra modificar la referencia

            return new Iterator<E>() {
                @SuppressWarnings("rawtypes")
                private Node actual = primer;

                public boolean hasNext() {
                    return actual.seguent != null;
                }

                @SuppressWarnings("unchecked")
                public E next() {
                    E element = (E) actual.seguent.dada;
                    actual = actual.seguent;
                    return element;
                }
            };
        }

}

