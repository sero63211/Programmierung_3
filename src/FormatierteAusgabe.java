import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;

public class FormatierteAusgabe {

    public static void main(String[] args) throws IOException {
         try (FileWriter fileWriter = new FileWriter("output.txt")) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            Scanner scanner = new Scanner(System.in);

            System.out.print("Bitte geben Sie eine ganze Zahl ein: ");
            long ganzZahl = scanner.nextInt();
            System.out.print("Bitte geben Sie eine Zahl ein: ");
            double kommaZahl = scanner.nextDouble();

            LocalDate heute = LocalDate.now();
            LocalTime jetzt = LocalTime.now();

            printWriter.printf("%d%n", ganzZahl);

            if (String.valueOf(ganzZahl).length() < 12) {
                printWriter.printf("%012d%n", ganzZahl);
            } else {
                printWriter.printf("%d%n", ganzZahl);
            }
            printWriter.printf("%+,d%n", ganzZahl);
            printWriter.printf("%x%n",ganzZahl);
            printWriter.printf("%f%n", kommaZahl);
            printWriter.printf("%+.5f%n", kommaZahl);
            printWriter.printf("%e%n", kommaZahl);
            printWriter.printf(Locale.US, "%,.2f%n", kommaZahl);
            printWriter.printf("%1$tA %1$te %1$tB %1$tY %n", heute);

            printWriter.printf("%tA %te %tB %tY%n", heute, heute, heute, heute);
            printWriter.printf(Locale.ITALY, "%tA %te %tB %tY%n", heute, heute, heute, heute);

            printWriter.printf("%tl:%tM %tp%n", jetzt, jetzt, jetzt);


            printWriter.flush();
            printWriter.close();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
    }
