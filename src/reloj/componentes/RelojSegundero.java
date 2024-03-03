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

public class RelojSegundero implements Runnable {

    private final Reloj RELOJ;
    private final int DIAMETRO_RELOJ;
    private final int TAMANO_SEGUNDOS;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    private Thread hilo;

    private BufferedImage segundero;

    public RelojSegundero(Reloj RELOJ, int DIAMETRO_RELOJ, int TAMANO_SEGUNDOS) {
        this.RELOJ = RELOJ;
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;
        this.TAMANO_SEGUNDOS = TAMANO_SEGUNDOS;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;

        this.hilo = new Thread(this);

        this.segundero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        this.hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            this.segundero = dibujarSegundero();
            RELOJ.dibujarSegundero(segundero);

            // Delay
            try {
                System.out.println("Segundero dibujado");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RelojSegundero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public BufferedImage dibujarSegundero() {
        BufferedImage tempSegundero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2 = tempSegundero.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int segundo = Calendar.getInstance().get(Calendar.SECOND);
        float angulo = calcularAngulo(segundo);
        
        Point puntaAjuga = calcularPuntoAngulo(angulo, TAMANO_SEGUNDOS);
        Point colaAjuga = calcularPuntoAngulo(angulo + 180, (int) (TAMANO_SEGUNDOS * 0.35));
        Point[] puntosCola = calcularPuntosCola(angulo, TAMANO_SEGUNDOS);

        // Dibujado en el buffer
        dibujarSegundero(g2, puntaAjuga, colaAjuga, puntosCola);

        g2.dispose();
        return tempSegundero;
    }

    private float calcularAngulo(int segundo) {
        return segundo * 6 - 90;
    }

    private Point calcularPuntoAngulo(float angulo, int tamanio) {
        int x = (int) (tamanio * Math.cos(Math.toRadians(angulo)));
        int y = (int) (tamanio * Math.sin(Math.toRadians(angulo)));
        
        return new Point(x, y);
    }

    private Point[] calcularPuntosCola(float angulo, int tamanio) {
        Point punto1Cola = calcularPuntoAngulo(angulo + 180 - 4, (int) (tamanio * 0.4));
        Point punto2Cola = calcularPuntoAngulo(angulo + 180 + 3, (int) (tamanio * 0.4));
        Point punto3Cola = calcularPuntoAngulo(angulo + 180 - 15, (int) (tamanio * 0.1));
        Point punto4Cola = calcularPuntoAngulo(angulo + 180 + 14, (int) (tamanio * 0.1));
        
        return new Point[]{punto1Cola, punto2Cola, punto4Cola, punto3Cola};
    }

    private void dibujarSegundero(Graphics2D g2, Point puntaAjuga, Point colaAjuga, Point[] puntosCola) {
        g2.setColor(new Color(255, 119, 119));
        g2.setStroke(new BasicStroke(3));

        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + puntaAjuga.x, CENTRO_Y + puntaAjuga.y);
        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + colaAjuga.x, CENTRO_Y + colaAjuga.y);

        int[] xPoints = {CENTRO_X + puntosCola[0].x, CENTRO_X + puntosCola[1].x, CENTRO_X + puntosCola[2].x, CENTRO_X + puntosCola[3].x};
        int[] yPoints = {CENTRO_Y + puntosCola[0].y, CENTRO_Y + puntosCola[1].y, CENTRO_Y + puntosCola[2].y, CENTRO_Y + puntosCola[3].y};
        
        g2.fillPolygon(xPoints, yPoints, 4);
    }
}
