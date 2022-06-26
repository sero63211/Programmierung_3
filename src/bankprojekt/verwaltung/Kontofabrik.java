package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

/**
 * Oberklasse, Fabrik
 */
public abstract class Kontofabrik {
    /**
     * Konto Erstellung
     * @param inhaber
     * @param kontonummer
     * @return
     */
    public abstract Konto kontoErstellen(Kunde inhaber, long kontonummer);

}
