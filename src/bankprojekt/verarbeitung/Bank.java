package bankprojekt.verarbeitung;
/**
 *
 * Bibliothek. Notwendig für die Verwendung von Collections
 */
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Klasse Bank. Verwaltet Girokonto, Sparbuch
 */
public class Bank  implements Serializable, Cloneable {

    /**
     * Versionsnummer
     */
    private static final long serialVersionUID = 2342234872L;


    private long maxKontonummer=0;
    /**
     * Attribut Bankleitzahl
     */
    private long bankleitzahl;
    /**
     * Map, um Konten zu speichern.
     * Key=Kontonummer(zufaellig generieerter Schlüssel)
     * Value= Objekt vom Typ Konto
     */
    private  HashMap<Long,Konto>konten=new HashMap<>();
    /**
     * Konstruktor. Verwaltet nur den Parameter Bankleitzahl.
     * @param bankleitzahl
     */
    public Bank(long bankleitzahl) {
        if (bankleitzahl > 0)
            this.bankleitzahl = bankleitzahl;
        else {
            throw new IllegalArgumentException("Bankleitzahl darf nicht negativ sein!");

        }
    }
    /**
     * Gibt den long-Wert bankleitzahl wieder
     * @return long bankleitzahl
     */
    public long getBankleitzahl(){return this.bankleitzahl;}

    /**
     * Erstellt ein Girokonto mit einem zufaellig generierten Key.
     * @param inhaber
     * @return Kontonummer
     */
    public long girokontoErstellen(Kunde inhaber)throws KontoNummerNichtVorhandenException{
        if(inhaber != null){
            //vergibt eine gueltige Kontonummer
            long ktnummer = maxKontonummer+1;
            //erstellt neues Giro anhand der Kontonummer und des Kunden inhaber
            Konto giro = new Girokonto(inhaber, ktnummer, 1500);
            //fuegt kontonummer und giro in die Hashmap
            konten.put(ktnummer,giro);
            //aktualisiert die bishervergebenen Kontonummer
            maxKontonummer = ktnummer;
            return ktnummer;
        }
        else {

            throw new KontoNummerNichtVorhandenException("Bitte uebergeben Sie einen echten Kunden!");

        }}



    /**
     * Erstellt ein Sparbuch mit einem zufällig generierten Key.
     * @param inhaber
     * @return Kontonummer
     */
    public long sparbuchErstellen(Kunde inhaber)throws KontoNummerNichtVorhandenException{
        if (inhaber != null) {
            // vergibt eine gueltige Kontonummer
            long ktnummer = maxKontonummer + 1;
            Konto sparbuch = new Sparbuch(inhaber, ktnummer);
            konten.put(ktnummer, sparbuch);
            maxKontonummer = ktnummer;
            return ktnummer;
        } else{
            throw new KontoNummerNichtVorhandenException("Bitte uebergeben Sie einen echten Kunden!");
        }

    }

    /**
     * Auflistung der Kontonummern und des Kontostands aller Konten.
     * @return  Kontonummer,Kontostand
     */
    public String getAlleKonten(){

        String result="";

        for(Map.Entry<Long, Konto> entry: konten.entrySet()){
            result+="Kontonummer: " + entry.getKey() + " Kontostand: " + entry.getValue().getKontostand()
                    + System.getProperty("line.separator");
        }
        return result;
    }

    public List<Long> getAlleKontonummern(){

        return konten.keySet().stream().collect(Collectors.toList());

    }

    public boolean geldAbheben(long von, double betrag) throws GesperrtException {
        konten.get(von).abheben(betrag);
        return false;
    }
    public void geldEinzahlen(long auf, double betrag){
        konten.get(auf).einzahlen(betrag);
    }

    public boolean kontoLoeschen(long nummer) throws KontoNummerNichtVorhandenException{
        Konto k = konten.remove(nummer);
        if(k!= null) return true;
        else {
            throw new KontoNummerNichtVorhandenException("Bitte uebergeben Sie einen echten Kunden!");

        }
    }

