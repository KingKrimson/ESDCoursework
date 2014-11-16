package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/WEB-INF/jspf/header.jspf");
    _jspx_dependants.add("/WEB-INF/jspf/footer.jspf");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/fatal_style.css\">\n");
      out.write("        <title>Fatal System - Home</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("<img src=\"./Images/logo.png\">\n");
      out.write("</header>\n");
      out.write("<div class=\"mainnav\"> ");
      out.write("\n");
      out.write("    <ul id=\"list-nav\">\n");
      out.write("        ");
      out.write("\n");
      out.write("        <li><a href=\"index.jsp\">Home</a></li>\n");
      out.write("        <li><a href=\"patients.jsp\">Patient Operations</a></li>\n");
      out.write("        <li><a href=\"medicines.jsp\">Medicine Operations</a></li> \n");
      out.write("    </ul>\n");
      out.write("</div> <!-- End of Navbar -->\n");
      out.write(" \n");
      out.write("        <div class=\"clear\"></div>\n");
      out.write("        <div id=\"pagecontent\">\n");
      out.write("            <h1>Fatal System Home</h1>\n");
      out.write("            <p>Welcome Doctor Fatal.</p>\n");
      out.write("            <p>\n");
      out.write("                To get started, click on either the patient or medicine links \n");
      out.write("                above. You will be able to view patients and medicines, and\n");
      out.write("                perform and required actions.\n");
      out.write("            </p>\n");
      out.write("        </div>    \n");
      out.write("        <div class=\"clear\"></div>\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("    <footer>\n");
      out.write("        <hr />\n");
      out.write("        <p>&copy; Andrew Brown, Andrew Heaford, and Christopher Steel.</p>\n");
      out.write("    </footer>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("\n");
      out.write("\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
