package os3;

import javax.swing.tree.MutableTreeNode;

public class Delete {


	void DelElFromTable(Element el){
		el.getParent().getElements().remove(el);
		if(el instanceof Directory){
			int n = ((Directory) el).getElements().size();
			for (int i = 0; i<n; i++) {
				DelElFromTable(((Directory) el).getElements().get(i));
				i--;
				n--;
			}
		}
		for(int j = 0; j < el.getSize(); j++){
			int i = el.getPositions().get(j);
			FileSystem.tableModel.setValueAt(null, i/8, i%8);
		}

		FileSystem.disk.removeElement(el);
		System.out.println(FileSystem.disk.findCountFreeBlock());
	}

	int delElFromTree(Element el) {   
		FileSystem.treeModel.removeNodeFromParent(
				(MutableTreeNode)FileSystem.tree.getSelectionPath().getLastPathComponent());
		return 1;
	} 

}
