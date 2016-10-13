package main.java;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class DirectoryLDAPSearchExample {

	public static void main(String[] args) throws NamingException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, "ldap://localhost:10389/o=jEduSearch");

		DirContext context = new InitialDirContext(environment);

		Attributes searchAttributes = new BasicAttributes("objectClass", "inetOrgPerson");
		// Additional
		searchAttributes.put("sn", "Amar");
		searchAttributes.put("initials", null);
		// Returns values with initials

		Enumeration<SearchResult> searchResults = context.search("ou=People", searchAttributes);
		// new String[] {"cn", "sn", "mail"} only these will return

		printSearchResults(searchResults);
		// Returns Search Results

		// Same as above
		String filter = "(& (initials=*) (sn=Amar) (ou=inetPerson)) ";
		printSearchResults(context.search("ou=People", filter, null));

		// Search Controls
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setCountLimit(5);
		// Will return object or else null
		searchControls.setReturningObjFlag(true);
		// What Attributes to Return
		searchControls.setReturningAttributes(new String[] {"objectClass"});
		// Time Limit, handle Exception if this is done, TimeoutException
		searchControls.setTimeLimit(500);
		printSearchResults(context.search("", "{objectClass=*}", searchControls));
		
	}

	private static void printSearchResults(Enumeration<SearchResult> searchResults) {
		// hasMore - SizeLimit Exception arises on count limit
		while (searchResults.hasMoreElements()) {
			SearchResult searchResult = searchResults.nextElement();
			System.out.println(searchResult.getName() + " " + searchResult.getNameInNamespace() + " "
					+ searchResult.getAttributes());
		}
	}

}
