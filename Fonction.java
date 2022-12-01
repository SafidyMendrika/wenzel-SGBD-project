package algo.fonction;
import java.util.Arrays;
import java.util.Vector;
import javax.print.DocFlavor.STRING;
import java.lang.Object.*;
import java.lang.reflect.*;
import java.lang.reflect.InvocationTargetException;
import java.io.*;
import algo.tableRelationnelle.*;
public class Fonction {

    //-----------------------------OUTILS----------------------------------

    String getOperation(String cond) {
        String[] split = cond.split("\\ ");
    return split[1];
    }

    String getType(Object[] lo, String field) {
        String val = new String();
        String attr = lo[0].toString();
        String[] fieldsAndTypes = attr.split("\\,");
        for(int i = 0; i<lo.length; i++) {
            for(int j = 0; j<fieldsAndTypes.length  ; j++) {
                String[] fields = fieldsAndTypes[i].split("\\ ");
                if(field.compareToIgnoreCase(fields[1]) == 0) {
                    return fields[0];
                }
            }
        }
    return val;
    }
    

    Vector indice(Object[] lo, String field) {
        Vector v = new Vector();
        String[] attr_select = field.split("\\,");                        //azo avy amle syntaxe
        String[] attr = lo[0].toString().split("\\,");                      //azo avy any anaty fichier
        for(int i = 0; i<attr.length; i++) {
            String[] fields = attr[i].split("\\ ");
            for (int j = 0; j < attr_select.length; j++) {
                // field[0] : type anle field
                // field[1] : nom anle field
                if(fields[1].compareToIgnoreCase(attr_select[j]) == 0) {
                    //System.out.println(i);
                    v.add(i);
                }
            }
        }
    return v;    
    }

    Vector indiceDifferentOf(Object[] lo, String field) {
        Vector v = new Vector();
        String[] attr_select = field.split("\\,");                        //azo avy amle syntaxe
        String[] attr = lo[0].toString().split("\\,");                      //azo avy any anaty fichier
        for(int i = 0; i<attr.length; i++) {
            String[] fields = attr[i].split("\\ ");
            for (int j = 0; j < attr_select.length; j++) {
                // field[0] : type anle field
                // field[1] : nom anle field
                if(fields[1].compareToIgnoreCase(attr_select[j]) != 0) {
                    //System.out.println(i);
                    v.add(i);
                }
            }
        }
    return v;    
    }

    String pop(String chaine, Vector indice) {
        String[] split = chaine.split("\\,");
        String[] valuesSelectione = new String[indice.size()];
        String val = "";
        for(int i = 0; i<split.length; i++) {
            for (int j = 0; j < indice.size(); j++) {
                if( i != (int)(indice.get(j)) ){
                    valuesSelectione[j] = split[i];
                    val = val + valuesSelectione[j] + ",";
                }
            }
        }
        int len = val.length();
        val = val.substring(0, len-1);
        //System.out.println(val);
    return val;
    }

    String addI(String chaine, Vector indice) {
        String[] split = chaine.split("\\,");
        String[] valuesSelectione = new String[indice.size()];
        String val = "";
        for(int i = 0; i<split.length; i++) {
            for (int j = 0; j < indice.size(); j++) {
                if( i == (int)(indice.get(j)) ){
                    valuesSelectione[j] = split[i];
                    val = val + valuesSelectione[j] + ",";
                }
            }
        }
        int len = val.length();
        val = val.substring(0, len-1);
        //System.out.println(val);
    return val;
    }

    public void aff(TableRelationnelle table) {
        Object[] lo = table.getDonnee();
        String attr = lo[0].toString();
        String[] fieldsAndTypes = attr.split("\\,");
        System.out.println(table.getNom());
        for(int j = 1; j < lo.length; j++) {
            System.out.println("---------------------------");
            String[] data = lo[j].toString().split("\\,");
            for(int i = 0; i < fieldsAndTypes.length; i++) {
                String[] fields = fieldsAndTypes[i].split("\\ ");
                System.out.println( fields[1] + " : " + data[i] );
            }
        }
        
    }

    

