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
import logic.SearchItemLogic;
import model.DiseaseItemModel;
import model.SearchItemModel;
import model.UserModel;
import settings.PageSettings;
import validation.SearchValidation;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 検索履歴リストを取得する。
			SearchItemLogic logic;
			logic = new SearchItemLogic();

			// セッションスコープからインスタンスを取得
			HttpSession session = request.getSession();
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			int user_id = loginUser.getUserId();

			// 検索履歴リストを取得してリクエストスコープに保存
			List<SearchItemModel> searchList = null;

			//リクエストパラメータを取得
			request.setCharacterEncoding("UTF-8");

			String key = request.getParameter("key");

			if (request.getParameter("key") != null && key.length() != 0) {
				// 検索キーワードがある場合。
				// GETパラメータで日本語を受け取ると文字化けするので、server.xmlに下記を追記する。
				// useBodyEncodingForURI="true"

				// 検索テキストボックス表示用
				request.setAttribute("key", request.getParameter("key"));

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

					// メインページ表示用のリストを取得
					searchList = logic.find(user_id);
					request.setAttribute("searchList", searchList);

					// Diseaseリストを取得する。
					DiseaseItemLogic logic_disease;
					logic_disease = new DiseaseItemLogic();
					List<DiseaseItemModel> diseaseList = logic_disease.find();
					request.setAttribute("diseaseList", diseaseList);

					// ユーザーメインページへフォワードして終了する。
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
					dispatcher.forward(request, response);

					return;
				}

				// resultページへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/Result");
				dispatcher.forward(request, response);
			} else {
				// 検索テキストボックス表示用
				request.setAttribute("key", request.getParameter("key"));

				// 検索キーワードがない場合。
				searchList = logic.find(user_id);
				request.setAttribute("searchList", searchList);

				// Diseaseリストを取得する。
				DiseaseItemLogic logic_disease;
				logic_disease = new DiseaseItemLogic();
				List<DiseaseItemModel> diseaseList = logic_disease.find();
				request.setAttribute("diseaseList", diseaseList);

				// メイン画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
				dispatcher.forward(request, response);
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

			// 検索履歴リストを取得する。
			SearchItemLogic logic;
			logic = new SearchItemLogic();

			// セッションスコープからインスタンスを取得
			HttpSession session = request.getSession();
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			int user_id = loginUser.getUserId();

			// リクエストパラメータを取得
			request.setCharacterEncoding("UTF-8");

			//「気になる」登録のチェックを取得
			String mark = request.getParameter("new_mark");

			if (mark == null) {
				System.out.println("NoMark");
			} else {
				//「気になる」症状結果の更新
				int cnt = logic.countAll();

				// 選択した検索履歴を削除する（markフラグを1に更新する）
				// リクエストパラメータを検索履歴Itemモデルに設定する。
				SearchItemModel searchItem = new SearchItemModel();
				searchItem.setId(cnt);

				if (!logic.updateMark(searchItem)) {
					// エラーがあったときは、Mainへフォワードする
					request.setAttribute("searchItem", searchItem);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
					dispatcher.forward(request, response);

					return;
				}
			}

			// 削除する履歴IDを取得
			String idno = request.getParameter("idno");
			if (idno == null) {
				System.out.println("NotDelete");
			} else {
				long searchresult_id = Integer.parseInt(idno);

				// 選択した検索履歴を削除する（is_Deletedフラグを1に更新する）
				// リクエストパラメータを検索履歴Itemモデルに設定する。
				SearchItemModel searchItem = new SearchItemModel();
				searchItem.setId(searchresult_id);

				if (!logic.update(searchItem)) {
					// エラーがあったときは、Mainへフォワードする
					request.setAttribute("searchItem", searchItem);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
					dispatcher.forward(request, response);

					return;
				}
			}

			// 検索履歴リストを取得してリクエストスコープに保存
			List<SearchItemModel> searchList = logic.find(user_id);
			request.setAttribute("searchList", searchList);

			// Diseaseリストを取得する。
			DiseaseItemLogic logic_disease;
			logic_disease = new DiseaseItemLogic();
			List<DiseaseItemModel> diseaseList = logic_disease.find();
			request.setAttribute("diseaseList", diseaseList);

			// メイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
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
