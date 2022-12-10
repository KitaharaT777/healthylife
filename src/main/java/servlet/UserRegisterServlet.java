package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.UserLogic;
import model.UserModel;
import settings.DatabaseSettings;
import settings.MessageSettings;
import settings.PageSettings;
import validation.UserValidation;

/**
 * Servlet implementation class Login
 */
@WebServlet("/UserRegister")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		
		// AddUser用
		// ユーザーIDは登録時は非表示
		String usermail = request.getParameter("usermail");
		String pass = request.getParameter("pass");
		String birthday = request.getParameter("birthday");
		int sex = Integer.parseInt(request.getParameter("sex"));

		try {
			// バリデーションチェックを行う。
			UserValidation validate = new UserValidation(request);
			Map<String, String> errors = validate.validate();

			// バリデーションエラーがあった時
			if (validate.hasErrors()) {
				System.out.println("UserRegisterValidationError");
				request.setAttribute("errors", errors);

				// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
				Map<String, String> user = new HashMap<String, String>();
				user.put("usermail", usermail);
				user.put("pass", pass);
				user.put("birthday", birthday);
				request.setAttribute("user", user);

				// ユーザー登録ページへフォワードして終了する。
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// リクエストパラメータをユーザーモデルに設定する。
			UserModel user = new UserModel();
			user.setEmail(usermail);
			user.setPass(pass);
			user.setBirthday(Date.valueOf(birthday));
			user.setSex(sex);
			user.setCountry("JP");
			user.setIsAISearch(0);
			user.setIsWithdrawaled(0);
			user.setIsDeleted(0);

			// ユーザーを登録する。
			UserLogic logic;
			logic = new UserLogic();

			// maxIDを取得
			int maxId = logic.maxID();
			user.setUserId(maxId + 1); //UserIDは規則的に付与したい
			
			int ret = logic.create(user);

			// 実行結果により処理を切り替える。
			switch (ret) {
			case DatabaseSettings.DB_EXECUTION_SUCCESS:
				// データベース操作成功のとき、ログインページへリダイレクトして終了する。
				response.sendRedirect(request.getContextPath() + "/Login");
				return;
			case DatabaseSettings.DB_EXECUTION_FAILURE_ERR_DUP_KEYNAME:
				// ユニークKEYが重複（メールアドレスが重複）しいているとき、エラーメッセージをリクエストスコープに保存する。
				request.setAttribute("db_error", String.format(MessageSettings.MSG_ER_DUP_KEYNAME, user.getEmail()));
				break;
			default:
				// その他エラーのとき、エラーメッセージをリクエストスコープに保存する。
				request.setAttribute("db_error", MessageSettings.MSG_ERROR_OCCURRED);
				break;
			}

			// リクエストスコープにユーザーモデルを保存する。
			request.setAttribute("user", user);

			// ユーザー登録ページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
			dispatcher.forward(request, response);

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
