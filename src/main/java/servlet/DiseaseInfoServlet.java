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

import logic.DiseaseItemLogic;
import model.DiseaseItemModel;
import model.UserModel;
import settings.PageSettings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/DiseaseInfo")
public class DiseaseInfoServlet extends HttpServlet {
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
				response.sendRedirect(request.getContextPath() + "/Main");
				return;
			}

			// 病名情報画面にフォワード
			request.setAttribute("DiseaseItem", model);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diseaseInfo.jsp");
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
		try {
			// リクエストパラメータを取得
			request.setCharacterEncoding("UTF-8");

			int id = Integer.parseInt(request.getParameter("id")); //Diseaseのid取得
			int name_id = Integer.parseInt(request.getParameter("name_id"));
			String name_of_disease = request.getParameter("name_of_disease");
			String information = request.getParameter("information");
			String link = request.getParameter("link");

			String del = request.getParameter("delete");
			if (del == null) {
				del = "0";
			}
			int delint = Integer.parseInt(del);

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
				// エラーがあったときは、Mainへフォワードする
				request.setAttribute("diseaseItem", diseaseItem);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// Mainへリダイレクト
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