    public void createFile(String path ,String nom, String attribute) {
        //System.out.println(emplacement.getAbsolutePath()+"\\\\"+nom);
        File creation = new File(path+nom);
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(creation, true) )) 
        {   
            writer.write(attribute);
            writer.newLine();
            writer.close();
        
        }
        catch(IOException a) 
        {   
            System.out.println(a.getMessage());
            a.printStackTrace();
        }
    }

    public void insertInto(String path, String nom, String values) {
        File creation = new File(path+nom);
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(creation, true) )) 
        {   
            writer.write(values);
            writer.newLine();
            writer.close();
        }
        catch(IOException a) 
        {   
            System.out.println(a.getMessage());
            a.printStackTrace();
        }
    }

    public String readFirstLine(String nomFichier) {
        File fichier=new File(nomFichier);
        String val = new String();
        //buffer --> le mpanoratra
        try(BufferedReader reader = new BufferedReader(new FileReader(fichier) )) 
        {   
            val = reader.readLine();
            //System.out.println(b);
        }
        catch(IOException a)
        {
            a.printStackTrace();
        }
        return val;
    }



    //---------------------------------OPERATION--------------------------------
    String getTsyItovizany(String champ1, String champ2) {
        String[] split = champ1.split("\\,");
        for(int i = 0; i< split.length; i++){
            if(split[i].compareToIgnoreCase(champ2) != 0) {
                return split[i];
            }
        }
    return new String();
    }
    
    // String getTsyItovizany(String champ1, String champ2) {
    //     String[] split = champ1.split("\\,");
    //     for(int i = 0; i< split.length; i++){
    //         if(split[i].compareToIgnoreCase(champ2) != 0) {
    //             return split[i];
    //         }
    //     }
    // return new String();
    // }

    public TableRelationnelle division(TableRelationnelle table1, TableRelationnelle table2, String champ1, String champ2){
        String tsymitovy = getTsyItovizany(champ1, champ2);
        //System.out.println(tsymitovy);
        TableRelationnelle r1 = select(table1, tsymitovy);
        TableRelationnelle r2 = select(table2, champ2);
        TableRelationnelle r3 = cartesien(r1, r2);
        // Vector v = new Vector();
        // v.add(table1);
        // v.add(r2);
        // TableRelationnelle tmp = jointureMultiple(v);
        //TableRelationnelle r4 = difference(r3, tmp);
        TableRelationnelle r4 = difference(r3, table1);
        TableRelationnelle r5 = select(r4, tsymitovy);
        TableRelationnelle r6 = difference(r1,r5);
        //System.out.println("tafa");
    return manalaDoublon(r6);
    }

    Object[] getTsyMitovy(Object[] cartesien) {
        Vector indDoublon = new Vector();
        String all = cartesien[0].toString();
        //System.out.println(cartesien.length);
        String[] split = all.split("\\,");
        Vector val = new Vector();
        int i = 0;
        while(i<cartesien.length) {
            val.add(cartesien[i]);
            //System.out.println(cartesien[i]);
            for (int j = 0; j < cartesien.length; j++) {
                if(j !=i ) {
                    if(val.contains(cartesien[j]) == true) {
                        System.out.println(cartesien[j]);
                        indDoublon.add(cartesien[j]);
                    }
                }
            }
            if(val.size() == 1 && cartesien.length != 1) {
                val.removeAllElements();
            }
            i++;
        }
        //indDoublon.remove( indDoublon );
    return project( indDoublon.toArray() );
    }

    boolean verifAll(Object obj1, Object[] obj) {
        for(int i = 0; i<obj.length; i++) {
            //System.out.println(obj1 + " " + obj[i].toString());
            if(obj1.toString().compareToIgnoreCase(obj[i].toString()) == 0) {
                return true;
            }
        }
    
    return false;
    }

    public TableRelationnelle difference(TableRelationnelle table1, TableRelationnelle table2) {
        Object[] obj1 = table1.getDonnee();
        Object[] obj2 = table2.getDonnee();
        Object[] intersect = intersect(obj1, obj2);
        Vector val = new Vector();
        val.add(obj1[0]);
        //System.out.println(obj1.length);
        for(int i = 1; i< obj1.length ; i++) {
            if( verifAll(obj1[i], intersect) == false ) {
                //System.out.println(obj1[i]);
                val.add(obj1[i]);
            }
        }
    return new TableRelationnelle(table1.getNom(), val.toArray() ); 
    }

    Object[] union(Object[] obj1, Object[] obj2 ) {
        Vector val = new Vector();
        val.add(obj1[0]);
        for(int i = 1; i < obj1.length; i++) {
            //System.out.println(obj1[i]);
            val.add(obj1[i]);
        }
        for(int j = 1; j < obj2.length; j++) {
            //System.out.println(obj2[j]);
            val.add(obj2[j]);
        }
    return val.toArray();
    }

    Object[] intersect(Object[] obj1, Object[] obj2) {
        Vector v = new Vector();
        for (int i = 0; i < obj1.length; i++) {
            for (int j = 0; j < obj2.length; j++) {
                if(obj1[i].toString().compareToIgnoreCase(obj2[j].toString()) == 0) {
                    //System.out.println(obj1[i] + " " + obj2[j]);
                    v.add(obj1[i]);
                }
            }
        }
        Object[] val = v.toArray();
        val = project(val);
        return val;
    }

    Object[] intsersectMuliple( Vector allTab) {
        if(allTab.size() == 1) {
            //System.out.println("huhu");
            return (Object[])(allTab.get(0));
        }
        int i = 1;
        Object[] val = (Object[])(allTab.get(0));
        while(i<allTab.size()) {
            Object[] obj1 = (Object[])(allTab.get(i));
            // for (int j = 0; j < obj1.length; j++) {
            //     System.out.println(obj1[j]);
            // }
            val = intersect(obj1, val);
            i++;
        }

    return val;
    }

    

    TableRelationnelle cartesien(TableRelationnelle table1, TableRelationnelle table2) {
        Object[] obj1 = table1.getDonnee();
        Object[] obj2 = table2.getDonnee();
        Vector v = new Vector();
        v.add( obj1[0].toString() + ","+obj2[0].toString() );
        for(int i = 1; i<obj1.length; i++) {
            for (int j = 1; j < obj2.length; j++) {
                v.add(obj1[i].toString() + ","+ obj2[j].toString() );
            }
        }
    return new TableRelationnelle( table1.getNom() +" cart " + table2.getNom() , v.toArray()) ;    
    }

    Vector getDoublon(Object[] obj1, Object[] obj2) {
        Vector doublon = new Vector();
        //Object[] cartesien = cartesien(obj1, obj2);
        String[] title_obj1 = obj1[0].toString().split("\\,");
        String[] title_obj2 = obj2[0].toString().split("\\,");
        for (int i = 0; i < title_obj1.length; i++) {
            for (int j = 0; j < title_obj2.length; j++) {
                if(title_obj1[i].compareToIgnoreCase(title_obj2[j]) == 0) {
                    doublon.add(title_obj1[i]);
                }
            }
        }   
    return doublon; 
    }

    Vector getIndDoublon(Object[] obj1, Object[] obj2) {
        Vector indDoublon = new Vector();
        //Object[] cartesien = cartesien(obj1, obj2);
        String[] title_obj1 = obj1[0].toString().split("\\,");
        String[] title_obj2 = obj2[0].toString().split("\\,");
        for (int i = 0; i < title_obj1.length; i++) {
            for (int j = 0; j < title_obj2.length; j++) {
                if(title_obj1[i].compareToIgnoreCase(title_obj2[j]) == 0) {
                    int[] tmp = new int[2];
                    tmp[0] = i;
                    tmp[1] = title_obj1.length + j;
                    indDoublon.add(tmp);
                }
            }
        }   
    return indDoublon; 
    }
 
    Vector getIndDoublonToDelete(Object[] obj1, Object[] obj2) {
        Vector indDoublon = new Vector();
        //Object[] cartesien = cartesien(obj1, obj2);
        String[] title_obj1 = obj1[0].toString().split("\\,");
        String[] title_obj2 = obj2[0].toString().split("\\,");
        // System.out.println(obj1[0]);
        // System.out.println(obj2[0]);
        for (int i = 0; i < title_obj1.length; i++) {
            for (int j = 0; j < title_obj2.length; j++) {
                if(title_obj1[i].compareToIgnoreCase(title_obj2[j]) == 0) {
                    //System.out.println(title_obj1.length + j + title_obj1[i] + " " + title_obj2[j]);
                    indDoublon.add(title_obj1.length + j);
                }
            }
        }   
    return indDoublon; 
    }

    Object[] getMitovy(Object[] cartesien) {
        Vector indDoublon = new Vector();
        String all = cartesien[0].toString();
        //System.out.println(all);
        String[] split = all.split("\\,");
        Vector val = new Vector();
        int i = 0;
        while(i<split.length) {
            val.add(split[i]);
            for (int j = 0; j < split.length; j++) {
                if(j !=i ) {
                    if(val.contains(split[j]) == true) {
                        //System.out.println(split[j]);
                        indDoublon.add(split[j]);
                    }
                }
            }
            if(val.size() == 1) {
                val.removeAllElements();
            }
            i++;
        }
        //indDoublon.remove( indDoublon );
    return project( indDoublon.toArray() );
    }

    Vector getInToDelete(Object[] cartesien) {
        Vector indDoublon = new Vector();
        String all = cartesien[0].toString();
        //System.out.println(all);
        String[] split = all.split("\\,");
        Object[] mitovy = getMitovy(cartesien);
        int count = 0;
        for (int i = 0; i < mitovy.length; i++) {
            for (int j = 0; j < split.length; j++) {
                //System.out.println(mitovy[i] + " " + split[j]);
                if(mitovy[i].toString().compareToIgnoreCase(split[j]) == 0) {
                    count ++;
                    //System.out.println(count + " "+split[j] + " " + j);
                    if(count > 1 ) {
                        //System.out.println(split[j] + " " + j);
                        indDoublon.add(j);
                    }
                }
                if(j == split.length - 1) {
                    count = 0;
                }
            }
        }
        
    return indDoublon; 
    }

    Vector getIndNotToDelete(Object[] cartesien) {
        Vector indDoublon = new Vector();
        String all = cartesien[0].toString();
        String[] split = all.split("\\,");
        Vector indToDelete = getInToDelete(cartesien);
        for (int i = 0; i < indToDelete.size(); i++) {
            for (int j = 0; j < split.length; j++) {
                if(indToDelete.contains(j) == false) {
                    indDoublon.add(j);
                }
            }
        }
        Object[] lo = project(indDoublon.toArray());
        indDoublon.removeAllElements();
        for (int i = 0; i < lo.length; i++) {
            indDoublon.add(lo[i]);
        }
    return indDoublon;
    }

    
    boolean verifJoin(String values, Vector indDoublon) {
        String[] split = values.split("\\,");
        for (int i = 0; i < split.length; i++) {
            for (int j = 0; j < indDoublon.size(); j++) {
                int[] tmp = (int[])indDoublon.get(j);
                if( split[tmp[0]].compareToIgnoreCase(split[tmp[1]]) == 0 ) {
                    return true;
                }
            }
        }
    return false;
    }

    public TableRelationnelle jointure(TableRelationnelle table1, TableRelationnelle table2) {
        Vector v = new Vector();
        Object[] obj1 = table1.getDonnee();
        Object[] obj2 = table2.getDonnee();
        Object[] cartesien = cartesien(table1, table2).getDonnee();
        v.add(cartesien[0]);
        Vector indDoublon = getIndDoublon(obj1, obj2);
        //Vector indDoublonToDelete = getIndDoublonToDelete(obj1, obj2);
        for (int i = 1; i < cartesien.length; i++) {
            if( verifJoin(cartesien[i].toString(), indDoublon) == true ) {
                v.add(cartesien[i]);
            }
        }
        Object[] allresult = v.toArray();
        //allresult = selectWithOut(allresult, indDoublonToDelete);
    return new TableRelationnelle( table1.getNom() + " join " + table2.getNom(), allresult);
    }

    public TableRelationnelle jointureMultiple(Vector allTab ) {
        if(allTab.size() == 1) {
            return (TableRelationnelle)(allTab.get(0));
        }
        int i = allTab.size() - 1;
        TableRelationnelle val = (TableRelationnelle)(allTab.get(allTab.size() - 1));
        //System.out.println(val[0]);
        while(i>=1) {
            TableRelationnelle obj1 = (TableRelationnelle)(allTab.get(i-1));
            val = jointure(obj1, val);
            //System.out.println(val[0]);
            i--;
        }
        Vector indDelete = getIndNotToDelete(val.getDonnee());
        // for (int j = 0; j < indDelete.size(); j++) {
        //     System.out.println(indDelete.get(j));
        // }
        val = selectWithOut(val, indDelete);
    return val;
    }

    // public Object[] jointureMultiple(Vector allTab ) {
    //     if(allTab.size() == 1) {
    //         return (Object[])(allTab.get(0));
    //     }
    //     int i = 1;
    //     Object[] val = (Object[])(allTab.get(0));
    //     while(i<allTab.size()) {
    //         Object[] obj1 = (Object[])(allTab.get(i));
    //         val = jointure(obj1, val);
    //         i++;
    //     }
    //     Vector indDelete = getIndDoulonToDelete(val);
    // return val;
    // }

    public Object[] project(Object[] obj) {
        Arrays.sort(obj);
        Vector v = new Vector();
        v.add(obj[0]);
        for(int i = 1; i<obj.length; i++) {
            if(!v.contains(obj[i])) {
                v.add(obj[i]);
            }
        }
        Object[] val = v.toArray();
    return val;
    }

    public TableRelationnelle manalaDoublon(TableRelationnelle table) {
        Object[] obj = table.getDonnee();
        Vector v = new Vector();
        v.add(obj[0]);
        for(int i = 1; i<obj.length; i++) {
            if(!v.contains(obj[i])) {
                v.add(obj[i]);
            }
        }
        Object[] val = v.toArray();
    return new TableRelationnelle(table.getNom(), val);
    }

    // public Object[] select(Object[] obj,Object condition) {
    //     Vector v = new Vector();
    //     for (int i = 0; i < obj.length; i++) {
    //         if(obj[i] == condition) {
    //             v.add(obj[i]);
    //         }
    //     }
    //     Object[] val = v.toArray();
    //     return val;
    // }

    Object[] indSelectOneCond(TableRelationnelle table, String cond) {
        Vector val = new Vector();
        Object[] lo = table.getDonnee();
        String[] split = cond.split("\\ ");
        String champ = split[0];
        String operation = split[1];
        String valeur_cond = split[2];
        //System.out.println(champ + " " + operation + " " + valeur_cond);
        Object[] tmp = select(table, champ).getDonnee();
        for (int i = 1; i < tmp.length; i++) {
            if(operation.compareToIgnoreCase("=") == 0) {                  
                if( valeur_cond.compareToIgnoreCase(tmp[i].toString()) == 0 ) {
                    val.add(i);
                }
            }
            if(operation.compareToIgnoreCase("!=") == 0) {
                if( valeur_cond.compareToIgnoreCase(tmp[i].toString()) != 0 ) {
                    val.add(i);
                }
            }
            if(operation.compareToIgnoreCase("<=") == 0) {
                if(getType(lo, champ).compareToIgnoreCase("string") != 0) {
                    if(  Double.parseDouble(tmp[i].toString()) <= Double.parseDouble(valeur_cond) ) {
                        val.add(i);
                    }
                }
            }
            if(operation.compareToIgnoreCase(">=") == 0) {
                if(getType(lo, champ).compareToIgnoreCase("string") != 0) {
                    if(  Double.parseDouble(tmp[i].toString()) >= Double.parseDouble(valeur_cond) ) {
                        val.add(i);
                    }
                }
            }
            if(operation.compareToIgnoreCase("<") == 0) {
                if(getType(lo, champ).compareToIgnoreCase("string") != 0) {
                    if(  Double.parseDouble(tmp[i].toString()) < Double.parseDouble(valeur_cond) ) {
                        val.add(i);
                    }
                }
            }
            if(operation.compareToIgnoreCase(">") == 0) {
                if(getType(lo, champ).compareToIgnoreCase("string") != 0) {
                    if(  Double.parseDouble(tmp[i].toString()) > Double.parseDouble(valeur_cond) ) {
                        val.add(i);
                    }
                }
            }
            if(operation.compareToIgnoreCase("misy") == 0) {
                if( tmp[i].toString().contains(valeur_cond) == true ) {
                    val.add(i);
                }
            }

        }
        //System.out.println(val.size());
    return project(val.toArray());
    }

    Vector indSelectCond(TableRelationnelle lo, String cond) {
        Vector val = new Vector();
        String[] allCond = cond.split("\\,");
        for(int i = 0; i < allCond.length; i++) {
            val.add( indSelectOneCond(lo, allCond[i]) );
        }
    return val;
    }

    public TableRelationnelle selectCond(TableRelationnelle table, String cond) {
        Vector v =  new Vector();
        Object[] lo = table.getDonnee();
        v.add(lo[0]);
        Vector tab_tab_indce = indSelectCond(table, cond); 
        Object[] tab_indice = intsersectMuliple(tab_tab_indce);
        for (int i = 0; i < tab_indice.length ; i++) {
            //System.out.println( tab_indice[i] ) ;
            v.add( lo[ (int)tab_indice[i] ]);
        }

    return new TableRelationnelle(table.getNom(), v.toArray()); 
    }

    public TableRelationnelle selectCond(TableRelationnelle table, String field ,String cond) {
        TableRelationnelle allValues = selectCond(table, cond);
    return select(allValues, field);
    }

    public TableRelationnelle select(TableRelationnelle table, String field) {
        Vector v =  new Vector();
        Object[] lo = table.getDonnee();
        Vector indice = indice(lo, field); 
        for (int i = 0; i < lo.length; i++) {
            v.add( addI(lo[i].toString(), indice) );
        }
    return new TableRelationnelle(table.getNom(), v.toArray());     
    }

    public TableRelationnelle selectWithOut(TableRelationnelle table, String field) {
        Vector v =  new Vector();
        Object[] lo = table.getDonnee();
        Vector indice = indiceDifferentOf(lo, field); 
        for (int i = 0; i < lo.length; i++) {
            v.add( pop(lo[i].toString(), indice) );
        }
    return new TableRelationnelle(table.getNom(), v.toArray());     
    }

    public TableRelationnelle selectWithOut(TableRelationnelle table, Vector indice) {
        Vector v =  new Vector();
        Object[] lo = table.getDonnee();
        //Vector indice = indiceDifferentOf(lo, field); 
        for (int i = 0; i < lo.length; i++) {
            v.add( addI(lo[i].toString(), indice) );
        }
    return new TableRelationnelle(table.getNom(), v.toArray());     
    }


    public TableRelationnelle select(TableRelationnelle table, Vector indice) {
        Object[] lo = table.getDonnee();
        Vector v =  new Vector();
        for (int i = 0; i < lo.length; i++) {
            v.add( addI(lo[i].toString(), indice) );
        }
    return new TableRelationnelle(table.getNom(), v.toArray());    
    }
}