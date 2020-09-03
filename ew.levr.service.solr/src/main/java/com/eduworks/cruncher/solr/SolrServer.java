package com.eduworks.cruncher.solr;

import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.HashMap;

public class SolrServer {
	public static HashMap<String, HttpSolrClient> serverMap = new HashMap<>();
	public static HashMap<String, ConcurrentUpdateSolrClient> updateServerMap = new HashMap<>();
}
