package bankprojekt.verarbeitung;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
	 * stellt ein allgemeines Konto dar
	 */
	public abstract class Konto implements Comparable<Konto>
	{
		/**
		 * Dispo
		 */
		protected Girokonto dispo;
		/**
		 * das gefuehrte Sparbuch
		 */
		public Sparbuch sparbuch;

		/**
		 * die gefuehrte Waehrung
		 */
		private Waehrung waehrung;
		/**
		 * der Kontoinhaber
		 */
		private Kunde inhaber;

		/**
		 * die Kontonummer
		 */
		private final long nummer;

		/**
		 * der aktuelle Kontostand
		 */
		private double kontostand;

		/**
		 * Gesamtzahl der Aktien werden hier gespeichert.
		 */
		private HashMap<String,Integer> aktiendepot=new HashMap<>();

		/**
		 * Liste der Beobachter
		 */
		private List<Beobachter> kontobeobachter;

		/**
		 * liefert Beobachterliste
		 * @return
		 */
		public List<Beobachter> getBeobachter(){
			return kontobeobachter;
		}


		public Future<Double> kaufauftrag(Aktie a, int anzahl, double hoechstpreis) {
			double gesamtkaufpreis = anzahl * a.getKurs();
			ExecutorService executor = Executors.newCachedThreadPool();
			Future<Double> future = executor.submit(new Callable<Double>() {
				@Override
				public Double call() throws Exception {
					if (gesamtkaufpreis > getKontostand()) {
						throw new NichtGenugGeldAufKontoException();
					}
					if (a.getKurs() <= hoechstpreis) {
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
		public Future<Double> verkaufauftrag(Aktie aktie,String wkn, double minimalpreis){
			double gesamtverkaufspreis=aktie.getKurs()*aktiendepot.get(wkn);
			ExecutorService executor = Executors.newCachedThreadPool();
			Future<Double> future = executor.submit(new Callable<Double>() {
				@Override
				public Double call() throws Exception {
					if(wkn!=aktie.getName() ) throw new GesperrtException(getKontonummer());

					if(aktie.getKurs()>= minimalpreis){
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

		public String getAktieName(Aktie aktie){

			return aktie.getName();
		}

		/**
		 * setzt den aktuellen Kontostand
		 * @param kontostand neuer Kontostand
		 */
		protected void setKontostand(double kontostand) {
			this.kontostand = kontostand;
			notifyObservers();
		}

		/**
		 * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
		 * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
		 */
		private boolean gesperrt;

		/**
		 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
		 * der anfängliche Kontostand wird auf 0 gesetzt.
		 *
		 * @param inhaber der Inhaber
		 * @param kontonummer die gewünschte Kontonummer
		 * @throws IllegalArgumentException wenn der Inhaber null
		 */
		public Konto(Kunde inhaber, long kontonummer) {
			if(inhaber == null)
				throw new IllegalArgumentException("Inhaber darf nicht null sein!");
			this.inhaber = inhaber;
			this.nummer = kontonummer;
			this.kontostand = 0;
			this.gesperrt = false;
			this.waehrung=Waehrung.EUR;

		}

		/**
		 * setzt alle Eigenschaften des Kontos auf Standardwerte
		 */
		public Konto() {
			this(Kunde.MUSTERMANN, 1234567);
		}

		/**
		 * liefert den Kontoinhaber zurück
		 * @return   der Inhaber
		 */
		public final Kunde getInhaber() {
			return this.inhaber;
		}

		/**
		 * setzt den Kontoinhaber
		 * @param kinh   neuer Kontoinhaber
		 * @throws GesperrtException wenn das Konto gesperrt ist
		 * @throws IllegalArgumentException wenn kinh null ist
		 */
		public final void setInhaber(Kunde kinh) throws GesperrtException{
			if (kinh == null)
				throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
			if(this.gesperrt)
				throw new GesperrtException(this.nummer);
			this.inhaber = kinh;
			notifyObservers();
		}

		/**
		 * liefert den aktuellen Kontostand
		 * @return   double
		 */
		public final double getKontostand() {
			return kontostand;
		}

		/**
		 * liefert die Kontonummer zurück
		 * @return   long
		 */
		public final long getKontonummer() {
			return nummer;
		}

		/**
		 * liefert zurück, ob das Konto gesperrt ist oder nicht
		 * @return true, wenn das Konto gesperrt ist
		 */
		public final boolean isGesperrt() {
			return gesperrt;
		}

		/**
		 * Erhöht den Kontostand um den eingezahlten Betrag.
		 *
		 * @param betrag double
		 * @throws IllegalArgumentException wenn der betrag negativ ist
		 */
		public void einzahlen(double betrag) {
			if (betrag < 0 || Double.isNaN(betrag)) {
				throw new IllegalArgumentException("Falscher Betrag");
			}
			setKontostand(getKontostand() + betrag);
			notifyObservers();
		}

		/**
		 * Gibt eine Zeichenkettendarstellung der Kontodaten zurück.
		 */
		@Override
		public String toString() {
			String ausgabe;
			ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
					+ System.getProperty("line.separator");
			ausgabe += "Inhaber: " + this.inhaber;
			ausgabe += "Aktueller Kontostand: " + getKontostandFormatiert() + " ";
			ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
			return ausgabe;
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
		public abstract boolean abhebenBedingung(double betrag) throws GesperrtException;

		/**
		 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
		 */
		public final void sperren() {
			this.gesperrt = true;
		}

		/**
		 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
		 */
		public final void entsperren() {
			this.gesperrt = false;
		}


		/**
		 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
		 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
		 */
		public final String getGesperrtText()
		{
			if (this.gesperrt)
			{
				return "GESPERRT";
			}
			else
			{
				return "";
			}
		}

		/**
		 * liefert die ordentlich formatierte Kontonummer
		 * @return auf 10 Stellen formatierte Kontonummer
		 */
		public String getKontonummerFormatiert()
		{
			return String.format("%10d", this.nummer);
		}

		/**
		 * liefert den ordentlich formatierten Kontostand
		 * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol €
		 */
		public String getKontostandFormatiert()
		{
			return String.format("%10.2f "+getAktuelleWaehrung(), this.getKontostand());
		}

		/**
		 * Vergleich von this mit other; Zwei Konten gelten als gleich,
		 * wen sie die gleiche Kontonummer haben
		 * @param other das Vergleichskonto
		 * @return true, wenn beide Konten die gleiche Nummer haben
		 */
		@Override
		public boolean equals(Object other)
		{
			if(this == other)
				return true;
			if(other == null)
				return false;
			if(this.getClass() != other.getClass())
				return false;
			if(this.nummer == ((Konto)other).nummer)
				return true;
			else
				return false;
		}

		@Override
		public int hashCode()
		{
			return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
		}

		@Override
		public int compareTo(Konto other)
		{
			if(other.getKontonummer() > this.getKontonummer())
				return -1;
			if(other.getKontonummer() < this.getKontonummer())
				return 1;
			return 0;
		}


		/**
		 * Angegebener Betrag wird eingezahlt
		 * @param betrag Einzuzahlender Betrag
		 * @param w Dazugehörige Währung
		 */
		public void einzahlen(double betrag, Waehrung w) throws IOException {
			if(betrag<0) throw new IOException();
			if(this.gesperrt)getGesperrtText(); //falls das Konto gesperrt ist
			if(w==null){throw new IllegalArgumentException("Währung darf nicht null sein!");}
			if(getAktuelleWaehrung()==w){
				setKontostand(betrag+getKontostand());
				notifyObservers();
			}
			else{
				setKontostand(waehrung.waehrungInEuroUmrechnen(betrag)+this.kontostand);//einzuzahlender Betrag [konvertiert in Euro] + aktueller Kontostand
			}

		}
		/**
		 * gibt die aktuell geführte Währung zurück
		 * @return Aktuelle Währung
		 */
		public Waehrung getAktuelleWaehrung(){return this.waehrung;}

		public void setWaehrung(Waehrung waehrung) {this.waehrung = waehrung;}

		/**
		 * Währung wird gewechselt, Abhebesumme, Dispo und Kontostand müssen dementsprechend angepasst werden
		 * @param neu neue Währung
		 */

		public void waehrungswechsel(Waehrung neu){
			setKontostand(waehrung.waehrungZuWaehrung(getKontostand(),getAktuelleWaehrung(),neu));
			setWaehrung(neu);
			notifyObservers();

		}

		/**
		 * Template
		 * Methode Abheben
		 * @param betrag
		 * @return true oder false
		 * @throws IllegalArgumentException
		 * @throws GesperrtException
		 */
		public boolean abheben(double betrag ) throws IllegalArgumentException, GesperrtException {
			if(betrag < 0) throw new IllegalArgumentException("Betrag darf nicht negativ sein.");
			if(isGesperrt()) throw new GesperrtException(this.getKontonummer());
				//prueft Vorbedingungen: Kontostand ausreichend hinsichtlich Dispos, monatl. Höchstbetrag
			if (abhebenBedingung(betrag)) {
				//passt den Kontostand an
				setKontostand(getKontostand() - betrag);
				notifyObservers();
				return true;

			}
			return false;

		}

		/**
		 * Meldet den Beobachter an
		 * @param ob
		 */
		public void addObserver(Beobachter ob){
			kontobeobachter.add(ob);
		}

		/**
		 *
		 * Meldet Beobachter ab
		 * @param b
		 */
		public void removeObserver(Beobachter b){
			kontobeobachter.remove(b);
		}

		/**
		 *
		 */
		protected void notifyObservers(){
			Bank.observerBenachrichtigen(this);
		}

	}












