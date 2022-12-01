package algo.filtre;
import java.io.*;
import algo.tableRelationnelle.*;

public class Filtre implements FilenameFilter {
    File f;
    String nom;
    TableRelationnelle table;
    
    public Filtre(TableRelationnelle table ,File f ,String nom) {
        this.f = f;
        this.nom = nom;
        //System.out.println(nom);
        this.table = table;
    }

    public boolean accept(File dir, String name) {
        //System.out.println( this.nom + " " + name);
        //System.out.println((table.getPath()+name) + " " + nom);
        if((name).compareToIgnoreCase(this.nom) == 0) {
            //System.out.println(dir.getPath());
            return true;
        }
    return false;
    }
}