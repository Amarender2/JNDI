package main.java;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class EmployeeFactory implements ObjectFactory {

	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
			throws Exception {
		if(obj instanceof Reference) {
			Reference reference = (Reference) obj;
			if(reference.getClassName().equals(Employee.class.getName())) {
				Employee employee = new Employee();
				Enumeration<RefAddr> addresses = reference.getAll();
				
				while(addresses.hasMoreElements()) {
					RefAddr refAddr = addresses.nextElement();
					switch(refAddr.getType()) {
						case Employee.NAME:
							employee.setName((String) refAddr.getContent());
							break;
						case Employee.ID:
							employee.setId(Integer.parseInt((String) refAddr.getContent()));
							break;
						case Employee.MANAGER: 
							employee.setManager(Boolean.valueOf((String)refAddr.getContent()));
							break;
					}
				}
				return employee;
				
			}
		}
		return null;
	}

}
