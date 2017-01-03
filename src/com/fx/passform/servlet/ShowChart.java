package com.fx.passform.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.servlet.ChartDeleter;
import org.jfree.chart.servlet.ServletUtilities;

/**
 * Servlet implementation class ShowChart
 */
public class ShowChart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ShowChart() {
        // TODO Auto-generated constructor stub
    }

    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("do service");
		showChart(request, response);
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void showChart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ShowChart");
		HttpSession session = request.getSession();
		String filename = request.getParameter("filename");

		if (filename == null) {
			throw new ServletException("Parameter 'filename' must be supplied");
		}

		// Replace ".." with ""
		// This is to prevent access to the rest of the file system
		filename = ServletUtilities.searchReplace(filename, "..", "");

		// Check the file exists
		File file = new File(System.getProperty("java.io.tmpdir"), filename);
		if (!file.exists()) {
			throw new ServletException(
					"Unable to display the chart with the filename '"
							+ filename + "'.");
		}

		// Check that the graph being served was created by the current user
		// or that it begins with "public"
		boolean isChartInUserList = false;
		ChartDeleter chartDeleter = (ChartDeleter) session
				.getAttribute("JFreeChart_Deleter");
		if (chartDeleter != null) {
			isChartInUserList = chartDeleter.isChartAvailable(filename);
		}

		boolean isChartPublic = false;
		if (filename.length() >= 6) {
			if (filename.substring(0, 6).equals("public")) {
				isChartPublic = true;
			}
		}

		boolean isOneTimeChart = false;
		if (filename.startsWith(ServletUtilities.getTempOneTimeFilePrefix())) {
			isOneTimeChart = true;
		}

		if (isChartInUserList || isChartPublic || isOneTimeChart) {
			// Serve it up
			ServletUtilities.sendTempFile(file, response);
			if (isOneTimeChart) {
				file.delete();
			}
		} else {
			throw new ServletException("Chart image not found");
		}
	}
	
}
