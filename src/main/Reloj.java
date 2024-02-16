package main;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;

public class Reloj extends JPanel {

    private BufferedImage cuerpoReloj;
    private int tamano;
    private int diametro;
    private int x;
    private int y;

    public Reloj() {
        tamano = 400;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (cuerpoReloj == null) {
            diametro = (int) (tamano - (tamano * 0.10));
            x = getWidth() / 2;
            y = getHeight() / 2;
            
            crearCirculo(); 
        } else {
            g2.drawImage(cuerpoReloj, x - cuerpoReloj.getWidth() / 2, y - cuerpoReloj.getHeight() / 2, null);

        }
    }

    private void crearCirculo() {
        BufferedImage circulo = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = circulo.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255));
        g2.fillOval(0, 0, diametro, diametro);
        g2.dispose();

        cuerpoReloj = Sombra.agregarSombra(circulo, 10, 0.05f);
        repaint();
    }
}
