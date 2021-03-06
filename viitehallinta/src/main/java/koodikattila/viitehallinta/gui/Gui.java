package koodikattila.viitehallinta.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import koodikattila.viitehallinta.hallinta.Kontrolleri;

/**
 *
 * @author Koodikattila
 */
public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    public Gui() {
        this.kontrolleri = new Kontrolleri(new File("viitehallinta.json"));
        initComponents();
		tekstiMuuttunut();
        viiteTaulukko.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (event.getValueIsAdjusting()) {
                    return;
                }
                paivitaTagit();
            }
        });
    }

    private Viite valittuViite() {
        if (this.viiteTaulukko.getSelectedRow() == -1) {
            return null;
        } else {
            return this.taulukko.getViitteet().get(this.viiteTaulukko.getSelectedRow());
        }
    }

    public void paivitaTagit() {
        tagiValintaBoksi.setModel(new javax.swing.DefaultComboBoxModel(kontrolleri.getTagit().toArray()));

        Viite valittu = valittuViite();
        if (valittu == null) {
            tagiLuettelo.setText("(ei viitettä valittuna)");
        } else {
            tagiLuettelo.setText(tagitTekstina(valittu.haeTagit()));
            viitteenTiedot.setText(viiteTekstina(valittu));
        }
    }

    public String tagitTekstina(Set<String> tagit) {
        String palautus = "";
        for (String s : tagit) {
            palautus += s + " ";
        }
        return palautus;
    }

    public String viiteTekstina(Viite viite) {
        String palautus = viite.getAvain() + "\n";
        for (Attribuutti a : viite.asetetutAttribuutit()) {
            palautus += a + ": " + viite.haeArvo(a) + "\n";
        }
        return palautus;
    }

    public boolean onkoValid(int rivi) {
        return kontrolleri.onkoValidi(rivi);
    }

    public void paivitaTaulukko() {
        ViiteTyyppiSailo valittu = attribuuttiLista.getSelectedValue();
        if (valittu == null) {
            return;
        }
        this.taulukko = new Taulukko(kontrolleri, valittu.getTyyppi(), hakuKentta.getText());
        yksittainenMuutos(valittu);
        attribuuttiLista.repaint();
        this.viiteTaulukko.setModel(taulukko);
    }

    private void tekstiMuuttunut() {
        ListModel<ViiteTyyppiSailo> modeli = attribuuttiLista.getModel();
        for (int i = 0; i < modeli.getSize(); i++) {
            yksittainenMuutos(modeli.getElementAt(i));
        }
        attribuuttiLista.repaint();
        paivitaTaulukko();
    }

    private void yksittainenMuutos(ViiteTyyppiSailo sailo) {
        sailo.setMaara(kontrolleri.hae(sailo.getTyyppi(), hakuKentta.getText()).size());
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
        attribuuttiLista = new javax.swing.JList<ViiteTyyppiSailo>();
        hakuKentta = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        viiteTaulukko = new JTable() {

            public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!onkoValid(row)) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(super.getBackground());
                    //c.setBackground(Color.WHITE);
                    if (isRowSelected(row)) {
                        c.setBackground(super.selectionBackground);
                        c.setForeground(super.selectionForeground);
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }

                return c;
            }
        };
        ;
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        viitteenTiedot = new javax.swing.JEditorPane();
        tagiValintaBoksi = new javax.swing.JComboBox();
        tagiLuettelo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        poistaTagiViitteestaNappi = new javax.swing.JButton();
        liitaNappi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        uusiTagiKentta = new javax.swing.JTextField();
        poistaTagiNappi = new javax.swing.JButton();
        lisaaTagiNappi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viitehallinta");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        attribuuttiLista.setModel(new DefaultListModel<ViiteTyyppiSailo>());
        attribuuttiLista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        attribuuttiLista.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                attribuuttiListaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(attribuuttiLista);
        ViiteTyyppi[] arvot = ViiteTyyppi.values();
        ViiteTyyppiSailo[] sailo = new ViiteTyyppiSailo[arvot.length];
        for(int i = 0; i < arvot.length; i++){
            sailo[i] = new ViiteTyyppiSailo(arvot[i]);
        }
        this.attribuuttiLista.setListData(sailo);

        hakuKentta.setToolTipText("Filtteri");
        hakuKentta.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {

            }
            public void removeUpdate(DocumentEvent e) {
                tekstiMuuttunut();
            }
            public void insertUpdate(DocumentEvent e) {
                tekstiMuuttunut();
            }
        });
        hakuKentta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hakuKenttaActionPerformed(evt);
            }
        });
        hakuKentta.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tekstiMuuttunut(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        viiteTaulukko.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        viiteTaulukko.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                viiteTaulukkoPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(viiteTaulukko);

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

        jScrollPane3.setViewportView(viitteenTiedot);

        tagiValintaBoksi.setModel(new javax.swing.DefaultComboBoxModel(kontrolleri.getTagit().toArray()));

        tagiLuettelo.setEditable(false);

        jLabel1.setText("tagit:");

        jLabel2.setText("liitä tagi:");

        poistaTagiViitteestaNappi.setText("poista tagi viitteesta");
        poistaTagiViitteestaNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poistaTagiViitteestaNappiActionPerformed(evt);
            }
        });

        liitaNappi.setText("liitä");
        liitaNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liitaNappiActionPerformed(evt);
            }
        });

        jLabel3.setText("uuden tagin lisääminen:");

        uusiTagiKentta.setText("uusi");
        uusiTagiKentta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uusiTagiKenttaActionPerformed(evt);
            }
        });

        poistaTagiNappi.setText("poista tagi kaikista");
        poistaTagiNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poistaTagiNappiActionPerformed(evt);
            }
        });

        lisaaTagiNappi.setText("lisää");
        lisaaTagiNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lisaaTagiNappiActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tagiLuettelo))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(uusiTagiKentta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lisaaTagiNappi)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tagiValintaBoksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(liitaNappi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(poistaTagiViitteestaNappi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(poistaTagiNappi, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))))
                    .addComponent(hakuKentta)
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
                .addComponent(hakuKentta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tagiLuettelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tagiValintaBoksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(poistaTagiViitteestaNappi)
                            .addComponent(liitaNappi)
                            .addComponent(poistaTagiNappi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(uusiTagiKentta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(lisaaTagiNappi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hakuKenttaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hakuKenttaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hakuKenttaActionPerformed

    private void attribuuttiListaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_attribuuttiListaValueChanged
        if (evt.getValueIsAdjusting()) {
            return;
        }
        this.paivitaTaulukko();
    }//GEN-LAST:event_attribuuttiListaValueChanged

    private void lisaaNappiaPainettu(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lisaaNappiaPainettu
        kontrolleri.lisaaViite(new Viite(attribuuttiLista.getSelectedValue().getTyyppi()));
        this.paivitaTaulukko();
    }//GEN-LAST:event_lisaaNappiaPainettu

    private void poistaNappiaPainettu(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poistaNappiaPainettu
        if (this.viiteTaulukko.getSelectedRow() < 0) {
            return;
        }
        kontrolleri.poista(this.viiteTaulukko.getSelectedRow());
        this.paivitaTaulukko();
    }//GEN-LAST:event_poistaNappiaPainettu

    private void tallennaNappiaPainettu(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tallennaNappiaPainettu
        final JFrame esikatselu = new JFrame();
        JPanel paneeli = new JPanel();
        paneeli.setLayout(new BorderLayout());
        paneeli.setBorder(new TitledBorder(new EtchedBorder(), "Esikatselu"));
        JTextArea tekstiRuutu = new JTextArea();
        tekstiRuutu.setText(kontrolleri.getBibTeX());
        tekstiRuutu.setEditable(false);
        tekstiRuutu.setFont(Font.decode(Font.MONOSPACED + "-" + 13));
        JScrollPane vieritys = new JScrollPane(tekstiRuutu);
        vieritys.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        paneeli.add(vieritys, BorderLayout.CENTER);
        JPanel painikePaneeli = new JPanel();
        JButton tallenna = new JButton("Tallenna");
        tallenna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valitseTiedostoJaTallenna();
                esikatselu.dispose();
            }
        });
        painikePaneeli.add(tallenna);
        JButton sulje = new JButton("Sulje");
        sulje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                esikatselu.dispose();
            }
        });
        painikePaneeli.add(sulje);
        paneeli.add(painikePaneeli, BorderLayout.SOUTH);
        paneeli.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        esikatselu.getContentPane().add(paneeli);
        esikatselu.pack();
        esikatselu.setVisible(true);
    }//GEN-LAST:event_tallennaNappiaPainettu

    private void valitseTiedostoJaTallenna() {
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

    private void viiteTaulukkoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_viiteTaulukkoPropertyChange
        this.viiteTaulukko.repaint();
    }//GEN-LAST:event_viiteTaulukkoPropertyChange

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        if (this.viiteTaulukko.getCellEditor() != null) {
            this.viiteTaulukko.getCellEditor().stopCellEditing();
        }
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        kontrolleri.tallenna();
    }//GEN-LAST:event_formWindowClosing

    private void poistaTagiViitteestaNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poistaTagiViitteestaNappiActionPerformed
        // TODO add your handling code here:
        Viite valittu = valittuViite();
        if (valittu != null) {
            valittu.haeTagit().remove((String) this.tagiValintaBoksi.getSelectedItem());
        }
        paivitaTagit();
    }//GEN-LAST:event_poistaTagiViitteestaNappiActionPerformed

    private void uusiTagiKenttaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uusiTagiKenttaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uusiTagiKenttaActionPerformed

    private void lisaaTagiNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lisaaTagiNappiActionPerformed
        // TODO add your handling code here:
        kontrolleri.getTagit().add(this.uusiTagiKentta.getText());
        paivitaTagit();
    }//GEN-LAST:event_lisaaTagiNappiActionPerformed

    private void liitaNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liitaNappiActionPerformed
        // TODO add your handling code here:
        Viite valittu = valittuViite();
        if (valittu != null) {
            valittu.haeTagit().add((String) this.tagiValintaBoksi.getSelectedItem());
        }
        paivitaTagit();

    }//GEN-LAST:event_liitaNappiActionPerformed

    private void tekstiMuuttunut(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tekstiMuuttunut
        // TODO add your handling code here:
    }//GEN-LAST:event_tekstiMuuttunut

    private void poistaTagiNappiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poistaTagiNappiActionPerformed
        // TODO add your handling code here:
        kontrolleri.poistaTagiKaikista((String) this.tagiValintaBoksi.getSelectedItem());
        paivitaTagit();
        
    }//GEN-LAST:event_poistaTagiNappiActionPerformed
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
    private final transient Kontrolleri kontrolleri;
    private Taulukko taulukko;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<ViiteTyyppiSailo> attribuuttiLista;
    private javax.swing.JTextField hakuKentta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton liitaNappi;
    private javax.swing.JButton lisaaTagiNappi;
    private javax.swing.JButton poistaTagiNappi;
    private javax.swing.JButton poistaTagiViitteestaNappi;
    private javax.swing.JTextField tagiLuettelo;
    private javax.swing.JComboBox tagiValintaBoksi;
    private javax.swing.JTextField uusiTagiKentta;
    private javax.swing.JTable viiteTaulukko;
    private javax.swing.JEditorPane viitteenTiedot;
    // End of variables declaration//GEN-END:variables
}