    public double getKontostand(long nummer)throws KontoNummerNichtVorhandenException{
        if(konten.containsKey(nummer))
            return konten.get(nummer).getKontostand();
        else{
            throw new KontoNummerNichtVorhandenException("Bitte uebergeben Sie einen echten Kunden!");

        }

    }



    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws GesperrtException, KontoNummerNichtVorhandenException, NurBankinterneÜberweisungenException {
        String benoetigterVerwendungszweck="Bankinterne Überweisung";
        if(!verwendungszweck.equals(benoetigterVerwendungszweck)){
            throw new NurBankinterneÜberweisungenException("Nur bankinterne Überweisungen!");
        }
        if(konten.get(vonKontonr).isGesperrt()){
            throw new GesperrtException(vonKontonr);
        }
        if(konten.get(nachKontonr).isGesperrt()){
            throw new GesperrtException(nachKontonr);
        }
        if(!konten.containsKey(vonKontonr)){
        throw new KontoNummerNichtVorhandenException("Kontonummer nicht vorhanden");
        }
        if(!konten.containsKey(nachKontonr)){
            throw new KontoNummerNichtVorhandenException("Kontonummer nicht vorhanden");
        }
        if(konten.get(vonKontonr).getKontostand()<betrag){
            throw new NurBankinterneÜberweisungenException("Nicht genug Geld auf dem Konto!");
        }

        /**
         * Diese Methode ist wahrscheinlich falsch. Da ich bei Map-Value den Typ Konto benutze ist
         * es nicht möglich die map.replace methode zu verwenden, da kein double Wert verwendet werden kann.
         */

        double differenz=konten.get(vonKontonr).getKontostand();
        konten.get(vonKontonr).setKontostand(differenz);
        konten.get(nachKontonr).setKontostand(getKontostand(nachKontonr)+betrag);
        return true;
    }


    /**
     * Methode: Sperrt Konten, dessen Kontostand < 0 ist.
     */


    public void pleitegeierSperren(){
        konten.values()
                .stream()
                .filter((k->k.getKontostand()<0))
                .forEach(k->k.sperren());
        }

    public String getKundengeburtstage(){
        return konten.values()
                .stream()
                .map(k->k.getInhaber())
                .distinct()
                .sorted(Comparator.comparing(Kunde::getGeburtstag))
                .collect(Collectors.toList())
                .toString();
    }
    public List<Long> getKontonummernLuecken(){
        return konten.values()
                .stream()
                .collect(Collectors.partitioningBy(k->k.getKontonummer()<maxKontonummer))
                .get(true)
                .stream()
                .map(k->k.getKontonummer())
                .collect(Collectors.toList());

    }
    /**
     * Liefert Liste mit allen Kunden, die einen Kontostand haben,
     * der mindestens "minumum" beträgt.
     * @param minimum
     * @return
     */
    public List<Kunde> getKundenMitVollemKonto(double minimum){
        return konten.values()
                .stream()
                .collect(Collectors.partitioningBy(k->k.getKontostand()>= minimum))
                .get(true)
                .stream()
                .map(k->k.getInhaber())
                .collect(Collectors.toList());
    }


    /**
     * Clone-Method für Bank-Objekt
     * byte array wird deklariert.
     *
     * @return
     */
    @Override
    public Bank clone()throws CloneNotSupportedException{
        byte[] bank = null; //byte-Array fuer die Umwandlung
        //1. Zu kopierendes Objekt wird in einen BAOS hineingeschrieben
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            //Objekt wird in einen OOS geschrieben
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            //Das Objekt wird in einen byte-Array gewandelt
            bank = baos.toByteArray();

        } catch (IOException e){

        }
        //Kopie wird gelesen und zurückgeliefert
        try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bank))){
            return (Bank) ois.readObject();
            //Kann nichts passieren
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {



        }

        return null;
    }








}



