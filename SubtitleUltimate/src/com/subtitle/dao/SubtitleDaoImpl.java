package com.subtitle.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.subtitle.beans.Subtitle;


public class SubtitleDaoImpl implements SubtitleDao {
	private DaoFactory daoFactory;

	public SubtitleDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void addSubtitles(ArrayList<String> listeTraduction, ArrayList<String> listeTimeTraduction) {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement pStatement = null;
		try {
			if(this.testSiLaTableExist()==1){
				connexion = daoFactory.getConnection();
				Iterator<String> itListeTraduction = listeTraduction.iterator();
				Iterator<String> itListeTimeTraduction = listeTimeTraduction.iterator();
				int i=1;
				while ((itListeTraduction.hasNext())&&(itListeTimeTraduction.hasNext())) {
					String s1 = itListeTimeTraduction.next();
					String s2 = itListeTraduction.next();
					pStatement = connexion.prepareStatement("INSERT INTO subtitles (id, time, subtitle) VALUES(?,?,?) on duplicate key update time=values(time), subtitle=values(subtitle);");
					pStatement.setInt(1, i);
					pStatement.setString(2, s1);
					pStatement.setString(3, s2);
					pStatement.executeUpdate();
					i++;
				}
				connexion.commit();
			}else{
				this.creerTable();
				connexion = daoFactory.getConnection();
				Iterator<String> itListeTraduction = listeTraduction.iterator();
				Iterator<String> itListeTimeTraduction = listeTimeTraduction.iterator();
				int i=1;
				while (itListeTraduction.hasNext()) {
					String s1 = itListeTimeTraduction.next();
					String s2 = itListeTraduction.next();
					pStatement = connexion.prepareStatement("INSERT INTO subtitles (id, time, subtitle) VALUES(?,?,?) on duplicate key update time=values(time), subtitle=values(subtitle);");
					pStatement.setInt(1, i);
					pStatement.setString(2, s1);
					pStatement.setString(3, s2);
					pStatement.executeUpdate();
					i++;
				}
				connexion.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			/*try{
				if(connexion != null){
					connexion.rollback();
				}
			} catch (SQLException e2){
			}*/
			//throw new DaoException("Impossible de communiquer avec la base de données");
			 System.out.println(e.getMessage());
		}
		finally {
			/*try{
				if(connexion != null){
					connexion.rollback();
				}
			} catch (SQLException e2){
			}*/
			//throw new DaoException("Impossible de communiquer avec la base de données finally");
		}
		
	}

	@Override
	public List<Subtitle> lister() {
		// TODO Auto-generated method stub
		List<Subtitle> subtitles = new ArrayList<Subtitle>();
		
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT nom, prenom FROM noms;");
			
			while (resultat.next()){
				String time = resultat.getString("time");
				String subtitle = resultat.getString("subtitle");
				Subtitle subtitleLu = new Subtitle();

				subtitleLu.setSubtitle(subtitle);
				subtitleLu.setTime(time);
				
				subtitles.add(subtitleLu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return subtitles;
	}
	
	public int testSiLaTableExist(){
		int test = 0;
		
		ResultSet resultSet;
		try {
			Connection connection = daoFactory.getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			resultSet = metadata.getTables(null, null, "subtitles", null);
			if(resultSet.next()){
			    // Table exists
				test = 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return test;
	}
	
	public void creerTable(){
		Connection connection = null;
		Statement statement = null;
		System.out.println("on est dans creerTable");
		try {
			connection = daoFactory.getConnection();
			statement = connection.createStatement();
				
			String requeteCreation ;
				
			requeteCreation = "CREATE TABLE `javadb`.`subtitles` ("+
						 " `id` INT NOT NULL AUTO_INCREMENT,"+
						 " `time` VARCHAR(45) NOT NULL,"+
						  "`subtitle` VARCHAR(1000) NOT NULL,"+
						  "PRIMARY KEY (`id`));";
			statement.executeUpdate(requeteCreation);
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
