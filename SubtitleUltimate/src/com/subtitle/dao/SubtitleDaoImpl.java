package com.subtitle.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	public void addSubtitles(ArrayList<String> listeTraduction, ArrayList<String> listeTimeTraduction, String nomTable) {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement pStatement = null;
		try {
			if(this.testSiLaTableExist(nomTable)==1){
				connexion = daoFactory.getConnection();
				Iterator<String> itListeTraduction = listeTraduction.iterator();
				Iterator<String> itListeTimeTraduction = listeTimeTraduction.iterator();
				int i=1;
				while ((itListeTraduction.hasNext())&&(itListeTimeTraduction.hasNext())) {
					String s1 = itListeTimeTraduction.next();
					String s2 = itListeTraduction.next();
					pStatement = connexion.prepareStatement("INSERT INTO "+nomTable+" (id, time, subtitle) VALUES(?,?,?) on duplicate key update time=values(time), subtitle=values(subtitle);");
					pStatement.setInt(1, i);
					pStatement.setString(2, s1);
					pStatement.setString(3, s2);
					pStatement.executeUpdate();
					i++;
				}
				connexion.commit();
			}else{
				this.creerTable(nomTable);
				connexion = daoFactory.getConnection();
				Iterator<String> itListeTraduction = listeTraduction.iterator();
				Iterator<String> itListeTimeTraduction = listeTimeTraduction.iterator();
				int i=1;
				while (itListeTraduction.hasNext()) {
					String s1 = itListeTimeTraduction.next();
					String s2 = itListeTraduction.next();
					pStatement = connexion.prepareStatement("INSERT INTO "+nomTable+ "(id, time, subtitle) VALUES(?,?,?) on duplicate key update time=values(time), subtitle=values(subtitle);");
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
	
	public int testSiLaTableExist(String nomTable){
		int test = 0;
		
		ResultSet resultSet;
		try {
			Connection connection = daoFactory.getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			resultSet = metadata.getTables(null, null, nomTable, null);
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
	
	public void creerTable(String nomTable){
		Connection connection = null;
		Statement statement = null;
		System.out.println("on est dans creerTable");
		System.out.println(nomTable);
		try {
			connection = daoFactory.getConnection();
			statement = connection.createStatement();
				
			String requeteCreation ;
				
			requeteCreation = "CREATE TABLE `javadb`.`"+nomTable+"` ("+
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
	
	public List<Subtitle> extraireTraduction(String nomTable){
		
		List<Subtitle> subtitles = new ArrayList<Subtitle>();
		
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT time, subtitle FROM "+nomTable+";");
			
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
		
		//on sauvegarde subtitles dans un fichier
		Iterator<Subtitle> itSubtitles = subtitles.iterator();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("C:\\Users\\Administrateur\\Desktop\\SubtitleUltimate\\SubtitleUltimate\\WebContent\\WEB-INF"+File.separator+"upload"+File.separator+nomTable+"Traduction.srt", "UTF-8");
			int i = 1;
			while (itSubtitles.hasNext()) {
				
				Subtitle s= itSubtitles.next();
				writer.println(i);
				writer.println(s.getTime());
				writer.println(s.getSubtitle()+"\n");
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(writer != null){
				writer.close();
			}
		}
		
		return subtitles;
	}
	
}
