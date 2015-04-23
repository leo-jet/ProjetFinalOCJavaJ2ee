package com.subtitle.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitle.dao.DaoFactory;
import com.subtitle.dao.SubtitleDao;

/**
 * Servlet implementation class Extract
 */
@WebServlet("/Extract")
public class Extract extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubtitleDao subtitleDao;
	
	 public void init() throws ServletException{
			DaoFactory daoFactory = DaoFactory.getInstance();
			this.subtitleDao = daoFactory.getSubtitleDao();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Extract() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/extraire.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		init();
		subtitleDao.extraireTraduction(request.getParameter("nomTable"));
		this.getServletContext().getRequestDispatcher("/WEB-INF/extraire.jsp").forward(request, response);
	}

}
