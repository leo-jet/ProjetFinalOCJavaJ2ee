package com.subtitle.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitle.beans.DossierUpload;
import com.subtitle.dao.DaoFactory;
import com.subtitle.dao.SubtitleDao;
import com.subtitle.utilities.SubtitlesHandler;
import com.subtitle.utilities.SubtitlesHandlerException;

/**
 * Servlet implementation class ModifiySubtitle
 */
@WebServlet("/ModifiySubtitle")
public class ModifySubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DossierUpload dossierUpload;
  
    private SubtitleDao subtitleDao;
	
	 public void init() throws ServletException{
			DaoFactory daoFactory = DaoFactory.getInstance();
			this.subtitleDao = daoFactory.getSubtitleDao();
			dossierUpload = new DossierUpload();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifySubtitle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		init();
		List<String> nomTables = new ArrayList<String>();
		nomTables = subtitleDao.lister();
		request.setAttribute("nomTables", nomTables);
		this.getServletContext().getRequestDispatcher("/WEB-INF/modify.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		init();
		String openSubtitle = request.getParameter("open");
		ArrayList<String> subtitles = new ArrayList<String>();
		String uploadPath = dossierUpload.getChemin()+ File.separator + "upload";
		String filePath = null;
		filePath = uploadPath + File.separator + openSubtitle +".srt";
		Cookie cookie = this.getCookie(request, "cheminFichierCourant");
		if(cookie != null){
			cookie.setValue(filePath);
			response.addCookie(cookie);
		}
		SubtitlesHandler subtitlesOriginal;
		try {
			subtitlesOriginal = new SubtitlesHandler(filePath);
			subtitles = (ArrayList<String>) subtitleDao.OuvrirUneTraduction(openSubtitle);
			subtitlesOriginal.setTranslatedSubtitles(subtitles);
			request.setAttribute("subtitlesOriginal", subtitlesOriginal.getSubtitles());
			request.setAttribute("subtitles", subtitles);
		} catch (SubtitlesHandlerException e) {
			// TODO Auto-generated catch block
			request.setAttribute("erreur", e.getMessage());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/modify.jsp").forward(request, response);
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
