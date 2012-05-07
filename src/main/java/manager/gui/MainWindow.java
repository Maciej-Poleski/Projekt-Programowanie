package manager.gui;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import manager.tags.*;
import manager.core.*;
import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.files.backup.*;
import manager.editor.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;
import manager.tags.Tags.IUserTagNode;

/**
 *
 * @author Jakub Brzeski
 * @author Jakub Czarnowicz
 */
public class MainWindow extends JFrame{

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        editTagsButton = new javax.swing.JButton();
        backupButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        editImageButton = new javax.swing.JButton();
        resetDataButton = new javax.swing.JButton();
        clearDataButton = new javax.swing.JCheckBox();
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
        createNewTag = new javax.swing.JButton();
        newTagTextField = new javax.swing.JTextField();
        addTagToFiles = new javax.swing.JButton();
        lastTagTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kompleksowa Obsługa Zdjęć i Katalogów");
        setName("mainFrame");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        buttonPanel.setBackground(new java.awt.Color(204, 204, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonPanel.setForeground(new java.awt.Color(204, 204, 255));

        editTagsButton.setText("EDYTUJ TAGI");
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

        editImageButton.setText("EDYTUJ OBRAZ");
        editImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImageButtonActionPerformed(evt);
            }
        });

        resetDataButton.setText("WYCZYŚĆ BAZĘ DANYCH");
        resetDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetDataButtonActionPerformed(evt);
            }
        });

        clearDataButton.setBackground(new java.awt.Color(204, 204, 255));
        clearDataButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        clearDataButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                clearDataButtonStateChanged(evt);
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
                .addComponent(editImageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resetDataButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearDataButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(importButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backupButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editImageButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetDataButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editTagsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearDataButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        rightPanel.setBackground(new java.awt.Color(204, 204, 255));
        rightPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        rightPanel.setMaximumSize(new java.awt.Dimension(300, 800));

        tagsLabel.setText("Wybrane tagi");

        tagsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        tagsList.setMaximumSize(new java.awt.Dimension(0, 80));
        tagsList.setMinimumSize(new java.awt.Dimension(10, 80));
        tagsList.setPreferredSize(new java.awt.Dimension(20, 80));
        tagsListScrollPane.setViewportView(tagsList);

        removeTagFromListButton.setText("Usuń zaznaczenie");
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
                .addContainerGap()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeTagFromListButton, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tagsListScrollPane)
                            .addGroup(rightPanelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(tagsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addComponent(tagsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagsListScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeTagFromListButton)
                .addContainerGap())
        );

        leftPanel.setBackground(new java.awt.Color(204, 204, 255));
        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setViewportView(masterTagsTree);

        lefMenu.addTab("Tagi macierzyste", jScrollPane1);

        jScrollPane2.setViewportView(userTagsTree);

        lefMenu.addTab("Tagi użytkownika", jScrollPane2);

        intersectionButton.setText("Przecięcie");
        intersectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intersectionButtonActionPerformed(evt);
            }
        });

        sumButton.setText("Suma");
        sumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Wyczyść listy");
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
                    .addComponent(lefMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(sumButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(intersectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lefMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(intersectionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sumButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        middlePanel.setBackground(new java.awt.Color(204, 204, 255));
        middlePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        searchTextField.setText("Znajdź");
        searchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseClicked(evt);
            }
        });

        tagSearchButton.setText("Wybrany tag");
        tagSearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tagSearchButtonMouseClicked(evt);
            }
        });

        fileSearchButton.setText("Plik");
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

        createNewTag.setText("Stwórz nowy tag użytkownika");
        createNewTag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createNewTagMouseClicked(evt);
            }
        });

        newTagTextField.setText("Nazwa tagu...");
        newTagTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newTagTextFieldMouseClicked(evt);
            }
        });

        addTagToFiles.setText("Dodaj wybrany tag do plików");
        addTagToFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTagToFilesMouseClicked(evt);
            }
        });

        lastTagTextField.setBackground(new java.awt.Color(220, 220, 240));
        lastTagTextField.setEditable(false);
        lastTagTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout middlePanelLayout = new javax.swing.GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addTagToFiles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(createNewTag, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(middlePanelLayout.createSequentialGroup()
                                .addComponent(tagSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(createNewTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addTagToFiles)
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
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(middlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(middlePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leftPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private static Data data;
    
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
            this.tags = data.getTags();
            this.tagFilesStore = data.getTagFilesStore();
            this.backupsmanager = data.getBackupsManager();
            this.setVisible(true);
            initComponents();
            
            masterTagsVector = new Vector<MyFile>();
            userTagsVector = new Vector<MyFile>();
            selectedTagsVector = new Vector<Tag<?>>();
            selectedTags = new HashSet<Tag<?>>();   
            displayMasterTagsTree(this.tags.getModelOfMasterTags());
            displayUserTagsTree(this.tags.getModelOfUserTags());
            masterTagsTree.setRootVisible(false);
            userTagsTree.setRootVisible(false);
            mainList.setListData(new Vector());
            tagsList.setListData(new Vector());  
            searchTextField.addActionListener(new searchTextFieldActionListener());
            newTagTextField.addActionListener(new newTagTextFieldActionListener());            
            new MasterTagsTreeSelectionListener(this);
            new UserTagsTreeSelectionListener(this); 
            mainList.addListSelectionListener(new MainListOneImageSelectionListener());
            editImageButton.setEnabled(false);
            resetDataButton.setEnabled(false);

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
            boolean b=false;
            for(MyFile f: tagsVector){
                if(f.fileID==fID)b=true;
            }
            if(b==false){
                try {
                    File file = this.backupsmanager.getFile(fID);
                    tagsVector.add(new MyFile(file,fID));
                } catch (        OperationInterruptedException | FileNotAvailableException ex) {
                    JOptionPane.showMessageDialog(this,"Nie udało się uzyskać pliku.","Error",JOptionPane.ERROR_MESSAGE);
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

            MasterTag mtag = tagFilesStore.getMasterTagFrom(imageToEdit);
            BackupManager bmanager = backupsmanager.getBackupManagerAssociatedWithMasterTag(mtag);
            PrimaryBackup primbackup = bmanager.getPrimaryBackup();           
            try {
                imageToEditHolder = primbackup.getImageToEdition(imageToEdit);
                BufferedImage bi = imageToEditHolder.getBufferedImage();
                if(bi!=null){
                    editimagewindow  = new EditWindow(imageToEditHolder,new ImageChangedActionListener());
                    editimagewindow.setVisible(true);
                } else{
                    JOptionPane.showMessageDialog(this,"Ten plik nie jest poprawnym plikiem graficznym.","Error",JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotAvailableException ex) {
                JOptionPane.showMessageDialog(this,"Nie udało się uzyskać obrazu do edycji.","Error",JOptionPane.ERROR_MESSAGE);
            }
            catch (OperationInterruptedException ex) {
                Set<SecondaryBackup> sbackups = bmanager.getSecondaryBackups();
                boolean retrieved=false;
                for(SecondaryBackup i : sbackups){
                    try {
                        File x = i.getFile(imageToEdit);
                        primbackup.addDeletedFile(imageToEdit, x); //addFile(mtag, x, false);
                        retrieved=true;
                        break;
                    } catch (        FileNotAvailableException | OperationInterruptedException ex1) {
                        JOptionPane.showMessageDialog(this,"Wybrany plik został usunięty lub jest uszkodzony,a próba "
                            + "\njego odzyskania przy użyciu kopii zapasowej nie powiodła się."
                            + "\nNie można kontynuować","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(!retrieved){
                    JOptionPane.showMessageDialog(this,"Wybrany plik został usunięty lub jest uszkodzony,a próba "
                            + "\njego odzyskania przy użyciu kopii zapasowej nie powiodła się."
                            + "\nNie można kontynuować","Error",JOptionPane.ERROR_MESSAGE);
                }else{
                    try {
                        imageToEditHolder = primbackup.getImageToEdition(imageToEdit);
                        
                        BufferedImage bi = imageToEditHolder.getBufferedImage();
                        if(bi!=null){
                            editimagewindow  = new EditWindow(imageToEditHolder,new ImageChangedActionListener());
                            editimagewindow.setVisible(true);
                        }
                        else{
                            JOptionPane.showMessageDialog(this,"Ten plik nie jest poprawnym plikiem graficznym.","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (        FileNotAvailableException | OperationInterruptedException ex1) {
                        JOptionPane.showMessageDialog(this,"Nie udało się uzyskać obrazu do edycji.","Error",JOptionPane.ERROR_MESSAGE);
                    }
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
                //changedImageHolder = null;
            } catch (    OperationInterruptedException | FileNotAvailableException ex) {
                JOptionPane.showMessageDialog(MainWindow.this,"Nie udało się zapisać obrazu.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
  private void fileSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileSearchButtonMouseClicked
      try{
        int indeks = mainList.getNextMatch(searchTextField.getText(), 0, Position.Bias.Forward);
        if(indeks != -1) mainList.setSelectedIndex(indeks);
        else{
            JOptionPane.showMessageDialog(MainWindow.this, "Nic nie znaleziono."); 
        }
      }catch(IllegalArgumentException e){
        if(searchTextField.getText()==null)JOptionPane.showMessageDialog(this, "Wpisz nazwę pliku który chcesz wyszukać.");  
        else JOptionPane.showMessageDialog(this, "Lista plików jest pusta.");
      }
       
  }//GEN-LAST:event_fileSearchButtonMouseClicked

  private void createNewTagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createNewTagMouseClicked
     if(!newTagTextField.getText().isEmpty()) {
        UserTag ut = tags.newUserTag(newTagTextField.getText());
        lastTag = ut;
        lastTagTextField.setText(lastTag.toString());
      }
     else{
         JOptionPane.showMessageDialog(this, "Wpisz nazwę tagu.");
     }
  }//GEN-LAST:event_createNewTagMouseClicked

  private void tagSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tagSearchButtonMouseClicked
      try{
        int indeks = tagsList.getNextMatch(searchTextField.getText(), 0, Position.Bias.Forward);
        if(indeks != -1) tagsList.setSelectedIndex(indeks);
        else{
        JOptionPane.showMessageDialog(MainWindow.this, "Nic nie znaleziono.");    
        }
      }catch(IllegalArgumentException e){
        if(searchTextField.getText()==null)JOptionPane.showMessageDialog(this, "Wpisz nazwę tagu który chcesz wyszukać.");  
        else JOptionPane.showMessageDialog(this, "Lista tagów jest pusta.");
      }  
  }//GEN-LAST:event_tagSearchButtonMouseClicked

  private void addTagToFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTagToFilesMouseClicked
    if(lastTag != null) {
      int [] ind = mainList.getSelectedIndices();
      for(int i = 0; i < ind.length; i++) {
        tagFilesStore.addUserTagToFile(lastTag, ((MyFile) mainList.getModel().getElementAt(ind[i])).fileID );
      }
      if(ind.length>0)JOptionPane.showMessageDialog(this, "Tag został dodany do plików.");
      else JOptionPane.showMessageDialog(this, "Nie zaznaczono plików.");
    }
    else{
        JOptionPane.showMessageDialog(this, "Wybierz tag.");
    }
  }//GEN-LAST:event_addTagToFilesMouseClicked

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

  private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
    if(searchTextField.getText().equals("Znajdź")) searchTextField.setText(null);
  }//GEN-LAST:event_searchTextFieldMouseClicked

  private void newTagTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newTagTextFieldMouseClicked
    if(newTagTextField.getText().equals("Nazwa tagu...")) newTagTextField.setText(null);
  }//GEN-LAST:event_newTagTextFieldMouseClicked

  private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    saveData();
  }//GEN-LAST:event_formWindowClosed

    private void resetDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetDataButtonActionPerformed
        try {
            JOptionPane.showMessageDialog(this, "Baza danych zostanie wyczyszczona przy zamknięciu aplikacji.\nWszystkie pliki nalezy usunąć z dysku ręcznie.");
            this.data=data.reset();
            data.save();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.this,"Wystąpił błąd wejścia/wyjścia.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_resetDataButtonActionPerformed

private void clearDataButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_clearDataButtonStateChanged
    if(clearDataButton.isSelected()==true){
        resetDataButton.setEnabled(true);
    } else resetDataButton.setEnabled(false);
}//GEN-LAST:event_clearDataButtonStateChanged

    private void displayTagsOnTagsList(){
        tagsList.setListData(selectedTagsVector);
    }
    private void displayUserTagsTree(TreeModel utm){
        this.userTagsTree.setModel(utm);
    }
    private void displayMasterTagsTree(TreeModel mtt){
        this.masterTagsTree.setModel(mtt);
    }
    private static class MasterTagsTreeSelectionListener implements MouseListener{
        MainWindow window;
        MasterTagsTreeSelectionListener(MainWindow w){
            window=w;
            window.masterTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            window.masterTagsTree.addMouseListener(this);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
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
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
    private static class UserTagsTreeSelectionListener implements MouseListener{
        MainWindow window;
        UserTagsTreeSelectionListener(MainWindow w){
            window=w;
            window.userTagsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);;
            window.userTagsTree.addMouseListener(this);      
        }
        @Override
        public void mouseClicked(MouseEvent e) {
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
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    } 
    private static class ExitDataDialogActionListener implements ActionListener{
        boolean disposed;
        DataDialog datadialog;
        ExitDataDialogActionListener(DataDialog d){
            disposed=false;
            this.datadialog=d;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            disposed=true;
            datadialog.dispose();
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
    private class MainListOneImageSelectionListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            editImageButton.setEnabled(false);
            int[] ind = mainList.getSelectedIndices();
            if(ind.length==1){
                MyFile myf = (MyFile)mainList.getModel().getElementAt(ind[0]);
                imageToEdit = myf.fileID;
 		String name = myf.file.getName();
		StringBuilder temp = new StringBuilder();
		// pobranie typu pliku
                for (int i = name.length() - 1; name.charAt(i) != '.'; i--) temp.append(name.charAt(i));
                temp.reverse();
                String type = temp.toString();    
                
                if (type.equals("JPEG") || type.equals("PNG") 
                            || type.equals("JPG")
                            || type.equals("BMP") || type.equals("WBMP")
                            || type.equals("GIF") || type.equals("jpeg") 
                            || type.equals("png") || type.equals("jpg")
                            || type.equals("bmp") || type.equals("wbmp")
                            || type.equals("gif")){ 
                    editImageButton.setEnabled(true);
                }
                else editImageButton.setEnabled(false);
             }
        }    
    }
    private void saveData(){
        try {
            data.saveAndRelease();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Wystąpił błąd wejścia/wyjścia. \n Możliwe, że dane nie zostały zapisane.","Error",JOptionPane.ERROR_MESSAGE);
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
                try{
                    data = Data.lockAndLoad();
                    final MainWindow window = new MainWindow();
                } catch (IllegalStateException ex) {                   
                    DataDialog datadialog=new DataDialog(data);
                    ExitDataDialogActionListener eddal = new ExitDataDialogActionListener(datadialog);
                    datadialog.pushListener(eddal);
                    datadialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    datadialog.setVisible(true);
                    
                    if(eddal.disposed==false){
                        try {
                            data = Data.lockAndLoad();
                            final MainWindow window = new MainWindow();
                        } catch (IOException ex1) {
                            JOptionPane.showMessageDialog(null,"Wystąpił błąd wejścia.wyjścia","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(null,"Wystąpił błąd wejścia.wyjścia","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTagToFiles;
    private javax.swing.JButton backupButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JCheckBox clearDataButton;
    private javax.swing.JButton createNewTag;
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
    private javax.swing.JButton resetDataButton;
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
