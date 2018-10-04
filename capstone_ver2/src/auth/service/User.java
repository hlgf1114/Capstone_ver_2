package auth.service;

public class User {

	private String id;
	private String name;
	private int access;
	
	public User(String id, String name, int access) {
		this.id = id;
		this.name = name;
		this.access = access;

	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getAccess() {
		return access;
	}
	
}
