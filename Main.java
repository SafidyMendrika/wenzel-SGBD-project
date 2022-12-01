package algo.main;
import algo.fonction.*;
import algo.langage.*;
import java.io.ObjectStreamField;
public class Main {

    public static void main(String[] args) {
        Langage myLangage = new Langage();
        try {
            //myLangage.execute("alaina * ao_@ stagiere");
            //myLangage.execute("alaina * ao_@ module");
            myLangage.executeDivision( "alaina id_S,id_M ao_@ stagiere/alaina id_M ao_@ module" );
            //myLangage.execute("alaina nom,numero_sal,valeur ao_@ emp atambatra_@ dept,atambatra_@ salaire ka [ numero_emp misy 1,nom misy o ]");
            //myLangage.executeInsertion("ampidiro ao_@ emp ( 16,raly )");
            //myLangage.execute("alaina * ao_@ emp ka [ numero_emp misy 1,nom misy o ]");
            //myLangage.executeCreation("mamorona table dept ( int numero_dept,int numero_emp,String location )");
            // myLangage.executeInsertion("ampidiro ao_@ dept ( 1,10,windows )");
            // myLangage.executeInsertion("ampidiro ao_@ dept ( 2,11,linux )");
            // myLangage.executeInsertion("ampidiro ao_@ dept ( 3,14,windows)");
            // myLangage.executeInsertion("ampidiro ao_@ dept ( 4,16,linux )");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}