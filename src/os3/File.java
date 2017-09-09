package os3;

public class File  extends Element{

	public File(String name, int size,Directory parent) {
		this.name=name;
		this.parent=parent;
		this.size=size;
	}
	public File(File file) {
		super();
		this.name = file.name;
		this.parent = file.parent;
		this.size = file.size;
	}

}
