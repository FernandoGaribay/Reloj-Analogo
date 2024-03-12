package main;

import javax.swing.JFrame;
import relojQuartz.Reloj;
import relojOnDisplay.RelojOnDisplay;

public class Main extends JFrame {

    private String relojSeleccionado;
    Reloj objRelojQuartz;
    RelojOnDisplay objRelojOnDisplay;

    public Main() {
        initComponents();

        relojSeleccionado = "Quartz";
        objRelojQuartz = new Reloj(500, 500, false);

        pnlContenedor.add(objRelojQuartz);
        pnlContenedor.revalidate();
        pnlContenedor.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        toggleAtomico = new javax.swing.JToggleButton();
        btnOnDisplay = new javax.swing.JButton();
        btnQuartz = new javax.swing.JButton();
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

        btnOnDisplay.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnOnDisplay.setText("On Display");
        btnOnDisplay.setFocusPainted(false);
        btnOnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnDisplayActionPerformed(evt);
            }
        });
        jPanel2.add(btnOnDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 40));

        btnQuartz.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnQuartz.setText("Quartz");
        btnQuartz.setFocusPainted(false);
        btnQuartz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuartzActionPerformed(evt);
            }
        });
        jPanel2.add(btnQuartz, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 95, 40));

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

        switch (relojSeleccionado) {
            case "Quartz":
                objRelojQuartz.pararReloj();
                objRelojQuartz = new Reloj(500, 500, toggleAtomico.isSelected());
                pnlContenedor.add(objRelojQuartz);
                break;
            case "OnDisplay":
                objRelojOnDisplay.pararReloj();
                objRelojOnDisplay = new RelojOnDisplay(500, 500, toggleAtomico.isSelected());
                pnlContenedor.add(objRelojOnDisplay);
                break;
            default:
                throw new AssertionError();
        }

        pnlContenedor.revalidate();
        pnlContenedor.repaint();
    }//GEN-LAST:event_toggleAtomicoActionPerformed

    private void btnOnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnDisplayActionPerformed
        if (!relojSeleccionado.equals("OnDisplay")) {
            relojSeleccionado = "OnDisplay";

            if (objRelojQuartz != null) {
                objRelojQuartz.pararReloj();
            }

            pnlContenedor.removeAll();
            objRelojOnDisplay = new RelojOnDisplay(500, 500, toggleAtomico.isSelected());
            pnlContenedor.add(objRelojOnDisplay);
            pnlContenedor.revalidate();
            pnlContenedor.repaint();
        }
    }//GEN-LAST:event_btnOnDisplayActionPerformed

    private void btnQuartzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuartzActionPerformed
        if (!relojSeleccionado.equals("Quartz")) {
            relojSeleccionado = "Quartz";

            if (objRelojOnDisplay != null) {
                objRelojOnDisplay.pararReloj();
            }

            pnlContenedor.removeAll();
            objRelojQuartz = new Reloj(500, 500, toggleAtomico.isSelected());
            pnlContenedor.add(objRelojQuartz);
            pnlContenedor.revalidate();
            pnlContenedor.repaint();
        }
    }//GEN-LAST:event_btnQuartzActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btnOnDisplay;
    private javax.swing.JButton btnQuartz;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JToggleButton toggleAtomico;
    // End of variables declaration//GEN-END:variables
}
