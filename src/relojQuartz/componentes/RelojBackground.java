package relojQuartz.componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import recursos.Sombra;

public class RelojBackground {

    private final int DIAMETRO_RELOJ;
    private final int DIAMETRO_CIRCULO_BG;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    public RelojBackground(int DIAMETRO_RELOJ) {
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;
        this.DIAMETRO_CIRCULO_BG = (int) (DIAMETRO_RELOJ * 0.37);

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2;
    }

    public BufferedImage dibujarReloj() {
        BufferedImage reloj = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) reloj.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(dibujarBackground(), 0, 0, null);
        g2.drawImage(dibujarNumeros(), 0, 0, null);
        g2.drawImage(dibujarRayas(), 0, 0, null);
        g2.drawImage(dibujarMarca(), 0, 0, null);
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
        Font fuenteNumero = new Font("Arial", Font.PLAIN, (int) (DIAMETRO_CIRCULO_BG * 0.2));
        BufferedImage numeros = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Obtenemos el objeto de Graphics2D y establecemos su fuente
        Graphics2D g2 = (Graphics2D) numeros.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(fuenteNumero);
        g2.setColor(Color.BLACK);

        // Imprimimos los numeros
        for (int i = 0; i < 12; i++) {
            Point cordsNumeros = calcularCoordenada((i + 1) * 30 - 90, DIAMETRO_CIRCULO_BG);
            String texto = String.valueOf(i + 1);

            FontMetrics metrics = g2.getFontMetrics(fuenteNumero);
            int anchoTexto = metrics.stringWidth(texto);
            int alturaTexto = metrics.getAscent() - metrics.getDescent();

            int numeroCentroX = CENTRO_X + cordsNumeros.x - anchoTexto / 2;
            int numeroCentroY = CENTRO_Y + cordsNumeros.y + alturaTexto / 2;

//            g2.drawRect(numeroCentroX, CENTRO_Y - alturaTexto / 2 + cordsNumeros.y, anchoTexto, alturaTexto);
            g2.drawString(texto, numeroCentroX, numeroCentroY);
        }
        g2.dispose();

        return numeros;
    }

    public BufferedImage dibujarRayas() {
        BufferedImage rayas = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) rayas.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));

        for (int i = 0; i < 12; i++) {
            Point cordsInicio = calcularCoordenada(i * 30 - 90, (int) (DIAMETRO_CIRCULO_BG * 0.75));
            Point cordsFinal = calcularCoordenada(i * 30 - 90, (int) (DIAMETRO_CIRCULO_BG * 0.65));

            g2.drawLine(CENTRO_X + cordsInicio.x, CENTRO_Y + cordsInicio.y, CENTRO_X + cordsFinal.x, CENTRO_Y + cordsFinal.y);
        }

        for (int i = 0; i < 60; i++) {
            if (i % 5 != 0) {
                Point cordsPunto = calcularCoordenada(i * 6 - 90, (int) (DIAMETRO_CIRCULO_BG * 0.70));
                g2.fillOval(CENTRO_X + cordsPunto.x - 2, CENTRO_Y + cordsPunto.y - 2, 4, 4);
            }
        }

        return rayas;
    }

    public BufferedImage dibujarMarca() {
        BufferedImage marca = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        Font fuenteMarca = new Font("Arial", Font.BOLD, 12);

        Graphics2D g2 = (Graphics2D) marca.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setFont(fuenteMarca);

        FontMetrics metrics = g2.getFontMetrics(fuenteMarca);
        Point cordsNumeros = calcularCoordenada(90, (int) (DIAMETRO_CIRCULO_BG * 0.45));
        int anchoTexto = metrics.stringWidth("QUARTZ");
        int alturaTexto = metrics.getAscent() - metrics.getDescent();

        int numeroCentroX = CENTRO_X + cordsNumeros.x - anchoTexto / 2;
        int numeroCentroY = CENTRO_Y + cordsNumeros.y + alturaTexto / 2;
        g2.drawString("QUARTZ", numeroCentroX, numeroCentroY);

        return marca;
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
