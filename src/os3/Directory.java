package os3;

import java.util.ArrayList;
import java.util.List;

public class Directory extends Element{
	private List<Element> elements = new ArrayList<Element>();

	public Directory(String name,Directory parent){
		this.name=name;
		this.parent=parent;
		this.size=1;
	}

	public Directory(Directory dir){
		this.name = dir.name;
		this.parent = dir.parent;
		this.size = 1;
		for(int i = 0; i < dir.elements.size(); i++){
			Element e = dir.elements.get(i);
			if(e instanceof Directory){
				elements.add(new Directory((Directory)e));
			}else{
				elements.add(new File((File)e));
			}
		}
	}

	public void addNewElements(Element el){
		elements.add(el);
	}

	public List<Element> getElements() {
		return elements;
	}


}
