package vn.iotstar.Controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.iotstar.Entity.Admin;
import vn.iotstar.Service.IAdminService;
import vn.iotstar.Service.Impl.AdminServiceImpl;

import java.util.List;

/**
 * Servlet implementation class CategoryListController
 */
@WebServlet(urlPatterns = { "/admin/admin/list" })
public class AdminListController extends HttpServlet {
	/** 
	 *  
	 */
	private static final long serialVersionUID = 1L;
	IAdminService adminService = new AdminServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Admin> adminList = adminService.getAll();
		req.setAttribute("adminlist", adminList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/view/admin/admin.jsp");
		dispatcher.forward(req, resp);
	}
}