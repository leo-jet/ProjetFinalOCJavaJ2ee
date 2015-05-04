package com.subtitle.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.subtitle.beans.DossierUpload;
import com.subtitle.dao.DaoFactory;
import com.subtitle.dao.SubtitleDao;
import com.subtitle.utilities.SubtitlesHandler;
import com.subtitle.utilities.SubtitlesHandlerException;



/**
 * Servlet implementation class EditSubtitle
 */
@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int TAILLE_TAMPON = 10240;
	private DossierUpload dossierUpload;
	private SubtitleDao subtitleDao;

	public void init() throws ServletException{
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.subtitleDao = daoFactory.getSubtitleDao();
		dossierUpload = new DossierUpload();
	}


	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "upload";

	// upload settings
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditSubtitle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		init();
		String openSubtitle = request.getParameter("open");
		ArrayList<String> subtitlesTraduit = new ArrayList<String>();
		String uploadPath = dossierUpload.getChemin() + File.separator + UPLOAD_DIRECTORY;
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
			subtitlesTraduit = (ArrayList<String>) subtitleDao.OuvrirUneTraduction(openSubtitle);
			subtitlesOriginal.setTranslatedSubtitles(subtitlesTraduit);
			request.setAttribute("subtitlesOriginal", subtitlesOriginal.getSubtitles());
			request.setAttribute("subtitlesTraduit", subtitlesTraduit);
		} catch (SubtitlesHandlerException e) {
			request.setAttribute("erreur", e.getMessage());
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/editSubtitle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(uploadFile(request, response)==1){
			this.getServletContext().getRequestDispatcher("/WEB-INF/editSubtitle.jsp").forward(request, response);
		}else{
			this.getServletContext().getRequestDispatcher("/WEB-INF/uploadFile.jsp").forward(request, response);
		}
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

	private int uploadFile(HttpServletRequest request, HttpServletResponse response) {
		
		int uploadSucceed = 0;
		
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets memory threshold - beyond which files are stored in disk
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// sets maximum size of upload file
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// sets maximum size of request (include file + form data)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		// this path is relative to application's directory
		ServletContext servletContext = request.getSession().getServletContext();
		String uploadPath = dossierUpload.getChemin()+ File.separator + UPLOAD_DIRECTORY;
		System.out.println(uploadPath);
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			// parses the request's content to extract file data
			List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(request));
			if(!formItems.get(1).getName().isEmpty()){
				String filePath = null;
				if (formItems != null && formItems.size() > 0) {
					// iterates over form's fields
					for (FileItem item : formItems) {
						// processes only fields that are not form fields
						if (!item.isFormField()) {

							String fileName = new File(item.getName()).getName();
							filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);

							// saves the file on disk
							item.write(storeFile);
							request.setAttribute("message","Upload has been done successfully!");
							SubtitlesHandler subtitles = new SubtitlesHandler(filePath);
							request.setAttribute("subtitlesOriginal", subtitles.getSubtitles());
							request.setAttribute("fichier", "");
							response.addCookie(new Cookie("cheminFichierCourant", filePath));
						}
					}
				}
				uploadSucceed = 1;
			}else{
				request.setAttribute("erreur", "Vous n'avez selectionné aucun fichier!!" );
			}

		} catch (Exception ex) {
			request.setAttribute("message",
					"There was an error: " + ex.getMessage());
			System.out.println("There was an error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return uploadSucceed;
	}

}
