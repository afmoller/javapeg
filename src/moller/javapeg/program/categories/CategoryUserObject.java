package moller.javapeg.program.categories;

public class CategoryUserObject {

	private String name;
	private String identity;
	
	public CategoryUserObject(String name, String identity) {
		this.name = name;
		this.identity = identity;
	}
	
	public String getName() {
		return name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
