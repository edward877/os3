package os3;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class CopyPaste {

	private Element copyElement;
	private Add add = new Add();
	
	public void copy(Element el) {
		this.copyElement = el;
	}

	public Element getElement(){
		return copyElement;
	}

	public int paste(Element el, DefaultMutableTreeNode parent){
		add.newName(el);
		el.getPositions().clear();
		if(!FileSystem.disk.addBlock(el, el.size)){
			JOptionPane.showMessageDialog(null, "Места нет", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
			return -1;
		}
		
		for(int j = 0; j < el.size; j++){
			int i = el.getPositions().get(j);
			FileSystem.tableModel.setValueAt(el, i/8, i%8);
		}
		
		boolean isDir = el instanceof Directory;
		if(!parent.getAllowsChildren()){
			return -1;
		}
		int index = parent.getChildCount();
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(el);
		newNode.setAllowsChildren(isDir);
		FileSystem.treeModel.insertNodeInto(newNode, parent, index);

		TreeNode [] nodes = FileSystem.treeModel.getPathToRoot(newNode);
		TreePath path = new TreePath(nodes);
		FileSystem.tree.scrollPathToVisible(path);

		if(isDir){
			for(int i = 0; i <((Directory)el).getElements().size(); i++){
				Element nextEl = ((Directory)el).getElements().get(i);
				nextEl.setParent((Directory)el);
				((Directory)el).getElements().set(i, nextEl);
				paste(nextEl, newNode);
			}
		}
		return 1;
	}


}
