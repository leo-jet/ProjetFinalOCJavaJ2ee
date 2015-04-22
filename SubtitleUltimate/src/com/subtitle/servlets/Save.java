package com.subtitle.servlets;

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
import com.subtitle.dao.SubtitleDaoImpl;
import com.subtitle.forms.FormSave;
import com.subtitle.utilities.SubtitlesHandler;

/**
 * Servlet implementation class Save
 */
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
		System.out.println("doGet Save");
		System.out.println(request.getParameter("line1"));
		form = new FormSave();
		ArrayList<String> listeTraduction = null;
		listeTraduction =  form.recupereTraduction(request);
		String filePath = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("cheminFichierCourant")){
					filePath = cookie.getValue();
				}
			}
		}
		SubtitlesHandler subtitles = new SubtitlesHandler(filePath);
		request.setAttribute("subtitles", subtitles.getSubtitles());
		init();
		subtitleDao.addSubtitles(listeTraduction, subtitles.getOriginalSubtitleTimes());
		this.getServletContext().getRequestDispatcher("/WEB-INF/saveFile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Save");
		this.getServletContext().getRequestDispatcher("/WEB-INF/saveFile.jsp").forward(request, response);
	}

}
