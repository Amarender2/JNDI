package main.java;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
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

		// lookup(context);

		// createDestroySubContext(context);

		// printList(context);
		
		// printListBindings(context);
		
		
		// compositeName();
		
		// compundName(context);
		
		nameSpace(context);
		
	}
	
	private static void nameSpace(Context context) throws NamingException {
		Context otherContext =  (Context) context.lookup("sample");
		System.out.println(otherContext.getNameInNamespace());
		
		String name = otherContext.composeName("sample", "Users");
		System.out.println(name);
	}
	
	private static Name compundName(Context context) throws NamingException {
		Properties properties = new Properties();
		properties.put("jndi.syntax.direction", "right_to_left");
		properties.put("jndi.syntax.separator", ",");
		properties.put("jndi.syntax.escape", ":");
		CompoundName compoundName = new CompoundName("some,first::second,aaa:,bbb:,ccc", properties);
		Enumeration<String> components = compoundName.getAll();
		while(components.hasMoreElements()) {
			System.out.println(components.nextElement());
		}
		
		// NameParser to parse string to Name, returns compoundName
		NameParser nameParser = context.getNameParser("");
		Name name = nameParser.parse("first/second/third");
		name.add(0, "headreplace");
		name.remove(1);
		System.out.println(name);
		
		components = name.getAll();
		while(components.hasMoreElements()) {
			System.out.println(components.nextElement());
		}
		
		return compoundName;
		
	}
	
	private static Name compositeName() throws InvalidNameException {
		CompositeName compositeName = new CompositeName();
		compositeName.add("first.name");
		compositeName.add("sample");
		System.out.println(compositeName);
		return compositeName;
		// first.name/sample\name
	}
	
	private static void createDestroySubContext(Context context) throws NamingException {
		context.createSubcontext("first.name");
		context.createSubcontext("sample");
		//context.destroySubcontext("sample/folder1");
	}
	
	private static void lookup(Context context) throws NamingException {
		Object employee = context.lookup("amar");
		// With and Without object Factory It works. One gives Reference, Other
		// Employee Object
		System.out.println("Class : " + employee.getClass().getName());
		System.out.println(employee);
	}

	private static void printListBindings(Context context) throws NamingException {
		NamingEnumeration<Binding> bindings = context.listBindings("");
		while(bindings.hasMore()) {
			Binding binding = bindings.next();
			System.out.print(binding.getName() + "\t "+ binding.getClassName() + "\t");
			if(binding.getObject() instanceof File) {
				System.out.println(((File)binding.getObject()).length());
			}
		}
	}

	private static void printList(Context context) throws NamingException {
		Enumeration<NameClassPair> contextData = context.list("");

		while (contextData.hasMoreElements()) {
			NameClassPair nameClassPair = contextData.nextElement();
			System.out.println(nameClassPair.getName() + "\t" + nameClassPair.getClassName());
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
