/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import uyen.account.AccountDTO;

/**
 *
 * @author HP
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    //private final static Logger log = Logger.getLogger(AuthFilter.class);
    //private final static Logger log = Logger.getLogger(AuthFilter.class);
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private final List<String> guest;
    private final List<String> user;
    private final List<String> admin;
    private final String ERROR_PAGE = "error.html";
    private final int ROLE_ADMIN = 1;
    private final int ROLE_USER = 2;
    private final int ROLE_LEADER = 3;

    public AuthFilter() {
        guest = new ArrayList<>();
        guest.add("");
        guest.add("login_page");
        guest.add("login.action");
        guest.add("logout");
        guest.add("logout.action");
        guest.add("invalid.html");
        guest.add("create_page");
        guest.add("create_account");
        guest.add("create_account.action");
        guest.add("login_google.action");
        guest.add("login_google");
        guest.add("error.html");

        user = new ArrayList<>();
        user.add("");
        user.add("display.jsp");
        user.add("display_page.action");
        user.add("logout");
        user.add("logout.action");
        user.add("activate.jsp");
        user.add("resend.action");
        user.add("activate.action");
        user.add("search.action");
        user.add("booking");
        user.add("booking.action");
        user.add("history_page.action");
        user.add("history.action");
        user.add("delete");
        user.add("error.html");
        user.add("cart_page.action");
        user.add("manage");
        user.add("checkout");
        user.add("checkout.action");

        admin = new ArrayList<>();
        admin.add("");
        admin.add("display_page.action");
        admin.add("logout");
        admin.add("logout.action");
        admin.add("search.action");
        admin.add("process_page.action");
        admin.add("process.action");
        admin.add("answer");
        admin.add("error.html");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;

            String uri = req.getRequestURI();
            int lastIndex = uri.lastIndexOf("/");
            String resources = uri.substring(lastIndex + 1);
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("ACCOUNT") == null) {
                //not login
                if (guest.contains(resources)) {
                    chain.doFilter(request, response);
                } else {
                    resources = ERROR_PAGE;
                }

            } else {
                //login
                AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
                if ((account.getRole() == ROLE_ADMIN && admin.contains(resources))
                        || ((account.getRole() == ROLE_USER || account.getRole() == ROLE_LEADER) && user.contains(resources))) {
                    chain.doFilter(request, response);
                } else {
                    if (resources.equals("")) {
                        resources = "display.jsp";
                    } else {
                        resources = ERROR_PAGE;
                    }
                }
                if (resources.equals("")) {
                    resources = "display.jsp";
                } else {
                    resources = ERROR_PAGE;
                }
            }

            //System.out.println("role: " + ((AccountDTO) session.getAttribute("ACCOUNT")).getRole() + " resources: " + resources);
            RequestDispatcher rd = req.getRequestDispatcher(resources);
            rd.forward(request, response);
        } catch (Throwable t) {
            //log.error(t.getMessage());
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
