package bankprojekt.verarbeitung;

/**
 * Schnittstelle für Beobachter in der Bank
 *
 */
public interface Beobachter {

    /**
     * Berichtet über Updates im subjekt
     * @param subjekt das beobachtete Konto
     */
     void update(Konto subjekt);}