package bankprojekt.verarbeitung;

import java.time.LocalDate;

/**
 * ein Sparbuch
 * @author Doro
 *
 */
public class Sparbuch extends Konto {
	/**
	 * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
	 */
	private double zinssatz;
	
	/**
	 * Monatlich erlaubter Gesamtbetrag für Abhebungen
	 */
	public static final double ABHEBESUMME = 2000;
	
	/**
	 * Betrag, der im aktuellen Monat bereits abgehoben wurde
	 */
	protected double bereitsAbgehoben = 0;
	/**
	 * Monat und Jahr der letzten Abhebung
	 */
	private LocalDate zeitpunkt = LocalDate.now();
	
	/**
	* ein Standard-Sparbuch
	*/
	public Sparbuch() {
		zinssatz = 0.03;
	}

	/**
	* ein Standard-Sparbuch, das inhaber gehört und die angegebene Kontonummer hat
	* @param inhaber der Kontoinhaber
	* @param kontonummer die Wunsch-Kontonummer
	* @throws IllegalArgumentException wenn inhaber null ist
	*/
	public Sparbuch(Kunde inhaber, long kontonummer) {
		super(inhaber, kontonummer);
		zinssatz = 0.03;
	}
	
	@Override
	public String toString()
	{
    	String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
    	super.toString()
    	+ "Zinssatz: " + this.zinssatz * 100 +"%" + System.lineSeparator();
    	return ausgabe;
	}

	/**
	 * Boolsche-Methode Bedingung pruefen vor dem Abheben
	 * @param betrag double
	 * @return true or false
	 * @throws GesperrtException
	 */
	@Override
	public boolean abhebenBedingung(double betrag) throws GesperrtException {
		LocalDate heute = LocalDate.now();
		//prueft ob neuer Monat angefangen hat
		if(heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear())
		{
			this.bereitsAbgehoben = 0; //sollte hier nichts ändern
		}
		//Pruefung, ob Kontostand ausreichend
		return getKontostand() - betrag >= 0.50 &&
				//Passt ABHEBESUMME an der aktuellen Kontowaehrung an
				bereitsAbgehoben + betrag <= Sparbuch.ABHEBESUMME*getAktuelleWaehrung().umrechnungskurs;

}




	/**
	 * set-Methode für Bereitsabgehoben
	 * @param bereitsAbgehoben
	 */
	public void setBereitsAbgehoben(double bereitsAbgehoben) {
		this.bereitsAbgehoben = bereitsAbgehoben;
	}

	/**
	 * get-Methode für Bereitsabgehoben
	 * @return
	 */
	public double getBereitsAbgehoben() {
		return bereitsAbgehoben;
	}




}
