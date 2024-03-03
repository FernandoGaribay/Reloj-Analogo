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

        BufferedImage imagenConSombra = new BufferedImage(anchoImagen, altoImagen, BufferedImage.TYPE_INT_ARGB);
        x = imagenConSombra.getWidth() / 2;
        y = imagenConSombra.getHeight() / 2;

        Graphics2D g2 = imagenConSombra.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        int radio = distancia * 2;
        for (int i = 0; i <= radio; i++) {
            int alpha = (int) (255 * transparencia * (1 - (double) (radio - i) / radio));
            diametro = anchoImagen - i * 2;
            int x = anchoImagen / 2;
            int y = altoImagen / 2;
            
            g2.setColor(new Color(0, 0, 0, alpha));
            g2.fillOval(x - diametro / 2, y - diametro / 2, diametro, diametro);
        }

        int diametroCirculo = (int) (anchoImagen - distancia * 2);
        int xCirculo = anchoImagen / 2;
        int yCirculo = altoImagen / 2;

        int xColor = anchoImagen / 2;
        int yColor = altoImagen / 2;
        Color colorOriginal = new Color(imagenOriginal.getRGB(xColor, yColor), true);

        g2.setColor(colorOriginal);
        g2.fillOval(xCirculo - diametroCirculo / 2, yCirculo - diametroCirculo / 2, diametroCirculo, diametroCirculo);

        g2.dispose();

        return imagenConSombra;
    }
}
