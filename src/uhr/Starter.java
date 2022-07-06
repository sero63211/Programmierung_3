package uhr;

import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
 
/**
 * Startet eine Uhrenoberfläche
 * @author Doro
 *
 */
public class Starter extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnKreis;
	private JButton btnDigital;
	private JButton btnHalt;
	private List<DigitalUhr> dUhren = new LinkedList<>();
	private List<KreisUhr> kUhren = new LinkedList<>();
	
	/**
	 * erzeugt die Oberfläche und bringt sie auf den Bildschirm
	 */
	public Starter()
	{
		setTitle("Uhren-Anzeiger");
		setSize(400, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		// der Button btnKreis erzeugt eine KreisUhr
		btnKreis = new JButton("Kreis");
		btnKreis.addActionListener(
				e -> kUhren.add(new KreisUhr()));
		add(btnKreis);
		
		//der Button btnDigital erzeugt eine DigitalUhr
		btnDigital = new JButton("Digital");
		btnDigital.addActionListener(
				e -> dUhren.add(new DigitalUhr()));
		add(btnDigital); 
		
		// der Button btnHalt löscht alle Uhranzeige-Fenster vom
		//Bildschirm und leert die beiden Listen
		btnHalt = new JButton("alle entfernen");
		btnHalt.addActionListener(e -> {for(KreisUhr k : kUhren) k.dispose();
						  for(DigitalUhr d : dUhren) d.dispose();
						  kUhren.clear();
						  dUhren.clear();
					});
		add(btnHalt);
		
		// Auf den Bildschirm bringen:
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new Starter();
	}

}
