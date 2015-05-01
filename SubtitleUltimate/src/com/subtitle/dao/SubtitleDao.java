package com.subtitle.dao;

import java.util.ArrayList;
import java.util.List;

 
public interface SubtitleDao {
	List<String> lister();
	void addSubtitles(ArrayList<String> listeTraduction, ArrayList<String> listeTimeTraduction, String nomTable);
	int testSiLaTableExist(String nomTable);
	void creerTable(String nomTable);
	int extraireTraduction(String nomTable, String cheminWebInf);
	List<String> OuvrirUneTraduction(String nomTable);
}
