package relojOnDisplay.componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import recursos.Sombra;

public class RelojOnDisplayBackground {

    private final int DIAMETRO_RELOJ;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    public RelojOnDisplayBackground(int DIAMETRO_RELOJ) {
        this.DIAMETRO_RELOJ = DIAMETRO_RELOJ;

        this.CENTRO_X = this.DIAMETRO_RELOJ / 2;
        this.CENTRO_Y = this.DIAMETRO_RELOJ / 2 - 30;
    }

    public BufferedImage dibujarReloj() {
        BufferedImage reloj = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) reloj.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(dibujarBackground(), 0, 0, null);
        g2.drawImage(dibujarFecha(), 0, 0, null);

        g2.dispose();

        System.out.println("Background del reloj dibujado.");
        return reloj;
    }

    private BufferedImage dibujarBackground() {
        BufferedImage background = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Obtenemos el objeto de Graphics2D
        Graphics2D g2 = (Graphics2D) background.getGraphics();
        g2.setColor(Color.black);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(crearEspiralDeCirculos(0.02f, 0.0f, 20, 28, 0, Color.BLUE), 0, 0, null);
        g2.drawImage(crearEspiralDeCirculos(0.022f, 0.0f, 20, 40, 3, Color.BLUE), 0, 0, null);
        g2.drawImage(crearEspiralDeCirculos(0.025f, 0.0f, 24, 52, 6, Color.BLUE), 0, 0, null);
        g2.drawImage(crearEspiralDeCirculos(0.027f, 0.0f, 24, 67, 9, Color.BLUE), 0, 0, null);
        g2.drawImage(crearEspiralDeCirculos(0.029f, 0.00045f, 36, 85, 12, Color.BLUE), 0, 0, null);
        g2.drawImage(crearEspiralDeCirculos(0.035f, 0.0007f, 36, 108, 15, Color.BLUE), 0, 0, null);
        g2.drawImage(crearEspiralDeCirculos(0.035f, 0.001f, 36, 133, 18, Color.BLUE), 0, 0, null);
        g2.drawImage(crearEspiralDeCirculos(0.04f, 0.0013f, 36, 160, 21, Color.BLUE), 0, 0, null);

        g2.dispose();

        return background;
    }

    private BufferedImage dibujarFecha() {
        BufferedImage fecha = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        Font fuenteFecha = new Font("Arial", Font.PLAIN, 20);

        // Obtenemos el objeto de Graphics2D
        Graphics2D g2 = (Graphics2D) fecha.getGraphics();
        g2.setColor(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point punto = calcularCoordenada(90, CENTRO_Y);
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
        String fechaFormateada = hoy.format(formatter);

        FontMetrics metrics = g2.getFontMetrics(fuenteFecha);
        int anchoTexto = metrics.stringWidth(fechaFormateada);

        g2.setFont(fuenteFecha);
        g2.drawString(fechaFormateada, CENTRO_X + punto.x - anchoTexto / 2, CENTRO_Y + punto.y);

        g2.dispose();

        return fecha;
    }
    
    private BufferedImage crearEspiralDeCirculos(float diametro_inicio, float aumeto_diametro, int numCirculos, int longitud_centro, int recorrido, Color color) {
        BufferedImage espiral = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) espiral.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float aumento = diametro_inicio;
        for (int i = 0; i < numCirculos / 2; i++) {
            int angulo = (i + recorrido) * (360 / numCirculos);
            aumento += aumeto_diametro;
            Point punto = calcularCoordenada(angulo, longitud_centro);

            BufferedImage circulos = crearCirculo(color, (int) (DIAMETRO_RELOJ * aumento), angulo);
            circulos = Sombra.agregarSombra(circulos, 5, 0.8f, Color.BLUE);

            g2.drawImage(circulos, CENTRO_X + punto.x - circulos.getWidth() / 2, CENTRO_Y + punto.y - circulos.getHeight() / 2, null);
        }

        for (int i = numCirculos / 2; i < numCirculos; i++) {
            int angulo = (i + recorrido) * (360 / numCirculos);
            aumento -= aumeto_diametro;
            Point punto = calcularCoordenada(angulo, longitud_centro);

            BufferedImage circulos = crearCirculo(color, (int) (DIAMETRO_RELOJ * aumento), angulo);
            circulos = Sombra.agregarSombra(circulos, 5, 0.8f, Color.BLUE);

            g2.drawImage(circulos, CENTRO_X + punto.x - circulos.getWidth() / 2, CENTRO_Y + punto.y - circulos.getHeight() / 2, null);
        }

        return espiral;
    }

    private BufferedImage crearCirculo(Color color_bg, int diametoCirculo, int angulo) {
        BufferedImage circulo = new BufferedImage(diametoCirculo, diametoCirculo, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = circulo.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(color_bg);
        g2.fillOval(0, 0, diametoCirculo, diametoCirculo);
        g2.dispose();

        return circulo;
    }

    private Point calcularCoordenada(float angle, int size) {
        int x = (int) (size * Math.cos(Math.toRadians(angle)));
        int y = (int) (size * Math.sin(Math.toRadians(angle)));

        return new Point(x, y);
    }
}
