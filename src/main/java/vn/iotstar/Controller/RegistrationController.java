package vn.iotstar.Controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import vn.iotstar.Entity.User;
import vn.iotstar.Service.IUserService;
import vn.iotstar.Service.Impl.UserServiceImpl;

/**
 * Servlet implementation class RegistrationController
 */
@WebServlet(urlPatterns = { "/view/client/register" })
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IUserService userservice = new UserServiceImpl();

	public RegistrationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/view/client/register.jsp");
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
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String created = request.getParameter("created");
		// Kiểm tra mật khẩu đáp ứng yêu cầu
	    if (isValidPassword(password) == 1) {
	        User user = new User(name, email, phone, username, password, created);
	        int register = userservice.RegisterUser(user);
	        if (register == 0) {
	            request.setAttribute("Message", "Tạo tài khoản thành công. Đăng nhập <a href='/DoAnLTWeb/view/client/login'>tại đây!</a>");
	            RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	            rd.forward(request, response);
	        } else if (register == 1) {
	            request.setAttribute("errMessage", "Đã tồn tại email!!!");
	            RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	            rd.forward(request, response);
	        } else {
	            request.setAttribute("errMessage", "Đã tồn tại username!!!");
	            RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	            rd.forward(request, response);
	        }
	    } else if (isValidPassword(password) == 0) {
	        request.setAttribute("errMessage", "Độ dài mật khẩu từ 8 - 20 ký tự!!!");
RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	        rd.forward(request, response);
	    } else if (isValidPassword(password) == 2) {
	        request.setAttribute("errMessage", "Mật khẩu chứa ít nhất một chữ cái viết thường!!!");
	        RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	        rd.forward(request, response);
	    } else if (isValidPassword(password) == 3) {
	        request.setAttribute("errMessage", "Mật khẩu chứa ít nhất một chữ cái viết hoa!!!");
	        RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	        rd.forward(request, response);
	    }
	    else if (isValidPassword(password) == 4) {
	        request.setAttribute("errMessage", "Mật khẩu chứa ít nhất một chữ số!!!");
	        RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	        rd.forward(request, response);
	    }
	    else {
	        request.setAttribute("errMessage", "Mật khẩu chứa ít nhất một ký tự đặc biệt!!!");
	        RequestDispatcher rd = request.getRequestDispatcher("/view/client/register.jsp");
	        rd.forward(request, response);
	    }
	}
	// Hàm mã hóa mật khẩu
    public static String hashPassword(String password) {
        // Số vòng lặp (tăng số vòng lặp tăng độ mạnh của thuật toán)
        int rounds = 12;

        // Mã hóa mật khẩu bằng thuật toán BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(rounds));

        return hashedPassword;
    }
	// Phương thức kiểm tra tính hợp lệ của mật khẩu
	private int isValidPassword(String password) {
	    // Kiểm tra độ dài mật khẩu từ 8 - 20 ký tự
	    if (password.length() < 8 || password.length() > 20) {
	        return 0;
	    }

	    // Kiểm tra chứa ít nhất một chữ cái viết thường
	    if (!Pattern.compile("[a-z]").matcher(password).find()) {
	        return 2;
	    }

	    // Kiểm tra chứa ít nhất một chữ cái viết hoa
	    if (!Pattern.compile("[A-Z]").matcher(password).find()) {
	        return 3;
	    }

	    // Kiểm tra chứa ít nhất một chữ số
	    if (!Pattern.compile("\\d").matcher(password).find()) {
	        return 4;
	    }

	    // Kiểm tra chứa ít nhất một ký tự đặc biệt
	    if (!Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]").matcher(password).find()) {
	        return 5;
	    }

	    return 1;
	}
}