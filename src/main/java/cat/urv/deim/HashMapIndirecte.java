package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import java.util.ArrayList;

import java.util.Iterator;


public class HashMapIndirecte<K, V> implements IHashMap<K, V> {
    private ArrayList<Node> taula;
    private int dim;
    private int numElems;

    public HashMapIndirecte(int dim) {
        this.dim = dim;
        this.taula = new ArrayList<>(dim);
        for(int i=0; i<dim; i++){
            taula.add(null);
        }
        this.numElems=0;
    }

    public class Node {
        public K clau;
        public V valor;
        public Node seguent;

        public Node(K clau, V valor) {
            this.clau=clau;
            this.valor=valor;
            this.seguent=null;
        }
    }

    public void inserir(K key, V value){
        int pos = getPos(key);
        Node actual = taula.get(pos);
        Node nodeHash = new Node(key, value);
        boolean trobat = false;
        if(taula.get(pos) == null) {
            taula.set(pos, nodeHash);
            numElems++;
        }
        else{
        if(actual.clau.equals(key)) trobat=true;
            while(actual.seguent != null && !trobat) {
                if(actual.clau.equals(key)) {
                    trobat = true;
                }
                else{
                    actual=actual.seguent;
                }
            }
            if(!trobat) {
                actual.seguent = nodeHash;
                numElems++;
            }
            //Si ya existe uno con esa clave actualzar valor de esa clave
            else{
                actual.valor=value;
            }
        }
        if(factorCarrega() > 0.75) {
            redimensionament();
        }
    }

    public V consultar(K key) throws ElementNoTrobat {
        int pos = getPos(key);
        Node actual = taula.get(pos);
        while(actual!=null) {
            if(actual.clau.equals(key)) {
                return actual.valor;
            }
            actual=actual.seguent;
        }
        throw new ElementNoTrobat();
    }

    public void esborrar(K key) throws ElementNoTrobat {
        int pos = getPos(key);
        Node actual = taula.get(pos);
        Node anterior = null;

        while(actual!=null ) {
            if(actual.clau.equals(key)) {
                if(anterior==null) {             //Si es el primero de la lista
                  taula.set(pos, actual.seguent);
                }
                else {
                    anterior.seguent = actual.seguent; //S'encadena el anterior amb el seguent
                }
            numElems--;
            return;                                 //Salir de la funcion esborrar cuando se elimine un elemento
            }
            anterior=actual;
            actual=actual.seguent;
        }
        throw new ElementNoTrobat();
    }

    public boolean buscar(K key) {
        int pos = getPos(key);
        Node actual = taula.get(pos);
        while(actual!=null) {
            if(actual.clau.equals(key)) {
                return true;
            }
            actual = actual.seguent;
        }
        return false;
    }

    public boolean esBuida() {
        return numElems==0;
    }

    public int numElements() {
        return numElems;
    }

    public K[] obtenirClaus() {
        ArrayList<K> claus = new ArrayList<>();
        for (int i =0; i< taula.size(); i++) {
            Node actual = taula.get(i);
            while (actual != null) {
                claus.add(actual.clau);
                actual = actual.seguent;
            }
        }

        @SuppressWarnings("unchecked")
        K[] taulaClaus = (K[]) claus.toArray(new Object[claus.size()]);
        return taulaClaus;
    }

    public float factorCarrega() {
        return (float) numElems/dim;
    }

    public int midaTaula() {
        return dim;
    }

    public Iterator<V> iterator() {
        ArrayList<V> llistaValors = new ArrayList<V>();
        Node actual;

        for(int i=0; i< midaTaula();  i++) {
            actual=taula.get(i);
        while(actual!=null) {
            llistaValors.add(actual.valor);
            actual=actual.seguent;
        }
        }

        return new Iterator<V>() {
            int pos=0;

            public boolean hasNext() {
                return pos < llistaValors.size();
            }

            public V next() {
                return llistaValors.get(pos++);
            }
        };
    }


    private int getPos(K key) {
        return  key.hashCode() % this.dim;
    }

    public void redimensionament() {
        int nouDim = dim*2;
        HashMapIndirecte<K, V> novaTaula = new HashMapIndirecte<>(nouDim);
        Node actual = null;
        for(int i=0; i<numElems; i++) {
            actual=taula.get(i);
            while(actual!=null) {
                novaTaula.inserir(actual.clau, actual.valor);
                actual=actual.seguent;
            }
        }
        this.taula=novaTaula.taula;
        this.dim=novaTaula.dim;

        }


}
