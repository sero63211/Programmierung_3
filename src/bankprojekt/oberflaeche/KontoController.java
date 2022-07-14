package bankprojekt.oberflaeche;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class KontoController extends Application {
    /**
     * Kunde Objekt
     */
    private Kunde kunde = new Kunde("Max", "Mustermann", "Testraße 22",
                                    LocalDate.parse("1950-03-12"));
    private KontoOberflaeche oberflaeche;

    /**
     * Das Model mit den aktuellen Daten des Kontos
     */
    private Girokonto kontoModel = new Girokonto(kunde, 1111, 100);

    /**
     * Das Hauptfenster der Anwendung
     */
    private Stage stage;



    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
            KontoController kontoOberflaecheController = new KontoController();

            stage.show();
        }

    /**
     * Aktion für einzahlen
     * @param betrag
     */
    public void einzahlen(double betrag){
        kontoModel.einzahlen(betrag);
        oberflaeche.setAccessibleText("Einzahlen erfolgreich");
    }

    /**
     * Aktion für Abheben
     * @param betrag
     */

    public void abheben(double betrag) {
        try {
            if (kontoModel.abheben(betrag)) {
                oberflaeche.setAccessibleText("Abheben erfolgreich");
            } else {
                oberflaeche.setAccessibleText("Abheben nicht möglich!");
            }
        } catch (GesperrtException e) {
            oberflaeche.setAccessibleText("Achtung - Konto: " + kontoModel.getGesperrtText());
        }
    }
}
