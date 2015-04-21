package com.subtitle.dao;

import java.util.ArrayList;
import java.util.List;

import com.subtitle.beans.Subtitle;

public interface SubtitleDao {
	List<Subtitle> lister();
	void addSubtitles(ArrayList<String> listeTraduction, ArrayList<String> listeTimeTraduction);
	int testSiLaTableExist();
	void creerTable();
}
