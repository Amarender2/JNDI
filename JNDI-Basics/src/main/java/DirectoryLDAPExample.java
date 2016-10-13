package main.java;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class DirectoryLDAPExample {

	public static void main(String[] args) throws NamingException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, "ldap://localhost:10389/o=jEdu");

		DirContext context = new InitialDirContext(environment);

		NamingEnumeration<Binding> bindings = context.listBindings("");
		while (bindings.hasMore()) {
			System.out.println(bindings.next().getName());
		}
		// Output:
		// ou=People
		// ou = Groups

		DirContext peopleContext = (DirContext) context.lookup("ou=People");
		DirContext groupsContext = (DirContext) context.lookup("ou=Groups");

		NameParser nameParser = peopleContext.getNameParser("");

		NamingEnumeration<Binding> groups = groupsContext.listBindings("");
		while (groups.hasMore()) {
			String groupName = groups.next().getName();
			Attributes groupAttributes = groupsContext.getAttributes(groupName);
			Attribute description = groupAttributes.get("description");
			Attribute membersAttr = groupAttributes.get("uniquemember");
			Attribute cn = groupAttributes.get("cn");
			System.out.println(groupName + " " + description + " " + membersAttr.size() + " " + cn);

			NamingEnumeration<?> members = membersAttr.getAll();
			while (members.hasMore()) {
				String member = members.next().toString();
				Name memberName = nameParser.parse(member);
				DirContext memberContext = (DirContext) peopleContext.lookup(memberName.get(2));
				Attributes membersAttributes = memberContext.getAttributes("",
						new String[] { "cn", "telephone", "mail" });
				System.out.println(member + " " + membersAttributes.get("cn") + " " + membersAttributes.get("telephone")
						+ " " + membersAttributes.get("mail"));
			}
			
		}
		
		
		// For loop of
		// Directory Name
		// All People and Info of them 

	}

}
