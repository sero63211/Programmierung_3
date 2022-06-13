package bankprojekt.verarbeitung;

import java.util.function.Function;

/**
 * Klasse Funktion: Nullstellensuche durch das Bisektionsverfahren.
 */
public class Funktion {

    static final float GENAUIGKEIT = 0.01F; //Genauigkeit
    /**
     * Suche nach der ersten Nullstelle einer gegebenen Funktion
     * @param funktion die Funktion
     * @param a Intervall
     * @param b Intervall
     * @return Ergebnis
     * @throws ArithmeticException falls keine Nullstelle gefunden wird
     */
    public static float suche(Function<Float, Float>funktion, float a, float b) throws ArithmeticException{
        float m = 0, yInA, yInB, yInM;
        yInA = funktion.apply(a);
        yInB = funktion.apply(b);
        if(yInA == 0) return a;
        if(yInB == 0) return b;
        while(Math.abs(a-b)> GENAUIGKEIT){
            m = (a+b)/2f;
            yInM = funktion.apply(m);
            if(yInM == 0) return m;
            if(funktion.apply(a)*yInM < 0){
                b=m;
            } else {
                a=m;
            }
        }
        //hier Intervall pruefen - wenn f flach ist kann f 0.1 schon �berschneiden und erst eine Nullstelle in 50 haben
        if(funktion.apply(m) <= GENAUIGKEIT) return m;
        else throw new ArithmeticException("Keine Nullstelle gefunden!");
    }


}
