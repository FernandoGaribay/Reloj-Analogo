package relojQuartz;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import relojQuartz.componentes.RelojQuartzBackground;
import relojQuartz.componentes.RelojQuartzClavo;
import relojQuartz.componentes.RelojQuartzHorario;
import relojQuartz.componentes.RelojQuartzMinutero;
import relojQuartz.componentes.RelojQuartzSegundero;
import interfaces.RelojInterface;
import recursos.Calendario;

public class RelojQuartz extends JPanel implements RelojInterface {

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

    private RelojQuartzBackground relojBackground;
    private RelojQuartzClavo relojClavo;
    private RelojQuartzSegundero relojSegundero;
    private RelojQuartzMinutero relojMinutero;
    private RelojQuartzHorario relojHorario;

    public RelojQuartz(int WIDTH, int HEIGHT, boolean atomico) {
        TAMANO_SEGUNDOS = 180;
        TAMANO_MINUTOS = 160;
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

                relojBackground = new RelojQuartzBackground(getWidth());
                relojClavo = new RelojQuartzClavo(getWidth());

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
            relojSegundero = new RelojQuartzSegundero(this, getWidth(), TAMANO_SEGUNDOS, atomico);
            relojMinutero = new RelojQuartzMinutero(this, getWidth(), TAMANO_MINUTOS, objCalendario, atomico);
            relojHorario = new RelojQuartzHorario(this, getWidth(), TAMANO_HORAS, atomico);
        }

        g.drawImage(reloj, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(horario, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
        g.drawImage(minutero, CENTRO_X - reloj.getWidth() / 2, CENTRO_Y - reloj.getHeight() / 2, null);
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void pararReloj() {
        relojSegundero.pararSegundero();
        relojMinutero.pararMinutero();
        relojHorario.pararHorario();
    }
}
