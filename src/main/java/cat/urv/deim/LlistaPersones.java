package cat.urv.deim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;

public class LlistaPersones {
    private static ILlistaGenerica<Persona> llista;
    // Constructor que crea una llista de persones buida del tipus especificat en el boolea (ordenada o no)
    public LlistaPersones(boolean ordenada) {
    if(ordenada) {
        llista=new LlistaOrdenada<>();
    }
    else {
        llista=new LlistaNoOrdenada<>();
    }
    }

    //Constructor que crea una llista del tipus especificat i hi carrega totes les dades del fitxer
    public LlistaPersones(boolean ordenada, String filename) {
    if(ordenada) {
        llista = new LlistaOrdenada<>();
        carregarDades(filename);
    }
    else {
        llista = new LlistaNoOrdenada<>();
        carregarDades(filename);
    }
    }

    //Afegeix una nova persona a la llista que tenim inicialitzada
    public void inserir(Persona p) {
        llista.inserir(p);
    }

    //Metode per a consultar una persona a partir de la seva posicio
    public Persona consultar(int pos) throws PosicioForaRang {
        return llista.consultar(pos);
    }

    //Metode per a saber si una persona existeix a l'estructura
    public boolean existeix(Persona p) {
        return llista.existeix(p);
    }

    //Metode per a esborrar una persona de l'estructura
    public void esborrar(Persona e) throws ElementNoTrobat {
        llista.esborrar(e);
    }

    //Metode que ens indica en quina posicio de la llista hi ha la persona que es passa per parametre
    public int posicioPersona(Persona persona) throws ElementNoTrobat {
        return llista.buscar(persona);

    }

    //Metode per a saber si la llista esta buida
    public boolean esBuida() {
        return llista.esBuida();
    }

    //Metode per a saber el nombre d'elements de la llista
    public int numElements() {
        return llista.numElements();
    }

    public Persona[] elements() {
        Persona[] llistaIt = new Persona[numElements()];
        Iterator<Persona> it = llista.iterator();
        int i=0;
        while(it.hasNext()) {
            llistaIt[i] = (Persona)it.next();
            i++;
        }
        return llistaIt;
    }

    public void carregarDades(String filename) {
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

                llista.inserir(p);

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
