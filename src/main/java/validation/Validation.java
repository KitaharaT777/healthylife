package validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * バリデーション基底クラス（抽象クラス）
 */
public abstract class Validation {

	/** リクエストオブジェクト */
	protected HttpServletRequest request;

	/** エラーが発生した項目名とエラー内容を格納するMap */
	protected Map<String, String> errors;

	/**
	 * コンストラクタ
	 *
	 * @param request リクエスト
	 */
	public Validation(HttpServletRequest request) {
		this.request = request;
		this.errors = new HashMap<String, String>();
	}

	/**
	 * バリデーションエラーの有無を判定します。
	 *
	 * @return true:エラーがある、false:エラーはない
	 */
	public boolean hasErrors() {
		if (this.errors.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * バリデーションを行います。（抽象メソッド）
	 * 実装は派生先のクラスで行います。
	 *
	 * @return バリデーションエラーのMap<項目名, エラーメッセージ>
	 */
	public abstract Map<String, String> validate();
}
