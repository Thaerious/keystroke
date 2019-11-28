package ca.frar.keystroke;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.jnativehook.NativeHookException;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ed
 */
public class MainFrame extends javax.swing.JFrame {

    private String filename;
    private KeyStrokeGenerator keyStrokeGenerator;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
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
        jTextArea1 = new javax.swing.JTextArea();
        labelCommand = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemLoad = new javax.swing.JMenuItem();
        menuItemSaveAs = new javax.swing.JMenuItem();
        menuItemSave = new javax.swing.JMenuItem();
        menuItemRun = new javax.swing.JMenuItem();
        menuItemStop = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        labelCommand.setFont(new java.awt.Font("Consolas", 0, 36)); // NOI18N
        labelCommand.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCommand.setText("---");

        jMenu1.setText("File");

        menuItemLoad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menuItemLoad.setText("Load Script");
        menuItemLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLoadActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemLoad);

        menuItemSaveAs.setText("Save Script As");
        menuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSaveAsActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemSaveAs);

        menuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSave.setText("Save Script");
        menuItemSave.setEnabled(false);
        menuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSaveActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemSave);

        menuItemRun.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        menuItemRun.setText("Run");
        menuItemRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRunActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemRun);

        menuItemStop.setText("Stop");
        menuItemStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemStopActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemStop);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                    .addComponent(labelCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCommand)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLoadActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("D:\\scratch"));
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            readFile(file);
            this.filename = file.getAbsolutePath();
            this.menuItemSave.setEnabled(true);
        } else {

        }
    }//GEN-LAST:event_menuItemLoadActionPerformed

    private void menuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSaveAsActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("D:\\scratch"));
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            saveFile(file);
            this.filename = file.getAbsolutePath();
            this.menuItemSave.setEnabled(true);
        } else {

        }
    }//GEN-LAST:event_menuItemSaveAsActionPerformed

    private void menuItemRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRunActionPerformed
        try {
            this.keyStrokeGenerator = new KeyStrokeGenerator(this.jTextArea1.getText());
            
            keyStrokeGenerator.nextCommand = new Consumer<String>(){
                @Override
                public void accept(String t) {
                    MainFrame.this.labelCommand.setText(t);
                }            
            };
            
            Thread t = new Thread(keyStrokeGenerator);
            System.out.println("keyStrokeGenerateor thread = " + t.hashCode());
            t.start();
        } catch (InterruptedException | AWTException | SAXException | NativeHookException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuItemRunActionPerformed

    private void menuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSaveActionPerformed
        this.saveFile(new File(this.filename));
    }//GEN-LAST:event_menuItemSaveActionPerformed

    private void menuItemStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemStopActionPerformed
        keyStrokeGenerator.stop();
    }//GEN-LAST:event_menuItemStopActionPerformed

    private void saveFile(File file) {
        try {
            FileWriter fWriter = new FileWriter(file);
            fWriter.write(this.jTextArea1.getText());
            fWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readFile(File file) {
        try {
            this.jTextArea1.setText("");
            FileReader fReader = new FileReader(file);
            BufferedReader bfReader = new BufferedReader(fReader);
            String line = bfReader.readLine();

            while (line != null) {
                System.out.println(line);
                this.jTextArea1.append(line + "\n");
                line = bfReader.readLine();
            }

            bfReader.close();
            fReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().hashCode());
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelCommand;
    private javax.swing.JMenuItem menuItemLoad;
    private javax.swing.JMenuItem menuItemRun;
    private javax.swing.JMenuItem menuItemSave;
    private javax.swing.JMenuItem menuItemSaveAs;
    private javax.swing.JMenuItem menuItemStop;
    // End of variables declaration//GEN-END:variables
}
