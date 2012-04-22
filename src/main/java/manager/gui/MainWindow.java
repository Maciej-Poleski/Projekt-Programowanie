package manager.gui;

import manager.tags.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Jakub Brzeski
 */
public class MainWindow extends javax.swing.JFrame {
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        editTagsButton = new javax.swing.JButton();
        backupButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        editImageButton = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        tagsLabel = new javax.swing.JLabel();
        allTagsButton = new javax.swing.JButton();
        commonTagsButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        rightEditorPane = new javax.swing.JEditorPane();
        leftPanel = new javax.swing.JPanel();
        lefMenu = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        masterTagsTree = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        userTagsTree = new javax.swing.JTree();
        addTagButton = new javax.swing.JButton();
        showButton = new javax.swing.JButton();
        middlePanel = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        tagSearchButton = new javax.swing.JButton();
        fileSearchButton = new javax.swing.JButton();
        middleScrollPane = new javax.swing.JScrollPane();
        middleEditorPane = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kompleksowa Obsługa Zdjęć i Katalogów");
        setName("mainFrame");
        setResizable(false);

        buttonPanel.setBackground(new java.awt.Color(204, 204, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonPanel.setForeground(new java.awt.Color(204, 204, 255));

        editTagsButton.setText("EDIT TAGS");
        editTagsButton.setMinimumSize(new java.awt.Dimension(72, 23));
        editTagsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTagsButtonActionPerformed(evt);
            }
        });

        backupButton.setText("BACKUP");
        backupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backupButtonActionPerformed(evt);
            }
        });

        importButton.setText("IMPORT");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        editImageButton.setText("EDIT IMAGE");

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(importButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editTagsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backupButton)
                .addGap(18, 18, 18)
                .addComponent(editImageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editTagsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backupButton)
                        .addComponent(editImageButton)))
                .addContainerGap())
        );

        rightPanel.setBackground(new java.awt.Color(204, 204, 255));
        rightPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tagsLabel.setText("Tags");

        allTagsButton.setText("All");

        commonTagsButton.setText("Common");

        jScrollPane3.setViewportView(rightEditorPane);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rightPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(allTagsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(commonTagsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rightPanelLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(tagsLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addComponent(tagsLabel)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commonTagsButton)
                    .addComponent(allTagsButton))
                .addGap(19, 19, 19))
        );

        leftPanel.setBackground(new java.awt.Color(204, 204, 255));
        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setViewportView(masterTagsTree);

        lefMenu.addTab("Master Tags", jScrollPane1);

        jScrollPane2.setViewportView(userTagsTree);

        lefMenu.addTab("User Tags", jScrollPane2);

        addTagButton.setText("Add Tag");

        showButton.setText("Show");

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lefMenu)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addComponent(addTagButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lefMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showButton)
                    .addComponent(addTagButton))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        middlePanel.setBackground(new java.awt.Color(204, 204, 255));
        middlePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        searchTextField.setText("Search...");

        tagSearchButton.setText("Tag");

        fileSearchButton.setText("File");

        middleScrollPane.setPreferredSize(new java.awt.Dimension(180, 22));

        middleEditorPane.setEditable(false);
        middleEditorPane.setPreferredSize(new java.awt.Dimension(180, 20));
        middleEditorPane.setRequestFocusEnabled(false);
        middleScrollPane.setViewportView(middleEditorPane);

        javax.swing.GroupLayout middlePanelLayout = new javax.swing.GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(middleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addComponent(tagSearchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileSearchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchTextField)))
                .addContainerGap())
        );
        middlePanelLayout.setVerticalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(middleScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tagSearchButton)
                        .addComponent(fileSearchButton))
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(middlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(leftPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(middlePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleParent(buttonPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public MainWindow() {initComponents();}
    private void editTagsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTagsButtonActionPerformed
        new EditTagsWindow().setVisible(true);
    }//GEN-LAST:event_editTagsButtonActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        new ImportWindow().setVisible(true);
    }//GEN-LAST:event_importButtonActionPerformed

    private void backupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupButtonActionPerformed
        new BackupWindow().setVisible(true);
    }//GEN-LAST:event_backupButtonActionPerformed
    
    void displayUserTagsTree(TreeModel utm){
        this.userTagsTree.setModel(utm);
    }
    void displayMasterTagsTree(TreeModel mtt){
        this.masterTagsTree.setModel(mtt);
    }
    static class MasterTagsTreeSelectionListener implements TreeSelectionListener{
        MainWindow window;
        MasterTagsTreeSelectionListener(MainWindow w){
            window=w;
            window.masterTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            window.masterTagsTree.addTreeSelectionListener(this);      
        }
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            MasterTag x = (MasterTag)window.masterTagsTree.getLastSelectedPathComponent();
            window.middleEditorPane.setText("** FILES FROM "+x.toString()+" **\n");
            //Set<FileID>
            
        }  
    }
    static class UserTagsTreeSelectionListener implements TreeSelectionListener{
        MainWindow window;
        UserTagsTreeSelectionListener(MainWindow w){
            window=w;
            window.masterTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            window.masterTagsTree.addTreeSelectionListener(this);      
        }
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            UserTag x = (UserTag)window.masterTagsTree.getLastSelectedPathComponent();
            window.middleEditorPane.setText("** FILES FROM "+x.toString()+" **\n");
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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
                
            final MainWindow F = new MainWindow();
            F.setVisible(true);       
            MasterTag parent1 = Tags.getDefaultInstance().newMasterTag("parent1");
            MasterTag child1 = Tags.getDefaultInstance().newMasterTag(parent1, "child1");
            MasterTag child2 = Tags.getDefaultInstance().newMasterTag(parent1, "child2");
            MasterTag childOfChild = Tags.getDefaultInstance().newMasterTag(child2, "child of child");
            MasterTag parent2 = Tags.getDefaultInstance().newMasterTag("parent2");
            F.displayMasterTagsTree(Tags.getDefaultInstance().getModelOfMasterTags());
            F.masterTagsTree.setRootVisible(false);

            new MasterTagsTreeSelectionListener(F);
  
                               
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTagButton;
    private javax.swing.JButton allTagsButton;
    private javax.swing.JButton backupButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton commonTagsButton;
    private javax.swing.JButton editImageButton;
    private javax.swing.JButton editTagsButton;
    private javax.swing.JButton fileSearchButton;
    private javax.swing.JButton importButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane lefMenu;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTree masterTagsTree;
    private javax.swing.JEditorPane middleEditorPane;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JScrollPane middleScrollPane;
    private javax.swing.JEditorPane rightEditorPane;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton showButton;
    private javax.swing.JButton tagSearchButton;
    private javax.swing.JLabel tagsLabel;
    private javax.swing.JTree userTagsTree;
    // End of variables declaration//GEN-END:variables
}
