package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.GetListLogic;
import model.TodoList;
import model.UpdateAccountLogic;
import model.UserModel;
import model.ValidationCheck;
/**
 * Servlet implementation class Login
 */
@WebServlet("/UserUpdate")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("UpdateAccount.javaのdoGet");
		
		//ログインしているか確認のため、セッションスコープからユーザー情報を取得
		//HttpSession session =request.getSession();
		//User loginUser=(User) session.getAttribute("loginUser");
		
		//if(loginUser==null) {
			//response.sendRedirect("/docoTsubu/");
		//}else {
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updateuser.jsp");
		dispatcher.forward(request, response);
			
			//メイン画面にリダイレクト
			//response.sendRedirect("./index.jsp");
			//response.sendRedirect("/WEB-INF/jsp/main.jsp");
		//}	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		
		//セッションスコープからインスタンスを取得
		HttpSession session=request.getSession();
		UserModel loginUser=(UserModel) session.getAttribute("loginUser");
		
		//UpdateAccount用
		//AccountDAO dao=new AccountDAO();
		//Account loginUser=dao.findByLogin(login);
		int userId=loginUser.getUserId();
		String email=request.getParameter("usermail");
		String pass=request.getParameter("pass");
		String userName=request.getParameter("username");
		//System.out.println("UserId: " +userId);
		//System.out.println("UserName: " +userName);
		
		//セッションスコープのloginUserの更新
		//loginUser=new Account(userId, email, pass, userName);
		
		ValidationCheck validationCheck=new ValidationCheck();
		boolean resultPass=validationCheck.isPass(pass);
		System.out.println("ResultPass:" +resultPass);
		
		boolean resultMail=validationCheck.isMail(email);
		System.out.println("ResultMail:" +resultMail);
				
		//入力値チェック
		if(email != null && email.length() != 0 
			&& pass != null && pass.length() != 0 
			&& userName != null && userName.length() != 0) {
			
			int updateflg=0;
			System.out.println("UpdateAccount.javaのdoPost");
			
			if(userName.length() > 50){
				updateflg=1;
				
				//エラー時の表示用
				UserModel updateerrUser=new UserModel(email, pass, userName);
				//HttpSession session=request.getSession();
				session.setAttribute("updateerrUser", updateerrUser);
				
				System.out.println("UpdateAcount失敗(Name50_1)");
				request.setAttribute("msgName", "ニックネームは50文字以下で入力してください。");
				//画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updateuser.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultPass==false) {
				updateflg=1;
				
				//エラー時の表示用
				UserModel updateerrUser=new UserModel(email, pass, userName);
				//HttpSession session=request.getSession();
				session.setAttribute("updateerrUser", updateerrUser);
				
				System.out.println("UpdateAcount失敗(Pass_1)");
				request.setAttribute("msgPass", "パスワードは、半角英字大文字小文字と半角英数字を取り混ぜて、8文字以上20文字以内で入力してください。");
			
				//画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updateuser.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultMail==false) {
				updateflg=1;
				
				//エラー時の表示用
				UserModel updateerrUser=new UserModel(email, pass, userName);
				//HttpSession session=request.getSession();
				session.setAttribute("updateerrUser", updateerrUser);
				
				System.out.println("UpdateAcount失敗(Mail_1)");
				request.setAttribute("msgMail", "正しいE−mailアドレスを入力してください。");
				
				//画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updateuser.jsp");
				dispatcher.forward(request, response);
			}
			
			if(updateflg==0) {
			//loginUserをUSERSリストに更新
			loginUser=new UserModel(userId, email, pass, userName);
			UpdateAccountLogic updateAccountLogic=new UpdateAccountLogic();
			updateAccountLogic.executeUpdateAccount(loginUser);
			
			//更新後のセッションスコープにユーザーIDを保存
			//HttpSession session=request.getSession();
			//session.setAttribute("userId", userId);
			//session.setAttribute("userName", userName);
			session.setAttribute("loginUser", loginUser);
			
			
			System.out.println("UpdateAccount.javaのdoPost成功");
	
		
			//Todoリストを取得してリクエストスコープに保存（追加）
			GetListLogic getListLogic=new GetListLogic();
			List<TodoList> todoList=getListLogic.execute(loginUser);
			request.setAttribute("todoList", todoList);
		
			//ログイン画面にリダイレクト
			//response.sendRedirect("./login.jsp");
			response.sendRedirect("/WEB-INF/jsp/main.jsp");
			}
		}else {
			//エラー時の表示用
			Account updateerrUser=new Account(email, pass, userName);
			//HttpSession session=request.getSession();
			session.setAttribute("updateerrUser", updateerrUser);
			
			
			if(email.length() == 0 || resultMail==false){
				System.out.println("UpdateAcount失敗(Mail)");
				request.setAttribute("msgMail", "正しいE−mailアドレスを入力してください。");
			}
			if(pass.length() == 0 || resultPass==false){
				System.out.println("UpdateAcount失敗(Pass)");
				request.setAttribute("msgPass", "パスワードは、半角英字大文字小文字と半角英数字を取り混ぜて、8文字以上20文字以内で入力してください。");
			}
			if(userName.length() == 0){
				System.out.println("UpdateAcount失敗(Name0)");
				request.setAttribute("msgName", "ニックネームは必ず入力してください。");
			}else if(userName.length() > 50){
				System.out.println("UpdateAcount失敗(Name50)");
				request.setAttribute("msgName", "ニックネームは50文字以下で入力してください。");
			}
			System.out.println("UpdateAcount失敗");

			//画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updateuser.jsp");
			dispatcher.forward(request, response);
		}
	}	
}
