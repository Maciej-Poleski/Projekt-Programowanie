package manager.gui;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import manager.tags.*;
import manager.core.*;
import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.files.backup.*;
import manager.editor.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.text.Position;
import manager.tags.Tags.IUserTagNode;

/**
 *
 * @author Jakub Brzeski
 * @author Jakub Czarnowicz
 */
public class MainWindow extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        editTagsButton = new javax.swing.JButton();
        backupButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        editImageButton = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        tagsLabel = new javax.swing.JLabel();
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
                .addContainerGap(438, Short.MAX_VALUE))
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

        tagsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        tagsList.setMaximumSize(new java.awt.Dimension(0, 80));
        tagsList.setMinimumSize(new java.awt.Dimension(10, 80));
        tagsList.setPreferredSize(new java.awt.Dimension(20, 80));
        tagsListScrollPane.setViewportView(tagsList);

        removeTagFromListButton.setText("Remove selection");
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
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tagsListScrollPane)
                            .addComponent(removeTagFromListButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(tagsLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addComponent(tagsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagsListScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeTagFromListButton)
                .addGap(19, 19, 19))
        );

        leftPanel.setBackground(new java.awt.Color(204, 204, 255));
        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setViewportView(masterTagsTree);

        lefMenu.addTab("Master Tags", jScrollPane1);

        jScrollPane2.setViewportView(userTagsTree);

        lefMenu.addTab("User Tags", jScrollPane2);

        intersectionButton.setText("Intersection");
        intersectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intersectionButtonActionPerformed(evt);
            }
        });

        sumButton.setText("Sum");
        sumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
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
                        .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                .addComponent(intersectionButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sumButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(clearButton))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addGap(0, 38, Short.MAX_VALUE))
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

        addNewTagToSelectedFiles.setText("Create new tag");
        addNewTagToSelectedFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewTagToSelectedFilesMouseClicked(evt);
            }
        });

        newTagTextField.setText("Tag name...");

        addTag.setText("Add tag to selected files");
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
                            .addComponent(newTagTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
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
                        .addGap(0, 15, Short.MAX_VALUE))))
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
                .addComponent(middlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private MasterTag masterTagToRemove;
    private Set<Tag<?>> selectedTags;
    private boolean lastSelectedTree; // false - MasterTag , true - UserTag
    private EditWindow editimagewindow;
    private ImportWindow importwindow;
    private BackupWindow backupwindow;
    private EditTagsWindow edittagswindow;
    private FileID imageToEdit; 
    private ImageHolder imageToEditHolder;
    private Data data;
    
    private class MyFile implements Comparable<MyFile> {
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
        @Override
        public int compareTo(MyFile m){
            return this.toString().compareTo(m.toString());
        }
    }
    
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
        Collections.sort((List)tagsVector);
        this.mainList.setListData(tagsVector);
    }
    private void sumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumButtonActionPerformed
        
        Set<FileID> files = this.tagFilesStore.getFilesWithOneOf(selectedTags);
        if(this.lastSelectedTree==false){ //MasterTags 
            masterTagsVector.clear();
            displayFilesOnMainList(files,this.masterTagsVector);
        }
        else{ //UserTags
            userTagsVector.clear();
            displayFilesOnMainList(files,this.userTagsVector);
        }
    }//GEN-LAST:event_sumButtonActionPerformed
    
    private void intersectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intersectionButtonActionPerformed
        Set<FileID> files = this.tagFilesStore.getFilesWithAllOf(selectedTags);
        if(this.lastSelectedTree==false){ //MasterTags 
            masterTagsVector.clear();
            displayFilesOnMainList(files,this.masterTagsVector);
        }
        else{ //UserTags
            userTagsVector.clear();
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
            int [] ind = mainList.getSelectedIndices();
            if(ind.length==1){
                int indeks = ind[0];
                MyFile myf = (MyFile)mainList.getModel().getElementAt(ind[0]);
                imageToEdit = myf.fileID;
                MasterTag mtag = tagFilesStore.getMasterTagFrom(imageToEdit);
                PrimaryBackup primbackup = backupsmanager.getBackupManagerAssociatedWithMasterTag(mtag).getPrimaryBackup();         
                ImageHolder ih;              
                try {
                    ih = primbackup.getImageToEdition(imageToEdit);
                    editimagewindow  = new EditWindow(ih,new ImageChangedActionListener());
                    editimagewindow.setVisible(true);                    
                } catch (FileNotAvailableException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OperationInterruptedException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

    }//GEN-LAST:event_editImageButtonActionPerformed
    private class ImageChangedActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MasterTag mtag = tagFilesStore.getMasterTagFrom(imageToEdit);
                PrimaryBackup primbackup = backupsmanager.getBackupManagerAssociatedWithMasterTag(mtag).getPrimaryBackup();         
                ImageHolder changedImageHolder = editimagewindow.getImage();  
                primbackup.saveEditedImage(changedImageHolder);
            } catch (OperationInterruptedException ex) {
            } catch (FileNotAvailableException ex) { }
        }
    }
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
                window.masterTagToRemove=mtag;
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
            if(utag!=null){
                if(window.selectedTags.contains(utag)==false){
                window.selectedTags.add(utag);
                }
            }  
            if(utag!=null){
                if(window.selectedTagsVector.contains(utag)==false){
                    window.selectedTagsVector.add(utag);
                }
            }
            window.lastTag = utag;
            window.lastTagTextField.setText(window.lastTag.toString());
            window.displayTagsOnTagsList();
        }  
    } 

    private class searchTextFieldActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                int indeks = mainList.getNextMatch(searchTextField.getText(), 0, Position.Bias.Forward);
                if(indeks != -1) mainList.setSelectedIndex(indeks);
            }          
    }
    private class newTagTextFieldActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!newTagTextField.getText().isEmpty() && mainList.getSelectedIndices().length > 0) {
                UserTag ut = tags.newUserTag(newTagTextField.getText());
                int [] ind = mainList.getSelectedIndices();
                for(int i = 0; i < ind.length; i++) {
                    tagFilesStore.addUserTagToFile(ut, ((MyFile) mainList.getModel().getElementAt(ind[i])).fileID );
                }
                lastTag = ut;
                lastTagTextField.setText(lastTag.toString());
                }
            }
    }
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
                    window.displayMasterTagsTree(window.tags.getModelOfMasterTags());
                    window.displayUserTagsTree(window.tags.getModelOfUserTags());
                    window.masterTagsTree.setRootVisible(false);
                    window.userTagsTree.setRootVisible(false);
                    window.mainList.setListData(new Vector());
                    window.tagsList.setListData(new Vector());
                    window.searchTextField.addActionListener(window.new searchTextFieldActionListener());
                    window.newTagTextField.addActionListener(window.new newTagTextFieldActionListener());
                    new MasterTagsTreeSelectionListener(window);
                    new UserTagsTreeSelectionListener(window);  
                try {
                    window.data.save();
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }                    
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewTagToSelectedFiles;
    private javax.swing.JButton addTag;
    private javax.swing.JButton backupButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton clearButton;
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
