package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.AddAccountLogic;
import model.ValidationCheck;
/**
 * Servlet implementation class Login
 */
@WebServlet("/UserRegister")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	public Main() {
		super();
	}
	*/
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Todoリストを取得してリクエストスコープに保存
		//GetListLogic getListLogic=new GetListLogic();
		//List<TodoList> todoList=getListLogic.execute();
		//request.setAttribute("todoList", todoList);
		
		//セッションスコープに保存されたTodoリストを取得
		//HttpSession session =request.getSession();
		//TodoList todoList=(TodoList) session.getAttribute("todoList");
		//GetListLogic getListLogic=new GetListLogic();
		//List<TodoList> todoList=getListLogic.execute();
		//getListLogic.execute(todoList);
		
		System.out.println("AddAccount.javaのdoGet");
		
		//ログインしているか確認のため、セッションスコープからユーザー情報を取得
		//HttpSession session =request.getSession();
		//User loginUser=(User) session.getAttribute("loginUser");
		
		//if(loginUser==null) {
			//response.sendRedirect("/docoTsubu/");
		//}else {
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adduser.jsp");
			dispatcher.forward(request, response);
		//}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Todoリストを取得してリクエストスコープに保存
		//GetListLogic getListLogic=new GetListLogic();
		//List<TodoList> todoList=getListLogic.execute();
		//request.setAttribute("todoList", todoList);
		
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		//AddAccount用
		String usermail=request.getParameter("usermail");
		String pass=request.getParameter("pass");
		String username=request.getParameter("username");
		
		System.out.println("Mail:" +usermail.length()+usermail);
		System.out.println("Pass:" +pass.length()+pass);
		System.out.println("Name:" +username.length()+username);
		
		ValidationCheck validationCheck=new ValidationCheck();
		boolean resultPass=validationCheck.isPass(pass);
		System.out.println("ResultPass:" +resultPass);
		
		boolean resultMail=validationCheck.isMail(usermail);
		System.out.println("ResultMail:" +resultMail);
		
		//入力値チェック
		//if(text != null && text.length() != 0) {
		if(usermail != null && usermail.length() != 0 
		&& pass != null && pass.length() != 0 
		&& username != null && username.length() != 0) {
		
			int addflg=0;
			
			if(username.length() > 50){
				addflg=1;
				
				//エラー時の表示用
				Account adderrUser=new Account(usermail, pass, username);
				HttpSession session=request.getSession();
				session.setAttribute("adderrUser", adderrUser);
				
				System.out.println("AddAcount失敗(Name50_1)");
				request.setAttribute("msgName", "ニックネームは50文字以下で入力してください。");
				//画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adduser.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultPass==false) {
				addflg=1;
				
				//エラー時の表示用
				Account adderrUser=new Account(usermail, pass, username);
				HttpSession session=request.getSession();
				session.setAttribute("adderrUser", adderrUser);
				
				System.out.println("AddAcount失敗(Pass_1)");
				request.setAttribute("msgPass", "パスワードは、半角英字大文字小文字と半角英数字を取り混ぜて、8文字以上20文字以内で入力してください。");
			
				//画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adduser.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultMail==false) {
				addflg=1;
				
				//エラー時の表示用
				Account adderrUser=new Account(usermail, pass, username);
				HttpSession session=request.getSession();
				session.setAttribute("adderrUser", adderrUser);
				
				System.out.println("AddAcount失敗(Mail_1)");
				request.setAttribute("msgMail", "正しいE−mailアドレスを入力してください。");
				
				//画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adduser.jsp");
				dispatcher.forward(request, response);
			}
			
			if(addflg==0) {
			//AccountをUSERSに追加
			Account account=new Account(usermail, pass, username);
			AddAccountLogic addAccountLogic=new AddAccountLogic();
			addAccountLogic.execute(account);
			
			System.out.println("AddAccount.javaのdoPost");
		
			//メイン画面にリダイレクト
			response.sendRedirect("/WEB-INF/jsp/login.jsp");
			}
		}else {
			//エラー時の表示用
			Account adderrUser=new Account(usermail, pass, username);
			HttpSession session=request.getSession();
			session.setAttribute("adderrUser", adderrUser);
			
			
			if(usermail.length() == 0 || resultMail==false){
				System.out.println("AddAcount失敗(Mail)");
				request.setAttribute("msgMail", "正しいE−mailアドレスを入力してください。");
			}
			if(pass.length() == 0 || resultPass==false){
				System.out.println("AddAcount失敗(Pass)");
				request.setAttribute("msgPass", "パスワードは、半角英字大文字小文字と半角英数字を取り混ぜて、8文字以上20文字以内で入力してください。");
			}
			if(username.length() == 0){
				System.out.println("AddAcount失敗(Name0)");
				request.setAttribute("msgName", "ニックネームは必ず入力してください。");
			}else if(username.length() > 50){
				System.out.println("AddAcount失敗(Name50)");
				request.setAttribute("msgName", "ニックネームは50文字以下で入力してください。");
			}
			System.out.println("AddAcount失敗");

			//同じ画面にリダイレクト
			//response.sendRedirect("./adduser.jsp");
			//画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adduser.jsp");
			dispatcher.forward(request, response);
		}
		
	}	
}
