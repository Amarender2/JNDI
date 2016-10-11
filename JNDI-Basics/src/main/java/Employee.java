package main.java;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

public class Employee implements Referenceable {

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
		Reference reference = new Reference(Employee.class.getName());
		// reference represented by two classes string and binary
		// String name(address type) and value
		// binary name and binary value

		reference.add(new StringRefAddr("name", this.name));
		reference.add(new StringRefAddr("id", Integer.toString(this.id)));
		reference.add(new StringRefAddr("manager", Boolean.toString(this.manager)));
		
		return reference;
	}

}
