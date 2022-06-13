package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * stellt ein allgemeines Konto dar
 */
public abstract class Konto implements Comparable<Konto>
{
	/**
	 * Gesamtzahl der Aktien werden hier gespeichert.
	 */
	private HashMap<String,Integer> aktiendepot=new HashMap<>();

	Aktie aktie;

	/**
	 * Ab dem bestimmten hoechstpreis, wird die entsprechende aktie gekafut
	 * @param a Aktie
	 * @param anzahl Anzahl der zu kaufenden Aktien
	 * @param hoechstpreis Limit-Preis
	 * @return gesamtkaufpreis
	 */
	public Future<Double> kaufauftrag(Aktie a, int anzahl, double hoechstpreis) {
		double gesamtkaufpreis = anzahl * hoechstpreis;
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Double> future = executor.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				if (gesamtkaufpreis > getKontostand()) {
					throw new NichtGenugGeldAufKontoException();
				}
				if (a.getKurs() >= hoechstpreis) {
					setKontostand(getKontostand() - gesamtkaufpreis);
					aktiendepot.put(a.getName(),anzahl);

				}

				return gesamtkaufpreis;
			}
		});
		executor.shutdown();
		try {
			System.out.println("Aktien wurden fuer " + future.get() + " gekauft ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return future;
	}

	/**
	 * Alle Papiere der jeweiligen Aktie werden verkauft und das geld werden auf das konto eingezahlt
	 * @param wkn Aktienname(?)
	 * @param minimalpreis Limit-preis
	 * @return gesamtverkaufspreis
	 */
	public Future<Double> verkaufauftrag(String wkn, double minimalpreis){
		double gesamtverkaufspreis=minimalpreis*aktiendepot.get(wkn);
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Double> future = executor.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				if(wkn!=aktie.getName() ) return 0.0;

				if(minimalpreis>= aktie.getKurs()){
					aktiendepot.replace(wkn,0);
					setKontostand(getKontostand()+gesamtverkaufspreis);
				}
				return gesamtverkaufspreis;
			}

		});

		try {
			System.out.println("Alle Aktien wurden verkauft fuer "+future.get()+" Euro");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return future;
	}







	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) {
		System.out.println("nicht aufrufen!");
	}

	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfängliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber Kunde
	 * @param kontonummer long
	 * @throws IllegalArgumentException wenn der Inhaber null
	 */
	public Konto(Kunde inhaber, long kontonummer) {
	}
	
	/**
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
	}

	/**
	 * liefert den Kontoinhaber zurück
	 * @return   Kunde
	 */
	public Kunde getInhaber() {
		System.out.println("nicht aufrufen!");
		return null;
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public void setInhaber(Kunde kinh) throws GesperrtException{
		System.out.println("nicht aufrufen!");

	}
	
	/**
	 * liefert den aktuellen Kontostand
	 * @return   double
	 */
	public double getKontostand() {
		System.out.println("nicht aufrufen!");

		return 0;
	}

	/**
	 * liefert die Kontonummer zurück
	 * @return   long
	 */
	public long getKontonummer() {
		System.out.println("nicht aufrufen!");
		return 0;
	}

	/**
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return
	 */
	public boolean isGesperrt() {
		System.out.println("nicht aufrufen!");
		return false;
	}
	
	/**
	 * Erhöht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag) {
		System.out.println("nicht aufrufen!");
	}
	
	/**
	 * Gibt eine Zeichenkettendarstellung der Kontodaten zurück.
	 */
	@Override
	public String toString() {
		System.out.println("nicht aufrufen!");
		return null;
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
	 *
	 * @param betrag double
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 * @return true, wenn die Abhebung geklappt hat, 
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public abstract boolean abheben(double betrag) throws GesperrtException;
	
	/**
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
	 */
	public void sperren() {
		System.out.println("nicht aufrufen!");
	}

	/**
	 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
	 */
	public void entsperren() {
		System.out.println("nicht aufrufen!");
	}
	
	
	/**
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public String getGesperrtText()
	{
		System.out.println("nicht aufrufen!");
		return null;
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		System.out.println("nicht aufrufen!");
		return null;
	}
	
	/**
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol €
	 */
	public String getKontostandFormatiert()
	{
		System.out.println("nicht aufrufen!");
		return null;
	}
	
	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		System.out.println("nicht aufrufen!");
		return false;
	}
	
	@Override
	public int hashCode()
	{
		System.out.println("nicht aufrufen!");
		return 0;
	}

	@Override
	public int compareTo(Konto other)
	{
		System.out.println("nicht aufrufen!");
		return 0;
	}
	
	// Diese Methode sollte hier eigentlich nicht sein!
	public void aufKonsoleAusgeben()
	{
		System.out.println("nicht aufrufen!");
	}


}
