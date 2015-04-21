package com.subtitle.servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.subtitle.dao.SubtitleDao;



/**
 * Servlet implementation class EditSubtitle
 */
@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int TAILLE_TAMPON = 10240;
	public static final String CHEMIN_FICHIER = "C:\\Users\\Administrateur\\Desktop\\SubtitleUltimate\\SubtitleUltimate\\WebContent\\WEB-INF";
	private static String FILE_NAME = "/WEB-INF/password_presentation.srt";
	private SubtitleDao subtitleDao;
	
	
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
		System.out.println("doGet Edit");
		this.getServletContext().getRequestDispatcher("/WEB-INF/editSubtitle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Edit");
		uploadFile(request);
		this.getServletContext().getRequestDispatcher("/WEB-INF/editSubtitle.jsp").forward(request, response);
	}
	
	
	private void uploadFile(HttpServletRequest request) {
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
		    String uploadPath = "C:\\Users\\Administrateur\\Desktop\\SubtitleUltimate\\SubtitleUltimate\\WebContent\\WEB-INF" + File.separator + UPLOAD_DIRECTORY;
		     
		    // creates the directory if it does not exist
		    File uploadDir = new File(uploadPath);
		    if (!uploadDir.exists()) {
		        uploadDir.mkdir();
		    }
		    try {
		        // parses the request's content to extract file data
		        @SuppressWarnings("unchecked")
		        List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(request));
		        System.out.println(formItems);
		            if(!formItems.get(0).getName().isEmpty()){
		            	String filePath = null;
			            if (formItems != null && formItems.size() > 0) {
			                // iterates over form's fields
			                for (FileItem item : formItems) {
			                    // processes only fields that are not form fields
			                    if (!item.isFormField()) {
			                    	
			                        String fileName = new File(item.getName()).getName();
			                        filePath = uploadPath + File.separator + fileName;
			                        System.out.println(filePath+fileName);
			                        File storeFile = new File(filePath);
			 
			                        // saves the file on disk
			                        item.write(storeFile);
			                        request.setAttribute("message",
			                            "Upload has been done successfully!");
			                		/*SubtitlesHandler subtitles = new SubtitlesHandler(filePath);
			                		request.setAttribute("subtitles", subtitles.getSubtitles());
			                		request.setAttribute("fichier", "");
				        			System.out.println("Sortie de lecture");*/
			                    }
			                }
			               /* SubtitlesHandler subtitles = new SubtitlesHandler(filePath);
			    			request.setAttribute("subtitles", subtitles.getSubtitles());
			    			
			    			Formulaires form = new Formulaires();
			    			ArrayList<String> listeTraduction = null;
			    			listeTraduction =  form.recupereTraduction(request);*/
			            }
		            }else{
		            	
		            }
		        
		    } catch (Exception ex) {
		        request.setAttribute("message",
		                "There was an error: " + ex.getMessage());
		        System.out.println("There was an error: " + ex.getMessage());
		        ex.printStackTrace();
		    }
	}

}
