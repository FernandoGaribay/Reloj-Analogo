package relojOnDisplay;

import javax.swing.JPanel;
import interfaces.RelojInterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import relojOnDisplay.componentes.RelojOnDisplayBackground;
import relojOnDisplay.componentes.RelojOnDisplayClavo;
import relojOnDisplay.componentes.RelojOnDisplayHorario;
import relojOnDisplay.componentes.RelojOnDisplayMinutero;
import relojOnDisplay.componentes.RelojOnDisplaySegundero;
import relojOnDisplay.componentes.RelojOnDisplayHora;

public class RelojOnDisplay extends JPanel implements RelojInterface {

    private final int TAMANO_SEGUNDOS;
    private final int TAMANO_MINUTOS;
    private final int TAMANO_HORAS;
    private final int CENTRO_X;
    private final int CENTRO_Y;

    private boolean atomico;
    private BufferedImage reloj;
    private BufferedImage segundero;
    private BufferedImage minutero;
    private BufferedImage horario;
    private BufferedImage clavo;
    private BufferedImage hora;

    private RelojOnDisplayBackground relojBackground;
    private RelojOnDisplayClavo relojClavo;
    private RelojOnDisplayHora relojHora;
    private RelojOnDisplaySegundero relojSegundero;
    private RelojOnDisplayMinutero relojMinutero;
    private RelojOnDisplayHorario relojHorario;

    public RelojOnDisplay(int WIDTH, int HEIGHT, boolean atomico) {
        setSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        // Variables
        this.atomico = atomico;

        // Constantes
        TAMANO_SEGUNDOS = 150;
        TAMANO_MINUTOS = 130;
        TAMANO_HORAS = 110;
        CENTRO_X = getWidth() / 2;
        CENTRO_Y = getHeight() / 2;

        // Objetos (Imagenes estaticas)
        relojBackground = new RelojOnDisplayBackground(getWidth());
        relojClavo = new RelojOnDisplayClavo(getWidth());

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (reloj == null) {
            // Obtener la imagen estatica
            reloj = relojBackground.dibujarReloj();
            clavo = relojClavo.dibujarClavo();

            // Creacion de los hilos
            relojSegundero = new RelojOnDisplaySegundero(this, getWidth(), TAMANO_SEGUNDOS, atomico);
            relojMinutero = new RelojOnDisplayMinutero(this, getWidth(), TAMANO_MINUTOS, atomico);
            relojHorario = new RelojOnDisplayHorario(this, getWidth(), TAMANO_HORAS, atomico);
            relojHora = new RelojOnDisplayHora(this, getWidth());
        }

        // Dibujado de los buffers
        g.drawImage(reloj, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(horario, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(minutero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(segundero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(hora, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(clavo, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de la interfaz"> 
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

    @Override
    public void dibujarHorario(BufferedImage horario) {
        this.horario = horario;
        repaint();
    }

    @Override
    public void dibujarHora(BufferedImage hora) {
        this.hora = hora;
        repaint();
    }
    // </editor-fold> 

    public void pararReloj() {
        relojSegundero.pararSegundero();
        relojMinutero.pararMinutero();
        relojHorario.pararHorario();
        relojHora.pararHora();
    }
}
