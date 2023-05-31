package vn.iotstar.Controller;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import vn.iotstar.Entity.User;
import vn.iotstar.Service.IUserService;
import vn.iotstar.Service.Impl.UserServiceImpl;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns = { "/view/client/login" })
public class LoginController extends HttpServlet {
	IUserService userservice = new UserServiceImpl();
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/view/client/login.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username == null || username.isEmpty()||password == null || password.isEmpty()) {
			response.sendRedirect("/DoAnLTWeb");
            return;
		}
		String filteredUsername = StringEscapeUtils.escapeHtml4(username);
		URLEncoder.encode(filteredUsername, "UTF-8");
		
		String filteredPassword = StringEscapeUtils.escapeHtml4(password);
		URLEncoder.encode(filteredPassword, "UTF-8");
		try {
			User u = userservice.checkLogin(username, password);
			if (u != null) {
				HttpSession session = request.getSession();
				session.setAttribute("username", filteredUsername);
				response.sendRedirect(request.getContextPath() + "/");
			} else {
				request.setAttribute("errorMsg", "Sai tài khoản hoặc mật khẩu!!!");
				RequestDispatcher rd = request.getRequestDispatcher("/view/client/login.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			response.sendRedirect("/DoAnLTWeb");
            return;
		}
	}

}
