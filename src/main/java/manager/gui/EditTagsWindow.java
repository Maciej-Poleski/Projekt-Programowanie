/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import manager.files.backup.BackupsManager;
import manager.tags.CycleException;
import manager.tags.TagFilesStore;
import manager.tags.Tags;
import manager.tags.UserTag;
import manager.core.*;
/**
 *
 * @author Jakub Brzeski
 */
public class EditTagsWindow extends javax.swing.JDialog {
    
    private Tags tags;
    private TagFilesStore tagFilesStore;
    private BackupsManager backupsmanager;
    private UserTag parent;
    private UserTag child;
    private Data data;
    
    public EditTagsWindow(Tags t, TagFilesStore tfs, BackupsManager bm, Data d) {
        this.tags=t;
        this.tagFilesStore=tfs;
        this.backupsmanager=bm;
        this.data=d;
        parent=null;
        child=null;
        initComponents();
        displayUserTagsTree(tags.getModelOfUserTags());
        new UserTagsTreeSelectionListener();
        
    }

    private void displayUserTagsTree(TreeModel utm){
        this.userTagsTree.setModel(utm);
        this.userTagsTree.setRootVisible(false);
    }
    private class UserTagsTreeSelectionListener implements TreeSelectionListener{

        UserTagsTreeSelectionListener(){
            userTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            userTagsTree.addTreeSelectionListener(this);      
        }
        @Override
        public void valueChanged(TreeSelectionEvent e) {
             Tags.IUserTagNode temptag = (Tags.IUserTagNode)userTagsTree.getLastSelectedPathComponent();
             if(temptag == null) return;
             UserTag utag = temptag.getTag();
             
            if(parent==null){
                parent=utag;
                parentField.setText(utag.toString());
            }else if(child==null){
                child=utag;
                childField.setText(utag.toString());
            }
        }  
    } 
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTagsTree = new javax.swing.JTree();
        jLabel2 = new javax.swing.JLabel();
        clearSelectionButton = new javax.swing.JButton();
        parentField = new javax.swing.JTextField();
        childField = new javax.swing.JTextField();
        parentLabel = new javax.swing.JLabel();
        childLabel = new javax.swing.JLabel();
        setConnectionButton = new javax.swing.JButton();
        changeNameButton = new javax.swing.JButton();
        removeParentButton = new javax.swing.JButton();
        newNameField = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit tags");
        setMaximumSize(new java.awt.Dimension(200, 500));
        setPreferredSize(new java.awt.Dimension(430, 472));
        setResizable(false);

        buttonPanel1.setBackground(new java.awt.Color(204, 204, 255));
        buttonPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonPanel1.setForeground(new java.awt.Color(204, 204, 255));

        jLabel1.setText("Edytuj tagi");

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
        jPanel1.setForeground(new java.awt.Color(204, 204, 255));

        jScrollPane1.setViewportView(userTagsTree);

        jLabel2.setText("Tagi użytkownika");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addContainerGap())
        );

        clearSelectionButton.setText("Usuń zaznaczenie");
        clearSelectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSelectionButtonActionPerformed(evt);
            }
        });

        parentField.setEditable(false);
        parentField.setText("Rodzic");

        childField.setEditable(false);
        childField.setText("Dziecko");

        parentLabel.setText("A (Rodzic)");

        childLabel.setText("B (Dziecko)");

        setConnectionButton.setText("Ustal A rodzicem B");
        setConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setConnectionButtonActionPerformed(evt);
            }
        });

        changeNameButton.setText("Zmień nazwę tagu A");
        changeNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeNameButtonActionPerformed(evt);
            }
        });

        removeParentButton.setText("Usuń tag A");
        removeParentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeParentButtonActionPerformed(evt);
            }
        });

        newNameField.setText("Nowa nazwa...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(newNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator2)
                        .addComponent(jSeparator3)
                        .addComponent(setConnectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changeNameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeParentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearSelectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(parentLabel)
                            .addGap(36, 36, 36)
                            .addComponent(childLabel)
                            .addGap(29, 29, 29)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(parentField, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(childField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(parentLabel)
                            .addComponent(childLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(parentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(childField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearSelectionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(newNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changeNameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeParentButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void removeParentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeParentButtonActionPerformed
        if(parent!=null)tags.removeTag(parent);
        else JOptionPane.showMessageDialog(this, "Musisz wybrać Tag.");
        try {
            data.save();
        } catch (IOException ex) {
            Logger.getLogger(EditTagsWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_removeParentButtonActionPerformed

    private void changeNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeNameButtonActionPerformed
        if(parent!=null)tags.setNameOfTag(parent, newNameField.getText());
        else JOptionPane.showMessageDialog(this, "Musisz wybrać Tag.");
        try {
            data.save();
        } catch (IOException ex) {
            Logger.getLogger(EditTagsWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_changeNameButtonActionPerformed

    private void setConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setConnectionButtonActionPerformed
        try {
            if(child!=null && parent!=null)tags.addChildToTag(child, parent);
            else JOptionPane.showMessageDialog(this, "Musisz wybrać Tagi.");
            try {
                data.save();
            } catch (IOException ex) {
                Logger.getLogger(EditTagsWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (CycleException ex) {
            Logger.getLogger(EditTagsWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_setConnectionButtonActionPerformed

    private void clearSelectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSelectionButtonActionPerformed
        parent=null;
        child=null;
        parentField.setText("");
        childField.setText("");
    }//GEN-LAST:event_clearSelectionButtonActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
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
      java.util.logging.Logger.getLogger(EditTagsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(EditTagsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(EditTagsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(EditTagsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel1;
    private javax.swing.JButton changeNameButton;
    private javax.swing.JTextField childField;
    private javax.swing.JLabel childLabel;
    private javax.swing.JButton clearSelectionButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField newNameField;
    private javax.swing.JTextField parentField;
    private javax.swing.JLabel parentLabel;
    private javax.swing.JButton removeParentButton;
    private javax.swing.JButton setConnectionButton;
    private javax.swing.JTree userTagsTree;
    // End of variables declaration//GEN-END:variables
}
