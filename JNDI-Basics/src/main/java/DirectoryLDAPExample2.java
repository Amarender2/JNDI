package main.java;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

public class DirectoryLDAPExample2 {

	public static void main(String[] args) throws NamingException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, "ldap://localhost:10389/o=jEdu");

		DirContext context = new InitialDirContext(environment);
		DirContext peopleContext = (DirContext) context.lookup("ou=People");

		ModificationItem modificationItem = new ModificationItem(DirContext.ADD_ATTRIBUTE,
				new BasicAttribute("description", "sample desc"));
		peopleContext.modifyAttributes("cn=Amar, ou=People", new ModificationItem[] { modificationItem });
		// Output: open Amar Dir in Apache Directory Studio(ADS), description is
		// added in that component

		Attributes attributes = new BasicAttributes(true);
		attributes.put("description", "Updated Sample Desc");
		peopleContext.modifyAttributes("cn=Amar, ou=People", DirContext.REPLACE_ATTRIBUTE, attributes);
		// Replaces description in Amar Dir

		ModificationItem removeItem = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
				new BasicAttribute("description", null));
		ModificationItem updateItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
				new BasicAttribute("mail", "ammv8@mst.edu"));
		Attribute basicAttribute = new BasicAttribute("title");
		basicAttribute.add("Mr.");
		basicAttribute.add("Master");
		ModificationItem addItem = new ModificationItem(DirContext.ADD_ATTRIBUTE, basicAttribute);

		context.modifyAttributes("cn=Amar, ou=People", new ModificationItem[] { removeItem, updateItem, addItem });

		// Applied in same order of the above - remove, update, add
		// Output:
		// Above changes are applied to Amar Dir, View in ADS
		
		

	}

}
