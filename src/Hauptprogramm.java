import bankprojekt.verarbeitung.*;

import java.time.LocalDate;


public class Hauptprogramm {

    public static void main(String[] args) {
    Aktie aktie1=new Aktie("Daimler AG",12345,60.3);
    //Aktie aktie2=new Aktie("Volkswagen",45678,2.1);
    //Aktie aktie3=new Aktie("Apple",98765,10.2);

        /**
         * funktioniert nicht so wie erwartet, weil getKontostand nur eine dummy-methode ist
         *Das Konto hat also garkein Geld und kann somit nichts kaufen bzw. anschlißend verkaufen.
         */
        Kunde kunde1=new Kunde("Max","Mustermann","Teststraße", LocalDate.now());
        Konto konto1=new Girokonto(kunde1,123324,10000);
        konto1.kaufauftrag(aktie1,2,58.0);
        konto1.verkaufauftrag(aktie1.getName(), 65);
    }


}
