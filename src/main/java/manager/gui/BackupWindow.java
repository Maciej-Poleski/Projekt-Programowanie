/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.gui;
import java.awt.Dialog;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import manager.tags.*;
import manager.core.*;
import manager.files.*;
import manager.files.backup.*;
/**
 *
 * @author Krzysztof Kornakiewicz
 */
public class BackupWindow extends javax.swing.JDialog {


    private Tags tags;
    private TagFilesStore tagFilesStore;
    private BackupsManager backupsmanager;
    private Data data;
    private Vector<MasterTag> masterTagVector;
    private Vector<SecondaryBackup> secondaryBackupVector;
    private MasterTag selectedMasterTag;
    private SecondaryBackup selectedSecondaryBackup;// wybrany mastertag
    private File backupLocation; // miejsce docelowe backupu na dysku
    private String picassaLogin;
    private String picassaPassword;
    
    public BackupWindow(Tags t, TagFilesStore tfs, BackupsManager bm, Data d) {

        this.tags=t;
        this.tagFilesStore=tfs;
        this.backupsmanager=bm;
        this.data=d;
        masterTagVector = new Vector<MasterTag>();
        secondaryBackupVector = new Vector<SecondaryBackup>();
        for(MasterTag a : this.tags.getMasterTagHeads())
            masterTagVector.add(a);
       // this.jList1.setListData(masterTagVector);
        initComponents();
        this.mastertagsList.setListData(masterTagVector);
        this.backupsList.setListData(new Vector<MasterTag>());
        mastertagsList.addListSelectionListener(new listListener(this));
        backupsList.addListSelectionListener(new backupListener(this));

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        buttonPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mastertagsList = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        backupsList = new javax.swing.JList();
        newBackupButton = new javax.swing.JButton();
        picassaBackupButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        updateButton = new javax.swing.JButton();

        buttonPanel.setBackground(new java.awt.Color(204, 204, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        buttonPanel.setForeground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Backup");
        setResizable(false);

        buttonPanel1.setBackground(new java.awt.Color(204, 204, 255));
        buttonPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonPanel1.setForeground(new java.awt.Color(204, 204, 255));

        jLabel1.setText("Backup");

        javax.swing.GroupLayout buttonPanel1Layout = new javax.swing.GroupLayout(buttonPanel1);
        buttonPanel1.setLayout(buttonPanel1Layout);
        buttonPanel1Layout.setHorizontalGroup(
            buttonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanel1Layout.setVerticalGroup(
            buttonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        mastertagsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(mastertagsList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        backupsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        backupsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        backupsList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(backupsList);

        newBackupButton.setText("Nowy Backup");
        newBackupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBackupButtonActionPerformed(evt);
            }
        });

        picassaBackupButton.setText("Picasa Backup");
        picassaBackupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                picassaBackupButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Aktualizuj Backup");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(picassaBackupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newBackupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(newBackupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(picassaBackupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newBackupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBackupButtonActionPerformed
        // TODO add your handling code here:
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        BackupManager b;
        this.backupLocation = fc.getSelectedFile();
        try
        {
        b=this.backupsmanager.getBackupManagerAssociatedWithMasterTag(this.selectedMasterTag);
        JOptionPane.showMessageDialog(this,"The backup of "+this.selectedMasterTag," created.",JOptionPane.INFORMATION_MESSAGE);
        b.registerFileSystemBackup(this.backupLocation);
        //MainWindow.data.save();
        }
        catch(OperationInterruptedException exp) //sprawdzic dlaczego
        {
            JOptionPane.showMessageDialog(this,"Error OIE","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch(IllegalArgumentException exp2) // brak wybranego mastertagu
        {
            JOptionPane.showMessageDialog(this,"No MasterTag choosen","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch(NullPointerException exp3) //sprawdzic dlaczego
        {
            JOptionPane.showMessageDialog(this,"Error NPE","Error",JOptionPane.ERROR_MESSAGE);
        }
        try {
            data.save();
        } catch (IOException ex) {
            Logger.getLogger(BackupWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_newBackupButtonActionPerformed

    private void picassaBackupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_picassaBackupButtonActionPerformed
        try
        {
        PicassaLoginWindow picasawindow = new PicassaLoginWindow(this.backupsmanager.getBackupManagerAssociatedWithMasterTag(this.selectedMasterTag),data);
        picasawindow.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        picasawindow.setVisible(true);
        }
        catch (IllegalArgumentException exp4)
        {
            JOptionPane.showMessageDialog(this,"No MasterTag choosen","Error",JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_picassaBackupButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        try {
            this.selectedSecondaryBackup.updateBackup();
        } catch (OperationInterruptedException ex) {
            Logger.getLogger(BackupWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            data.save();
        } catch (IOException ex) {
            Logger.getLogger(BackupWindow.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_updateButtonActionPerformed
    
    
    private static class backupListener implements ListSelectionListener
    {
        BackupWindow window;
        backupListener(BackupWindow w)
        {
            window = w;
        }
        
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
             window.selectedSecondaryBackup=(SecondaryBackup) window.backupsList.getSelectedValue();
        }
    }
    private static class listListener implements ListSelectionListener
    {
        
        BackupWindow window;
        listListener(BackupWindow w)
        {
            window=w;
        }
        
        @Override
        public void valueChanged(ListSelectionEvent e)
        {

            BackupManager b;
            window.selectedMasterTag=(MasterTag) window.mastertagsList.getSelectedValue();
            b=window.backupsmanager.getBackupManagerAssociatedWithMasterTag(window.selectedMasterTag);
            try
            {
                window.secondaryBackupVector.clear();
                for(SecondaryBackup i : b.getSecondaryBackups())
                {
                    window.secondaryBackupVector.add(i);
                }
            }
            catch(NullPointerException ex) // brak backupow
                {}
            finally
                {
                    window.backupsList.setListData(window.secondaryBackupVector);
                }
            
        }
    }
  public static void main(String args[]) {
    /*
     * Set the Nimbus look and feel
     */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
     * If Nimbus (introduced in Java SE 6) is not available, stay with the
     * default look and feel. For details see
     * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(BackupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(BackupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(BackupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(BackupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList backupsList;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel buttonPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList mastertagsList;
    private javax.swing.JButton newBackupButton;
    private javax.swing.JButton picassaBackupButton;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
