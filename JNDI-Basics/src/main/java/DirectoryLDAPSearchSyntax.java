package main.java;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class DirectoryLDAPSearchSyntax {

	public static void main(String[] args) throws NamingException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, "ldap://localhost:10389/o=jEduSearchSyntax");

		DirContext context = new InitialDirContext(environment);

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// caseSensitiveString=dog or d* or *o* , int=92
		printSearchResults(context.search("", "(caseIgnoreString=DOG)", searchControls));
		
		// case=*\\2a*
		printSearchResults(context.search("", "( | (& (caseIgnoreString=DOG) (xyz=amar) )"
				+ "(& (anc=DOG) (def=amar) )", searchControls));

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
