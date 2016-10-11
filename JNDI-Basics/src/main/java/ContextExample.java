package main.java;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
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
		// With and Without object Factory It works. One gives Reference, Other
		// Employee Object
		System.out.println("Class : " + employee.getClass().getName());
		System.out.println(employee);

		// context.createSubcontext("sample/folder1");
		// context.createSubcontext("sample/folder2");
		// context.destroySubcontext("sample/folder1");

		printList(context);
		
		printListBindings(context);

	}

	private static void printListBindings(Context context) throws NamingException {
		NamingEnumeration<Binding> bindings = context.listBindings("");
		while(bindings.hasMore()) {
			Binding binding = bindings.next();
			System.out.println(binding.getName() + " , "+ binding.getClassName());
			if(binding.getObject() instanceof File) {
				System.out.println(((File)binding.getObject()).length());
			}
		}
	}

	private static void printList(Context context) throws NamingException {
		Enumeration<NameClassPair> contextData = context.list("");

		while (contextData.hasMoreElements()) {
			NameClassPair nameClassPair = contextData.nextElement();
			System.out.println(nameClassPair.getName() + " , " + nameClassPair.getClassName());
		}
	}
	
	private static void bind(Context context) throws NamingException {
		Employee employeeAmar = new Employee("Amarender", 2, true);
		// context.bind("amar", employeeAmar);

		// context.unbind("amar");
		// Removes binding and removes .binding file

		context.rebind("amar", employeeAmar);
		// U can change object to which name references to
	}

}
