package relojQuartz.componentes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class RelojQuartzClavo {

    private final int DIAMETRO_RELOJ;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    public RelojQuartzClavo(int DIAMETRO_RELOJ) {
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;
    }

    public BufferedImage dibujarClavo() {
        BufferedImage clavo = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = clavo.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Detalles del clavo
        g2.setColor(new Color(255, 255, 153));
        g2.fillOval(CENTRO_X - 6, CENTRO_Y - 6, 12, 12);
        
        g2.setColor(new Color(153, 153, 0));
        g2.fillOval(CENTRO_X - 5, CENTRO_Y - 5, 10, 10);
        
        g2.setColor(new Color(255, 255, 153));
        g2.fillOval(CENTRO_X - 3, CENTRO_Y - 3, 6, 6);

        return clavo;
    }
}
