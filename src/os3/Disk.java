package os3;

public class Disk {
	int size = 64;
	private Element[] disk = new Element[size];
	
	boolean addBlock(Element el,int n){
		
		for(int j = 0; j < n; j++){
			int i = findFreeBlock();
			if(i == -1){
				return false;
			}
			disk[i] = el;
			el.addPosition(i);
		}
		return true;
	}

	public Element[] getDisk() {
		return disk;
	}
	
	public void removeElement(Element el){
		for(int i=0; i<el.getSize(); i++){
			disk[el.getPositions().get(i)] = null;
		}
		
	}
	
	private int findFreeBlock(){
		for(int i=0; i<size; i++){
			if(disk[i] == null){
				return i;
			}
		}
		return -1;
	}
	
	public int  findCountFreeBlock(){
		int count = 0;
		for(int i=0; i<size; i++){
			if(disk[i] != null){
				count++;
			}
		}
		return count;
	}
	
}
