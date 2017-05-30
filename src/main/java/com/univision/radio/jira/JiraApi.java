package com.univision.radio.jira;
//package akc.com.jira;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
//import com.sun.jersey.core.util.MultivaluedMapImpl;
//import org.codehaus.jettison.json.JSONException;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.Map;
//import javax.ws.rs.core.MultivaluedMap;
//
//public class JiraApi {
//	private String URL;
//	private HTTPBasicAuthFilter authorization ;
//	
//	private static String API = "rest/api/latest/" ;
//	
//	//resources from Jira API
//	private static final String RESOURCE_GROUP = "group" ;
//	private static final String RESOURCE_SEARCH = "search" ;
//	
//	
//	//parameters for each resource
//	// group
//	private static final String RGROUP_GROUPNAME = "groupname" ;
//	private static final String RGROUP_EXPAND = "expand" ;
//	
//	// search
//	private static final String RSEARCH_QUERY = "jql" ;
//	private static final String RSEARCH_START = "startAt"; 
//	private static final String RSEARCH_MAX = "maxResults";
//		   
//	public JiraApi(String url, String user, String pass) throws URISyntaxException, JSONException, IOException {
//		URL = url ;
//		authorization = new HTTPBasicAuthFilter(user, pass) ;
//	}
//
//	private String get( String resource, MultivaluedMap<String, String> params ) {
//		try {
//			Client client = Client.create();
//			
//			WebResource webResource = client.resource(URL + API + resource) ;
//			webResource.addFilter(authorization);
//			webResource.setProperty("Content-Type", "application/json;charset=UTF-8");
//			
//			ClientResponse response = webResource
//	                .queryParams(params)
//					.accept("application/json")
//					.get(ClientResponse.class) ;
//					 
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus());
//			}
//			String output = response.getEntity(String.class);
//			return output ;
//				
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null ;
//	
//	}
//	
//	public Map<String,String> getAllIssues(String projectKey) throws Exception {
//
//		Client client = Client.create();
//		
//		WebResource webResource = client.resource(URL + API + RESOURCE_SEARCH + "?jql=project=WA");
//		
//		webResource.addFilter(authorization);
//		webResource.setProperty("Content-Type", "application/json;charset=UTF-8");
//		
//		ClientResponse response = webResource
//				.accept("application/json")
//				.get(ClientResponse.class) ;
//				 
//		if (response.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ response.getStatus());
//		}
//		 Map<String,String> stories = new HashMap<String, String>();
//		 
//		String output = response.getEntity(String.class);
//		JSONObject jsonIssues = new JSONObject(output);
// 
//		JSONArray projectArray = jsonIssues.getJSONArray("issues");
//		for (int i = 0; i < projectArray.length(); i++) {
//		    JSONObject issue = projectArray.getJSONObject(i);
//		    String issueId = issue.getString("key");
//		    String description = issue.getJSONObject("fields").getString("description");
//			stories.put(issueId,description);
//		}
//		
//		return stories;
//	}
//
//	public String getIssues( String query, Integer min, Integer max ) {
//		MultivaluedMap<String, String> params = new MultivaluedMapImpl(); 
//		params.add(RSEARCH_QUERY, query ); 
//		params.add(RSEARCH_START, min.toString()) ;
//		params.add(RSEARCH_MAX, max.toString()) ;
//		return get(RESOURCE_SEARCH, params) ;
//	}
//
//	public String getIssues(Integer min, Integer max) {
//		return getIssues("", min, max) ;
//	}
//	
//	public String getUser(String userKey) {
//		//TODO
//		return null ;
//	}
//	
//	public String getUsers( String group, Integer min, Integer max ) {
//		MultivaluedMap<String, String> params = new MultivaluedMapImpl(); 
//		params.add(RGROUP_GROUPNAME, group );
//		params.add(RGROUP_EXPAND, "users[" + min.toString() + ":" + max.toString() + "]") ;
//		return get(RESOURCE_GROUP, params) ;
//	}
//}
