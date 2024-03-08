package reloj.componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import recursos.Calendario;
import reloj.Reloj;

public class RelojMinutero implements Runnable {

    private final Reloj RELOJ;
    private final Calendario objCalendario;
    private final int DIAMETRO_RELOJ;
    private final int TAMANO_MINUTOS;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    private Thread hilo;
    private float anguloActual;
    private int delay;
    private float periodo;

    private long milisDiff;
    private BufferedImage minutero;

    public RelojMinutero(Reloj RELOJ, int DIAMETRO_RELOJ, int TAMANO_MINUTOS) {
        this.RELOJ = RELOJ;
        this.objCalendario = new Calendario(4);
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;
        this.TAMANO_MINUTOS = TAMANO_MINUTOS;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;

        this.hilo = new Thread(this);
        this.anguloActual = calcularAngulo(Calendar.getInstance().get(Calendar.MINUTE));
        this.setAtomico(true);

        this.minutero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        this.hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            this.minutero = dibujarMinutero(this.anguloActual);
            RELOJ.dibujarMinutero(minutero);

            this.anguloActual = avanzarAngulo(this.anguloActual);
            this.isAtomico();
            
            try {
//                System.out.println("Minutero dibujado");
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(RelojSegundero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public float avanzarAngulo(float anguloActual) {
        int gradosPorMinuto = 6;
        float gradosPorIntervalo = gradosPorMinuto * periodo; // avanzar 0.6 grados cada 100 ms
        anguloActual += gradosPorIntervalo;

        if (anguloActual >= 360) {
            anguloActual -= 360;
        }
        return anguloActual;
    }

    public BufferedImage dibujarMinutero(float angulo) {
        BufferedImage tempMinutero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = tempMinutero.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point[] puntos = calcularPuntos(angulo, TAMANO_MINUTOS);

        dibujarMinutero(g2, puntos);

        g2.dispose();
        return tempMinutero;
    }

    private float calcularAngulo(int minuto) {
        return minuto * 6 - 90;
    }

    private void dibujarMinutero(Graphics2D g2, Point[] puntos) {
        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(2));

        int[] xPuntos = {CENTRO_X + puntos[0].x,
            CENTRO_X + puntos[1].x,
            CENTRO_X + puntos[2].x,
            CENTRO_X + puntos[3].x,};
        int[] yPuntos = {CENTRO_Y + puntos[0].y,
            CENTRO_Y + puntos[1].y,
            CENTRO_Y + puntos[2].y,
            CENTRO_Y + puntos[3].y,};

//        g2.setStroke(new BasicStroke(1));
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[0], yPoints[0]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[1], yPoints[1]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[2], yPoints[2]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[3], yPoints[3]);
        g2.fillPolygon(xPuntos, yPuntos, 4);
    }

    private Point[] calcularPuntos(float angulo, int tamanio) {
        Point punto0Cola = calcularCoordenada(angulo, (int) (tamanio * 1));
        Point punto1Cola = calcularCoordenada(angulo + 110, (int) (tamanio * 0.07));
        Point punto2Cola = calcularCoordenada(angulo + 180, (int) (tamanio * 0.15));
        Point punto3Cola = calcularCoordenada(angulo - 110, (int) (tamanio * 0.07));

        return new Point[]{punto0Cola, punto1Cola, punto2Cola, punto3Cola};
    }

    private Point calcularCoordenada(float angulo, int tamanio) {
        int x = (int) (tamanio * Math.cos(Math.toRadians(angulo)));
        int y = (int) (tamanio * Math.sin(Math.toRadians(angulo)));

        return new Point(x, y);
    }

    public void setAtomico(boolean atomico) {
        if (atomico) {
            this.delay = 3000;
            this.periodo = 0.05f;

            int seg = Calendar.getInstance().get(Calendar.SECOND);
            for (int i = 0; i < seg / 3; i++) {
                this.anguloActual = avanzarAngulo(this.anguloActual);
            }
        } else {
            this.periodo = 1.0f;

            if (Calendar.getInstance().get(Calendar.SECOND) == 0) {
                this.delay = 60000;
            } else {
                this.delay = 60000 - (Calendar.getInstance().get(Calendar.SECOND) * 1000);
            }
        }
    }

    public void isAtomico() {
        if (this.periodo == 0.05f) {
            this.delay = 3000;
            this.periodo = 0.05f;
        } else {
            this.delay = 60000;
            this.periodo = 1.0f;
        }
    }
}
