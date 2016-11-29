package com.cyou.mrd.disunityweb.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyou.mrd.disunityweb.core.domain.application.ApplicationFactory;
import com.cyou.mrd.disunityweb.core.domain.application.UnityApplication;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();
		
		// unzip
		String zipPath = "D:\\workspace_unity\\unzip\\df_dev_orange.ipa";
		String unzipPath = "D:\\workspace_unity\\unzip";

		//TODO get uuid
		UnityApplication ua = ApplicationFactory.getInstance().getApplication(zipPath);
		
		pw.write("success to TestServlet ! remember your id : "+ua.getUUID());
		pw.flush();
		pw.close();
		
		ua.startExtract();
		
		
		
//		AntZip zip = new AntZip(); 
//		zip.unZip(zipPath,unzipPath+"\\"+id+"\\");
//		
//		//TODO 判断是apk还是ipa，不同后缀文件的目录结构不同
//		
//		//ipa 
//		String[] testArgs = {"weblist",unzipPath+"/"+id+"/Payload/orange.app/Data/resources.assets"};
//		//apk 
////		String[] testArgs = {"weblist",unzipPath+"/"+id+"/Payload/orange.app/Data/"};
//		
//		
//		
//		DisUnity du= DisUnityThreadFactory.getThread(id);
//		du.parse(testArgs);
//		du.start();
		
	}

}
