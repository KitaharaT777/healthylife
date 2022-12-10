package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.SearchItemLogic;
import logic.UserLogic;
import model.SearchItemModel;
import model.UserModel;
import settings.PageSettings;
import validation.UserValidation;

/**
 * Servlet implementation class Login
 */
@WebServlet("/UserUpdate")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");

		// セッションスコープからインスタンスを取得
		HttpSession session = request.getSession();
		UserModel loginUser = (UserModel) session.getAttribute("loginUser");
		int user_id = loginUser.getUserId();

		if (user_id != 1) {
			// 更新画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
			dispatcher.forward(request, response);
		} else {
			// 管理者IDの場合
			try {
				// 指定IDのユーザー情報を取得する。
				UserLogic logic;
				logic = new UserLogic();

				String id = request.getParameter("id");

				if (id == null) {
					// 更新画面にフォワード
					request.setAttribute("userItem", loginUser);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
					dispatcher.forward(request, response);

					return;
				} else {

					// 指定ユーザーIDのユーザー情報を取得する。
					UserModel model = logic.find(Integer.parseInt(request.getParameter("id")));

					if (model == null) {
						// ユーザーリストを取得できなかったら、Mainにリダイレクトする。
						response.sendRedirect(request.getContextPath() + "/AdminMain");
						return;
					}

					// 更新画面にフォワード
					request.setAttribute("userItem", model);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
					dispatcher.forward(request, response);

					return;
				}

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();

				// エラーページへフォワードする。
				RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
				dispatcher.forward(request, response);

				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");

		// セッションスコープからインスタンスを取得
		HttpSession session = request.getSession();
		UserModel loginUser = (UserModel) session.getAttribute("loginUser");

		// リクエストパラメータ
		int id = loginUser.getId();
		String userId_str = request.getParameter("userId");
		String email = request.getParameter("usermail");
		String pass = request.getParameter("pass");
		String birthday = request.getParameter("birthday");
		int sex = Integer.parseInt(request.getParameter("sex"));
		String sex_str = request.getParameter("sex");

		try {
			// バリデーションチェックを行う。
			UserValidation validate = new UserValidation(request);
			Map<String, String> errors = validate.validate();

			// バリデーションエラーがあった時
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);

				// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
				Map<String, String> user = new HashMap<String, String>();
				user.put("userId", userId_str);
				user.put("email", email);
				user.put("pass", pass);
				user.put("birthday", birthday);
				user.put("sex", sex_str);
				if (id == 1) {
					request.setAttribute("userItem", user);
				} else {
					request.setAttribute("loginUser", user);
				}

				// ユーザー登録ページへフォワードして終了する。
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
				dispatcher.forward(request, response);

				return;
			}
			// バリデーションチェック以降に変換しないとエラーになる
			int userId = Integer.parseInt(request.getParameter("userId"));

			// リクエストパラメータをユーザーモデルに設定する。
			UserModel user = new UserModel();
			user.setId(id);
			user.setUserId(userId); //UserIDは規則的に付与したい
			user.setEmail(email);
			user.setPass(pass);
			user.setBirthday(Date.valueOf(birthday));
			user.setSex(sex);
			user.setCountry("JP");
			user.setIsAISearch(0);
			user.setIsWithdrawaled(0);
			user.setIsDeleted(0);

			// ユーザーを更新する。
			UserLogic logic;
			logic = new UserLogic();

			if (!logic.update(user)) {
				// エラーがあったときは、Mainへフォワードする
				request.setAttribute("loginUser", user);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// 更新後のセッションスコープにユーザーIDを保存
			session.setAttribute("loginUser", user);

			// 検索履歴リストを取得してリクエストスコープに保存（追加）
			SearchItemLogic logicSearch;
			logicSearch = new SearchItemLogic();
			List<SearchItemModel> searchList = logicSearch.find(id);
			request.setAttribute("searchList", searchList);

			// メイン画面にリダイレクト
			response.sendRedirect(request.getContextPath() + "/Main");
			return;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}
	}
}
