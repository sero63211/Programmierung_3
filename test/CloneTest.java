import bankprojekt.verarbeitung.Bank;
import bankprojekt.verarbeitung.KontoNummerNichtVorhandenException;
import bankprojekt.verarbeitung.Kunde;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class CloneTest {

    private Bank original = new Bank(12341111);

    /**
     * Exception wird geliefert, weil buf(?) null ist. Ich weiß leider nicht wie genau das gelöst werden
     * kann.
     * @throws KontoNummerNichtVorhandenException
     * @throws CloneNotSupportedException
     */

    @Test
    public void CloneTest() throws KontoNummerNichtVorhandenException, CloneNotSupportedException {
      try{
        /**
         * Konto wird auf Original-Bank erstellt.
         */
        original.girokontoErstellen(Kunde.MUSTERMANN);
        /**
         * Konto wird kopiert.
         */
          Bank bankClone = (Bank) original.clone();




            /**
             * Geld wird auf ein originales Konto eingezahlt.
             */
            original.geldEinzahlen(6321, 999);


            assertEquals(original.getKontostand(6321), 999);
            assertNotEquals(bankClone.getKontostand(6321), 999);

            assertFalse(bankClone.getKontostand(1000000001) == original.getKontostand(1000000001));
        }
        catch (CloneNotSupportedException e) {
              throw e;  }

      }}





