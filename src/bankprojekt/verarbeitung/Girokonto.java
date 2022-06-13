package bankprojekt.verarbeitung;

/**
 * Ein Girokonto
 * @author Doro
 *
 */
public class Girokonto extends Konto implements Ueberweisungsfaehig{

	/**
	 * erzeugt ein leeres, nicht gesperrtes Standard-Girokonto
	 * von Herrn MUSTERMANN
	 */
	public Girokonto()
	{
	}
	
	/**
	 * erzeugt ein Girokonto mit den angegebenen Werten
	 * @param inhaber Kontoinhaber
	 * @param nummer Kontonummer
	 * @param dispo Dispo
	 * @throws IllegalArgumentException wenn der inhaber null ist 
	 * 									oder der angegebene dispo negativ bzw. NaN ist
	 */
	public Girokonto(Kunde inhaber, long nummer, double dispo)
	{
	}
	
	/**
	 * liefert den Dispo
	 * @return Dispo von this
	 */
	public double getDispo() {
		System.out.println("nicht aufrufen!");
		return 0;
	}

	/**
	 * setzt den Dispo neu
	 * @param dispo muss größer sein als 0
	 * @throw IllegalArgumentException wenn dispo negativ bzw. NaN ist
	 */
	public void setDispo(double dispo) {
		System.out.println("nicht aufrufen!");
	}
	
    /**
     * vermindert den Kontostand um den angegebenen Betrag, falls das Konto nicht gesperrt ist.
     * Am Empfängerkonto wird keine Änderung vorgenommen, da davon ausgegangen wird, dass dieses sich
     * bei einer anderen Bank befindet.
     * @param betrag double
     * @param empfaenger String
     * @param nachKontonr int
     * @param nachBlz int
     * @param verwendungszweck String
     * @return boolean
     * @throws GesperrtException wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der Betrag negativ bzw. NaN ist oder
     * 									empfaenger oder verwendungszweck null ist
     */
    public boolean ueberweisungAbsenden(double betrag, String empfaenger, long nachKontonr,
    		long nachBlz, String verwendungszweck) 
    				throws GesperrtException 
    {
    	System.out.println("nicht aufrufen!");
        return false;
    }

    /**
     * erhöht den Kontostand um den angegebenen Betrag
     * @param betrag double
     * @param vonName String
     * @param vonKontonr int
     * @param vonBlz int
     * @param verwendungszweck String
     *      * @throws IllegalArgumentException wenn der Betrag negativ bzw. NaN ist oder
     * 									vonName oder verwendungszweck null ist
     */
    public void ueberweisungEmpfangen(double betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck)
    {
    	System.out.println("nicht aufrufen!");
    }

    @Override
    public String toString()
    {
    	System.out.println("nicht aufrufen!");
    	return null;
    }

	@Override
	public boolean abheben(double betrag) throws GesperrtException{
		System.out.println("nicht aufrufen!");
		return false;
	}





}
