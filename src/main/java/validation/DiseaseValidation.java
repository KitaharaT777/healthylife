package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;

/**
 * ユーザー登録・更新バリデーションクラス
 */
public class DiseaseValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request リクエストオブジェクト
	 */
	public DiseaseValidation(HttpServletRequest request) {
		super(request);
	}

	/**
	 * バリデーションを行います。
	 * @return バリデーションエラーのMap<項目名, エラーメッセージ>
	 */
	public Map<String, String> validate(){
		// 病名のバリデーション
		if (!ValidationUtil.isMinLength(this.request.getParameter("name_of_disease"), 1)) {
			this.errors.put("name_of_disease", String.format(MessageSettings.MSG_REQUIRED, "病名"));
		}

		if (!ValidationUtil.isMaxLength(this.request.getParameter("name_of_disease"), 200)) {
			this.errors.put("name_of_disease", String.format(MessageSettings.MSG_LENGTH_LONG, "病名", 200));
		}

		// 症状のバリデーション
		if (!ValidationUtil.isMinLength(this.request.getParameter("information"), 1)) {
			this.errors.put("information", String.format(MessageSettings.MSG_REQUIRED, "症状"));
		}

		if (!ValidationUtil.isMaxLength(this.request.getParameter("information"),10000)) {
			this.errors.put("information", String.format(MessageSettings.MSG_LENGTH_LONG, "症状", 10000));
		}
		
		// リンクのバリデーション		
		if (!ValidationUtil.isMaxLength(this.request.getParameter("link"), 1000)) {
			this.errors.put("link", String.format(MessageSettings.MSG_LENGTH_LONG, "リンク", 1000));
		}
		
		// NAME_IDのバリデーション（数字かどうか）
		if (!ValidationUtil.isInteger(this.request.getParameter("name_id"))) {
			this.errors.put("name_id", MessageSettings.MSG_NAMEID_FAILURE);
		}
		return errors;
	}
}
