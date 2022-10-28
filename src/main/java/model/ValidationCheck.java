package model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public class ValidationCheck{
	/**
	 * メールアドレスチェック
	 * @param value 検証対象の値
	 * @return 結果（true：メールアドレス、false：メールアドレスではない）
	 */
	public boolean isMail(String value) {
	   
		boolean result = false;

	    if (value != null) {
	    	//String pattern = "^([a-zA-Z0-9])+([a-zA-Z0-9\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\._-]+)+$";
	        
	    	//String pattern = "^([a-zA-Z0-9])+([a-zA-Z0-9\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\._-]+)+$";
	        //Pattern p = Pattern.compile(pattern);
	    	
	       //Pattern pattern = Pattern.compile("^[\!#%&'/=~`\*\+\?\{\}\^$\-\|]+(\.[\w!#%&'/=~`\*\+\?\{\}\^$\-\|]+)*@[\w!#%&'/=~`\*\+\?\{\}\^$\-\|]+(\.[\w!#%&'/=~`\*\+\?\{\}\^$\-\|]+)*$");
	       //Pattern pattern = Pattern.compile("^([a-zA-Z0-9])+([a-zA-Z0-9\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\._-]+)+$");
	        
	    	
	    	 // 正規表現のパターンを作成
	        Pattern pattern = Pattern.compile(
	                //"^(([0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*)|("[^"]*"))"
	        		"^([0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)"
	                        + "@[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+"
	                        + "(.[0-9a-zA-Z!#$%&'*+-/=?^_`{}|~]+)*$");
	 
	        //Matcher m = p.matcher(str1);
	    	
	    	
	        result = pattern.matcher(value).matches();
	        
	    	//result=true;
		    System.out.println("アドレスチェック");
	    }

	    return result;
	}
	
	//両方の場合の数字とアルファベットを必要とする4〜8文字のパスワード
    private final String PASSWORD_REGEX =
            //"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
    		"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
 
    // 4つのうち少なくとも3つを必要とする4〜32文字のパスワード(大文字
    //および小文字、数字、特殊文字)および最大で
    //2つの等しい連続文字。
    private final String COMPLEX_PASSWORD_REGEX =
            "^(?:(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])|" +
            "(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|" +
            "(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])" +
            //"(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|" +
           // "(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))(?!.*(.)\\1{2,})" +
           // "[A-Za-z0-9!~<>,;:_=?*+#.\"&§%°()\\|\\[\\]\\-\\$\\^\\@\\/]" +
            ".{8,20}$";
    		//"{8,32}$";
 
    //private final Pattern PASSWORD_PATTERN = Pattern.compile(COMPLEX_PASSWORD_REGEX);
    private final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    
	public boolean isPass(String value) {
	    boolean result = false;

	    if (value != null) {
	    	result = PASSWORD_PATTERN.matcher(value).matches();
	       //result=true;
	       System.out.println("パスワードチェック");
	    }

	    return result;
	}
	
	/**
	 * 日付チェック
	 * @param value 検証対象の値
	 * @return 結果（true：日付、false：日付ではない）
	 */
	public boolean isDate(String value) {
	    boolean result = false;

	    if (value != null) {
	        try {
	            String checkDate = value.replace("-", "").replace("/", "");
	            DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT).parse(checkDate, LocalDate::from);
	            result = true;
	            
	            System.out.println("日付チェック");

	        } catch (Exception e) {
	            result = false;
	        }
	    }

	    return result;
	}
}
