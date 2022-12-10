package servlet;

import java.io.IOException;
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

import logic.DiseaseItemLogic;
import logic.UserLogic;
import model.DiseaseItemModel;
import model.UserModel;
import settings.PageSettings;
import validation.SearchValidation;

/**
 * Servlet implementation class Login
 */
@WebServlet("/AdminMain")
public class AdminMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminMainServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// インポート/エクスポートメッセージの取得
			HttpSession session = request.getSession();
			session.getAttribute("msg_data");
			//String msg_data = (String)session.getAttribute("msg_data");
			//System.out.println("msg_data: " +msg_data);
			
			// Diseaseリストを取得する。
			DiseaseItemLogic logic;
			logic = new DiseaseItemLogic();

			// Diseaseリストを取得してリクエストスコープに保存
			List<DiseaseItemModel> diseaseList = logic.find();
			request.setAttribute("diseaseList", diseaseList);

			// リクエストパラメータを取得
			request.setCharacterEncoding("UTF-8");

			String key = request.getParameter("key");
			// 検索テキストボックス表示用
			request.setAttribute("key", request.getParameter("key"));

			String searchKind = request.getParameter("search");

			if (request.getParameter("key") != null && key.length() != 0) {
				// 検索キーワードがある場合。
				// GETパラメータで日本語を受け取ると文字化けするので、server.xmlに下記を追記する。
				// useBodyEncodingForURI="true"

				// バリデーションチェックを行う。(検索ワード)
				SearchValidation validate = new SearchValidation(request);
				Map<String, String> errors = validate.validate();

				// バリデーションエラーがあった時
				if (validate.hasErrors()) {
					request.setAttribute("errors", errors);

					// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
					Map<String, String> keyword = new HashMap<String, String>();
					keyword.put("key", key);
					request.setAttribute("key", keyword);

					if (searchKind.equals("病名検索")) {
						// キーワードのDiseaseリストを取得してリクエストスコープに保存
						diseaseList = logic.find();
						request.setAttribute("diseaseList", diseaseList);

						// AdminMainページへフォワード
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
						dispatcher.forward(request, response);

						return;
					} else { // ユーザー検索
						// Userを取得する。
						UserLogic logic_user;
						logic_user = new UserLogic();
						//Userリストを取得してリクエストスコープに保存
						List<UserModel> userList = logic_user.find();
						request.setAttribute("userList", userList);

						// ユーザー一覧ページへフォワード
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
						dispatcher.forward(request, response);

						return;
					}
				}

				if (searchKind.equals("病名検索")) {
					// キーワードのDiseaseリストを取得してリクエストスコープに保存
					diseaseList = logic.find(key);
					request.setAttribute("diseaseList", diseaseList);

					// AdminMainページへフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
					dispatcher.forward(request, response);
				} else { //ユーザー検索
					// Userを取得する。
					UserLogic logic_user;
					logic_user = new UserLogic();
					// Userリストを取得してリクエストスコープに保存
					List<UserModel> userList = logic_user.find(key);
					request.setAttribute("userList", userList);

					// ユーザー一覧ページへフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				// 検索キーワードがない場合。

				try {
					if (searchKind.equals("病名検索")) {
						// Diseaseリストを取得してリクエストスコープに保存
						diseaseList = logic.find();
						request.setAttribute("diseaseList", diseaseList);

						// メイン画面にフォワード
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
						dispatcher.forward(request, response);
					} else { //ユーザー検索
						// Userを取得する。
						UserLogic logic_user;
						logic_user = new UserLogic();
						// Userリストを取得してリクエストスコープに保存
						List<UserModel> userList = logic_user.find();
						request.setAttribute("userList", userList);

						// ユーザー一覧ページへフォワード
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
						dispatcher.forward(request, response);
					}
				} catch (Exception e) {
					// searchKindがnullのとき

					// Diseaseリストを取得してリクエストスコープに保存
					diseaseList = logic.find();
					request.setAttribute("diseaseList", diseaseList);

					// メイン画面にフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
					dispatcher.forward(request, response);
					
				}
			}

			return;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// Diseaseリストを取得する。
			DiseaseItemLogic logic;
			logic = new DiseaseItemLogic();

			// Diseaseリストを取得してリクエストスコープに保存
			List<DiseaseItemModel> diseaseList = logic.find();
			request.setAttribute("diseaseList", diseaseList);

			// リクエストパラメータを取得
			request.setCharacterEncoding("UTF-8");

			// メイン画面にリダイレクト
			response.sendRedirect("/WEB-INF/jsp/adminMain.jsp");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}
	}
}
