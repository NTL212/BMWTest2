package vn.iotstar.Controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.iotstar.Entity.Item;
import vn.iotstar.Entity.Order;
import vn.iotstar.Entity.Product;
import vn.iotstar.Service.IProductService;
import vn.iotstar.Service.Impl.ProductServiceImpl;

@WebServlet(urlPatterns = {"/view/client/cart-delete"})
public class DeleteProductInCartController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	IProductService productservice = new ProductServiceImpl();
	DecimalFormat df = new DecimalFormat("#.000");
	DecimalFormat df1 = new DecimalFormat("#.0");
	public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // Nếu không thể chuyển đổi thành số nguyên, ném ra ngoại lệ NumberFormatException
            return false;
        }
    }
	@Override
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if(id == null||id == ""||!isInteger(id)) {
			resp.sendRedirect("/DoAnLTWeb");
			return;
		}
		
		HttpSession session = req.getSession(true);
		Product product = productservice.get(Integer.parseInt(id));
		Order order = (Order) session.getAttribute("order");
		if(order == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		List<Item> listItems = order.getItems();
		for(Item item: listItems)
		{
			if(item.getProduct().getId() == product.getId())
			{
				order.setSumPrice(order.getSumPrice() - item.getPrice());
				listItems.remove(item);
				break;
			}
		}
		order.setItems(listItems);
		session.setAttribute("order", order);
		resp.sendRedirect(req.getContextPath() + "/view/client/cart");
		if(order.getSumPrice() == 0)
		{
			session.setAttribute("sumprice", "0");
		} else {
			session.setAttribute("sumprice", df.format(order.getSumPrice()));
		}
		
	}
}
