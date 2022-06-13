import java.time.LocalDate;
import java.util.Arrays;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kontoart;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

/**
 * Testprogramm für Konten
 * @author Doro
 *
 */
public class Kontentest {

	/**
	 * Testprogramm für Konten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
		Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));

		Girokonto meinGiro = new Girokonto(ich, 1234, 1000.0);
		meinGiro.einzahlen(50);
		System.out.println(meinGiro);
		
		Sparbuch meinSpar = new Sparbuch(ich, 9876);
		meinSpar.einzahlen(50);
		try
		{
			boolean hatGeklappt = meinSpar.abheben(70);
			System.out.println("Abhebung hat geklappt: " + hatGeklappt);
			System.out.println(meinSpar);
		}
		catch (GesperrtException e)
		{
			System.out.println("Zugriff auf gesperrtes Konto - Polizei rufen!");
		}
		
		Konto k = new Girokonto();  //Polymorphie
			//Konstruktoraufruf entscheidet über den auszuführenden Code
			//immer und überall
		//k.ausgeben();
		System.out.println(k);
		
		Kontoart art;
		art = Kontoart.SPARBUCH;
		art = Kontoart.valueOf("FESTGELDKONTO");
		System.out.println(art.name() + " " + art.ordinal());
		System.out.println(art.getWerbung());
		
		Kontoart[] alle = Kontoart.values();
		System.out.println("Unser Prospekt: ");
		System.out.println(Arrays.toString(alle));
		
		
	}

}
