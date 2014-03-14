package com.google.appengine.codelab;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	resp.setContentType("application/json; charset=utf-8");
	resp.setHeader("Cache-Control", "no-cache");
  }
}
