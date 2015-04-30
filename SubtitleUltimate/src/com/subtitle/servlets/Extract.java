package com.subtitle.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
		if(subtitleDao.extraireTraduction(request.getParameter("nomTable"))==1){
			String nomTable = request.getParameter("nomTable");
			request.setAttribute("message", "Le fichier a été bien exporté");
			response.setContentType("text/html");  
			PrintWriter out = response.getWriter();  
			String filename = nomTable+".srt";   
			String filepath = "C:\\Users\\Administrateur\\Desktop\\SubtitleUltimate\\SubtitleUltimate\\WebContent\\WEB-INF\\upload\\";   
			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   
			  
			FileInputStream fileInputStream = new FileInputStream(filepath + filename);  
			            
			int i;   
			while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
			}   
			fileInputStream.close();   
			out.close();   
			this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		}else{
			request.setAttribute("message", "Le fichier que vous voulez exporter n'existe pas");
			this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		}
		
	}

}
