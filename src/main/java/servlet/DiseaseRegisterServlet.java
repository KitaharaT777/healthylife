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

import logic.DiseaseItemLogic;
import model.DiseaseItemModel;
import settings.PageSettings;
import validation.DiseaseValidation;

/**
 * Servlet implementation class Login
 */
@WebServlet("/DiseaseRegister")
public class DiseaseRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diseaseRegister.jsp");
		dispatcher.forward(request, response);

		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		
		// Add用
		String name_id_str = request.getParameter("name_id");
		String name_of_disease = request.getParameter("name_of_disease");
		String information = request.getParameter("information");
		String link = request.getParameter("link");
		String is_deleted = request.getParameter("is_deleted");
		if (is_deleted == null) {
			is_deleted = "0";
		}
		int isDeletedInt = Integer.parseInt(is_deleted);

		try {
			// バリデーションチェックを行う。
			DiseaseValidation validate = new DiseaseValidation(request);
			Map<String, String> errors = validate.validate();

			// バリデーションエラーがあった時
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);

				// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
				Map<String, String> diseaseItem = new HashMap<String, String>();
				diseaseItem.put("name_id", name_id_str);
				diseaseItem.put("name_of_disease", name_of_disease);
				diseaseItem.put("information", information);
				diseaseItem.put("link", link);
				diseaseItem.put("is_deleted", is_deleted);
				request.setAttribute("diseaseItem", diseaseItem);

				// 病名登録ページへフォワードして終了する。
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diseaseRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}
			// idを追加のために変換
			int name_id = Integer.parseInt(request.getParameter("name_id"));

			// DiseaseItemを追加
			// リクエストパラメータをDiseaseItemモデルに設定する。
			DiseaseItemModel disease = new DiseaseItemModel();
			disease.setNameId(name_id);
			disease.setNameOfDisease(name_of_disease);
			disease.setInfo(information);
			disease.setLink(link);
			disease.setIsDeleted(isDeletedInt);

			// DiseaseItemを登録する。
			DiseaseItemLogic logic;
			logic = new DiseaseItemLogic();
			logic.create(disease);

			// 管理者メイン画面にリダイレクト
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
