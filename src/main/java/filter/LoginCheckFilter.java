package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import settings.PageSettings;

/**
 * ログインチェックを行うフィルタ。
 */
@WebFilter(filterName = "LoginCheckFilter") // フィルタを実行するURLは/WEB-INF/web.xmlで指定する。
public class LoginCheckFilter implements Filter {

	/**
	 * ログインチェックを行う。
	 * セッションにユーザー情報が登録されているかを確認して、登録されていなければログイン画面にリダイレクトする。
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ServletRequestクラスオブジェクトではgetSession()メソッド、getContextPath()メソッドが使えないので、
		// HttpServletRequestクラスオブジェクトにキャストする。
		HttpServletRequest req = (HttpServletRequest) request;

		// ServletResponseクラスオブジェクトではsendRedirect()メソッドが使えないので、
		// HttpServletResponseクラスオブジェクトにキャストする。
		HttpServletResponse res = (HttpServletResponse) response;

		// セッションスコープを取得する。なければ作成する。
		HttpSession session = req.getSession(true);

		// セッションスコープに保存されているユーザー情報がないとき
		if (session.getAttribute("loginUser") == null) {
			// ログインページにリダイレクトする。
			res.sendRedirect(req.getContextPath() + PageSettings.LOGIN_SERVLET);

			// ここでreturnしないと「レスポンスをコミットした後でフォワードできません」と例外が発生する。
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
}
