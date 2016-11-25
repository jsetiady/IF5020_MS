package com.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.meeting.MeetingInvitation;

/**
 * @author jessiesetiady
 *
 * @param <T>
 */

public class JSONParser<T> {
	private static final Class T = null;
	ObjectMapper mapper = new ObjectMapper();

	
	public List<T> load(String filename) {
		List<T> objList = null;
		try {
			objList = mapper.readValue(new File(filename), new TypeReference<List<?>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objList;
	}
	
	public <T> T loadObj(String filename) {
		T obj = null;
		try {
			obj = mapper.readValue(new File(filename), (Class<T>) obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
   
   public void write(List <?> objList, String filename) {
	   try {
		   mapper.writeValue(new File(filename), objList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
   public void writeObj(T obj, String filename) {
	   try {
		   mapper.writeValue(new File(filename), obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
   
   public <E> void append(String filename, T obj) {
	   List<T> objList = null;
	   try {
		   objList = mapper.readValue(new File(filename), new TypeReference<List<?>>(){});
		   objList.add(obj);
		   mapper.writeValue(new File(filename), objList);
	   } catch (JsonParseException e) {
			System.out.println("Error: JSON Parse");
	   } catch (JsonMappingException e) {
			System.out.println("Error: JSON Mapping");
	   } catch (IOException e) {
			System.out.println("Error: IO Exception");
	   }
	   
   }

}