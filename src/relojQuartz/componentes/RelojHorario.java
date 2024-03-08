package relojQuartz.componentes;

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
import relojQuartz.Reloj;

public class RelojHorario implements Runnable {

    private final Reloj RELOJ;
    private boolean RUNNING;
    private final Calendario objCalendario;
    private final int DIAMETRO_RELOJ;
    private final int TAMANO_HORARIO;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    private Thread hilo;
    private float anguloActual;
    private int delay;
    private float periodo;

    private BufferedImage horario;

    public RelojHorario(Reloj RELOJ, int DIAMETRO_RELOJ, int TAMANO_HORARIO, boolean atomico) {
        this.RELOJ = RELOJ;
        this.RUNNING = true;
        this.objCalendario = new Calendario(4);
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;
        this.TAMANO_HORARIO = TAMANO_HORARIO;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;

        this.hilo = new Thread(this);
        this.anguloActual = calcularAngulo(Calendar.getInstance().get(Calendar.HOUR));
        this.setAtomico(atomico);

        this.horario = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        this.hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Horario corriendo");
            this.horario = dibujarHorario(this.anguloActual);
            RELOJ.dibujarHorario(horario);

            this.anguloActual = avanzarAngulo(this.anguloActual);

            try {
                Thread.sleep(delay);
                this.isAtomico();
            } catch (InterruptedException ex) {
                Logger.getLogger(RelojSegundero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public float avanzarAngulo(float anguloActual) {
        int gradosPorHora = 30;
        float gradosPorIntervalo = gradosPorHora * periodo;
        anguloActual += gradosPorIntervalo;

        if (anguloActual >= 360) {
            anguloActual -= 360;
        }
        return anguloActual;
    }

    private float calcularAngulo(int hora) {
        return hora * 30 - 90;
    }

    public BufferedImage dibujarHorario(float angulo) {
        BufferedImage tempHorario = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = tempHorario.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point[] puntos = calcularPuntos(angulo, TAMANO_HORARIO);

        dibujarHorario(g2, puntos);

        g2.dispose();
        return tempHorario;
    }

    private void dibujarHorario(Graphics2D g2, Point[] puntos) {
        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(2));

        int[] xPoints = {CENTRO_X + puntos[0].x,
            CENTRO_X + puntos[1].x,
            CENTRO_X + puntos[2].x,
            CENTRO_X + puntos[3].x,};
        int[] yPoints = {CENTRO_Y + puntos[0].y,
            CENTRO_Y + puntos[1].y,
            CENTRO_Y + puntos[2].y,
            CENTRO_Y + puntos[3].y,};

//        g2.setStroke(new BasicStroke(1));
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[0], yPoints[0]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[1], yPoints[1]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[2], yPoints[2]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[3], yPoints[3]);
        g2.fillPolygon(xPoints, yPoints, 4);
    }

    private Point[] calcularPuntos(float angulo, int tamanio) {
        Point punto0Cola = calcularCoordenada(angulo, (int) (tamanio * 1));
        Point punto1Cola = calcularCoordenada(angulo + 110, (int) (tamanio * 0.115));
        Point punto2Cola = calcularCoordenada(angulo + 180, (int) (tamanio * 0.25));
        Point punto3Cola = calcularCoordenada(angulo - 110, (int) (tamanio * 0.115));

        return new Point[]{punto0Cola, punto1Cola, punto2Cola, punto3Cola};
    }

    private Point calcularCoordenada(float angulo, int tamanio) {
        int x = (int) (tamanio * Math.cos(Math.toRadians(angulo)));
        int y = (int) (tamanio * Math.sin(Math.toRadians(angulo)));

        return new Point(x, y);
    }

    public void setAtomico(boolean atomico) {
        if (atomico) {
            this.delay = 180000;
            this.periodo = 0.05f;

            int min = Calendar.getInstance().get(Calendar.MINUTE);
            for (int i = 0; i < min / 3; i++) {
                this.anguloActual = avanzarAngulo(this.anguloActual);
            }
        } else {
            this.periodo = 1.0f;
            this.delay = 3600000 - (Calendar.getInstance().get(Calendar.MINUTE) * 60000);
        }
    }

    public void pararHorario() {
        RUNNING = false;
    }

    public void isAtomico() {
        if (this.periodo == 0.05f) {
            this.delay = 180000;
            this.periodo = 0.05f;
        } else {
            this.delay = 3600000;
            this.periodo = 1.0f;
        }
    }
}
