package settings;

/**
 * メッセージ設定クラス
 */
public class MessageSettings {

	/** %sはすでに登録されているので使用できません。 */
	public static final String MSG_ER_DUP_KEYNAME = "%sはすでに登録されているので使用できません。";

	/** 申し訳ございません。エラーが発生しました。 */
	public static final String MSG_ERROR_OCCURRED = "申し訳ございません。エラーが発生しました。";

	/** 不正な処理が行われました */
	public static final String MSG_INVALID_PROCESS = "不正な処理が行われました。";

	/** E-mailアドレス、または、パスワードが間違っています。 */
	public static final String MSG_LOGIN_FAILURE = "E-mailアドレス、または、パスワードが間違っています。";

	/** 正しいE−mailアドレスを入力してください。 */
	public static final String MSG_EMAIL_FAILURE = "正しいE−mailアドレスを入力してください。";
	
	/** 数字で入力してください。 */
	public static final String MSG_USERID_FAILURE = "数字で入力してください。";
	public static final String MSG_NAMEID_FAILURE = "数字で入力してください。";
	
	/** パスワードは、半角英字大文字小文字と半角英数字を取り混ぜて、8文字以上20文字以内で入力してください。 */
	public static final String MSG_PASSWORD_FAILURE = "パスワードは、半角英字大文字小文字と半角英数字を取り混ぜて、8文字以上20文字以内で入力してください。";

	/** %sは%d文字以上で入力してください。*/
	public static final String MSG_LENGTH_SHORT = "%sは%d文字以上で入力してください。";

	/** %sは%d文字以下で入力してください。*/
	public static final String MSG_LENGTH_LONG = "%sは%d文字以下で入力してください。";

	/** %sは必ず入力してください。 */
	public static final String MSG_REQUIRED = "%sは必ず入力してください。";

	/** %sの形式が正しくありません。*/
	public static final String MSG_INVALID_FORMAT = "%sの形式が正しくありません。";

	/** %sが正しくありません。 */
	public static final String MSG_INVALID_VALUE = "%sが正しくありません。";
	
	/** %d件のデータをインポートしました。*/
	public static final String MSG_IMPORT = "件のデータをインポートしました。";
	
	/** インポートに失敗しました。*/
	public static final String MSG_IMPORT_FAILURE = "インポートに失敗しました。";
	
	/** %d件のデータをエクスポートしました。*/
	public static final String MSG_EXPORT = "件のデータをエクスポートしました。";
	
	/** エクスポートに失敗しました。*/
	public static final String MSG_EXPORT_FAILURE = "エクスポートに失敗しました。";
}
