package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Sombra {

    private static int diametro;
    private static int x;
    private static int y;

    public static BufferedImage agregarSombra(BufferedImage imagenOriginal, int distancia, float transparencia) {
        int anchoImagen = imagenOriginal.getWidth();
        int altoImagen = imagenOriginal.getHeight();

        BufferedImage imagenConSombra = new BufferedImage(anchoImagen + distancia * 4, altoImagen + distancia * 4, BufferedImage.TYPE_INT_ARGB);
        x = imagenConSombra.getWidth() / 2;
        y = imagenConSombra.getHeight() / 2;

        Graphics2D g2 = imagenConSombra.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear un degradado de transparencia para la sombra
        int radio = distancia * 2;
        for (int i = radio; i >= 0; i--) {
            int alpha = (int) (255 * transparencia * (1 - (double) i / radio));
            diametro = anchoImagen + i * 2;
            g2.setColor(new Color(0, 0, 0, alpha));

            g2.fillOval(x - diametro / 2, y - diametro / 2, diametro, diametro);
        }

        // Dibujar la imagen original sobre la sombra
        g2.drawImage(imagenOriginal, x - diametro / 2, y - diametro / 2, null);
        g2.dispose();

        return imagenConSombra;
    }
}
