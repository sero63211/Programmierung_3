package bankprojekt.verarbeitung;

import java.time.LocalDate;

/**
 * ein Sparbuch
 * @author Doro
 *
 */
public class Sparbuch extends Konto {
	
	public Sparbuch() {
	}

	public Sparbuch(Kunde inhaber, long kontonummer) {
	}
	
	@Override
	public String toString()
	{
		System.out.println("nicht aufrufen!");
    	return null;
	}

	@Override
	public boolean abheben (double betrag) throws GesperrtException{
		System.out.println("nicht aufrufen!");
		return false;
	}

}
