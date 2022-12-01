package algo.langage;
import algo.fonction.*;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Vector;
import java.lang.Object.*;
import java.lang.reflect.InvocationTargetException;
import java.io.*;
import algo.tableRelationnelle.*;

public class Langage {
    String[] vocabulaire = {"alaina", "*","ao_@", "ka",",","ary"};
    String creation = "mamorona table";
    String insertion = "ampidiro ao_@";
    String jointure = "atambatra_@";
    String[] type = {"int","float","double","String","date"};
    String[] opertaion = {"=","!=","<=",">=","<",">","misy"};
    Fonction f = new Fonction();
    TableRelationnelle table = new TableRelationnelle();

    public Langage() {
    }
   
    //---------------------------------------------OUTILS------------------------------------------------
    boolean containsChar(String n) {
        String maj = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String min = "abcdefghijklmnopqrstuvwxyz";
        char[] maj_char = maj.toCharArray();
        char[] min_char = min.toCharArray();
        for(int i = 0 ; i<maj_char.length ; i++) {
            if( n.contains( String.valueOf( maj_char[i]) ) == true || n.contains( String.valueOf(min_char[i]) )== true ) {
                return true;
            }
        }
    return false;
    }
    
    boolean isInEmplacement(String nom_table) {
        
        String[] list_name = table.getNomFiles();
        if(list_name == null) {
            return false;
        }
        for (int i = 0; i < list_name.length; i++) {
            if(list_name[i].compareToIgnoreCase(nom_table) == 0) {
                //System.out.println(list_name[i]);
                return true;
            }
        }
    return false;
    }
    
    String getNomTable(String request) {
        String[] split = request.split("\\ ");
    return split[3];
    }

    boolean isInTable(String nom_table) {
        String[] nomFiles = table.getNomFiles();
        for(int i = 0; i<nomFiles.length; i++) {
            //System.out.println(nom_table + " " + nomFiles[i]);
            if(nom_table.compareToIgnoreCase(nomFiles[i]) == 0) {
                return true;
            }
        }
    return false;
    }

    boolean isInFields(String nom_table, String field) {
        if(isInTable(nom_table) == false) {
            return false;
        }

        File table_trouver = table.getFile(nom_table);
        String allField = f.readFirstLine(table.getPath()+nom_table);
        //System.out.println( allField);
        String[] fields = allField.split("\\,");
         
        for(int i = 0; i<fields.length ; i++ ) {
            String[] split = fields[i].split("\\ ");
            //System.out.println( (split[1]) + " " + field);
            if(field.compareToIgnoreCase(split[1]) == 0) {
                //System.out.println( (split[1]) + " " + field);
                return true;
            }
        }
    return false;
    }

    boolean isInFields(String[] nom_table, String field) {
        File[] table_trouver = new File[nom_table.length];
        for (int i = 0; i < table_trouver.length; i++) {
            table_trouver[i] = table.getFile(nom_table[i]);
            String allField = f.readFirstLine(table.getPath()+nom_table[i]);
            String[] fields = allField.split("\\,");
            for(int j = 0; j<fields.length ; j++ ) {
                String[] split = fields[j].split("\\ ");
                //System.out.println( (split[1]) + " " + field);
                if(field.compareToIgnoreCase(split[1]) == 0) {
                    return true;
                }
            }
        }
        
    return false;
    }

    boolean areInFields(String[] nom_table, String field) {
        String[] split = field.split("\\,");
        int count = split.length;
        Vector v = new Vector();
        for(int i = 0; i< split.length; i++) {
            //System.out.println(isInFields(nom_table, split[i]) + " sdf");
            if(isInFields(nom_table, split[i]) == true) {
                v.add(split[i]);
            }
        }
        if(v.size()  == count) {
            //System.out.println("tafa "+count);
            return true;
        }
    return false;
    }

