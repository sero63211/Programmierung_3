package bankprojekt.verarbeitung;

/**
 * Sammlung aller verschiedenen Kontoarten, die wir anbieten
 * @author Admin
 *
 */
public enum Kontoart {
	GIROKONTO("Total viel Dispo"), 
	SPARBUCH("Ganz viele Zinsen"), 
	FESTGELDKONTO("Ham wa eigentlich jar nich");
	
	private String werbebotschaft;
	
	Kontoart(String werbebotschaft) //automatisch private
	{
		this.werbebotschaft = werbebotschaft;
	}
	
	/**
	 * liefert die Werbebotschaft zu dieser Kontoart
	 * @return
	 */
	public String getWerbung()
	{
		return this.werbebotschaft;
	}
}
