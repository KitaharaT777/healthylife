package servlet;

import java.io.FileOutputStream;
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

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import logic.DiseaseItemLogic;
import model.DiseaseItemModel;
import settings.MessageSettings;
import settings.PageSettings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Export")
public class ExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

			try {
				// エクスポート用の既存のエクセルを開きます。
				FileOutputStream fo = new FileOutputStream("C:\\Files\\Export.xlsx");
				// 既存のワークブックを開きます。
				XSSFWorkbook bookR = new XSSFWorkbook();
				// 作成するワークシートの名前を付ける
				XSSFSheet sheetR = bookR.createSheet("病名情報");

				// diseaseListをExportDataとして配列順を固定する
				int cnt = 0;
				int ColSize = 8; //name_of_diseaseテーブルのCol数
				int RowSize = diseaseList.size() + 1; //タイトル行を追加

				String[][] exportData = null;
				exportData = new String[ColSize][RowSize];

				// Export1行目
				exportData[0][0] = "id";
				exportData[1][0] = "name_id";
				exportData[2][0] = "name_of_disease";
				exportData[3][0] = "information";
				exportData[4][0] = "link";
				exportData[5][0] = "is_deleted";
				exportData[6][0] = "create_date_time";
				exportData[7][0] = "update_date_time";

				for (int i = 1; i < RowSize; i++) {
					cnt = 1;
					for (DiseaseItemModel exportItem : diseaseList) {
						if (i == cnt) {
							// DiseaseItemModelの変数をpublicにしないとエラーになる
							exportData[0][i] = String.valueOf(exportItem.id);
							exportData[1][i] = String.valueOf(exportItem.nameId);
							exportData[2][i] = String.valueOf(exportItem.nameOfDisease);
							exportData[3][i] = String.valueOf(exportItem.info);
							exportData[4][i] = String.valueOf(exportItem.link);
							exportData[5][i] = String.valueOf(exportItem.isDeleted);
							exportData[6][i] = String.valueOf(exportItem.createdAt);
							exportData[7][i] = String.valueOf(exportItem.updatedAt);
						}
						cnt++;
					}
				}

				// エクセル上でのデータ出力用の行とセルの作成
				XSSFRow rowR = sheetR.createRow((short) 0);
				XSSFCell cellR;

				for (int i = 0; i < RowSize; i++) {
					rowR = sheetR.createRow((short) i);
					for (int j = 0; j < ColSize; j++) {
						rowR.createCell((short) j);
					}
				}

				// 数値をエクセルへの書き込み(name_id順)
				for (int i = 0; i < ColSize; i++) {
					for (int j = 0; j < RowSize; j++) {
						rowR = sheetR.getRow(j);
						cellR = rowR.getCell(i);
						cellR.setCellValue(exportData[i][j]);
					}
				}

				bookR.write(fo);
				bookR.close();
				fo.close();
				
				// JSPのエクスポートメッセージ表示に使うためにリクエストパラメータをMapに保存する。
				// インポート/エクスポートメッセージの設定
				HttpSession session = request.getSession();
				session.removeAttribute("msg_data");
				String msg_data = (String)session.getAttribute("msg_data");
				System.out.println("msg_data(Exp): " +msg_data);
				session.setAttribute("msg_data", diseaseList.size()+MessageSettings.MSG_EXPORT);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			// メイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
			dispatcher.forward(request, response);

			// メッセージセッションの削除
			HttpSession session = request.getSession();
			session.removeAttribute("msg_data");
			
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