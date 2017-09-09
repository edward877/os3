package os3;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class FileSystem {
	private DefaultTableCellRenderer jTableRenderer;
	private JFrame frame;
	private JTextField textFieldName;
	private JSpinner spinner;
	private Add add;
	private Delete del;
	private CopyPaste cp;

	public static JTree tree;
	public static JTable table;
	public static DefaultTableModel tableModel = new DefaultTableModel();
	public static DefaultTreeModel treeModel;
	public static Disk disk;

	/** 
	 * Launch the application.
	 */
	public void run(FileSystem window) {
		try {
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public FileSystem() {
		add = new Add();
		del = new Delete();
		cp = new CopyPaste();
		tableModel = new DefaultTableModel();
		tree = new JTree();
		tree.setVisibleRowCount(30);
		tree.setShowsRootHandles(true);
		disk = new Disk();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1036, 391);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinner.setBounds(230, 90, 29, 20);
		frame.getContentPane().add(spinner);

		tableModel.setRowCount(8);
		tableModel.setColumnCount(8);
		table = new JTable(tableModel);
		table.setBounds(10, 126, 510, 127);
		frame.getContentPane().add(table);

		setRoot();

		JButton btnNewButton = new JButton("addFile");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				String name = textFieldName.getText();
				int size = (Integer) spinner.getValue();

				Directory parent = getParent();
				if(parent!=null){
					File file = new File(name, size, (Directory)parent);
					parent.addNewElements(file);

					if(add.addElToTree(file, false) == 1){
						add.addElToTable(file, size, false);
						PaintCell();
					}else{
						JOptionPane.showMessageDialog(null, "Выберите каталог", "InfoBox", JOptionPane.INFORMATION_MESSAGE);	
					}
				}
				treeModel.reload();
			}
		});
		btnNewButton.setBounds(10, 45, 116, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnAdddir = new JButton("addDir");
		btnAdddir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textFieldName.getText();
				Directory parent = getParent();
				if(parent!=null){
					Directory dir = new Directory(name, parent);
					parent.addNewElements(dir);

					if(add.addElToTree(dir, true) == 1){
						add.addElToTable(dir, 1, true);
						PaintCell();		
					}else{
						JOptionPane.showMessageDialog(null, "Выберите каталог", "InfoBox", JOptionPane.INFORMATION_MESSAGE);	
					}
				}
				treeModel.reload();
			}
		});
		btnAdddir.setBounds(10, 11, 116, 23);
		frame.getContentPane().add(btnAdddir);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(529, 8, 387, 343);
		frame.getContentPane().add(scrollPane_1);


		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//				if(table.isEditing()){ //To prevent Null Pointer Exception
				//				    table.getCellEditor().stopCellEditing();
				//				    table.getSelectionModel().clearSelection();
				//				}
				//				
				//				Object el = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
				//				for(int j = 0; j < ((Element)el).size; j++){
				//					int i = ((Element)el).getPositions().get(j);
				//					table.editCellAt(i/8,i%8);
				//					PaintCell();
				//				}
			}
		});
		scrollPane_1.setViewportView(tree);
		tree.setModel(treeModel);

		textFieldName = new JTextField();
		textFieldName.setBounds(59, 90, 86, 20);
		frame.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);

		JLabel lblName = new JLabel("Name = ");
		lblName.setBounds(16, 93, 46, 14);
		frame.getContentPane().add(lblName);

		JLabel lblSize = new JLabel("Size = ");
		lblSize.setBounds(196, 93, 46, 14);
		frame.getContentPane().add(lblSize);

		JButton btnNewButton_1 = new JButton("delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object el = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
				if(((Element)el).name != "Root"){
					del.DelElFromTable((Element)el);
					del.delElFromTree((Element)el);
				}else{
					JOptionPane.showMessageDialog(null, "Нельзя удалить корневой каталог", "InfoBox", JOptionPane.INFORMATION_MESSAGE);		

				}

			}
		});
		btnNewButton_1.setBounds(153, 11, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("copy");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object el = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
				if(el instanceof Directory){
					Directory dir = new Directory((Directory)el);
					cp.copy(dir);
				}else{
					File file = new File((File)el);
					cp.copy(file);
				}
			}
		});
		btnNewButton_2.setBounds(196, 45, 89, 23);
		frame.getContentPane().add(btnNewButton_2);

		JButton btnPaste = new JButton("paste");
		btnPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((Element)cp.getElement()).name != "Root"){
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					((Directory)parent.getUserObject()).addNewElements(cp.getElement());
					cp.getElement().setParent((Directory)parent.getUserObject());
					cp.paste(cp.getElement(), parent);
				}

			}
		});
		btnPaste.setBounds(295, 45, 89, 23);
		frame.getContentPane().add(btnPaste);

		startTableRender();
		PaintCell();
	}

	private void PaintCell(){
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(jTableRenderer);
		}
		table.updateUI();
	}

	private void setRoot(){
		Element root = new Directory("Root", null);
		add.addElToTable(root, 1, true);
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(root), true);
	}

	@SuppressWarnings("serial")
	private void startTableRender(){
		jTableRenderer = new DefaultTableCellRenderer() {
			@Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					Color color = Color.WHITE;
					if(value instanceof  Directory){
						color = Color.RED;
					}
					else if(value instanceof  File){
						color = Color.BLUE;
					}
					cell.setBackground(color);
				}
				return cell;
			}
		};

		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

	}

	private Directory getParent(){
		if(textFieldName.getText().length()>0){
			if(tree.getLastSelectedPathComponent() == null){
				JOptionPane.showMessageDialog(null, "Выберите папку", "InfoBox", JOptionPane.WARNING_MESSAGE);	
				return null;
			}
			Object parent = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
			if(parent instanceof Directory){
				return (Directory) parent;
			}else{
				JOptionPane.showMessageDialog(null, "Нельзя добавлять в файл", "InfoBox", JOptionPane.WARNING_MESSAGE);			
			}
			return null;
		}else{
			JOptionPane.showMessageDialog(null, "Введите имя", "InfoBox", JOptionPane.INFORMATION_MESSAGE);		
			return null;
		}
	}
}