    boolean areInFields(String nom_table, String field) {
        String[] split = field.split("\\,");
        int count = split.length;
        Vector v = new Vector();
        for(int i = 0; i< split.length; i++) {
            //System.out.println(isInFields(nom_table, split[i]) + " sdf" );
            if(isInFields(nom_table, split[i]) == true) {
                //System.out.println(split[i]);
                v.add(split[i]);
            }
        }
        if(v.size()  == count) {
            //System.out.println("tafa "+count);
            return true;
        }
    return false;
    }

    //----------------------------------------------------CREATION TABLE---------------------------------------
    String getValuesCreation(String request) throws Exception {
        if(request.contains("(") == false || request.contains(")")== false ) {
            throw new Exception("Check your round-brackets in creation values");
        }
        int ind1 = request.indexOf("(");
        int ind2 = request.indexOf(")");
        String inBrackets = request.substring(ind1+2,ind2-1);
    return inBrackets;
    }


    boolean checkOneValuesCreation(String value) {
        String[] split = value.split("\\ ");
        for(int j = 0; j < type.length; j++) {
            if( split[0].compareToIgnoreCase(type[j]) == 0 ) {
                return true;
            }
        }
    return false;  
    }

    boolean checkAllValuesCreation(String[] values) throws Exception {
        int count = 0;
        for(int i = 0; i < values.length; i++) {
            if(checkOneValuesCreation(values[i]) == true) {
                count ++;
            }
            else{
                throw new Exception("Type "+ values[i] + " undeterminated");
            }
        }
        if(count == values.length) {
            return true;
        }
    return false;
    }

