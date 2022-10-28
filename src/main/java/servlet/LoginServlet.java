package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.Login;
import model.LoginLogic;
import model.TodoSearch;
import model.UserModel;
/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("loginservletGet ");
		//ログインページにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		//RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.LOGIN_JSP);
		dispatcher.forward(request, response);
		
		return;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String email=request.getParameter("email");
		String pass=request.getParameter("pass");
		
		//System.out.println("Email: " +email);
		//System.out.println("Pass: " +pass);
		
		//try {
		
		//ログイン処理の実行
		Login login=new Login(email, pass);
		LoginLogic bo=new LoginLogic();
		boolean result=bo.execute(login);
		System.out.println("ログイン判定: "+result);
		
		//ログイン処理の成否によって処理を分岐
		if(result) { //ログイン成功
			UserDAO dao=new UserDAO();
			UserModel account=dao.findByLogin(login);
			int userId=account.getUserId();
			String userName=account.getUserName();
			//String email=account.getEmail();
			//String pass=account.getPass();
			//System.out.println("UserId: " +userId);
			//System.out.println("UserName: " +userName);
			
			int loginflg=1; //ログイン成功時
			//UserModel loginUser=new UserModel(userId, email, pass, userName, loginflg);
			UserModel loginUser=new UserModel(userId, email, pass, loginflg);
			
			//セッションスコープにユーザーIDを保存
			HttpSession session=request.getSession();
			//session.setAttribute("userId", userId);
			//session.setAttribute("userName", userName);
			session.setAttribute("loginUser", loginUser);
			
			
			//初期todosearchをセッションスコープに保存
			TodoSearch search=new TodoSearch();
			HttpSession session2=request.getSession();
			session2.setAttribute("search",search);
			
			//ログアウトメッセージをリクエストスコープに保存
			HttpSession session3=request.getSession();
			session3.setAttribute("msg", "ログアウトしました");
			
			System.out.println("ログイン成功");
			
			if(userId==1) {
				//AdminMainへリダイレクトする。
				response.sendRedirect(request.getContextPath() + "/AdminMain");
			}else {
				//Mainへリダイレクトする。
				response.sendRedirect(request.getContextPath() + "/Main");
			}
			
		}else {	//ログイン失敗
			//メッセージ
			HttpSession session3=request.getSession();
			session3.setAttribute("msg", "E-mailアドレス、または、パスワードが間違っています。");
			
			
			System.out.println("errEmail: " +email);
			System.out.println("errPass: " +pass);
			int loginerrflg=1; //ログイン失敗時
			UserModel loginerrUser=new UserModel(email, pass, loginerrflg);
			
			//セッションスコープにユーザーIDを保存
			HttpSession session=request.getSession();
			session.setAttribute("loginerrUser", loginerrUser);
			
			System.out.println("ログイン失敗");
			// ログインページへフォワードする。
			//RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.LOGIN_JSP);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
		
		return;
	
	/*} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();

		// エラーページへフォワードする。
		RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
		dispatcher.forward(request, response);

		return;
	}*/
	}	
}
