package main;

import javax.swing.JFrame;
import relojQuartz.Reloj;
import relojOnDisplay.RelojOnDisplay;

public class Main extends JFrame {

    RelojOnDisplay objReloj;
    
    public Main() {
        initComponents();
        
        objReloj = new RelojOnDisplay(500, 500, false);
        pnlContenedor.add(objReloj);
        pnlContenedor.revalidate();
        pnlContenedor.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        toggleAtomico = new javax.swing.JToggleButton();
        background = new javax.swing.JPanel();
        pnlContenedor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(500, 560));
        setMinimumSize(new java.awt.Dimension(500, 560));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        toggleAtomico.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        toggleAtomico.setText("Reloj Atomico");
        toggleAtomico.setFocusPainted(false);
        toggleAtomico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAtomicoActionPerformed(evt);
            }
        });
        jPanel2.add(toggleAtomico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 60));

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlContenedor.setBackground(new java.awt.Color(255, 255, 255));
        pnlContenedor.setLayout(null);
        background.add(pnlContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 500));

        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 500, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void toggleAtomicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAtomicoActionPerformed
        pnlContenedor.removeAll();
        objReloj.pararReloj();
        objReloj = new RelojOnDisplay(500, 500, toggleAtomico.isSelected());
        pnlContenedor.add(objReloj);
        pnlContenedor.revalidate();
        pnlContenedor.repaint();
    }//GEN-LAST:event_toggleAtomicoActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JToggleButton toggleAtomico;
    // End of variables declaration//GEN-END:variables
}
