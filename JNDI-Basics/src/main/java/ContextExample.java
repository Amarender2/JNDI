package main.java;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextExample {

	public static void main(String[] args) throws NamingException {
		// Access Naming Service with any provider,
		// How does JNDI know what naming service it should work with
		// Properties stored in hash table,(environment), we use File Systems,
		// naming service,
		// File names mapped to actual files on disk
		// Not supported by JDK
		// Download provider with external
		Hashtable<String, String> environment = new Hashtable<>();
		// Set context depends on service provider using
		// two props used across different SProviders
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
		// FS, If DNS used will put DNS server id, LDAP LDAP server followed by
		// partitions
		environment.put(Context.PROVIDER_URL, "file:/Users/Amar/Downloads/");

		Context context = new InitialContext(environment);
		
		// bind(context);
		
		Object employee = context.lookup("amar");
		System.out.println("Class : " + employee.getClass().getName());
		System.out.println(employee);
		
		

	}
	
	
	private static void bind(Context context) throws NamingException {
		Employee employeeAmar = new Employee("Amarender", 2, true);
		context.bind("amar", employeeAmar);
		
		// context.unbind("amar");
		// Removes binding and removes .binding file
		
		// context.rebind("amar", new Employee());
		// U can change object to which name references to
	}

}
