package com;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/billAPI")
public class billAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	bill itemObj = new bill();
	
    public billAPI() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String output = itemObj.insertBill(request.getParameter("locationCode"),
				 		request.getParameter("locationName"),
				 		request.getParameter("date"),
				 		request.getParameter("time"));
					response.getWriter().write(output);
		
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map paras = getParasMap(request);
		String output = itemObj.updateBill(paras.get("hidbillIDSave").toString(),
		paras.get("locationCode").toString(),
		paras.get("locationName").toString(),
		paras.get("date").toString(),
		paras.get("time").toString());
		response.getWriter().write(output);
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		 String output = itemObj.deleteBill(paras.get("locationId").toString());
		response.getWriter().write(output);
	}
	
    // Convert request parameters to a Map
    private static Map getParasMap(HttpServletRequest request)
    {
    	Map<String, String> map = new HashMap<String, String>();
    	try
    	{
    		Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
    		String queryString = scanner.hasNext() ?
    				scanner.useDelimiter("\\A").next() : "";
    		scanner.close();
    		String[] params = queryString.split("&");
    		for (String param : params)
    		{ 
    			String[] p = param.split("=");
    			map.put(p[0], p[1]);
    		}
    	}
    	catch (Exception e)
    	{
    	}
    	return map;
    }
	

}
