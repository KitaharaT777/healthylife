package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.DiseaseItemLogic;
import model.DiseaseItemModel;
import model.UserModel;
import settings.PageSettings;
import validation.DiseaseValidation;

/**
 * Servlet implementation class Login
 */
@WebServlet("/DiseaseUpdate")
public class DiseaseUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// 指定IDの病名情報リストを取得する。
			DiseaseItemLogic logic;
			logic = new DiseaseItemLogic();

			// セッションスコープに保存されたユーザー情報を取得する。
			HttpSession session = request.getSession();
			UserModel user = (UserModel) session.getAttribute("loginUser");

			// 指定ID、指定ユーザーIDの病名情報リストを取得する。
			DiseaseItemModel model = logic.find(Integer.parseInt(request.getParameter("id")), user.getId());

			if (model == null) {
				// 病名情報リストを取得できなかったら、Mainにリダイレクトする。
				response.sendRedirect(request.getContextPath() + "/AdminMain");
				return;
			}

			// 更新画面にフォワード
			request.setAttribute("DiseaseItem", model);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diseaseUpdate.jsp");
			dispatcher.forward(request, response);

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
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");

		// Update用
		String id_str = request.getParameter("id");
		int id = Integer.parseInt(request.getParameter("id"));
		String name_id_str = request.getParameter("name_id");
		String name_of_disease = request.getParameter("name_of_disease");
		String information = request.getParameter("information");
		String link = request.getParameter("link");

		String del = request.getParameter("delete");
		if (del == null) {
			del = "0";
		}
		int delint = Integer.parseInt(del);

		try {
			// バリデーションチェックを行う。
			DiseaseValidation validate = new DiseaseValidation(request);
			Map<String, String> errors = validate.validate();

			// バリデーションエラーがあった時
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);

				// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
				Map<String, String> diseaseItem = new HashMap<String, String>();
				diseaseItem.put("id", id_str);
				diseaseItem.put("nameId", name_id_str);
				diseaseItem.put("nameOfDisease", name_of_disease);
				diseaseItem.put("info", information);
				diseaseItem.put("link", link);
				diseaseItem.put("delete", del);
				request.setAttribute("DiseaseItem", diseaseItem);

				// 病名更新ページへフォワードして終了する。
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diseaseUpdate.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// データの更新用にname_idを変換
			int name_id = Integer.parseInt(request.getParameter("name_id"));

			// リクエストパラメータをDiseaseItemモデルに設定する。
			DiseaseItemModel diseaseItem = new DiseaseItemModel();
			diseaseItem.setId(id);
			diseaseItem.setNameId(name_id);
			diseaseItem.setNameOfDisease(name_of_disease);
			diseaseItem.setInfo(information);
			diseaseItem.setLink(link);
			diseaseItem.setIsDeleted(delint);

			// DiseaseItemを更新する
			DiseaseItemLogic logic;
			logic = new DiseaseItemLogic();

			if (!logic.update(diseaseItem)) {
				// エラーがあったときは、adminMainへフォワードする
				request.setAttribute("diseaseItem", diseaseItem);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// AdminMainへリダイレクト
			response.sendRedirect(request.getContextPath() + "/AdminMain");

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
