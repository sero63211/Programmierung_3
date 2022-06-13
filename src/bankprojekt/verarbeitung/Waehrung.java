package bankprojekt.verarbeitung;




/**
 * Sammlung aller Waehrungen, die wir behandeln.
 */
public enum Waehrung {
    /**
     * Konstanten, welche folgende Waehrungen darstellen:
     */
    EUR(1),
    BGN(1.9558),
    DKK(61.62),
    MKD( 7.4604);

    /**
     * Umrechnungskurs
     */
    double umrechnungskurs;
    /**
     * Privater(automatisch privat) Konstruktor, welcher die Umrechnungskurse der Waehrungen zum Euro verarbeitet.
     * @param umrechnungskurs
     */
 Waehrung(double umrechnungskurs){
 this.umrechnungskurs=umrechnungskurs;
}


    /**
     * der in Euro angegebene Betrag wird in die jeweilige Währung umgerechnet.
     * @param betrag
     * @return Umgerechneter Betrag
     */
    public double euroInWaehrungUmrechnen(double betrag) {
       return (betrag * this.umrechnungskurs);
    }


    /**
     * die this.Währung wird in Euro umgerechnet.
     * @param betrag
     * @return Umgerechneter Betrag
     */
    public double waehrungInEuroUmrechnen(double betrag){
        return (betrag/this.umrechnungskurs);


    }

    /**
     * Währung wird in eine andere Währung umgerechnet.
     * @param betrag Betrag
     * @param von zu ändernde Währung
     * @param zu neue Währung
     * @return umgerechneter Betrag
     */
    public double waehrungZuWaehrung(double betrag,Waehrung von, Waehrung zu){
        return (zu.euroInWaehrungUmrechnen(von.waehrungInEuroUmrechnen(betrag)));

    }
}
