package reloj;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import reloj.componentes.RelojBackground;
import reloj.componentes.RelojClavo;
import reloj.componentes.RelojHorario;
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

    private boolean atomico;
    private BufferedImage reloj;
    private BufferedImage segundero;
    private BufferedImage minutero;
    private BufferedImage horario;
    private BufferedImage clavo;

    private RelojBackground relojBackground;
    private RelojClavo relojClavo;
    private RelojSegundero relojSegundero;
    private RelojMinutero relojMinutero;
    private RelojHorario relojHorario;

    public Reloj(int WIDTH, int HEIGHT, boolean atomico) {
        TAMANO_SEGUNDOS = 200;
        TAMANO_MINUTOS = 175;
        TAMANO_HORAS = 110;
        this.atomico = atomico;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setSize(new Dimension(WIDTH, HEIGHT));
                setBackground(Color.white);
                
                DIAMETRO_RELOJ = getWidth();
                CENTRO_X = getWidth() / 2;
                CENTRO_Y = getHeight() / 2;

                relojBackground = new RelojBackground(getWidth());
                relojClavo = new RelojClavo(getWidth());

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
            relojSegundero = new RelojSegundero(this, getWidth(), TAMANO_SEGUNDOS, atomico);
            relojMinutero = new RelojMinutero(this, getWidth(), TAMANO_MINUTOS, atomico);
            relojHorario = new RelojHorario(this, getWidth(), TAMANO_HORAS, atomico);
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
    
    public void pararReloj(){
        relojSegundero.pararSegundero();
        relojMinutero.pararMinutero();
    }
}
