package com.subtitle.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class FormSave {
	
	public ArrayList<String> recupereTraduction(HttpServletRequest request){
		ArrayList<String> listeTraduction = new ArrayList<String>();
		int i=0;
		while (request.getParameter("line"+Integer.toString(i)) != null){
			listeTraduction.add(request.getParameter("line"+Integer.toString(i)));
			i++;
		}
		return listeTraduction;
	}
}
