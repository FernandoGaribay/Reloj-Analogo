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
    private float anguloActual;
    private int delay;
    private float periodo;
    
    private BufferedImage segundero;
    
    public RelojSegundero(Reloj RELOJ, int DIAMETRO_RELOJ, int TAMANO_SEGUNDOS) {
        this.RELOJ = RELOJ;
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;
        this.TAMANO_SEGUNDOS = TAMANO_SEGUNDOS;
        
        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;
        
        this.hilo = new Thread(this);
        this.anguloActual = calcularAngulo(Calendar.getInstance().get(Calendar.SECOND));
        this.setAtomico(false);
        
        this.segundero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        
        this.hilo.start();
    }
    
    @Override
    public void run() {
        while (true) {
            this.anguloActual = avanzarAngulo(this.anguloActual);
            this.segundero = dibujarSegundero(this.anguloActual);
            RELOJ.dibujarSegundero(segundero);

            // Delay
            try {
                System.out.println("Segundero dibujado");
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(RelojSegundero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public float avanzarAngulo(float anguloActual) {
        float gradosPorSegundo = 6.0f;
        float gradosPorIntervalo = gradosPorSegundo * periodo; // avanzar 6 grados cada 100 ms
        anguloActual += gradosPorIntervalo;
        
        if (anguloActual >= 360) {
            anguloActual = 0;
        }
        return anguloActual;
    }
    
    public BufferedImage dibujarSegundero(float angulo) {
        BufferedImage tempSegundero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2 = tempSegundero.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Point puntaAjuga = calcularCoordenada(angulo, TAMANO_SEGUNDOS);
        Point colaAjuga = calcularCoordenada(angulo + 180, (int) (TAMANO_SEGUNDOS * 0.25));
        Point[] puntosCola = calcularPuntosCola(angulo, TAMANO_SEGUNDOS);

        // Dibujado en el buffer
        dibujarSegundero(g2, puntaAjuga, colaAjuga, puntosCola);
        
        g2.dispose();
        return tempSegundero;
    }
    
    private float calcularAngulo(int segundo) {
        return segundo * 6 - 90;
    }
    
    private void dibujarSegundero(Graphics2D g2, Point puntaAjuga, Point colaAjuga, Point[] puntosCola) {
        g2.setColor(new Color(255, 120, 120));
        g2.setStroke(new BasicStroke(3));
        
        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + puntaAjuga.x, CENTRO_Y + puntaAjuga.y);
        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + colaAjuga.x, CENTRO_Y + colaAjuga.y);
        
        int[] xPoints = {CENTRO_X + puntosCola[0].x,
            CENTRO_X + puntosCola[1].x,
            CENTRO_X + puntosCola[2].x,
            CENTRO_X + puntosCola[3].x,
            CENTRO_X + puntosCola[4].x,
            CENTRO_X + puntosCola[5].x,};
        int[] yPoints = {CENTRO_Y + puntosCola[0].y,
            CENTRO_Y + puntosCola[1].y,
            CENTRO_Y + puntosCola[2].y,
            CENTRO_Y + puntosCola[3].y,
            CENTRO_Y + puntosCola[4].y,
            CENTRO_Y + puntosCola[5].y,};

//        g2.setStroke(new BasicStroke(1));
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[0], yPoints[0]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[1], yPoints[1]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[2], yPoints[2]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[3], yPoints[3]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[4], yPoints[4]);
//        g2.drawLine(CENTRO_X, CENTRO_Y, xPoints[5], yPoints[5]);
        g2.fillPolygon(xPoints, yPoints, 6);
    }
    
    private Point[] calcularPuntosCola(float angulo, int tamanio) {
        Point punto0Cola = calcularCoordenada(angulo + 180, (int) (tamanio * 0.07));
        Point punto1Cola = calcularCoordenada(angulo + 180 + 11, (int) (tamanio * 0.15));
        Point punto2Cola = calcularCoordenada(angulo + 180 + 5, (int) (tamanio * 0.3));
        Point punto3Cola = calcularCoordenada(angulo + 180 - 5, (int) (tamanio * 0.3));
        Point punto4Cola = calcularCoordenada(angulo + 180 - 11, (int) (tamanio * 0.15));
        Point punto5Cola = calcularCoordenada(angulo + 180, (int) (tamanio * 0.07));
        
        return new Point[]{punto0Cola, punto1Cola, punto2Cola, punto3Cola, punto4Cola, punto5Cola};
    }
    
    private Point calcularCoordenada(float angulo, int tamanio) {
        int x = (int) (tamanio * Math.cos(Math.toRadians(angulo)));
        int y = (int) (tamanio * Math.sin(Math.toRadians(angulo)));
        
        return new Point(x, y);
    }
    
    public void setAtomico(boolean atomico) {
        if (atomico) {
            this.delay = 100;
            this.periodo = 0.1f;
        } else {
            this.delay = 1000;
            this.periodo = 1.0f;
        }
    }
}
