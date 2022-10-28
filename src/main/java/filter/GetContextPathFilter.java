package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * コンテキストのパスを取得するためのフィルタ。
 */
@WebFilter(filterName = "GetContextPathFilter") // フィルタを実行するURLは/WEB-INF/web.xmlで指定する。
public class GetContextPathFilter implements Filter {

	/**
	 * コンテキストのパスを取得します。
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// コンテキストパスを取得し、JSPで${root_path}でアクセスできるようにする。
		request.setAttribute("root_path", ((HttpServletRequest) request).getContextPath());
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
}
