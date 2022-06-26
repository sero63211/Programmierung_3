package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
/**
 * Unterklasse von KontoFabrik
 */
public class GirokontoFabrik extends Kontofabrik{
    /**
     * Benötigtes Attribut: Dispo für Girokonto
     */
    private final int DISPO =500 ;

    /**
     * Erstellt Girokonto
     * @param inhaber
     * @param kontonummer
     * @return
     */
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) {
        return new Girokonto(inhaber,kontonummer, DISPO);
    }
}
