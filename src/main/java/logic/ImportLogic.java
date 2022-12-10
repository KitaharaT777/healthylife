package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import model.DiseaseItemModel;

/**
 * ユーザーロジッククラス
 */
public class ImportLogic {

	/**
	 * 指定ファイルをインポートします。
	 *
	 * @param args ファイル内の文字列
	 * @return 病名アイテムモデルのArrayList
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 */

	public List<DiseaseItemModel> readExcel(String[] args) throws EncryptedDocumentException, IOException {
		// レコードを格納するArrayListを生成する。
		List<DiseaseItemModel> list = new ArrayList<DiseaseItemModel>();

		try {
			// Excelファイルへアクセス
			Workbook excel = WorkbookFactory.create(new File("C:\\Files\\Import.xlsx")); //ファイル名を固定している

			// シート名を取得
			Sheet sheet = excel.getSheet("Sheet1");

			//シート内の最大行を取得（1行少ない？）
			int rowMaxA = sheet.getLastRowNum();

			for (int i = 1; i <= rowMaxA; i++) {
				// 1行目から取得（0からスタートで0行目は項目名の行）
				Row row = sheet.getRow(i);

				// エクスポートしたデータの並び順
				// 0番目のセルの値を取得 id 
				// idは自動採番なので含めない

				// 1番目のセルの値を取得 name_id
				Cell cell_nameId = row.getCell(1);
				// 2番目のセルの値を取得 name_of_disease
				Cell cell_nameOfDisease = row.getCell(2);
				// 3番目のセルの値を取得 information
				Cell cell_info = row.getCell(3);
				// 4番目のセルの値を取得 link
				Cell cell_link = row.getCell(4);
				// 5番目のセルの値を取得 is_deleted
				Cell cell_isDeleted = row.getCell(5);

				// セルの値を取得
				int nameId = Integer.parseInt(cell_nameId.getStringCellValue());
				String nameOfDisease = cell_nameOfDisease.getStringCellValue();
				String info = cell_info.getStringCellValue();
				String link = cell_link.getStringCellValue();
				int isDeleted = Integer.parseInt(cell_isDeleted.getStringCellValue());

				//インポートしたセルの値をDiseaseItemモデルに設定する。
				DiseaseItemModel importDiseaseItem = new DiseaseItemModel();
				importDiseaseItem.setNameId(nameId);
				importDiseaseItem.setNameOfDisease(nameOfDisease);
				importDiseaseItem.setInfo(info);
				importDiseaseItem.setLink(link);
				importDiseaseItem.setIsDeleted(isDeleted);

				// ArrayListにレコードを追加する。
				// 今はセルがnullの場合、エラーになるので注意
				list.add(importDiseaseItem);
			}
			
			excel.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 指定ファイルをインポートします。（インポートデータをUpdateする場合の課題確認用）
	 *
	 * @param args ファイル内の文字列
	 * @return 結果（true:成功、false:失敗）
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 */
	
	public boolean readExcelBoolean(String[] args) throws EncryptedDocumentException, IOException {

		try {
			// Excelファイルへアクセス
			Workbook excel = WorkbookFactory.create(new File("C:\\Files\\Input.xlsx"));
			//System.out.println("File ACCESS");

			// シート名を取得
			Sheet sheet = excel.getSheet("Sheet1");
			//System.out.println("Sheet ACCESS");

			//シート内の最大行を取得（1行少ない？）
			int rowMaxA = sheet.getLastRowNum();
			System.out.println("SheetLow: " + rowMaxA);

			// 1行目を取得（0からスタートで0行目は項目名）
			Row row = sheet.getRow(1);
			//System.out.println("Row ACCESS");

			// 0番目のセルの値を取得 id
			//Cell cell_id = row.getCell(0);
			// 1番目のセルの値を取得 name_id
			Cell cell_nameId = row.getCell(1);
			// 2番目のセルの値を取得 name_of_disease
			Cell cell_nameOfDisease = row.getCell(2);
			// 3番目のセルの値を取得 information
			Cell cell_info = row.getCell(3);
			// 4番目のセルの値を取得 link
			Cell cell_link = row.getCell(4);
			// 5番目のセルの値を取得 is_deleted
			Cell cell_isDeleted = row.getCell(5);

			// セルの値を文字列として取得
			int nameId = Integer.parseInt(cell_nameId.getStringCellValue());
			String nameOfDisease = cell_nameOfDisease.getStringCellValue();
			String info = cell_info.getStringCellValue();
			String link = cell_link.getStringCellValue();
			int isDeleted = Integer.parseInt(cell_isDeleted.getStringCellValue());

			// インポートした情報をDiseaseItemモデルに設定する。
			DiseaseItemModel importDiseaseItem = new DiseaseItemModel();
			importDiseaseItem.setNameId(nameId);
			importDiseaseItem.setNameOfDisease(nameOfDisease);
			importDiseaseItem.setInfo(info);
			importDiseaseItem.setLink(link);
			importDiseaseItem.setIsDeleted(isDeleted);

			// DiseaseItemモデルをリストにする。
			// レコードを格納するArrayListを生成する。
			List<DiseaseItemModel> list = new ArrayList<DiseaseItemModel>();

			// ArrayListにレコードを追加する。
			list.add(importDiseaseItem);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
