package reloj.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import reloj.Sombra;

public class RelojBackground {

    private final int DIAMETRO_RELOJ;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    public RelojBackground(int DIAMETRO_RELOJ) {
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;
    }

    public BufferedImage dibujarReloj() {
        BufferedImage reloj = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) reloj.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(dibujarBackground(), 0, 0, null);
        g2.drawImage(dibujarNumeros(), 0, 0, null);
        g2.dispose();

        System.out.println("Background del reloj dibujado.");
        return reloj;
    }

    private BufferedImage dibujarBackground() {
        BufferedImage background = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Obtenemos el objeto de Graphics2D
        Graphics2D g2 = (Graphics2D) background.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Primer circulo del reloj
        BufferedImage circulo1 = crearCirculo(Color.WHITE, DIAMETRO_RELOJ);
        circulo1 = Sombra.agregarSombra(circulo1, 20, 0.05f);

        // Segundo circulo del reloj
        BufferedImage circulo2 = crearCirculo(Color.WHITE, (int) (DIAMETRO_RELOJ * 0.65));
        circulo2 = Sombra.agregarSombra(circulo2, 20, 0.025f);

        // Dibujamos en el Buffer
        g2.setColor(Color.black);
        g2.drawImage(circulo1, background.getWidth() / 2 - circulo1.getWidth() / 2, background.getHeight() / 2 - circulo1.getWidth() / 2, null);
        g2.drawImage(circulo2, background.getWidth() / 2 - circulo2.getWidth() / 2, background.getHeight() / 2 - circulo2.getWidth() / 2, null);
        g2.dispose();

        return background;
    }

    public BufferedImage dibujarNumeros() {
        Font fuenteNumero = new Font("Arial", Font.PLAIN, 30);
        BufferedImage numeros = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Obtenemos el objeto de Graphics2D y establecemos su fuente
        Graphics2D g2 = (Graphics2D) numeros.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(fuenteNumero);
        
        // Imprimimos los numeros
        for (int i = 0; i < 12; i++) {
            Point cordsNumeros = calcularCoordenada((i + 1) * 30 - 90, (int) (DIAMETRO_RELOJ * 0.37));
            String texto = String.valueOf(i + 1);

            FontMetrics metrics = g2.getFontMetrics(fuenteNumero);
            int anchoTexto = metrics.stringWidth(texto);
            int alturaTexto = metrics.getAscent() - metrics.getDescent();

            int numeroCentroX = CENTRO_X + cordsNumeros.x - anchoTexto / 2;
            int numeroCentroY = CENTRO_Y + cordsNumeros.y + alturaTexto / 2;

            g2.setColor(Color.BLACK);
//            g2.drawRect(numeroCentroX, CENTRO_Y - alturaTexto / 2 + cordsNumeros.y, anchoTexto, alturaTexto);
            g2.drawString(texto, numeroCentroX, numeroCentroY);
        }
        g2.dispose();

        return numeros;
    }

    private BufferedImage crearCirculo(Color color_bg, int diametro) {
        BufferedImage circulo = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = circulo.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(color_bg);
        g2.fillOval(0, 0, diametro, diametro);
        g2.dispose();

        return circulo;
    }

    private Point calcularCoordenada(float angle, int size) {
        int x = (int) (size * Math.cos(Math.toRadians(angle)));
        int y = (int) (size * Math.sin(Math.toRadians(angle)));

        return new Point(x, y);
    }
}