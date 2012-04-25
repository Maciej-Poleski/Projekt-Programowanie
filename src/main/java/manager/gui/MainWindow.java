package manager.gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import manager.tags.*;
import manager.core.*;
import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.FileSaveException;
import manager.files.OperationInterruptedException;
import manager.files.backup.*;
import manager.editor.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.text.Position;
import manager.tags.Tags.IUserTagNode;
/**
 *
 * @author Jakub Brzeski
 * @author Jakub Czarnowicz
 */
public class MainWindow extends javax.swing.JDialog {
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
        tagsListScrollPane = new javax.swing.JScrollPane();
        tagsList = new javax.swing.JList();
        removeTagFromListButton = new javax.swing.JButton();
        leftPanel = new javax.swing.JPanel();
        lefMenu = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        masterTagsTree = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        userTagsTree = new javax.swing.JTree();
        intersectionButton = new javax.swing.JButton();
        sumButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        middlePanel = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        tagSearchButton = new javax.swing.JButton();
        fileSearchButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JList();
        addNewTagToSelectedFiles = new javax.swing.JButton();
        newTagTextField = new javax.swing.JTextField();
        addTag = new javax.swing.JButton();
        lastTagTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        editImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImageButtonActionPerformed(evt);
            }
        });

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
                .addContainerGap(556, Short.MAX_VALUE))
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
        rightPanel.setMaximumSize(new java.awt.Dimension(300, 800));

        tagsLabel.setText("Selected Tags");

        allTagsButton.setText("All");

        commonTagsButton.setText("Common");

        tagsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        tagsListScrollPane.setViewportView(tagsList);

        removeTagFromListButton.setText("Remove");
        removeTagFromListButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeTagFromListButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(tagsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(rightPanelLayout.createSequentialGroup()
                                    .addGap(51, 51, 51)
                                    .addComponent(tagsLabel))
                                .addGroup(rightPanelLayout.createSequentialGroup()
                                    .addGap(28, 28, 28)
                                    .addComponent(commonTagsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(removeTagFromListButton, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                .addComponent(allTagsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addComponent(tagsLabel)
                .addGap(7, 7, 7)
                .addComponent(tagsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commonTagsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allTagsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeTagFromListButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        leftPanel.setBackground(new java.awt.Color(204, 204, 255));
        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setViewportView(masterTagsTree);

        lefMenu.addTab("Master Tags", jScrollPane1);

        jScrollPane2.setViewportView(userTagsTree);

        lefMenu.addTab("User Tags", jScrollPane2);

        intersectionButton.setText("INTERSECTION");
        intersectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intersectionButtonActionPerformed(evt);
            }
        });

        sumButton.setText("SUM");
        sumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumButtonActionPerformed(evt);
            }
        });

        clearButton.setText("CLEAR");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lefMenu)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addComponent(intersectionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sumButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(clearButton))
                .addContainerGap())
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lefMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sumButton)
                    .addComponent(intersectionButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        middlePanel.setBackground(new java.awt.Color(204, 204, 255));
        middlePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        searchTextField.setText("Search...");

        tagSearchButton.setText("Tag");
        tagSearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tagSearchButtonMouseClicked(evt);
            }
        });

        fileSearchButton.setText("File");
        fileSearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileSearchButtonMouseClicked(evt);
            }
        });

        mainList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(mainList);

        addNewTagToSelectedFiles.setText("Add new tag");
        addNewTagToSelectedFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewTagToSelectedFilesMouseClicked(evt);
            }
        });

        newTagTextField.setText("Tag name...");

        addTag.setText("Add tag");
        addTag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTagMouseClicked(evt);
            }
        });

        lastTagTextField.setEditable(false);

        javax.swing.GroupLayout middlePanelLayout = new javax.swing.GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addTag, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addNewTagToSelectedFiles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(middlePanelLayout.createSequentialGroup()
                                .addComponent(tagSearchButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileSearchButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchTextField)
                            .addComponent(newTagTextField)
                            .addComponent(lastTagTextField))))
                .addContainerGap())
        );
        middlePanelLayout.setVerticalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileSearchButton)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tagSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addComponent(newTagTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addComponent(addNewTagToSelectedFiles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addTag)
                            .addComponent(lastTagTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                    .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(middlePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleParent(buttonPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Tags tags;
    private UserTag lastTag;
    private TagFilesStore tagFilesStore;
    private BackupsManager backupsmanager;
    private Vector<MyFile> masterTagsVector;
    private Vector<MyFile> userTagsVector;
    private Vector<Tag<?>> selectedTagsVector;
    private Set<Tag<?>> selectedTags;
    private boolean lastSelectedTree; // false - MasterTag , true - UserTag
    
    private EditWindow editimagewindow;
    private ImportWindow importwindow;
    private BackupWindow backupwindow;
    private EditTagsWindow edittagswindow;
    
    private FileID imageToEdit; 
    private ImageHolder imageToEditHolder;
    private Data data;
    
    MainWindow(){    
        try {
            data = Data.load();
            this.tags = data.getTags();
            this.tagFilesStore = data.getTagFilesStore();
            this.backupsmanager = data.getBackupsManager();
            masterTagsVector = new Vector<MyFile>();
            userTagsVector = new Vector<MyFile>();
            selectedTagsVector = new Vector<Tag<?>>();
            selectedTags = new HashSet<Tag<?>>();
                
        } catch (IOException ex) { }
        initComponents();
    }
    

    private class MyFile{

        private File file;
        private FileID fileID;
        MyFile(File a,FileID b){
            file=a;
            fileID=b;
        }
        @Override
        public String toString(){
            return file.getName();
        }
    }
    
    private void editTagsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTagsButtonActionPerformed
        edittagswindow = new EditTagsWindow(this.tags, this.tagFilesStore, this.backupsmanager, this.data);
        edittagswindow.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        edittagswindow.setVisible(true);
    }//GEN-LAST:event_editTagsButtonActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
       importwindow = new ImportWindow(this.tags, this.tagFilesStore, this.backupsmanager, this.data);
       importwindow.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
       importwindow.setVisible(true);
    }//GEN-LAST:event_importButtonActionPerformed

    private void backupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupButtonActionPerformed
       backupwindow = new BackupWindow(this.tags, this.tagFilesStore, this.backupsmanager, this.data);
       backupwindow.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
       backupwindow.setVisible(true);
    }//GEN-LAST:event_backupButtonActionPerformed

    private void displayFilesOnMainList(Set<FileID> files, Vector<MyFile> tagsVector){
        for(FileID fID: files){
            if(tagsVector.contains(fID)==false){
                try {
                    File file = this.backupsmanager.getFile(fID);
                    tagsVector.add(new MyFile(file,fID));
                } catch (OperationInterruptedException ex) {
                } catch (FileNotAvailableException ex) {
                }
            }    
        }
        this.mainList.setListData(tagsVector);       
    }
    private void sumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumButtonActionPerformed
        Set<FileID> files = this.tagFilesStore.getFilesWithOneOf(selectedTags);
        if(this.lastSelectedTree==false){ //MasterTags 
            displayFilesOnMainList(files,this.masterTagsVector);
        }
        else{ //UserTags
            displayFilesOnMainList(files,this.userTagsVector);
            this.userTagsVector.clear();
        }
    }//GEN-LAST:event_sumButtonActionPerformed

    private void intersectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intersectionButtonActionPerformed
        Set<FileID> files = this.tagFilesStore.getFilesWithAllOf(selectedTags);
        if(this.lastSelectedTree==false){ //MasterTags 
            displayFilesOnMainList(files,this.masterTagsVector);
        }
        else{ //UserTags
            displayFilesOnMainList(files,this.userTagsVector);
        }       
    }//GEN-LAST:event_intersectionButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.selectedTags.clear();
        this.selectedTagsVector.clear();
        this.masterTagsVector.clear();
        this.userTagsVector.clear();
        this.mainList.setListData(new Vector());
        this.tagsList.setListData(new Vector());
        
    }//GEN-LAST:event_clearButtonActionPerformed

    private void editImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImageButtonActionPerformed
        BufferedImage im3; 
        try {
            im3 = ImageIO.read(new File("1.jpg"));
            ImageHolder i =new ImageHolder(im3, null, null);    
            editimagewindow = new EditWindow(i,null);
            editimagewindow.setVisible(true);            
        } catch (IOException ex) {
           
        }

    }//GEN-LAST:event_editImageButtonActionPerformed

  private void fileSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileSearchButtonMouseClicked
      int indeks = mainList.getNextMatch(searchTextField.getText(), 0, Position.Bias.Forward);
      if(indeks != -1) mainList.setSelectedIndex(indeks);
  }//GEN-LAST:event_fileSearchButtonMouseClicked

  private void addNewTagToSelectedFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewTagToSelectedFilesMouseClicked
     if(!newTagTextField.getText().isEmpty() && mainList.getSelectedIndices().length > 0) {
        UserTag ut = tags.newUserTag(newTagTextField.getText());
        int [] ind = mainList.getSelectedIndices();
        for(int i = 0; i < ind.length; i++) {
          tagFilesStore.addUserTagToFile(ut, ((MyFile) mainList.getModel().getElementAt(ind[i])).fileID );
        }
        lastTag = ut;
        lastTagTextField.setText(lastTag.toString());
      }
  }//GEN-LAST:event_addNewTagToSelectedFilesMouseClicked

  private void tagSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tagSearchButtonMouseClicked
      int indeks = tagsList.getNextMatch(searchTextField.getText(), 0, Position.Bias.Forward);
      if(indeks != -1) tagsList.setSelectedIndex(indeks);
  }//GEN-LAST:event_tagSearchButtonMouseClicked

  private void addTagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTagMouseClicked
    if(lastTag != null) {
      int [] ind = mainList.getSelectedIndices();
      for(int i = 0; i < ind.length; i++) {
        tagFilesStore.addUserTagToFile(lastTag, ((MyFile) mainList.getModel().getElementAt(ind[i])).fileID );
      }
    }
  }//GEN-LAST:event_addTagMouseClicked

