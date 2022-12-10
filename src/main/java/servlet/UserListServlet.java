package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.DiseaseItemLogic;
import logic.UserLogic;
import model.DiseaseItemModel;
import model.UserModel;
import settings.PageSettings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/UserList")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserListServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// Userを取得する。
			UserLogic logic;
			logic = new UserLogic();

			// Userリストを取得してリクエストスコープに保存
			List<UserModel> userList = logic.find();
			request.setAttribute("userList", userList);

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

				if (searchKind.equals("病名検索")) {
					// Diseaseリストを取得する。
					DiseaseItemLogic logic_disease;
					logic_disease = new DiseaseItemLogic();
					// キーワードのDiseaseリストを取得してリクエストスコープに保存
					List<DiseaseItemModel> diseaseList = logic_disease.find(key);
					request.setAttribute("diseaseList", diseaseList);

					// AdminMainページへフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
					dispatcher.forward(request, response);
				} else { // ユーザー検索
					// Userリストを取得してリクエストスコープに保存
					userList = logic.find(key);
					request.setAttribute("userList", userList);

					// ユーザー一覧ページへフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
					dispatcher.forward(request, response);
				}

			} else {

				// 検索キーワードがない場合。
				try {
					if (searchKind.equals("病名検索")) {
						// Diseaseリストを取得する。
						DiseaseItemLogic logic_disease;
						logic_disease = new DiseaseItemLogic();
						// キーワードのDiseaseリストを取得してリクエストスコープに保存
						List<DiseaseItemModel> diseaseList = logic_disease.find();
						request.setAttribute("diseaseList", diseaseList);

						// AdminMainページへフォワード
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
						dispatcher.forward(request, response);
					} else { // ユーザー検索
						// Userリストを取得してリクエストスコープに保存
						userList = logic.find();
						request.setAttribute("userList", userList);

						// ユーザー一覧ページへフォワード
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
						dispatcher.forward(request, response);
					}
				} catch (Exception e) {
					//searchKindがnullのとき

					// Userリストを取得してリクエストスコープに保存
					userList = logic.find();
					request.setAttribute("userList", userList);

					// ユーザー一覧ページへフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
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
