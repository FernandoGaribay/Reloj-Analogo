package reloj;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.util.Calendar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import reloj.componentes.RelojBackground;

public class Reloj extends JPanel {

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
                timer.start();
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (reloj == null) {
            reloj = background.dibujarReloj();
            segundero = dibujarSegundos();
            minutero = dibujarMinutos();
            horario = dibujarHoras();
            clavo = dibujarClavo();
        }

        g.drawImage(reloj, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(segundero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(minutero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(horario, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(clavo, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
    }

    @Override
    public void repaint() {
        super.repaint();

        if (reloj == null) {
            return;
        }

        minutero = dibujarMinutos();
        horario = dibujarHoras();
        clavo = dibujarClavo();
        segundero = dibujarSegundos();
    }

    private BufferedImage dibujarReloj() {
        BufferedImage reloj = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);
        BufferedImage circulo1;
        BufferedImage circulo2;

        // Primer circulo del reloj
        circulo1 = crearCirculo(Color.WHITE, DIAMETRO_RELOJ);
        circulo1 = Sombra.agregarSombra(circulo1, 20, 0.05f);

        // Segundo circulo del reloj
        circulo2 = crearCirculo(Color.WHITE, (int) (DIAMETRO_RELOJ * 0.65));
        circulo2 = Sombra.agregarSombra(circulo2, 20, 0.025f);

        // Dibujamos en el Buffer
        Graphics2D g2 = (Graphics2D) reloj.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.black);
        g2.drawImage(circulo1, reloj.getWidth() / 2 - circulo1.getWidth() / 2, reloj.getHeight() / 2 - circulo1.getWidth() / 2, null);
        g2.drawImage(circulo2, reloj.getWidth() / 2 - circulo2.getWidth() / 2, reloj.getHeight() / 2 - circulo2.getWidth() / 2, null);

        for (int i = 0; i < 12; i++) {
            Point cordsNumeros = calcularCoordenada((i+1) * 30 - 90, (int) (DIAMETRO_RELOJ * 0.37));

            Font numero = new Font("Arial", Font.PLAIN, 30);
            g2.setFont(numero);

            String texto = String.valueOf(i+1);

            FontMetrics metrics = g2.getFontMetrics(numero);
            int anchoTexto = metrics.stringWidth(texto);
            int alturaTexto = metrics.getAscent() - metrics.getDescent();

            int numeroCentroX = CENTRO_X + cordsNumeros.x - anchoTexto / 2;
            int numeroCentroY = CENTRO_Y + cordsNumeros.y + alturaTexto / 2; 

            g2.setColor(Color.BLACK);
//            g2.drawRect(numeroCentroX, CENTRO_Y - alturaTexto / 2 + cordsNumeros.y, anchoTexto, alturaTexto);
            g2.drawString(texto, numeroCentroX, numeroCentroY);
        }

        g2.dispose();
        return reloj;
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

    private BufferedImage dibujarSegundos() {
        int segundo = Calendar.getInstance().get(Calendar.SECOND);
//        int segundo = 0;
        BufferedImage segundero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // 1 segundo equivale a 6 grados
        float angulo = segundo * 6 - 90;
        float anguloInverso = angulo + 180;

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = segundero.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Se calculan los puntos a donde debe mirar la ajuga
        Point puntaAjuga = calcularCoordenada(angulo, TAMANO_SEGUNDOS);
        Point colaAjuga = calcularCoordenada(anguloInverso, (int) (TAMANO_SEGUNDOS * 0.35));

        Point punto1Cola = calcularCoordenada(anguloInverso - 4, (int) (TAMANO_SEGUNDOS * 0.4));
        Point punto2Cola = calcularCoordenada(anguloInverso + 3, (int) (TAMANO_SEGUNDOS * 0.4));
        Point punto3Cola = calcularCoordenada(anguloInverso - 15, (int) (TAMANO_SEGUNDOS * 0.1));
        Point punto4Cola = calcularCoordenada(anguloInverso + 14, (int) (TAMANO_SEGUNDOS * 0.1));

        // Se dibuja
        g2.setColor(new Color(255, 119, 119));
        g2.setStroke(new BasicStroke(3));

        g2.fillOval(CENTRO_X - 5, CENTRO_Y - 5, 10, 10);
        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + puntaAjuga.x, CENTRO_Y + puntaAjuga.y);
        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + colaAjuga.x, CENTRO_Y + colaAjuga.y);

        int[] xPoints = {CENTRO_X + punto1Cola.x, CENTRO_X + punto2Cola.x, CENTRO_X + punto4Cola.x, CENTRO_X + punto3Cola.x};
        int[] yPoints = {CENTRO_Y + punto1Cola.y, CENTRO_Y + punto2Cola.y, CENTRO_Y + punto4Cola.y, CENTRO_Y + punto3Cola.y};
        g2.fillPolygon(xPoints, yPoints, 4);

        g2.dispose();
        return segundero;
    }

    private BufferedImage dibujarMinutos() {
        int minuto = Calendar.getInstance().get(Calendar.MINUTE);
//        int minuto = 15;

        BufferedImage minutero = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        float angulo = minuto * 6 - 90;

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = minutero.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point point = calcularCoordenada(angulo, TAMANO_MINUTOS);

        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + point.x, CENTRO_Y + point.y);

        g2.dispose();
        return minutero;
    }

    private BufferedImage dibujarHoras() {
        int hora = Calendar.getInstance().get(Calendar.HOUR);

        BufferedImage horario = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // 1 segundo equivale a 6 grados
        float angulo = hora * 30 - 90;

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = horario.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Se calculan los puntos a donde debe mirar la ajuga
        Point puntaAjuga = calcularCoordenada(angulo, TAMANO_HORAS);

        // Se dibuja
        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(CENTRO_X, CENTRO_Y, CENTRO_X + puntaAjuga.x, CENTRO_Y + puntaAjuga.y);

        g2.dispose();
        return horario;
    }

    private BufferedImage dibujarClavo() {
        BufferedImage clavo = new BufferedImage(DIAMETRO_RELOJ, DIAMETRO_RELOJ, BufferedImage.TYPE_INT_ARGB);

        // Se crea el objeto graphics del buffer
        Graphics2D g2 = horario.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white);
        g2.fillOval(CENTRO_X - 10, CENTRO_Y - 10, 20, 20);
        g2.setColor(Color.BLACK);
        g2.fillOval(CENTRO_X - 5, CENTRO_Y - 5, 10, 10);

        return clavo;
    }

    private Point calcularCoordenada(float angle, int size) {
        int x = (int) (size * Math.cos(Math.toRadians(angle)));
        int y = (int) (size * Math.sin(Math.toRadians(angle)));

        return new Point(x, y);
    }
}
