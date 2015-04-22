package com.subtitle.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SubtitlesHandler {
	private ArrayList<String> originalSubtitles = null;
	private ArrayList<String> originalSubtitleTimes = null;
	private ArrayList<String> translatedSubtitles = null;
	private String nomTable;

	public SubtitlesHandler(String fileName) {
		
		ArrayList<String> tamponSubtitles = new ArrayList<String>();
		originalSubtitles = new ArrayList<String>();
		translatedSubtitles = new ArrayList<String>();
		originalSubtitleTimes = new ArrayList<String>();
		BufferedReader br;
		
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			
			while ((line = br.readLine()) != null) {
				tamponSubtitles.add(line);
			}
			br.close();
			
			//je filtre tampon
			Iterator<String> it = tamponSubtitles.iterator();
			 
			while (it.hasNext()) {
				it.next();
				String s = it.next();
				originalSubtitleTimes.add(s);
				s = it.next();
				String sTmp;
			    while ((it.hasNext())&&(!(sTmp = it.next()).equals(""))) {
			    	s+= ' '+sTmp;
			    }
			    originalSubtitles.add(s);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getSubtitles() {
		return originalSubtitles;
	}

	public ArrayList<String> getTranslatedSubtitles() {
		return translatedSubtitles;
	}

	public void setTranslatedSubtitles(ArrayList<String> translatedSubtitles) {
		this.translatedSubtitles = translatedSubtitles;
	}

	public ArrayList<String> getOriginalSubtitleTimes() {
		return originalSubtitleTimes;
	}
	
	
	
}
