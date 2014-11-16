<%-- 
    Document   : header
    Created on : Nov 16, 2014, 3:00:35 PM
    Author     : Andrew
    Description:
    This is the header and navigation bar for the site. These elements have 
    been put into their own file so that they're not duplicated all over the
    site, as that's a sure way to introduce little presentation errors into the 
    website. NOTE: This should only ever be included using jsp:include. It is 
    not intended to be viewed on it's own. -Andrew

    The skull image in the logo was created by Simon Strandgaard, and licensed
    under the Creative Commons Attribution 2.0 generic license.
    Image: http://www.photoree.com/photos/permalink/11920377-12739382@N04
    License: http://creativecommons.org/licenses/by/2.0/
--%>

<%--   --%>
<img src="./Images/logo.png">
</header>
<div class="mainnav"> <%-- start of Navbar --%>
    <ul id="list-nav">
        <%-- link to servlets, rather than the index --%>
        <li><a href="index.jsp">Patient Operations</a></li>
        <li><a href="index.jsp">Medicine Operations</a></li> 
    </ul>
</div> <!-- End of Navbar -->