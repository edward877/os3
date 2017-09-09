package os3;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Add {

	
	void addElToTable(Element el, int size, boolean isDir){
		newName(el);
		if(!FileSystem.disk.addBlock(el, size)){
			JOptionPane.showMessageDialog(null, "Места нет", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		for(int j = 0; j < size; j++){
			int i = el.getPositions().get(j);
			FileSystem.tableModel.setValueAt(el, i/8, i%8);
		}
	}

	int addElToTree(Element el, boolean isDir) {                         
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) FileSystem.tree.getLastSelectedPathComponent();

		if (parent == null) return 0;
		if(!parent.getAllowsChildren()){
			return -1;
		}

		int index = parent.getChildCount();
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(el);
		newNode.setAllowsChildren(isDir);
		FileSystem.treeModel.insertNodeInto(newNode, parent, index);

		// Отображение нового узла
		TreeNode [] nodes = FileSystem.treeModel.getPathToRoot(newNode);
		TreePath path = new TreePath(nodes);
		FileSystem.tree.scrollPathToVisible(path);
		return 1;
	} 
	
	void newName(Element el){
		int id = 0;
		if(el.name!="Root"){
			List<Element> l = el.parent.getElements();
			for(int i = 0 ; i < l.size(); i++){
				Element child = l.get(i);
				if(id > 0){
					if(child!=el && child.name.equals(el.name + "(" +id + ")") ){
						i=0;
						id++;
					}
				}else{
					if(child!=el && child.name.equals(el.name) ){
						id++;
						i=0;
					}
				}
			}
			if(id>0){
				el.name = el.getName() + "(" +id + ")";
			}
		}
	}
	

}
