package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.UserLogic;
import model.UserModel;
import settings.PageSettings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/UserWithdraw")
public class UserWithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userWithdraw.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");

		// セッションスコープからインスタンスを取得
		HttpSession session = request.getSession();
		UserModel loginUser = (UserModel) session.getAttribute("loginUser");

		int is_withdrawaled = Integer.parseInt(request.getParameter("is_withdrawaled"));

		// リクエストパラメータ
		int id = loginUser.getId();

		if (is_withdrawaled == 0) {

			try {
				// リクエストパラメータをユーザーモデルに設定する。
				UserModel user = new UserModel();
				user.setId(id);
				user.setIsWithdrawaled(1);

				// ユーザーを更新する。（退会フラグ）
				UserLogic logic;
				logic = new UserLogic();

				if (!logic.updateIsWithdrawaled(user)) {
					// エラーがあったときは、Mainへフォワードする
					request.setAttribute("loginUser", user);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
					dispatcher.forward(request, response);

					return;
				}
				
				// 退会メッセージをリクエストスコープに保存
				HttpSession session3 = request.getSession();
				session3.setAttribute("msg", "退会処理が完了しました");

				// ログイン画面にリダイレクト
				response.sendRedirect(request.getContextPath() + "/Login");
				return;

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();

				// エラーページへフォワードする。
				RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
				dispatcher.forward(request, response);

				return;
			}
		} else {
			// メイン画面にリダイレクト
			response.sendRedirect(request.getContextPath() + "/Main");
			return;
		}
	}
}
