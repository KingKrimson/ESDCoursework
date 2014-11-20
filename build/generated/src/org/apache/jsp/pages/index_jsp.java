package org.apache.jsp.pages;

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

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/fatal_style.css\">\r\n");
      out.write("        <title>Fatal System - Home</title>\r\n");
      out.write("    </head>\r\n");
      out.write("    <body>\r\n");
      out.write("        ");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<img src=\"images/logo.png\">\r\n");
      out.write("</header>\r\n");
      out.write("<div class=\"mainnav\"> ");
      out.write("\r\n");
      out.write("    <ul id=\"list-nav\">\r\n");
      out.write("        ");
      out.write("\r\n");
      out.write("        <li><a href=\"HomeController\">Home</a></li>\r\n");
      out.write("        <li><a href=\"PatientController\">Patient Operations</a></li>\r\n");
      out.write("        <li><a href=\"MedicineController\">Medicine Operations</a></li> \r\n");
      out.write("    </ul>\r\n");
      out.write("</div> <!-- End of Navbar -->\r\n");
      out.write("\r\n");
      out.write("        <div class=\"clear\"></div>\r\n");
      out.write("        <div id=\"pagecontent\">\r\n");
      out.write("            <h1>Fatal System Home</h1>\r\n");
      out.write("            <p>Welcome Doctor Fatal.</p>\r\n");
      out.write("            <p>\r\n");
      out.write("                To get started, click on either the patient or medicine links \r\n");
      out.write("                above. You will be able to view patients and medicines, and\r\n");
      out.write("                perform and required actions.\r\n");
      out.write("            </p>\r\n");
      out.write("        </div>    \r\n");
      out.write("        <div class=\"clear\"></div>\r\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("    <footer>\n");
      out.write("        <hr />\n");
      out.write("        <p>&copy; Andrew Brown, Andrew Heaford, and Christopher Steel.</p>\n");
      out.write("    </footer>\n");
      out.write("\r\n");
      out.write("    </body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
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
