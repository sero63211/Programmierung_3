package uhr;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Stellt eine Digitale Uhr dar, die man anhalten und weiterlaufen lassen kann
 *
 */ 
public class DigitalUhr extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private static final String TITEL = "Digitaluhr";
	private static final String KNOPF_EIN = "Ein";
	private static final String KNOPF_AUS = "Aus";
	private static final int BREITE = 500;
	private static final int HOEHE = 300;

	private JLabel anzeige;
	private JButton[] knoepfe; // Ein = Einschalten der Anzeige, 
							  // Aus = Ausschalten der Anzeige, 
	
	private boolean uhrAn = true;
	private Zeit z;
	
	/**
	 * erstellt das Fenster für die digitale Uhr und bringt es auf den
	 * Bildschirm; zu Beginn läuft die Uhr im 1-Sekunden-Takt
	 */
	public DigitalUhr() {
		uhrAn = true;
		z = new Zeit();

		// Erstellung der Oberflächenelemente:
		setTitle(TITEL);
		setSize(BREITE, HOEHE);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(new JLabel(TITEL));
		anzeige = new JLabel();
		add(anzeige);
		knoepfe = new JButton[2];
		knoepfe[0] = new JButton(KNOPF_EIN);
		knoepfe[1] = new JButton(KNOPF_AUS);
		for (JButton knopf : knoepfe) {
			super.add(knopf);
		}
		knoepfe[0].setEnabled(false); // "Ein"

		// Erstellen des ActionListeners für die beiden Buttons (Ein, Aus):
		ActionListener lauscher = new ActionListener() {
			/**
			 * schaltet je nach gedrückten Knopf die Uhrzeitanzeige ein (Ein), die Uhrzeitanzeige
			 * aus (Aus) 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case KNOPF_EIN:   //Uhrzeitanzeige anschalten
					uhrAn = true;
					break;
				case KNOPF_AUS:  //Uhrzeitanzeige ausschalten
					uhrAn = false;
					break;
				}
				knoepfe[0].setEnabled(!uhrAn);
				knoepfe[1].setEnabled(uhrAn);
			}
		};

		// Zufügen des ActionListeners zu den Buttons
		for (JButton knopf : knoepfe)
		{
			knopf.addActionListener(lauscher);
		}
		
		//Thread starten, um die Uhrzeit laufen zu lassen:
    	ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(() -> tick(), 0, 1, TimeUnit.SECONDS);

		// Auf den Bildschirm bringen:
		pack();
		setVisible(true);
	}

	/**
	 * Holen der aktuellen Uhrzeit und Anzeige, wenn die Uhr angestellt ist
	 */
	private void tick() 
	{
		if (uhrAn)
		{
			anzeige.setText(String.format("%02d:%02d:%02d", z.getStunde(), z.getMinute(),
					z.getSekunde()));
		}
	}
}
