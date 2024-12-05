package cat.urv.deim;

import java.util.Iterator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapPersones {
    private HashMapIndirecte <Integer, Persona> taula;

    public HashMapPersones(int dim) {
        taula = new HashMapIndirecte<Integer, Persona>(dim);
    }

    public HashMapPersones(int dim, String fit) {
        taula = new HashMapIndirecte<Integer, Persona>(dim);
        carregarDades(taula, fit);
    }

    public void inserir(Persona p) {
        taula.inserir(p.getId_persona(), p);
    }

    public void esborrar(int key) throws ElementNoTrobat {
        try {
            taula.esborrar(key);
        }
        catch(ElementNoTrobat e){
           throw e;
        }
    }

    public boolean buscar(int key) {
        return taula.buscar(key);
    }

    public boolean buscar(Persona p) {
        return taula.buscar(p.getId_persona());
    }

    public Persona consultar(int key)  throws ElementNoTrobat {
    try {
       Persona p = taula.consultar(key);
        return p;
    }
    catch(ElementNoTrobat e) {
       throw e;
    }
    }

    public int numElements() {
        return taula.numElements();
    }

    public int mida(){
        return taula.midaTaula();
    }

    public boolean esBuida() {
        return taula.esBuida();
    }

    public int[] obtenirIDs() {
        Object[] llistaAux = taula.obtenirClaus();
        int[] llistaIDs = new int[llistaAux.length];
        for(int i=0; i< llistaIDs.length; i++) {
            llistaIDs[i]= (Integer) llistaAux[i];
        }
        return llistaIDs;
    }


    public Persona[] elements() {
        Iterator<Persona> it = taula.iterator();
        Persona[] persones = new Persona[taula.numElements()];
        boolean trobat = false;
        int i = 0;

        while (it.hasNext()) {
            Persona p = it.next();
            int j = i;
            while (j != 0 && !trobat) {
                if (persones[j - 1].getId_persona()<p.getId_persona()) {
                    trobat = true;
                } else {
                    persones[j] = persones[j - 1];
                    j--;
                }
            }
            persones[j] = p;
            i++;
            trobat = false;
        }
        return persones;
    }

    public float factorCarrega() {
        return taula.factorCarrega();
    }

    public Persona[]personesPesInferior(int pes) {
        Iterator<Persona> llistaIt = taula.iterator();
        Persona[] persones = new Persona[taula.numElements()];
        int i=0;
        while(llistaIt.hasNext()) {
            Persona p = llistaIt.next();
            if(p != null && p.getPes() < pes ){
                persones[i] = p;
                i++;
            }
        }
        int j=0;
        Persona[] personesAmbPesInf = new Persona[i];
        while (j < i ) {
            personesAmbPesInf[j] = persones[j];
            j++;
        }
        return personesAmbPesInf;
    }

    private void carregarDades(IHashMap<Integer, Persona> taula, String filename ){
        try {
            BufferedReader f = new BufferedReader(new FileReader(filename));
                String linea;
                linea = f.readLine();
                while(linea != null)
                {
                    StringTokenizer coma = new StringTokenizer(linea,",");
                    int id_persona = Integer.parseInt(coma.nextToken());
                    int edat = Integer.parseInt(coma.nextToken());
                    String nom = coma.nextToken();
                    String cognom = coma.nextToken();
                    int alsada = Integer.parseInt(coma.nextToken());
                    int pes = Integer.parseInt(coma.nextToken());
                    Persona p = new Persona(id_persona, edat, nom, cognom, alsada, pes);

                    taula.inserir(id_persona, p);

                    linea = f.readLine();
                }
                f.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("L'arxiu d'entrada no existeix");
            }
            catch (IOException e) {
                System.out.println("S'ha produit un error en els arxius");
            }
    }
}
