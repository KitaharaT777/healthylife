package servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import logic.LoginLogic;
import model.Login;
import model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ログインページにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);

		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");

		// ログイン処理の実行
		Login login = new Login(email, pass);
		LoginLogic bo = new LoginLogic();
		boolean result = bo.execute(login);

		// ログイン処理の成否によって処理を分岐
		if (result) {
			// ログイン成功
			UserDAO dao = new UserDAO();
			UserModel account = dao.findByLogin(login);
			int id = account.getId();
			int userId = account.getUserId();
			Date birthday = account.getBirthday();
			int sex = account.getSex();
			String country = account.getCountry();

			int loginflg = 1; //ログイン成功時
			UserModel loginUser = new UserModel(id, userId, email, pass, birthday, sex, country, loginflg);

			// セッションスコープにユーザーIDを保存
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);

			// ログアウトメッセージをリクエストスコープに保存
			HttpSession session3 = request.getSession();
			session3.setAttribute("msg", "ログアウトしました");

			if (userId == 1) {
				// インポート/エクスポートメッセージの初期化
				//Map<String, String> msg = new HashMap<String, String>();
				//msg.put("importData", "test");
				HttpSession session2 = request.getSession();
				session2.setAttribute("msg_data", null);
				//System.out.println("msg: "+msg);
				//request.setAttribute("msg_data", msg);
				//System.out.println("msg(): "+(String)request.getAttribute("msg_data"));
				
				// AdminMainへリダイレクトする。
				response.sendRedirect(request.getContextPath() + "/AdminMain");
			} else {
				// Mainへリダイレクトする。
				response.sendRedirect(request.getContextPath() + "/Main");
			}

		} else { // ログイン失敗
			// メッセージ
			HttpSession session3 = request.getSession();
			session3.setAttribute("msg", "E-mailアドレス、または、パスワードが間違っています。");

			int loginerrflg = 1; //ログイン失敗時
			UserModel loginerrUser = new UserModel(email, pass, loginerrflg);

			// セッションスコープにユーザーIDを保存
			HttpSession session = request.getSession();
			session.setAttribute("loginerrUser", loginerrUser);

			// ログインページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}

		return;

	}
}
