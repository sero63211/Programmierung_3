package bankprojekt.verwaltung;


import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

/**
 * Unterklasse von KontoFabrik
 */
public class SparbuchFabrik extends Kontofabrik {
    /**
     * Sparbuch Konto erstellen
     * @param inhaber
     * @param kontonummer
     * @return
     */
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) {
        return new Sparbuch(inhaber, kontonummer);
    }
}
