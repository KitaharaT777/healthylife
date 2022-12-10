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
import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;

import logic.DiseaseItemLogic;
import logic.ImportLogic;
import model.DiseaseItemModel;
import settings.MessageSettings;
import settings.PageSettings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Import")
public class ImportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, EncryptedDocumentException, IOException {
		try {
			String[] args = null;

			// インポート処理/////////////////////////////////////////
			ImportLogic logic_import; //インポートするファイル名の設定も書いている
			logic_import = new ImportLogic();

			// readExcelのList型の場合
			List<DiseaseItemModel> importDiseaseItemList = logic_import.readExcel(args);
			request.setAttribute("importDiseaseItemList", importDiseaseItemList);

			// readExcelのboolean型の場合////////////////////////////////
			// インポートデータをUpdateする場合の課題があるので、この方法を残しておく
			/*
			if (!logic_import.readExcelBoolean(args)) {
				// エラーがあったときは、AdminMainへフォワードする
				
				// Diseaseリストを取得する。
				DiseaseItemLogic logic;
				logic = new DiseaseItemLogic();
				
				//Diseaseリストを取得してリクエストスコープに保存
				List<DiseaseItemModel> diseaseList = logic.find();
				request.setAttribute("diseaseList", diseaseList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
				dispatcher.forward(request, response);
			
				return;
			}*///////////////////////////////////////////////////////////

			// インポートした情報をリストにする。DiseaseItemモデルに設定する。
			DiseaseItemModel diseaseItem = new DiseaseItemModel();

			// インポートしたDiseaseItemリストをname_of_diseaseテーブルに追加する
			DiseaseItemLogic logic;
			logic = new DiseaseItemLogic();

			int cnt=0;
			for (DiseaseItemModel saveItem : importDiseaseItemList) {
				//DiseaseItemModelの変数をprivateでなく、publicにしないとエラーになる
				int nameId = saveItem.nameId;
				String nameOfDisease = saveItem.nameOfDisease;
				String info = saveItem.info;
				String link = saveItem.link;
				int isDeleted = saveItem.isDeleted;

				diseaseItem.setNameId(nameId);
				diseaseItem.setNameOfDisease(nameOfDisease);
				diseaseItem.setInfo(info);
				diseaseItem.setLink(link);
				diseaseItem.setIsDeleted(isDeleted);

				// Insertのみ（ひとまず同じname_idがあってもUpdateしない）
				logic.create(diseaseItem);
				
				// DBにcreateした数をカウントする
				cnt++;
			}

			System.out.println("cnt: " + cnt);
			
			// JSPのインポートメッセージ表示に使うためにリクエストパラメータをMapに保存する。
			//Map<String, String> msg = new HashMap<String, String>();
			//msg.put("import", MessageSettings.MSG_IMPORT);
			// インポート/エクスポートメッセージの設定
			HttpSession session = request.getSession();
			session.removeAttribute("msg_data");
			String msg_data = (String)session.getAttribute("msg_data");
			System.out.println("msg_data(Imp): " +msg_data);
			//session.setAttribute("msg_data", "インポートしました");
			session.setAttribute("msg_data", cnt+MessageSettings.MSG_IMPORT);
			/////////////////////////////////////////////////////////

			// 追加されたDiseaseリストを取得してリクエストスコープに保存
			List<DiseaseItemModel> diseaseList = logic.find();
			request.setAttribute("diseaseList", diseaseList);

			// リクエストパラメータを取得
			request.setCharacterEncoding("UTF-8");

			// メイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
			dispatcher.forward(request, response);
			
			// メッセージセッションの削除
			session.removeAttribute("msg_data");

			return;

		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
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

			// adminMain画面にリダイレクト
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
