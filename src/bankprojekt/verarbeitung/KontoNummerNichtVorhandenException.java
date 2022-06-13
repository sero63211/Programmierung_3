package bankprojekt.verarbeitung;


public class KontoNummerNichtVorhandenException extends Exception {
    public KontoNummerNichtVorhandenException() { super(); }
    public KontoNummerNichtVorhandenException(String message) { super(message); }
    public KontoNummerNichtVorhandenException(String message, Throwable t) { super(message, t); }
}
