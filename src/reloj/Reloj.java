package reloj;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import reloj.componentes.RelojBackground;
import reloj.componentes.RelojMinutero;
import reloj.componentes.RelojSegundero;
import reloj.interfaces.RelojInterface;

public class Reloj extends JPanel implements RelojInterface {

    private int TAMANO_SEGUNDOS;
    private int TAMANO_MINUTOS;
    private int TAMANO_HORAS;
    private int DIAMETRO_RELOJ;
    private int CENTRO_X;
    private int CENTRO_Y;

    private Timer timer;
    private BufferedImage reloj;
    private BufferedImage segundero;
    private BufferedImage minutero;
    private BufferedImage horario;
    private BufferedImage clavo;

    private RelojBackground background;
    private RelojSegundero relojSegundero;
    private RelojMinutero relojMinutero;

    public Reloj() {
        TAMANO_SEGUNDOS = 200;
        TAMANO_MINUTOS = 175;
        TAMANO_HORAS = 110;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DIAMETRO_RELOJ = getWidth();
                CENTRO_X = getWidth() / 2;
                CENTRO_Y = getHeight() / 2;

                background = new RelojBackground(getWidth());

                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (reloj == null) {
            reloj = background.dibujarReloj();
            relojSegundero = new RelojSegundero(this, getWidth(), TAMANO_SEGUNDOS);
            relojMinutero = new RelojMinutero(this, getWidth(), TAMANO_MINUTOS);
        }

        g.drawImage(reloj, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(minutero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(segundero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
    }

    @Override
    public void dibujarSegundero(BufferedImage segundero) {
        this.segundero = segundero;

        repaint();
    }
    
    @Override
    public void dibujarMinutero(BufferedImage minutero) {
        this.minutero = minutero;

        repaint();
    }
}

//    private BufferedImage dibujarMinutos() {
//        int minuto = Calendar.getInstance().get(Calendar.MINUTE);
////        int minuto = 15;
//
//        BufferedImage minutero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
//
//        float angulo = minuto * 6 - 90;
//
//        // Se crea el objeto graphics del buffer
//        Graphics2D g2 = minutero.createGraphics();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        Point point = calcularCoordenada(angulo, TAMANO_MINUTOS);
//
//        g2.setColor(new Color(0, 0, 0));
//        g2.setStroke(new BasicStroke(2));
//        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + point.x, CENTRO_Y + point.y);
//
//        g2.dispose();
//        return minutero;
//    }
//
//    private BufferedImage dibujarHoras() {
//        int hora = Calendar.getInstance().get(Calendar.HOUR);
//
//        BufferedImage horario = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
//
//        // 1 segundo equivale a 6 grados
//        float angulo = hora * 30 - 90;
//
//        // Se crea el objeto graphics del buffer
//        Graphics2D g2 = horario.createGraphics();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // Se calculan los puntos a donde debe mirar la ajuga
//        Point puntaAjuga = calcularCoordenada(angulo, TAMANO_HORAS);
//
//        // Se dibuja
//        g2.setColor(new Color(0, 0, 0));
//        g2.setStroke(new BasicStroke(2));
//        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + puntaAjuga.x, CENTRO_Y + puntaAjuga.y);
//
//        g2.dispose();
//        return horario;
//    }
//
//    private BufferedImage dibujarClavo() {
//        BufferedImage clavo = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
//
//        // Se crea el objeto graphics del buffer
//        Graphics2D g2 = horario.createGraphics();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        g2.setColor(Color.white);
//        g2.fillOval(CENTRO_X - 10, CENTRO_Y - 10, 20, 20);
//        g2.setColor(Color.BLACK);
//        g2.fillOval(CENTRO_X - 5, CENTRO_Y - 5, 10, 10);
//
//        return clavo;
//    }
//
//    private Point calcularCoordenada(float angle, int size) {
//        int x = (int) (size * Math.cos(Math.toRadians(angle)));
//        int y = (int) (size * Math.sin(Math.toRadians(angle)));
//
//        return new Point(x, y);
//    }
