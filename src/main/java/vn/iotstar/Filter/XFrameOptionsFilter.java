package vn.iotstar.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class XFrameOptionsFilter implements Filter{
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo Filter
    }
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("X-Frame-Options", "DENY");
        chain.doFilter(request, response);
	}

	@Override
    public void destroy() {
        // Hủy Filter
    }
}