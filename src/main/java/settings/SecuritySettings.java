package settings;

/**
 * セキュリティ対策設定クラス
 */
public class SecuritySettings {

	/** 生成するワンタイムトークンの長さ */
	public static final int TOKEN_LENGTH = 16;

	/** ワンタイムトークンの生成に利用される暗号化アルゴリズム */
	public static final String ENCRYPTION_ALGORITHM = "SHA1PRNG";

	/** パスワードの妥当性チェックのための正規表現文字列 */
	public static final String PASSWORD_REGEXP_STRING = "^[0-9a-zA-Z]{8,20}$";
}
