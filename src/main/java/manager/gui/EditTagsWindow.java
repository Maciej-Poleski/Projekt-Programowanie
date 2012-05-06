/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.tags.*;
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
        displayExtensions();
        extensionList.addListSelectionListener(new extensionListListener());
    }

    private void displayUserTagsTree(TreeModel utm){
        this.userTagsTree.setModel(utm);
        this.userTagsTree.setRootVisible(false);
    }
    private class extensionListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(extensionList.getSelectedValue()!=null){
            extensionTextField.setText(extensionList.getSelectedValue().toString());
            }
        }
    }

    private class UserTagsTreeSelectionListener implements MouseListener{

        UserTagsTreeSelectionListener(){
            userTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            userTagsTree.addMouseListener(this);      
        }
        @Override
        public void mouseClicked(MouseEvent e) {
           Tags.IUserTagNode temptag = (Tags.IUserTagNode)userTagsTree.getLastSelectedPathComponent();
             if(temptag == null) return;
             UserTag utag = temptag.getTag();
             
            if(parent==null){
                parent=utag;
                parentField.setText(utag.toString());
                displayExtensions();
            }else if(child==null){
                child=utag;
                childField.setText(utag.toString());
            }          
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    } 
     private void displayExtensions(){
        Map<UserTag, Set<String>> mapa = tags.getUserTagAutoExtensionManager().getUserTagToAutoExtensionsMapping(); 
        
        Vector<String> v = new Vector<String>();
        if(mapa.get(parent)!=null){
        for(String s: mapa.get(parent))v.add(s);  
        Collections.sort((List)v);
        }
        this.extensionList.setListData(v);
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
        jSeparator1 = new javax.swing.JSeparator();
        newUserTagTextField = new javax.swing.JTextField();
        newUserTagButton = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        extensionTextField = new javax.swing.JTextField();
        addExtensionButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        removeExtensionButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        extensionList = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit tags");
        setMaximumSize(new java.awt.Dimension(400, 700));
        setPreferredSize(new java.awt.Dimension(430, 700));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        clearSelectionButton.setText("Usuń zaznaczenie");
        clearSelectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSelectionButtonActionPerformed(evt);
            }
        });

        parentField.setBackground(new java.awt.Color(220, 220, 240));
        parentField.setEditable(false);
        parentField.setText("Rodzic");
        parentField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        childField.setBackground(new java.awt.Color(220, 220, 240));
        childField.setEditable(false);
        childField.setText("Dziecko");
        childField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        newNameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newNameFieldMouseClicked(evt);
            }
        });

        newUserTagTextField.setText("Nazwa...");
        newUserTagTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newUserTagTextFieldMouseClicked(evt);
            }
        });

        newUserTagButton.setText("Nowy UserTag");
        newUserTagButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newUserTagButtonActionPerformed(evt);
            }
        });

        extensionTextField.setText("Nazwa rozszerzenia...");
        extensionTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                extensionTextFieldMouseClicked(evt);
            }
        });

        addExtensionButton.setText("Powiąż tag A z rozszerzeniem");
        addExtensionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExtensionButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Podaj rozszerzenie bez kropki.");

        removeExtensionButton.setText("Usuń rozszerzenie z tagu A");
        removeExtensionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeExtensionButtonActionPerformed(evt);
            }
        });

        extensionList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(extensionList);

        jLabel4.setText("Rozszerzenia związane z tagiem:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(setConnectionButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(changeNameButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeParentButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearSelectionButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newUserTagButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newUserTagTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newNameField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(extensionTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addExtensionButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeExtensionButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(parentField, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(parentLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(childLabel)
                                    .addComponent(childField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
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
                        .addComponent(removeParentButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extensionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addExtensionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeExtensionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(newUserTagTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newUserTagButton)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void removeParentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeParentButtonActionPerformed
        if(parent!=null){
            tags.removeTag(parent);
            parentField.setText(null);
            newNameField.setText(null);
            parent=null;
        }
        else JOptionPane.showMessageDialog(this, "Musisz wybrać Tag.");
    }//GEN-LAST:event_removeParentButtonActionPerformed

    private void changeNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeNameButtonActionPerformed
        if(parent!=null){
            tags.setNameOfTag(parent, newNameField.getText());
            parentField.setText(newNameField.getText());
        }
        else JOptionPane.showMessageDialog(this, "Musisz wybrać Tag.");
    }//GEN-LAST:event_changeNameButtonActionPerformed

    private void setConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setConnectionButtonActionPerformed
        try {
            if(child!=null && parent!=null)tags.addChildToTag(child, parent);
            else JOptionPane.showMessageDialog(this, "Musisz wybrać Tagi.");
        } catch (CycleException ex) {
            JOptionPane.showMessageDialog(this, "Te tagi są już w relacji.");
        } catch (IllegalStateException ex){
            JOptionPane.showMessageDialog(this, "Taka relacja już istnieje.");
        }
    }//GEN-LAST:event_setConnectionButtonActionPerformed

    private void clearSelectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSelectionButtonActionPerformed
        parent=null;
        child=null;
        parentField.setText("");
        childField.setText("");
        extensionList.setListData(new Vector());
    }//GEN-LAST:event_clearSelectionButtonActionPerformed

    private void addExtensionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExtensionButtonActionPerformed
        if(extensionTextField.getText().isEmpty()==false){
            if(parent!=null){
                    UserTagAutoExtensionsManager extmanager=tags.getUserTagAutoExtensionManager();
                    extmanager.registerUserTagAutoExtension(parent, extensionTextField.getText());
                    displayExtensions();
            }else{
                JOptionPane.showMessageDialog(this, "Musisz wybrać tag.");
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Najpierw wpisz nazwę.");
        }        
    }//GEN-LAST:event_addExtensionButtonActionPerformed

    private void newUserTagButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newUserTagButtonActionPerformed
        if(newUserTagTextField.getText().isEmpty()==false){
                UserTag ut = tags.newUserTag(newUserTagTextField.getText());
        }
        else{
            JOptionPane.showMessageDialog(this, "Najpierw wpisz nazwę.");
        }
    }//GEN-LAST:event_newUserTagButtonActionPerformed

    private void removeExtensionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeExtensionButtonActionPerformed
        if(extensionTextField.getText().isEmpty()==false){
            if(parent!=null){
                    UserTagAutoExtensionsManager extmanager=tags.getUserTagAutoExtensionManager();
                    extmanager.unregisterUserTagAutoExtension(parent, extensionTextField.getText());
                    displayExtensions();
            }else{
                JOptionPane.showMessageDialog(this, "Musisz wybrać tag.");
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Najpierw wpisz nazwę.");
        }         
    }//GEN-LAST:event_removeExtensionButtonActionPerformed

    private void newNameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newNameFieldMouseClicked
        if(newNameField.getText().equals("Nowa nazwa...")) newNameField.setText(null);
    }//GEN-LAST:event_newNameFieldMouseClicked

    private void extensionTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extensionTextFieldMouseClicked
       if(extensionTextField.getText().equals("Nazwa rozszerzenia...")) extensionTextField.setText(null);
    }//GEN-LAST:event_extensionTextFieldMouseClicked

    private void newUserTagTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newUserTagTextFieldMouseClicked
        if(newUserTagTextField.getText().equals("Nazwa...")) newUserTagTextField.setText(null);
    }//GEN-LAST:event_newUserTagTextFieldMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            data.save();
        } catch (IOException ex) {
            Logger.getLogger(EditTagsWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed

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
    private javax.swing.JButton addExtensionButton;
    private javax.swing.JPanel buttonPanel1;
    private javax.swing.JButton changeNameButton;
    private javax.swing.JTextField childField;
    private javax.swing.JLabel childLabel;
    private javax.swing.JButton clearSelectionButton;
    private javax.swing.JList extensionList;
    private javax.swing.JTextField extensionTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField newNameField;
    private javax.swing.JButton newUserTagButton;
    private javax.swing.JTextField newUserTagTextField;
    private javax.swing.JTextField parentField;
    private javax.swing.JLabel parentLabel;
    private javax.swing.JButton removeExtensionButton;
    private javax.swing.JButton removeParentButton;
    private javax.swing.JButton setConnectionButton;
    private javax.swing.JTree userTagsTree;
    // End of variables declaration//GEN-END:variables
}
