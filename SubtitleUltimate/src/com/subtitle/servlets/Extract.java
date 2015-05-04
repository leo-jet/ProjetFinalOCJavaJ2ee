package com.subtitle.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitle.beans.DossierUpload;
import com.subtitle.dao.DaoException;
import com.subtitle.dao.DaoFactory;
import com.subtitle.dao.SubtitleDao;

/**
 * Servlet implementation class Extract
 */
@WebServlet("/Extract")
public class Extract extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubtitleDao subtitleDao;
	private DossierUpload dossierUpload;
	
	 public void init() throws ServletException{
			DaoFactory daoFactory = DaoFactory.getInstance();
			this.subtitleDao = daoFactory.getSubtitleDao();
			dossierUpload = new DossierUpload();
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
		init();
		List<String> nomTables = new ArrayList<String>();
		nomTables = subtitleDao.lister();
		request.setAttribute("nomTables", nomTables);
		this.getServletContext().getRequestDispatcher("/WEB-INF/extraire.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		init();
		int test = 0;
		try {
			test = subtitleDao.extraireTraduction(request.getParameter("nomTable"), dossierUpload.getChemin());
			if(test==1){
				String nomTable = request.getParameter("nomTable");
				request.setAttribute("message", "Le fichier a été bien exporté");
				response.setContentType("text/html");  
				PrintWriter out = response.getWriter();  
				String filename = nomTable+".srt";   
				String filepath = dossierUpload.getChemin()+"\\upload\\";   
				response.setContentType("APPLICATION/OCTET-STREAM");   
				response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   
				  
				FileInputStream fileInputStream = new FileInputStream(filepath + filename);  
				            
				int i;   
				while ((i=fileInputStream.read()) != -1) {  
				out.write(i);   
				} 
				fileInputStream.close();   
				out.close();   
			}else{
				request.setAttribute("erreur", "La traduction que vous voulez exporter n'existe pas");
			}
			List<String> nomTables = new ArrayList<String>();
			nomTables = subtitleDao.lister();
			request.setAttribute("nomTables", nomTables);
			try {
				this.getServletContext().getRequestDispatcher("/WEB-INF/extraire.jsp").forward(request, response);
			} catch (Exception e) {
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("erreur", e.getMessage());
			List<String> nomTables = new ArrayList<String>();
			nomTables = subtitleDao.lister();
			request.setAttribute("nomTables", nomTables);
			System.out.println("ddddddddddddddddddddddddddddddd");
			this.getServletContext().getRequestDispatcher("/WEB-INF/extraire.jsp").forward(request, response);
		}
		
	}

}
