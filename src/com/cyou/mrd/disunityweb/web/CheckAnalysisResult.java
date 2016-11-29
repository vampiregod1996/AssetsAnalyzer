package com.cyou.mrd.disunityweb.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cyou.mrd.disunityweb.data.DatabaseAnalyzeResult;
import com.cyou.mrd.disunityweb.util.DisunityWebUtil;

/**
 * Servlet implementation class TestCheck
 */
@WebServlet("/CheckAnalysisResult")
public class CheckAnalysisResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckAnalysisResult() {
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
		String uuidStr = request.getParameter("bundleID");
		PrintWriter pw = response.getWriter();
		if (DisunityWebUtil.checkStringIsEmpty(uuidStr)) {
			pw.write("post me the uuid please!");
			return;
		}
		if (DisunityWebUtil.checkStringIsEmpty(uuidStr)) {
			pw.write("post me the uuid please!");
			return;
		}
	
		UUID uuid = UUID.fromString(uuidStr);
		DatabaseAnalyzeResult dar = new DatabaseAnalyzeResult(uuid, "resource");
		pw.write(dar.getResult().toString());
		pw.flush();
		pw.close();
	}
}
