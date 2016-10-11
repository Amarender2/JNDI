package main.java;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

public class Employee implements Referenceable {

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String MANAGER = "manager";
	
	private String name;
	private int id;
	private boolean manager;

	public Employee() {

	}

	public Employee(String name, int id, boolean manager) {
		this.name = name;
		this.id = id;
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	@Override
	public Reference getReference() throws NamingException {
		// null where class is loaded from
		Reference reference = new Reference(Employee.class.getName(), EmployeeFactory.class.getName(), null);
		// reference represented by two classes string and binary
		// String name(address type) and value
		// binary name and binary value

		reference.add(new StringRefAddr(NAME, this.name));
		reference.add(new StringRefAddr(ID, Integer.toString(this.id)));
		reference.add(new StringRefAddr(MANAGER, Boolean.toString(this.manager)));
		
		return reference;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", id=" + id + ", manager=" + manager + "]";
	}
	
	

}
