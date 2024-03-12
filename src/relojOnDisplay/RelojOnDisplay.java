package relojOnDisplay;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import relojQuartz.componentes.RelojBackground;
import relojQuartz.componentes.RelojClavo;
import relojQuartz.componentes.RelojHorario;
import relojQuartz.componentes.RelojMinutero;
import relojQuartz.componentes.RelojSegundero;
import interfaces.RelojInterface;
import recursos.Calendario;
import relojOnDisplay.componentes.RelojOnDisplayBackground;
import relojOnDisplay.componentes.RelojOnDisplayClavo;
import relojOnDisplay.componentes.RelojOnDisplayHorario;
import relojOnDisplay.componentes.RelojOnDisplayMinutero;
import relojOnDisplay.componentes.RelojOnDisplaySegundero;
import relojOnDisplay.componentes.RelojOnDisplayHora;

public class RelojOnDisplay extends JPanel implements RelojInterface {

    private int TAMANO_SEGUNDOS;
    private int TAMANO_MINUTOS;
    private int TAMANO_HORAS;
    private int DIAMETRO_RELOJ;
    private int CENTRO_X;
    private int CENTRO_Y;
    private Calendario objCalendario;
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
        TAMANO_SEGUNDOS = 150;
        TAMANO_MINUTOS = 130;
        TAMANO_HORAS = 110;
        this.objCalendario = new Calendario(10, 58, 30);
        this.atomico = atomico;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setSize(new Dimension(WIDTH, HEIGHT));
                setBackground(Color.white);

                DIAMETRO_RELOJ = getWidth();
                CENTRO_X = getWidth() / 2;
                CENTRO_Y = getHeight() / 2;

                relojBackground = new RelojOnDisplayBackground(getWidth());
                relojClavo = new RelojOnDisplayClavo(getWidth());
                setBackground(Color.BLACK);
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (reloj == null) {
            reloj = relojBackground.dibujarReloj();
            clavo = relojClavo.dibujarClavo();
            relojSegundero = new RelojOnDisplaySegundero(this, getWidth(), TAMANO_SEGUNDOS, atomico);
            relojHora = new RelojOnDisplayHora(this, getWidth());
            relojMinutero = new RelojOnDisplayMinutero(this, getWidth(), TAMANO_MINUTOS, objCalendario, atomico);
            relojHorario = new RelojOnDisplayHorario(this, getWidth(), TAMANO_HORAS, atomico);
        }

        g.drawImage(reloj, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(horario, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(minutero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(hora, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(segundero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(clavo, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
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

    public void pararReloj() {
        relojSegundero.pararSegundero();
        relojMinutero.pararMinutero();
        relojHorario.pararHorario();
        relojHora.pararHora();
    }
}
