package bankprojekt.verarbeitung;

public class NichtGenugGeldAufKontoException extends Exception {
    public NichtGenugGeldAufKontoException() { super(); }
    public NichtGenugGeldAufKontoException(String message) { super(message); }
    public NichtGenugGeldAufKontoException(String message, Throwable t) { super(message, t); }
}