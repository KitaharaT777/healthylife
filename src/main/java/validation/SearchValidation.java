package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;

/**
 * ユーザー登録・更新バリデーションクラス
 */
public class SearchValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request リクエストオブジェクト
	 */
	public SearchValidation(HttpServletRequest request) {
		super(request);
	}

	/**
	 * バリデーションを行います。
	 * @return バリデーションエラーのMap<項目名, エラーメッセージ>
	 */
	public Map<String, String> validate(){
		// 検索ワードのバリデーション
		if (!ValidationUtil.isMaxLength(this.request.getParameter("key"), 100)) {
			this.errors.put("key", String.format(MessageSettings.MSG_LENGTH_LONG, "検索ワード", 100));
		}
		
		return errors;
	}
}
