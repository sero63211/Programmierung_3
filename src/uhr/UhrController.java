package uhr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * Uhr IController
 */
public class UhrController implements Observer, ActionListener, KeyListener {
    private Observable model;
    private IUhrView digitalUhrView;
    private IUhrView kreisUhrView;

    public UhrController() {
        digitalUhrView = new DigitalUhr();
        kreisUhrView = new KreisUhr();

        model = new Uhr();
        model.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        Uhr uhr = (Uhr) observable;

        kreisUhrView.tick();
        digitalUhrView.tick();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case DigitalUhr.KNOPF_AUS:
            case DigitalUhr.KNOPF_EIN:
                digitalUhrView.toggleUhr();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (Character.toUpperCase(keyEvent.getKeyChar())) {
            case 'E':
                kreisUhrView.enableUhr();
            case 'A':
                kreisUhrView.disableUhr();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
