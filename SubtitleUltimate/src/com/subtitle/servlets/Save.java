package com.subtitle.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitle.dao.DaoFactory;
import com.subtitle.dao.SubtitleDao;
import com.subtitle.forms.FormSave;
import com.subtitle.utilities.SubtitlesHandler;
import com.subtitle.utilities.SubtitlesHandlerException;

@WebServlet("/Save")
public class Save extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubtitleDao subtitleDao;
	private FormSave form ;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Save() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException{
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.subtitleDao = daoFactory.getSubtitleDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		form = new FormSave();
		ArrayList<String> listeTraduction = null;
		listeTraduction =  form.recupereTraduction(request);
		String filePath = null;
		
		Cookie cookie = this.getCookie(request, "cheminFichierCourant");
		if(cookie != null){
			filePath = cookie.getValue();
		}
		SubtitlesHandler subtitles;
		try {
			subtitles = new SubtitlesHandler(filePath);
			request.setAttribute("subtitles", subtitles.getSubtitles());
			init();
			File fichier = new File(filePath);
			String nomFichier = fichier.getName().substring(0, fichier.getName().indexOf("."));
			subtitleDao.addSubtitles(listeTraduction, subtitles.getOriginalSubtitleTimes(), nomFichier);
		} catch (SubtitlesHandlerException e) {
			request.setAttribute("erreur", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/saveFile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/saveFile.jsp").forward(request, response);
	}
	
	public String getNomFichier(String cheminFichier){
		String nomFichier = null;
		nomFichier = cheminFichier.substring(cheminFichier.lastIndexOf("\"")+1, cheminFichier.lastIndexOf(".")-1);
		return nomFichier;
	}
	
	public Cookie getCookie(HttpServletRequest request, String nomCookie){
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals(nomCookie)){
					return cookie;
				}
			}
		}
		return null;
	}
}
