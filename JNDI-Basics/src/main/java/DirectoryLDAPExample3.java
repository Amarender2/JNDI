package main.java;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class DirectoryLDAPExample3 {

	public static void main(String[] args) throws NamingException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, "ldap://localhost:10389/o=jEdu");

		DirContext context = new InitialDirContext(environment);
		DirContext peopleContext = (DirContext) context.lookup("ou=People");

		Attributes attributes = new BasicAttributes();
		attributes.put("objClass", "inetOrgPerson");
		attributes.put("sn", "Mekala");
		peopleContext.bind("cn=Amar", null, attributes);
		// null only
		// Output : New cn=Amar object created

		// Backup People from people ou
		// Create Directory ou=PeopleBackup in ADS(Apache Directory Studio)

		DirContext peopleBackup = (DirContext) context.lookup("ou=PeopleBackup");
		Enumeration<Binding> peopleBindings = peopleContext.listBindings("");
		while (peopleBindings.hasMoreElements()) {
			Binding person = peopleBindings.nextElement();
			peopleBackup.bind(person.getName(), person.getObject(), null);
		}
		// Output : ou=peoplebackup created with contents of ou=people

		// Equivalent Codes:

		DirContext contextObject = (DirContext) context.lookup("cn=Amar Reddy");

		// 1
		peopleBackup.bind("cn=Amar Reddy", contextObject, null);
		// 2
		peopleBackup.bind("cn=Amar Reddy", null, contextObject.getAttributes(""));
		// 3
		peopleBackup.createSubcontext("cn=Amar Reddy", contextObject.getAttributes(""));
	}

}