private void removeTagFromListButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeTagFromListButtonMouseClicked
    if(tagsList.getSelectedIndices().length > 0 ) {
      int[] ind = tagsList.getSelectedIndices();
      for(int i = ind.length - 1; i >= 0; i--) {
        selectedTags.remove(tagsList.getModel().getElementAt(ind[i]));
        selectedTagsVector.remove(tagsList.getModel().getElementAt(ind[i]));
      }
      tagsList.setListData(selectedTagsVector);
    }
}//GEN-LAST:event_removeTagFromListButtonMouseClicked

    private void displayTagsOnTagsList(){
            tagsList.setListData(selectedTagsVector);
    }
    private void displayUserTagsTree(TreeModel utm){
        this.userTagsTree.setModel(utm);
    }
    private void displayMasterTagsTree(TreeModel mtt){
        this.masterTagsTree.setModel(mtt);
    }
    private static class MasterTagsTreeSelectionListener implements TreeSelectionListener{
        MainWindow window;
        MasterTagsTreeSelectionListener(MainWindow w){
            window=w;
            window.masterTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            window.masterTagsTree.addTreeSelectionListener(this);      
        }
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            MasterTag mtag = (MasterTag)window.masterTagsTree.getLastSelectedPathComponent();
            if(window.lastSelectedTree==true){
                window.selectedTags.clear();
                window.selectedTagsVector.clear();
                window.lastSelectedTree=false;
            }
            if(mtag!=null){
                if(window.selectedTags.contains(mtag)==false){
                window.selectedTags.add(mtag);
                }
            }
            if(mtag!=null){
                if(window.selectedTagsVector.contains(mtag)==false){
                window.selectedTagsVector.add(mtag);
                }
            }
            window.displayTagsOnTagsList();
        }  
    }
    private static class UserTagsTreeSelectionListener implements TreeSelectionListener{
        MainWindow window;
        UserTagsTreeSelectionListener(MainWindow w){
            window=w;
            window.userTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            window.userTagsTree.addTreeSelectionListener(this);      
        }
        @Override
        public void valueChanged(TreeSelectionEvent e) {
             IUserTagNode temptag = (IUserTagNode)window.userTagsTree.getLastSelectedPathComponent();
             if(temptag == null) return;
             UserTag utag = temptag.getTag();
             
            if(window.lastSelectedTree==false){
                window.selectedTags.clear();
                window.selectedTagsVector.clear();
                window.lastSelectedTree=true;
            }
            if(utag!=null)window.selectedTags.add(utag);  
            if(utag!=null)window.selectedTagsVector.add(utag);
            window.lastTag = utag;
            window.lastTagTextField.setText(window.lastTag.toString());
            window.displayTagsOnTagsList();
        }  
    } 
    private class ImageChangedActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                File file = backupsmanager.getFile(imageToEdit);
                MasterTag mtag = tagFilesStore.getMasterTagFrom(imageToEdit);
                PrimaryBackup primbackup = backupsmanager.getBackupManagerAssociatedWithMasterTag(mtag).getPrimaryBackup();         
                ImageHolder changedImageHolder = editimagewindow.getImage();  
                primbackup.saveEditedImage(changedImageHolder);
            } catch (OperationInterruptedException ex) {
            } catch (FileNotAvailableException ex) { }
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
                
                    final MainWindow window = new MainWindow();
                    window.setVisible(true);       
                    
                    // testy
                    /*
                    MasterTag parent1 = window.tags.newMasterTag("parent1");
                    MasterTag child1 = window.tags.newMasterTag(parent1, "child1");
                    MasterTag child2 = window.tags.newMasterTag(parent1, "child2");
                    MasterTag childOfChild = window.tags.newMasterTag(child2, "child of child");
                    
                    UserTag parent2 = window.tags.newUserTag("userTag1");
                    UserTag child3 = window.tags.newUserTag("userTag2");
                    
                    */
                    window.displayMasterTagsTree(window.tags.getModelOfMasterTags());
                    window.displayUserTagsTree(window.tags.getModelOfUserTags());
                    window.masterTagsTree.setRootVisible(false);
                    window.mainList.setListData(new Vector());
                    window.tagsList.setListData(new Vector());
                    
                    
                    window.searchTextField.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                            int indeks = window.mainList.getNextMatch(window.searchTextField.getText(), 0, Position.Bias.Forward);
                            if(indeks != -1) window.mainList.setSelectedIndex(indeks);
                       }
                    });
                    window.newTagTextField.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                          if(!window.newTagTextField.getText().isEmpty() && window.mainList.getSelectedIndices().length > 0) {
                            UserTag ut = window.tags.newUserTag(window.newTagTextField.getText());
                            int [] ind = window.mainList.getSelectedIndices();
                            for(int i = 0; i < ind.length; i++) {
                              window.tagFilesStore.addUserTagToFile(ut, ((MyFile) window.mainList.getModel().getElementAt(ind[i])).fileID );
                            }
                            window.lastTag = ut;
                            window.lastTagTextField.setText(window.lastTag.toString());
                          }
                        }
                    });
                    new MasterTagsTreeSelectionListener(window);
                    new UserTagsTreeSelectionListener(window);           
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewTagToSelectedFiles;
    private javax.swing.JButton addTag;
    private javax.swing.JButton allTagsButton;
    private javax.swing.JButton backupButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton commonTagsButton;
    private javax.swing.JButton editImageButton;
    private javax.swing.JButton editTagsButton;
    private javax.swing.JButton fileSearchButton;
    private javax.swing.JButton importButton;
    private javax.swing.JButton intersectionButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField lastTagTextField;
    private javax.swing.JTabbedPane lefMenu;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JList mainList;
    private javax.swing.JTree masterTagsTree;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JTextField newTagTextField;
    private javax.swing.JButton removeTagFromListButton;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton sumButton;
    private javax.swing.JButton tagSearchButton;
    private javax.swing.JLabel tagsLabel;
    private javax.swing.JList tagsList;
    private javax.swing.JScrollPane tagsListScrollPane;
    private javax.swing.JTree userTagsTree;
    // End of variables declaration//GEN-END:variables
}
