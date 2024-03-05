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
import reloj.Reloj;

public class RelojHorario implements Runnable {

    private final Reloj RELOJ;
    private final int DIAMETRO_RELOJ;
    private final int TAMANO_HORARIO;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    private Thread hilo;

    private BufferedImage horario;

    public RelojHorario(Reloj RELOJ, int DIAMETRO_RELOJ, int TAMANO_HORARIO) {
        this.RELOJ = RELOJ;
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;
        this.TAMANO_HORARIO = TAMANO_HORARIO;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;

        this.hilo = new Thread(this);

        this.horario = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        this.hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            this.horario = RelojHorario.this.dibujarHorario();
            RELOJ.dibujarHorario(horario);

            // Delay
            try {
//                System.out.println("Horario dibujado");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RelojSegundero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public BufferedImage dibujarHorario() {
        BufferedImage tempHorario = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        int horario = Calendar.getInstance().get(Calendar.HOUR);
//        int horario = 0;
        float angulo = horario * 30 - 90;

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = tempHorario.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point point = calcularCoordenada(angulo, TAMANO_HORARIO);
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
}
