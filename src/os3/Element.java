	package os3;

import java.util.ArrayList;
import java.util.List;

public class Element{
	String name;
	Directory parent;
	int size;
	private List<Integer> positions = new ArrayList<Integer>();
	
	public Element(Element el) {
		this.name = el.name;
		this.parent = el.parent;
		this.size = el.size;
		this.positions = el.positions;
	}
	
	public Element() {
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Directory getParent() {
		return parent;
	}
	public void setParent(Directory parent) {
		this.parent = parent;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public List<Integer> getPositions() {
		return positions;
	}
	public void addPosition(int position) {
		this.positions.add(position);
	}
		
	@Override
	public String toString() {
		return name;
	}
	
	
}