    boolean checkSyntaxeCreation(String request) throws Exception {
        try {
            String[] split = request.split("\\ ");
            if( (split[0]+" "+split[1]).compareToIgnoreCase(creation) != 0) {
                //System.out.println((split[0]+" "+split[1]) + "/haha/" +creation);
                throw new Exception("command of creation " + (split[0]+" "+split[1]) +" does not exist");
            }
            String nom_table = split[2];
            //System.out.println(nom_table);
            String allValues = getValuesCreation(request);
            String[] values = allValues.split("\\,");
            if(checkAllValuesCreation(values) == true) {
                if(isInEmplacement(nom_table) == false) {
                    return true;
                }
                else if(isInEmplacement(nom_table) == true) {
                    throw new  Exception("Table "+ nom_table+ " already exists");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;
    }

    public void executeCreation(String request) throws Exception {
        try {
            if(checkSyntaxeCreation(request) == true) {
                String[] split = request.split("\\ ");
                String nom_table = split[2];
                String attribute = getValuesCreation(request);
                String path = table.getPath();
                f.createFile(path, nom_table , attribute);
            }
        } catch (Exception e) {
            System.out.println("Syntax error "+ e.getMessage());
        }
    }

    //--------------------------------------------INSERTION------------------------------------
    String getValuesInsertion(String request) throws Exception {
        if(request.contains("(") == false || request.contains(")")== false ) {
            throw new Exception("Check your round-brackets in insertion values");
        }
        int ind1 = request.indexOf("(");
        int ind2 = request.indexOf(")");
        String inBrackets = request.substring(ind1+2,ind2-1);
    return inBrackets;
    }

    String[] valuesInInsertion(String request) throws Exception {
        return getValuesInsertion(request).split("\\,");
    }

    boolean checkAllValuesInsertion(String[] values, String nom_table) throws Exception {
        if(isInEmplacement(nom_table) == false) {
            throw new  Exception("Table "+ nom_table+ " does not exist");
        }
        String attribut = f.readFirstLine(table.getPath()+nom_table);
        String[] valuesInFile = attribut.split("\\,");
        if(values.length != valuesInFile.length) {
            throw new Exception("Inapropriate number of field");
        }
        for(int i = 0; i<values.length; i++) {
            String[] tmp = valuesInFile[i].split("\\ ");
            String typeOfValue = tmp[0];
            if(typeOfValue.compareToIgnoreCase("String") != 0) {
                if( containsChar(values[i]) == true ) {
                    throw new Exception(values[i] + " is not a number");
                }
            }
        }
       
    return true;
    }

    boolean checkSyntaxeInsertion(String request) throws Exception{
        try {
            String[] split = request.split("\\ ");
            if( (split[0]+" "+split[1]).compareToIgnoreCase(insertion) != 0) {
                //System.out.println((split[0]+" "+split[1]) + "/haha/" +creation);
                throw new Exception("command of insertion " + (split[0]+" "+split[1]) +" does not exist");
            }
            String nom_table = split[2];
            //System.out.println(nom_table);
            String allValues = getValuesInsertion(request);
            String[] values = valuesInInsertion(request);
            if(checkAllValuesInsertion(values, nom_table) == true) {
                if(isInEmplacement(nom_table) == true) {
                    return true;
                }
                else if(isInEmplacement(nom_table) == false) {
                    throw new  Exception("Table "+ nom_table+ " does not exist");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;
    }

    public void executeInsertion(String request) throws Exception {
        try {
            if(checkSyntaxeInsertion(request) == true) {
                String[] split = request.split("\\ ");
                String nom_table = split[2];
                String attribute = getValuesInsertion(request);
                String path = table.getPath();
                f.insertInto(path, nom_table , attribute);
            }
        } catch (Exception e) {
            System.out.println("Syntax error "+ e.getMessage());
        }
    }
 
    //-----------------------------------------SELECTION----------------------------------------
    String getAllCondition(String request) throws Exception {
        
        if(request.contains("[") == false || request.contains("]")== false ) {
            throw new Exception("Check your brackets in condition");
        }
        int ind1 = request.indexOf("[");
        int ind2 = request.indexOf("]");
        String inBrackets = request.substring(ind1+2,ind2-1);
        //System.out.println(inBrackets);
    return inBrackets;
    }

    String[] conditions(String allCond) throws Exception {
        //System.out.println("hahahaha" + request);
        //String allCond = getAllCondition(request);
        //System.out.println(allCond);
        String[] split = allCond.split("\\,");
        //System.out.println(split[0]);
    return split;
    }

    boolean checkOperator(String condition) {
        int count = 0;
        for(int i = 0; i<opertaion.length ; i++) {
            if(condition.contains(opertaion[i]) == true) {
                count ++;
            }
        }
        if(count == 0) {
            return false;
        }
    return true;
    }

    String getOperator(String condition) throws Exception {
        //System.out.println("mety");
        if(checkOperator(condition) == false) {
            throw new Exception("Syntax error in condition");
        }
        String operator = new String();
        String[] split = condition.split("\\ ");
        for(int i = 0; i<opertaion.length ; i++) {
            if(split[1].compareToIgnoreCase(opertaion[i]) == 0) {
                operator = opertaion[i];
            }
        }
    return operator;
    }

    boolean checkOneJoin(String[] oneJoin) throws Exception {
        if(oneJoin[0].compareTo(jointure) != 0) {
            throw new Exception("Syntax error "+ oneJoin[0]);
        }
        if(isInTable(oneJoin[1]) ==  false) {
            throw new Exception("Table ou vue inexistant : " + oneJoin[1]);
        }
    return true;
    }

    int countNbJoin(String request_join) {
        int count = 0;
        String[] split = request_join.split("\\,");
        for(int i = 0; i<split.length; i++) {
            String[] word = split[i].split("\\ ");
            for (int j = 0; j < word.length; j++) {
                if(word[j].compareToIgnoreCase(jointure) == 0) {
                    count ++;
                }
            }
        }
        //System.out.println(count +"sdvs");
    return count;
    }

    String[] nomTableToJoin(String request) {
        String[] split = request.split("\\ ");
        String semi_request = split[0]+" "+split[1]+ " " +split[2]+ " " +split[3];
        int len = semi_request.length();
        String request_join = request.substring(len+1);
        String nom_table = getNomTable(request);
        int alloc = countNbJoin(request_join);
        if(alloc == 0) {
            String[] val = new String[ countNbJoin(request_join) + 1];
            val[0] = nom_table;
        return val;
        }
        String[] val = new String[ countNbJoin(request_join) + 1];
        val[0] = nom_table;
        String[] allJoinEnDur = request_join.split("\\,");
        for(int i = 0; i<allJoinEnDur.length; i++) {
            String[] word = allJoinEnDur[i].split("\\ ");
            if(isInTable(word[1]) == true) {
                val[i+1] = word[1];
                //System.out.println(val[i+1] + " " +val.length);
            }
        }
    return val;
    }

    boolean checkAllJoin(String request_join) {
        int nb_jointure = countNbJoin(request_join);
        String[] allJoinEnDur = request_join.split("\\,",nb_jointure);
        int count = 0;
        try {
            for (int i = 0; i < allJoinEnDur.length; i++) {
                String[] oneJoin = allJoinEnDur[i].split("\\ ");
                if(i == nb_jointure-1) {
                    allJoinEnDur[i] = oneJoin[0] + " " + oneJoin[1];
                }
                //System.out.println(allJoinEnDur[i]);
                if(checkOneJoin(oneJoin) == true) {
                    count ++;
                }
    
            }
            if(count == allJoinEnDur.length) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;
    }

    boolean checkOneCondition(String condition, String nom_table) throws Exception {
        try {
            //System.out.println("mety1");
            String operator = getOperator(condition);
            //System.out.println(operator);
            String[] split = condition.split("\\ "+operator+" ");
            if(isInFields(nom_table , split[0]) == false) {
                throw new Exception("No field " + split[0] + " in table " + nom_table);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;
    }

    boolean checkOneCondition(String condition, String[] nom_table) throws Exception {
        try {
            //System.out.println("mety1");
            String operator = getOperator(condition);
            //System.out.println(operator);
            String[] split = condition.split("\\ "+operator+" ");
            if(isInFields(nom_table , split[0]) == false) {
                throw new Exception("No field " + split[0] + " in tables ");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;
    }

    boolean checkAllCondition(String[] condition, String nom_table) throws Exception {
        //System.out.println("mety");
        int count = 0;
        //System.out.println(nom_table);
        for(int i = 0; i<condition.length ; i++) { 
            //System.out.println(checkOneCondition(condition[i], nom_table) + " hh "+ nom_table);    
            if(checkOneCondition(condition[i], nom_table) == true) {
                count ++;
            }
        }
        if(count == condition.length) {
            return true;
        }
    return false;
    }
    
    boolean checkAllCondition(String[] condition, String[] nom_table) throws Exception {
        //System.out.println("mety");
        int count = 0;
        //System.out.println(nom_table);
        for(int i = 0; i<condition.length ; i++) { 
            //System.out.println(checkOneCondition(condition[i], nom_table) + " hh "+ nom_table);    
            if(checkOneCondition(condition[i], nom_table) == true) {
                count ++;
            }
        }
        if(count == condition.length) {
            return true;
        }
    return false;
    }


    boolean checkSimpleSelect(String request) throws Exception {
        String[] split = request.split("\\ ");
        for(int i = 0; i<2; i++) {
            if(i != 1) {
                if(split[i].compareToIgnoreCase(vocabulaire[i]) != 0) {
                    throw new Exception("Syntax error");
                }
            }
            
        }

        if(split.length > 4) {
            return false;
        }
        
        if(split[1].compareToIgnoreCase("*") == 0) {
            try {
                String nom_table = getNomTable(request);
                if(isInTable(nom_table) == false) {
                    throw new Exception("Table or view inexistant");
                }
                else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if(split[1].compareToIgnoreCase("*") != 0) {
            try {
                String nom_table = getNomTable(request);
                if(isInTable(nom_table) == false) {
                    throw new Exception("Table ou view inexistant");
                }
                else {
                    if(areInFields(nom_table, split[1]) != true ) {
                        throw new Exception("Field inexistant : "+ split[1]);
                    }
                    //System.out.println("taa");
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    return false;

    }

    boolean checkSimpleSelect(String request, String[] nom) throws Exception {
        String[] split = request.split("\\ ");
        for(int i = 0; i<2; i++) {
            if(i != 1) {
                if(split[i].compareToIgnoreCase(vocabulaire[i]) != 0) {
                    throw new Exception("Syntax error");
                }
            }
            
        }

        if(split.length > 4) {
            return false;
        }
        
        if(split[1].compareToIgnoreCase("*") == 0) {
            try {
                String nom_table = getNomTable(request);
                if(isInTable(nom_table) == false) {
                    throw new Exception("Table or view inexistant");
                }
                else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if(split[1].compareToIgnoreCase("*") != 0) {
            try {
                String nom_table = getNomTable(request);
                if(isInTable(nom_table) == false) {
                    throw new Exception("Table ou view inexistant");
                }
                else {
                    if(areInFields(nom, split[1]) != true ) {
                        throw new Exception("Field inexistant : "+ split[1]);
                    }
                    //System.out.println("taa");
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    return false;

    }

    boolean checkSyntaxCondition(String semirequest, String[] nom) throws Exception {
        String[] split = semirequest.split("\\ ");
        if(split[0].compareToIgnoreCase("ka") != 0) {
            return false;
        }
            try {
                //System.out.println(request);
                String allCond = getAllCondition(semirequest);
                //System.out.println( allCond );
                String[] conditions = conditions(allCond);
                //System.out.println( " hahaha");
                if(checkAllCondition(conditions, nom) == true) {
                    //System.out.println("mety");
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        
    return false;

    }

    boolean checkSelectWithCondition(String request) throws Exception {
        String[] split = request.split("\\ ");
        if(split[4].compareToIgnoreCase("ka") != 0 || split.length <6) {
            return false;
        }
    
        String semi_request = split[0]+" "+split[1]+ " " +split[2]+ " " +split[3];
        if(checkSimpleSelect(semi_request) == true) {
            try {
                //System.out.println(request);
                String allCond = getAllCondition(request);
                //System.out.println( allCond );
                String[] conditions = conditions(allCond);
                //System.out.println( " hahaha");
                if(checkAllCondition(conditions, split[3]) == true) {
                    //System.out.println("mety");
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    return false;

    }

    boolean checkSelectWithJoin(String request) throws Exception {
        //System.out.println("tafa");
        String[] split = request.split("\\ ");
        String semi_request = split[0]+" "+split[1]+ " " +split[2]+ " " +split[3];
        //System.out.println("tafa");
        String[] nom_tables = nomTableToJoin(request);
        //System.out.println("tafa");
        if(split[4].compareToIgnoreCase(jointure) != 0) {
            System.out.println("Syntax error "+ split[4]);
            return false;
        }
        if(request.contains("[") == true || request.contains("]") == true) {
            //System.out.println("tafa");
            return false;
        }
        if(checkSimpleSelect(semi_request, nom_tables) == true) {
            try {
                //System.out.println("tafa");
                int len = semi_request.length();
                String request_join = request.substring(len+1);
                if(checkAllJoin(request_join) == true) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    return false;
    }

    boolean checkSelectWithJoinAndCond(String request) throws Exception {
        //System.out.println("tafa");
        int len = 0;
        if(request.contains("[") == true) {
            len = request.indexOf("[");
        }
        else{
            return false;
        }
        String cond = request.substring(len - 3);
        String semi_request = request.substring(0, len-3);
        String[] nom_tables = nomTableToJoin(request);
        try {
            if(checkSelectWithJoin(semi_request) == true) {
                try {
                    if(checkSyntaxCondition(cond, nom_tables) == true) {
                        return true;
                    }
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                } 
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;
    }

    boolean checkSyntaxe(String request) throws Exception {
        String[] split = request.split("\\ ");
        if(split.length == 4) {
            if(checkSimpleSelect(request) == true) {
                //System.out.println("mety");
                return true;
            }
        }
        if(split.length > 4) {
            //System.out.println(checkSelectWithJoin(request));
            if(checkSelectWithCondition(request) == true || checkSelectWithJoin(request) == true || checkSelectWithJoinAndCond(request) == true) {
                //System.out.println("mety1");
                return true;
            }
        }
    return false;
    }


    public void execute(String request) throws Exception {
        String[] split = request.split("\\ ");
        try {
            try {
                String nom_table = getNomTable(request);
                //System.out.println("tafa");
                TableRelationnelle all = new TableRelationnelle(nom_table);
                if(checkSyntaxe(request) == false) { 
                    //System.out.println(" tsy mety");
                    throw new Exception("syntax error, please recheck your syntax");
                }
                
                if(checkSimpleSelect(request) == true) {
                    
                    if(split[1].compareToIgnoreCase("*") == 0 ) {   
                        f.aff(all);
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        //System.out.println("nandalo tao");
                        TableRelationnelle result = f.select(all, split[1]);
                        f.aff(result);
                    }
                }
                if( checkSelectWithCondition(request) == true ) {
                    //System.out.println("mety eeeee");
                    String allCond = getAllCondition(request);
                    if(split[1].compareToIgnoreCase("*") == 0 ) {   
                        TableRelationnelle result = f.selectCond(all, allCond);
                        f.aff(result);
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        TableRelationnelle result = f.selectCond(all, split[1], allCond);
                        f.aff(result);
                    }
                    
                }
                if(checkSelectWithJoin(request) == true) {
                    //System.out.println("tafa");
                    String[] nomTableToJoin = nomTableToJoin(request);
                    Vector allTab = new Vector();
                    for(int i = 0; i<nomTableToJoin.length; i++) {
                        //System.out.println(nomTableToJoin[i]);
                        allTab.add( new TableRelationnelle(nomTableToJoin[i]));
                    }
                    TableRelationnelle result = f.jointureMultiple(allTab);
                    if(split[1].compareToIgnoreCase("*") == 0 ) {   
                        f.aff(result);
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        result = f.select(result, split[1]);
                        f.aff(result);
                    }    
                }
                if(checkSelectWithJoinAndCond(request) == true) {
                    //System.out.println("mety le izy");
                    String[] nomTableToJoin = nomTableToJoin(request);
                    Vector allTab = new Vector();
                    String allCond = getAllCondition(request);
                    for(int i = 0; i<nomTableToJoin.length; i++) {
                        allTab.add(new TableRelationnelle(nomTableToJoin[i]));
                    }
                    TableRelationnelle result = f.jointureMultiple(allTab);
                    if(split[1].compareToIgnoreCase("*") == 0 ) { 
                        result = f.selectCond(result, allCond);  
                        f.aff(result);
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        result = f.selectCond(result, split[1],allCond);
                        f.aff(result);
                    }
                }
                    
                
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public TableRelationnelle reponseToRequest(String request) throws Exception {
        String[] split = request.split("\\ ");
        TableRelationnelle reponse = new TableRelationnelle();
        try {
            try {
                String nom_table = getNomTable(request);
                //System.out.println("tafa");
                TableRelationnelle all = new TableRelationnelle(nom_table);
                if(checkSyntaxe(request) == false) { 
                    //System.out.println(" tsy mety");
                    throw new Exception("syntax error, please recheck your syntax");
                }
                
                if(checkSimpleSelect(request) == true) {
                    
                    if(split[1].compareToIgnoreCase("*") == 0 ) {   
                        //f.aff(all);
                        reponse = all;
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        //System.out.println("nandalo tao");
                        TableRelationnelle result = f.select(all, split[1]);
                        //f.aff(result);
                        reponse = result;
                    }
                }
                if( checkSelectWithCondition(request) == true ) {
                    //System.out.println("mety eeeee");
                    String allCond = getAllCondition(request);
                    if(split[1].compareToIgnoreCase("*") == 0 ) {   
                        TableRelationnelle result = f.selectCond(all, allCond);
                        //f.aff(result);
                        reponse = result;
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        TableRelationnelle result = f.selectCond(all, split[1], allCond);
                        //f.aff(result);
                        reponse = result;
                    }
                    
                }
                if(checkSelectWithJoin(request) == true) {
                    //System.out.println("tafa");
                    String[] nomTableToJoin = nomTableToJoin(request);
                    Vector allTab = new Vector();
                    for(int i = 0; i<nomTableToJoin.length; i++) {
                        //System.out.println(nomTableToJoin[i]);
                        allTab.add( new TableRelationnelle(nomTableToJoin[i]));
                    }
                    TableRelationnelle result = f.jointureMultiple(allTab);
                    if(split[1].compareToIgnoreCase("*") == 0 ) {   
                        //f.aff(result);
                        reponse = result;
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        result = f.select(result, split[1]);
                        //f.aff(result);
                        reponse = result;
                    }    
                }
                if(checkSelectWithJoinAndCond(request) == true) {
                    //System.out.println("mety le izy");
                    String[] nomTableToJoin = nomTableToJoin(request);
                    Vector allTab = new Vector();
                    String allCond = getAllCondition(request);
                    for(int i = 0; i<nomTableToJoin.length; i++) {
                        allTab.add(new TableRelationnelle(nomTableToJoin[i]));
                    }
                    TableRelationnelle result = f.jointureMultiple(allTab);
                    if(split[1].compareToIgnoreCase("*") == 0 ) { 
                        result = f.selectCond(result, allCond);  
                        //f.aff(result);
                        reponse = result;
                    } 
                    else if(split[1].compareToIgnoreCase("*") != 0 ) {
                        result = f.selectCond(result, split[1],allCond);
                        //f.aff(result);
                        reponse = result;
                    }
                }
                    
                
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return reponse;
    }

    //----------------------------------------DIFFERENCE-----------------------------------------------
    boolean checkDifference(String request) {
        String[] allSyntax = request.split("\\-");
        String req1 = allSyntax[0];
        String req2 = allSyntax[1];
        try {
            if(checkSimpleSelect(req1) == true && checkSimpleSelect(req2) == true ) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;    
    }

    public void executeDifference(String request) {
        String[] allSyntax = request.split("\\-");
        String req1 = allSyntax[0];
        String req2 = allSyntax[1];
        if(checkDifference(request) == true) {
            String nom_table1 = getNomTable(req1);
            String nom_table2 = getNomTable(req2);
            TableRelationnelle obj1 = new TableRelationnelle(nom_table1);
            TableRelationnelle obj2 = new TableRelationnelle(nom_table2);
            String[] split1 = req1.split("\\ ");
            String[] split2 = req2.split("\\ ");
            obj1 = f.select(obj1, split1[1]);
            obj2 = f.select(obj2, split2[1]);
            TableRelationnelle result = f.difference(obj1, obj2);
            //System.out.println("tafa");
            f.aff(result);
        }
    }

    //-------------------------DIVISION------------------------
    boolean checkDivision(String request) {
        String[] allSyntax = request.split("\\/");
        String req1 = allSyntax[0];
        String req2 = allSyntax[1];
        try {
            if(checkSimpleSelect(req1) == true && checkSimpleSelect(req2) == true ) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return false;    
    }

    public void executeDivision(String request) {
        String[] allSyntax = request.split("\\/");
        String req1 = allSyntax[0];
        String req2 = allSyntax[1];
        if(checkDivision(request) == true) {
            //System.out.println("tafa");
            String nom_table1 = getNomTable(req1);
            String nom_table2 = getNomTable(req2);
            TableRelationnelle obj1 = new TableRelationnelle(nom_table1);
            TableRelationnelle obj2 = new TableRelationnelle(nom_table2);
            String[] split1 = req1.split("\\ ");
            String[] split2 = req2.split("\\ ");
            TableRelationnelle result = f.division(obj1, obj2, split1[1], split2[1]);
            //System.out.println("tafa");
            f.aff(result);
        }
    }


}
