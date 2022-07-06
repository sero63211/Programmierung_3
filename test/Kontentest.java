import bankprojekt.verarbeitung.*;
import bankprojekt.verwaltung.Kontofabrik;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;


public class Kontentest {
    Bank bank;
    long kontonummer=0;
    Konto test1 = Mockito.mock(Konto.class, Mockito.CALLS_REAL_METHODS);
    Kontofabrik testfabrik = Mockito.mock(Kontofabrik.class);

    @Test
    public void testGeldEinzahlenNormalfall() throws KontoNummerNichtVorhandenException {

        kontonummer= bank.kontoErstellen(testfabrik, Kunde.MUSTERMANN);
        Mockito.doNothing().when(test1).notifyObservers();
        Mockito.doNothing().when(test1).einzahlen(ArgumentMatchers.anyDouble());
        bank.geldEinzahlen(kontonummer, 150.0);

        Mockito.verify(test1).einzahlen(ArgumentMatchers.anyDouble());

    }






}
