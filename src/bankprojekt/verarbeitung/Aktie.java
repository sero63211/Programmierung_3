package bankprojekt.verarbeitung;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Klasse Aktie
 */
public class Aktie{
    /**
     * Name der Aktie
     */
    private String name;
    /**
     * Identifikationsnummer der Aktie
     */
    private long wertpapierkennnummer;

    /**
     * Kurs der Aktie
     */
    private double kurs;
    /**
     * Möglicher, maximaler Kurs der Aktie
     */
    private final double max=3.0;
    /**
     * Möglicher, minimaler Kurs der Aktie
     */
    private final double min=-3.0;

public Aktie(String name,long wertpapierkennnummer,double kurs){
    this.name=name;
    this.wertpapierkennnummer=wertpapierkennnummer;
    this.kurs=kurs;
/**
 * Timertask, der jede sekunde die Methode Kurswechsel ausführt.
 */
    Timer t = new Timer();
    t.scheduleAtFixedRate(new TimerTask() {

        @Override
        public void run() {
            kurswechsel();
        }
    }, 0,1000);


}

    /**
     * Das Attribut (double) kurs, wird eine zufällig erzeugten Zahl hinzugefügt.
     */
    public void kurswechsel(){
    Random random=new Random();
    this.kurs+=min+(max-min)*random.nextDouble();
    System.out.println(kurs);
}

    /**
     * Get-Methode vom Kurs
     * @return
     */
    public double getKurs(){
        return kurs;
}
/**
 * Get-Methode vom Name
 */
public String getName(){
    return name;
}

    public long getWertpapierkennnummer() {
        return wertpapierkennnummer;
    }
}
