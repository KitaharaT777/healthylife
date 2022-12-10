package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;

/**
 * ユーザー登録・更新バリデーションクラス
 */
public class UserValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request リクエストオブジェクト
	 */
	public UserValidation(HttpServletRequest request) {
		super(request);
	}

	/**
	 * バリデーションを行います。
	 * @return バリデーションエラーのMap<項目名, エラーメッセージ>
	 */
	public Map<String, String> validate(){
		// メールアドレスのバリデーション
		if (!ValidationUtil.isEmail(this.request.getParameter("usermail"))) {
			this.errors.put("usermail", MessageSettings.MSG_EMAIL_FAILURE);
		}

		// パスワードのバリデーション
		if (!ValidationUtil.isPassword(this.request.getParameter("pass"))) {
			this.errors.put("pass", MessageSettings.MSG_PASSWORD_FAILURE);
		}
		
		// 生年月日のバリデーション
		if (!ValidationUtil.isDate(this.request.getParameter("birthday"))) {
			this.errors.put("birthday", String.format(MessageSettings.MSG_INVALID_VALUE, "生年月日"));
		}
				
		if (!ValidationUtil.isMinLength(this.request.getParameter("birthday"), 1)) {
			this.errors.put("birthday", String.format(MessageSettings.MSG_REQUIRED, "生年月日"));
		}
		
		// ユーザーIDのバリデーション（数字かどうか）（新規登録ではチェックしない）
		if (!ValidationUtil.isInteger(this.request.getParameter("userId"))) {
			this.errors.put("userId", MessageSettings.MSG_USERID_FAILURE);
		}
		
		return errors;
	}
}
