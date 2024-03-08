package recursos;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calendario implements Runnable {

    private static Thread hilo;
    private static int multiplicador;
    private static int delay;

    private static int hora;
    private static int minuto;
    private static int segundo;
    private static int milisegundo;

    public Calendario() {
        this.hilo = new Thread(this);
        this.delay = 1000;
        this.multiplicador = 1;

        this.hora = Calendar.getInstance().get(Calendar.HOUR);
        this.minuto = Calendar.getInstance().get(Calendar.MINUTE);
        this.segundo = Calendar.getInstance().get(Calendar.SECOND);
        this.milisegundo = Calendar.getInstance().get(Calendar.MILLISECOND);
    }

    public Calendario(int multiplicador) {
        this();
        this.multiplicador = multiplicador;

        this.hilo.start();
    }

    public Calendario(int hora, int minuto, int segundo) {
        this();
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;

        this.hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            this.milisegundo += 10;
            if (this.milisegundo >= 1000) {
                this.milisegundo %= 1000;
                this.segundo++;
            }

            // Actualizar hora, minuto y segundo
            if (segundo == 59) {
                this.minuto++;
                this.segundo = 0;
            }
            if (minuto == 59) {
                this.hora++;
                this.minuto = 0;
            }
            if (hora == 12) {
                this.hora = 0;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void establecerMultiplicador(int multiplicador) {
        if (multiplicador <= 0) {
            throw new IllegalArgumentException("El multiplicador debe ser mayor que cero.");
        }
        this.multiplicador = multiplicador;
    }

    public static int getHora() {
        return hora;
    }

    public static int getMinuto() {
        return minuto;
    }

    public static int getSegundo() {
        return segundo;
    }

    public static int getMilisegundo() {
        return milisegundo;
    }

    public static void main(String[] args) {
        Calendario calendario = new Calendario(10,58,0);

        while (true) {
            System.out.println("Hora: " + calendario.getHora());
            System.out.println("Minuto: " + calendario.getMinuto());
            System.out.println("Segundo: " + calendario.getSegundo());
            System.out.println("Milisegundo: " + calendario.getMilisegundo());
            System.out.println("");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

/*

            this.delay = 1000 / multiplicador;

            // Actualizar hora, minuto y segundo
            if (segundo == 59) {
                this.minuto++;
                this.segundo = 0;
            }
            if (minuto == 59) {
                this.hora++;
                this.minuto = 0;
            }
            if (hora == 12 && minuto == 59 && segundo == 59) {
                this.hora = 0;
            } else {
                this.segundo++;
            }

            // Actualizar milisegundo
            this.milisegundo += delay % 1000;
            if (this.milisegundo >= 1000) {
                this.milisegundo %= 1000;
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
            }

 */
