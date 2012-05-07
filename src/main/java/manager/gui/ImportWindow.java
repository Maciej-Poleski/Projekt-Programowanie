/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import manager.tags.*;
import manager.core.*;
import manager.files.*;
import manager.files.backup.*;

/**
 * 
 * @author Michał Kowalik
 */
public class ImportWindow extends javax.swing.JDialog {

	private Tags tags;// klasa obsługująca wszystkie tagi
	private TagFilesStore tagFilesStore;// główny magazyn plików
	private BackupsManager backupsManager;
	private Data data;

	public ImportWindow(Tags t, TagFilesStore tfs, BackupsManager bm, Data d) {
		this.tags = t;
		this.tagFilesStore = tfs;
		this.backupsManager = bm;
		this.data = d;
		this.masterTagToAdd = null;
		this.fileToAdd = null;
		initComponents();
		this.masterTagsTree.setModel(tags.getModelOfMasterTags());
		this.masterTagsTree.setRootVisible(false);

	}

	private MasterTag masterTagToAdd;
	private File fileToAdd;

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jOptionPane1 = new javax.swing.JOptionPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		panel1 = new java.awt.Panel();
		buttonPanel = new javax.swing.JPanel();
		jLabelImport = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		masterTagsTree = new javax.swing.JTree();
		jLabelWybierzTag = new javax.swing.JLabel();
		importButton = new javax.swing.JButton();
		filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
				new java.awt.Dimension(0, 0), new java.awt.Dimension(32767,
						32767));
		chooseFileButton = new javax.swing.JButton();
		newMasterTagButton = new javax.swing.JButton();
		pathField = new javax.swing.JTextField();
		tagsField = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane2.setViewportView(jTextArea1);

		javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(
				panel1);
		panel1.setLayout(panel1Layout);
		panel1Layout.setHorizontalGroup(panel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));
		panel1Layout.setVerticalGroup(panel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Import");
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent evt) {
				formWindowClosed(evt);
			}
		});

		buttonPanel.setBackground(new java.awt.Color(204, 204, 255));
		buttonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		buttonPanel.setForeground(new java.awt.Color(204, 204, 255));

		jLabelImport.setText("Import");

		javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(
				buttonPanel);
		buttonPanel.setLayout(buttonPanelLayout);
		buttonPanelLayout.setHorizontalGroup(buttonPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						buttonPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabelImport,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										84,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		buttonPanelLayout.setVerticalGroup(buttonPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						buttonPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabelImport,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		masterTagsTree
				.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
					public void valueChanged(
							javax.swing.event.TreeSelectionEvent evt) {
						masterTagsTreeValueChanged(evt);
					}
				});
		jScrollPane1.setViewportView(masterTagsTree);

		jLabelWybierzTag
				.setText("Po wybraniu pliku wybierz jego rodzica w drzewie,");

		importButton.setText("IMPORTUJ DO PROGRAMU");
		importButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				importButtonActionPerformed(evt);
			}
		});

		chooseFileButton.setText("WYBIERZ PLIK LUB KATALOG");
		chooseFileButton.setActionCommand("chooseFile");
		chooseFileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chooseFileButtonActionPerformed(evt);
			}
		});

		newMasterTagButton.setText("Nowa składowa");
		newMasterTagButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						newMasterTagButtonActionPerformed(evt);
					}
				});

		pathField.setBackground(new java.awt.Color(220, 220, 250));
		pathField.setEditable(false);
		pathField.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		pathField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		tagsField.setBackground(new java.awt.Color(220, 220, 250));
		tagsField.setEditable(false);
		tagsField.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		tagsField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jLabel1.setText("lub stwórz nową spójną składową:");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(buttonPanel,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel1)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																				.addComponent(
																						pathField)
																				.addComponent(
																						jScrollPane1)
																				.addComponent(
																						tagsField)
																				.addComponent(
																						newMasterTagButton,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						chooseFileButton,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						importButton,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						jLabelWybierzTag,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		filler1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(buttonPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(40, 40,
																		40)
																.addComponent(
																		filler1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		chooseFileButton)
																.addGap(5, 5, 5)
																.addComponent(
																		pathField,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jLabelWybierzTag,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		19,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jScrollPane1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		243,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel1)
								.addGap(3, 3, 3)
								.addComponent(newMasterTagButton)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(tagsField,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(importButton,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										41,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(15, Short.MAX_VALUE)));

		pathField.getAccessibleContext().setAccessibleName("");
		pathField.getAccessibleContext().setAccessibleDescription("");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void chooseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chooseFileButtonActionPerformed
		// Create a file chooser
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// In response to a button click:
		int returnVal = fc.showOpenDialog(this);
		fileToAdd = fc.getSelectedFile();
		if (fileToAdd != null)
			pathField.setText(fileToAdd.toString());
		else
			pathField.setText(null);
	}// GEN-LAST:event_chooseFileButtonActionPerformed

	private void masterTagsTreeValueChanged(
			javax.swing.event.TreeSelectionEvent evt) {// GEN-FIRST:event_masterTagsTreeValueChanged
		masterTagToAdd = (MasterTag) masterTagsTree
				.getLastSelectedPathComponent();
		if (masterTagToAdd != null)
			tagsField.setText(masterTagToAdd.toString());
		else
			tagsField.setText(null);
	}// GEN-LAST:event_masterTagsTreeValueChanged

	private void newMasterTagButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newMasterTagButtonActionPerformed
		masterTagToAdd = null;
		tagsField.setText("<< NOWA SKŁADOWA >>");
		JOptionPane.showMessageDialog(this,
				"Teraz kliknij IMPORTUJ. Nowa składowa będzie utworzona.");
	}// GEN-LAST:event_newMasterTagButtonActionPerformed

	private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_importButtonActionPerformed
		// TODO add your handling code here:
		if (fileToAdd == null) {
			JOptionPane.showMessageDialog(this,
					"Musisz wybrać plik lub katalog.");
			return;
		}

		if (!fileToAdd.isDirectory() && masterTagToAdd == null) {
			JOptionPane
					.showMessageDialog(this,
							"Plik musi być przyporządkowany do konkretnego MasterTagu.");
			return;
		}

		try {

			if (masterTagToAdd == null) {
				PrimaryBackup primaryBackup = new PrimaryBackupImpl(
						fileToAdd.getParent(), tags);
				BackupManager firstBackupManager = new BackupManager(
						primaryBackup);

				MasterTag mTag = tags.newMasterTag(fileToAdd.getName());
				try{
                                    backupsManager.registerBackupManager(mTag, firstBackupManager);
                                    primaryBackup.addFile(mTag, fileToAdd, true);
                                } catch (OperationInterruptedException ex){
                                    tags.removeTag(mTag);    
                                    ex.printStackTrace();
                                }
				
			} else {
				// System.out.println("uwaga:"+masterTagToAdd);
				BackupManager bm = backupsManager
						.getBackupManagerAssociatedWithMasterTag(masterTagToAdd);
				PrimaryBackup pb = bm.getPrimaryBackup();
				pb.addFile(masterTagToAdd, fileToAdd, false);
			}

			fileToAdd = null;
			masterTagToAdd = null;
			pathField.setText(null);
			tagsField.setText(null);

		} catch (OperationInterruptedException | java.io.FileNotFoundException ex) {
			java.util.logging.Logger.getLogger(ImportWindow.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
			
			JOptionPane
			.showMessageDialog(this,
					ex.getMessage());
			
		} catch (IOException ex) {
			Logger.getLogger(ImportWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}// GEN-LAST:event_importButtonActionPerformed

	private void formWindowClosed(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosed
		try {
			data.save();
		} catch (IOException ex) {
			Logger.getLogger(ImportWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}// GEN-LAST:event_formWindowClosed

	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ImportWindow.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ImportWindow.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ImportWindow.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ImportWindow.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel buttonPanel;
	private javax.swing.JButton chooseFileButton;
	private javax.swing.Box.Filler filler1;
	private javax.swing.JButton importButton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabelImport;
	private javax.swing.JLabel jLabelWybierzTag;
	private javax.swing.JOptionPane jOptionPane1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTree masterTagsTree;
	private javax.swing.JButton newMasterTagButton;
	private java.awt.Panel panel1;
	private javax.swing.JTextField pathField;
	private javax.swing.JTextField tagsField;
	// End of variables declaration//GEN-END:variables
}
