/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koodikattila.viitehallinta.gui;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import koodikattila.viitehallinta.hallinta.Kontrolleri;
import koodikattila.viitehallinta.tieto.JsonTiedonsaanti;

/**
 *
 * @author kumikumi
 */
public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    public Gui() {
        this.kontrolleri = new Kontrolleri();
        initComponents();
    }
    
    public boolean onkoValid(int rivi) {
        return kontrolleri.onkoValidi(rivi);
    }

    public void paivitaTaulukko() {
        //System.out.println("paivitaTaulukko");
        //System.out.println(new Exception().getStackTrace()[1]);
        if (jList1.getSelectedValue() == null) {
            return;
        }

        TableModel model = new AbstractTableModel() {
            List<Attribuutti> sarakkeet = haeSarakkeet();
            List<Viite> viitteet = kontrolleri.hae((ViiteTyyppi) jList1.getSelectedValue(), "");
            
            private List<Attribuutti> haeSarakkeet() {
                List<Attribuutti> pakolliset = ((ViiteTyyppi) jList1.getSelectedValue()).haePakolliset();
                List<Attribuutti> valinnaiset = ((ViiteTyyppi) jList1.getSelectedValue()).haeValinnaiset();
                List<Attribuutti> palautus = new ArrayList<>();
                palautus.addAll(pakolliset);
                palautus.addAll(valinnaiset);
                return palautus;
            }

            @Override
            public String getColumnName(int col) {
                if (col == 0) {
                    return "avain";
                }
                return sarakkeet.get(col - 1).toString();
            }

            @Override
            public int getRowCount() {
                return viitteet.size();
            }

            @Override
            public int getColumnCount() {
                return sarakkeet.size()+1;
            }

            @Override
            public Object getValueAt(int row, int col) {
                if (col == 0) {
                    return viitteet.get(row).getAvain();
                }
                return viitteet.get(row).haeArvo(sarakkeet.get(col - 1));
            }

            @Override
            public boolean isCellEditable(int row, int col) {
//                if (col == 0) {
//                    return false;
//                }
                return true;
            }

            @Override
            public void setValueAt(Object value, int row, int col) {
                if (col == 0) {
                    viitteet.get(row).setAvain((String) value);
                } else {
                    viitteet.get(row).asetaArvo(sarakkeet.get(col - 1), (String) value);
                }
            }
        };

        this.jTable1.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new JTable() {

            public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!onkoValid(row)) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.WHITE);
                }
                //if (!isRowSelected(row)) {
                    //c.setBackground(Color.yellow);
                    //c.setFont(row == 0 ? font : getFont());
                    //}
                return c;
            }
        };
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viitehallinta");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jList1.setModel(new DefaultListModel<ViiteTyyppi>());
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);
        this.jList1.setListData(ViiteTyyppi.values());

        jTextField1.setText("filter");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jButton1.setText("Lisää uusi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lisaaNappiaPainettu(evt);
            }
        });

        jButton2.setText("Poista");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poistaNappiaPainettu(evt);
            }
        });

        jButton3.setText("Vie bibtex-tiedostoon ...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tallennaNappiaPainettu(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))
                    .addComponent(jTextField1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if (evt.getValueIsAdjusting()) {
            return;
        }
        this.paivitaTaulukko();
    }//GEN-LAST:event_jList1ValueChanged

    private void lisaaNappiaPainettu(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lisaaNappiaPainettu
        kontrolleri.lisaaViite(new Viite((ViiteTyyppi) jList1.getSelectedValue()));
        this.paivitaTaulukko();
    }//GEN-LAST:event_lisaaNappiaPainettu

    private void poistaNappiaPainettu(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poistaNappiaPainettu
        if (this.jTable1.getSelectedRow() < 0) {
            return;
        }
        //viitteet.remove(this.jTable1.getSelectedRow());
        kontrolleri.poista(this.jTable1.getSelectedRow());
        this.paivitaTaulukko();
    }//GEN-LAST:event_poistaNappiaPainettu

    private void tallennaNappiaPainettu(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tallennaNappiaPainettu
        JFileChooser saveFile = new JFileChooser();
        saveFile.setDialogTitle("Tallenna bibtex-tiedostoon");
        int retval = saveFile.showSaveDialog(null);
        File file = saveFile.getSelectedFile();
        if (retval != JFileChooser.APPROVE_OPTION || file == null) {
            return;
        }
        if (file.exists()) {
            int retval1 = JOptionPane.showConfirmDialog(this, "Tiedosto on jo olemassa, kirjoitetaanko yli?", "Tiedosto on jo olemassa", JOptionPane.YES_NO_OPTION);
            if (retval1 != JFileChooser.APPROVE_OPTION) {
                return;
            }
        }
        System.out.println("Tallennetaan tiedostoon " + file.getAbsolutePath());
        kontrolleri.talletaBibtexTiedostoon(file);
    }//GEN-LAST:event_tallennaNappiaPainettu

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        this.jTable1.repaint();
    }//GEN-LAST:event_jTable1PropertyChange

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        if (this.jTable1.getCellEditor() != null ) {
        this.jTable1.getCellEditor().stopCellEditing();
        }      
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        kontrolleri.tallenna();
    }//GEN-LAST:event_formWindowClosing
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Gui().setVisible(true);
//            }
//        });
//    }
    private Kontrolleri kontrolleri;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
