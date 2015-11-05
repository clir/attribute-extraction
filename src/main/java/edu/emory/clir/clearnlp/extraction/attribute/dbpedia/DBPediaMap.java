package edu.emory.clir.clearnlp.extraction.attribute.dbpedia;

import java.io.BufferedReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.emory.clir.clearnlp.ner.NERTag;
import edu.emory.clir.clearnlp.util.IOUtils;
import edu.emory.clir.clearnlp.util.Splitter;

public class DBPediaMap implements NERTags {
	
	
	protected Map<String,Set<String>> db_map;
	public DBPediaMap(){
		db_map = new HashMap<>();
	}
	
	
	public void constructMap(String filePath, boolean rawTags, boolean trimSpace) throws Exception{
		BufferedReader br = new BufferedReader(IOUtils.createBufferedReader(filePath));
		String NERTags, word = "", line;
		String[] tags;
		int tabIndex;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine())!= null){
			sb.setLength(0);
			tabIndex = line.lastIndexOf('\t');
			NERTags = line.substring(tabIndex+1, line.length());
			word = line.substring(0,tabIndex);
			if(trimSpace){
				tags = Splitter.splitSpace(word);
				for(int i = 0; i<tags.length; i++){
					sb.append(tags[i]);
				}
				word = sb.toString();
			}
			tags = Splitter.splitSpace(NERTags);
			put(word,tags,rawTags);
		}
	}
	
	public void put(String word, String[] tags, boolean rawTags){
		Set<String> set = new HashSet<>();
		if(rawTags) Collections.addAll(set, tags);
		else for(String tag : tags) set.add(getNERTag(tag));
		db_map.put(word, set);
	}
	
	private String getNERTag(String tag) {
		if(tag.equals("0")) return NERTags.PERSON;
		if(tag.equals("3") || tag.equals("1")) return NERTags.ORGANIZATION;
		if(tag.equals("2") || tag.equals("5")) return NERTags.LOCATION;
		return NERTags.MISC;
	}

	public void readMap(String filePath){
		
		try {
		    ObjectInput in = new ObjectInputStream(IOUtils.createObjectXZBufferedInputStream(filePath));
		    try {
		         db_map = (Map<String,Set<String>>) in.readObject();
		    } finally {
		        in.close();
		    }
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void serializeMap(String filePath){
		try {
		    ObjectOutput out = new ObjectOutputStream(IOUtils.createObjectXZBufferedOutputStream(filePath));
		    try {
		        out.writeObject(db_map);
		    } finally {
		        out.close();
		    }
		} catch (Exception e) { e.printStackTrace(); }
	}


	
	public Map<String,Set<String>> getDBMap(){
		return this.db_map;
	}
	
	
	
	static public void main(String[] args) throws Exception{
		String inputFile = args[0], outputFile = args[1];
		boolean rawTags = args[2].equals("t") ? true : false;
		boolean trimSpace = args[3].equals("t") ? true: false;
		DBPediaMap db = new DBPediaMap();
		db.constructMap(inputFile, rawTags, trimSpace);
		db.serializeMap(outputFile);
	}	
	
	
	
	
}
