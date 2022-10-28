package settings;

/**
 * ページ表示に関する設定クラス
 */
public class PageSettings {

	/** エラー発生時のフォワード先のJSPファイル */
	public final static String PAGE_ERROR = "/WEB-INF/jsp/error.jsp";

	/** ログインページのJSPファイル */
	public static final String LOGIN_JSP = "/WEB-INF/jsp/login.jsp";

	/** ログインページのサーブレット */
	public static final String LOGIN_SERVLET = "/Login";
}
