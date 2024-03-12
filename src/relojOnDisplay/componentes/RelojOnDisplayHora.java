package relojOnDisplay.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import relojOnDisplay.RelojOnDisplay;

public class RelojOnDisplayHora implements Runnable {

    private final RelojOnDisplay RELOJ;
    private boolean RUNNING;
    private final int DIAMETRO_RELOJ;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    private Thread hilo;
    private BufferedImage hora;

    public RelojOnDisplayHora(RelojOnDisplay RELOJ, int DIAMETRO_RELOJ) {
        this.RELOJ = RELOJ;
        this.RUNNING = true;
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2 - 30;

        this.hilo = new Thread(this);
        this.hilo.start();
    }

    @Override
    public void run() {
        while (RUNNING) {
//            System.out.println("Hora corriendo");
            this.hora = dibujarReloj();
            RELOJ.dibujarHora(hora);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RelojOnDisplaySegundero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public BufferedImage dibujarReloj() {
        BufferedImage reloj = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) reloj.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(dibujarHora(), 0, 0, null);

        g2.dispose();

//        System.out.println("Horra del reloj dibujado.");
        return reloj;
    }

    public BufferedImage dibujarHora() {
        BufferedImage hora = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        Font fuenteHora = new Font("Arial", Font.BOLD, 15);

        // Obtenemos el objeto de Graphics2D
        Graphics2D g2 = (Graphics2D) hora.getGraphics();
        g2.setColor(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point punto = calcularCoordenada(90, CENTRO_Y);
        LocalTime ahora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String horaFormateada = ahora.format(formatter);

        g2.setFont(fuenteHora);

        FontMetrics metrics = g2.getFontMetrics(fuenteHora);
        int anchoTexto = metrics.stringWidth(horaFormateada);

        g2.drawString(horaFormateada, CENTRO_X - anchoTexto / 2, CENTRO_Y + punto.y + 25);

        g2.dispose();

        return hora;
    }

    public void pararHora() {
        RUNNING = false;
    }

    private Point calcularCoordenada(float angle, int size) {
        int x = (int) (size * Math.cos(Math.toRadians(angle)));
        int y = (int) (size * Math.sin(Math.toRadians(angle)));

        return new Point(x, y);
    }
}
