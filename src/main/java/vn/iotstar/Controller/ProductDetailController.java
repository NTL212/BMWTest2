package vn.iotstar.Controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.iotstar.Entity.Category;
import vn.iotstar.Entity.Product;
import vn.iotstar.Service.ICategoryService;
import vn.iotstar.Service.IProductService;
import vn.iotstar.Service.Impl.CategoryServiceImpl;
import vn.iotstar.Service.Impl.ProductServiceImpl;
import vn.iotstar.UrlUtil.UrlEncoded;

@WebServlet(urlPatterns = { "/view/client/product-detail" })
public class ProductDetailController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ICategoryService cateService = new CategoryServiceImpl();
    private IProductService productService = new ProductServiceImpl();
    private DecimalFormat df = new DecimalFormat("#.000");

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String id = req.getParameter("id");
        if (!isInteger(id) || !UrlEncoded.isSafeParameter(id)) {
            resp.sendRedirect("/DoAnLTWeb");
            return;
        }

        File file = new File(id);
        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            resp.sendRedirect("/DoAnLTWeb");
            return;
        }

        if (!canonicalPath.equals(file.getAbsolutePath())) {
            resp.sendRedirect("/DoAnLTWeb");
            return;
        }

        int productId = Integer.parseInt(id);
        Product detailProduct = productService.get(productId);
        if (detailProduct == null) {
            resp.sendRedirect("/DoAnLTWeb");
            return;
        }
        req.setAttribute("detail_product", detailProduct);

        List<Category> nameCateOfProduct = cateService.getCateByProduct(productId);
        if (nameCateOfProduct == null) {
            resp.sendRedirect("/DoAnLTWeb");
            return;
        }
        req.setAttribute("name_cate_of_product", nameCateOfProduct);

        Category idCate = detailProduct.getCategory_id();

        List<Product> productListCate = productService.getProductById(idCate.getId());

        req.setAttribute("productById", productListCate);

        List<Product> productList = productService.findAll();
        req.setAttribute("productlist", productList);

        List<Product> productsList1 = new ArrayList<Product>();
        for (Product product : productList) {
            Product product1 = productService.get(product.getId());
            product1.setPrice(String.valueOf(df.format(Double.parseDouble(product.getPrice()) * (1 - (product.getDiscount() / 100)))));
            productsList1.add(product1);
        }

        req.setAttribute("productlist1", productsList1);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/client/product-detail.jsp");
        dispatcher.forward(req, resp);
    }
}
