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

import model.GetListLogic;
import model.PostAddLogic;
import model.TodoList;
import model.UserModel;
import model.ValidationCheck;
/**
 * Servlet implementation class Login
 */
@WebServlet("/DiseaseRegister")
public class DiseaseRegisterServlet extends HttpServlet {
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
		System.out.println("DiseaseRegisterServlet.javaのdoGet");
		
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diseaseRegister.jsp");
		dispatcher.forward(request, response);
		
		return;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッションスコープからインスタンスを取得
		HttpSession session=request.getSession();
		UserModel loginUser=(UserModel) session.getAttribute("loginUser");
		
		//Todoリストを取得してリクエストスコープに保存
		GetListLogic getListLogic=new GetListLogic();
		List<TodoList> todoList=getListLogic.execute(loginUser);
		request.setAttribute("todoList", todoList);
		
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		//Add用
		//int user_id=1;
		int user_id=loginUser.getUserId();
		String registration_date=request.getParameter("registration_date");
		String todo=request.getParameter("todo");
		String expiration_date=request.getParameter("expiration_date");
		String comp=request.getParameter("complete");
		if(comp==null) {
			comp="0";
		}
		int compint=Integer.parseInt(comp);
		String today=request.getParameter("today");
		
		System.out.println("loginUser(add.java): "+user_id);
		System.out.println("registration_date: "+registration_date);
		System.out.println("todo: "+todo);
		System.out.println("complete: "+compint); //0なら未完了、1なら完了で本日付けにする
		System.out.println("today: "+today);
		
		
		ValidationCheck validationCheck=new ValidationCheck();
		boolean resultReg=validationCheck.isDate(registration_date);
		System.out.println("ResultReg:" +resultReg);
		
		boolean resultExpi=validationCheck.isDate(expiration_date);
		System.out.println("ResultExpi:" +resultExpi);
		
		//入力値チェック
		if(todo != null && todo.length() != 0
				&& registration_date != null && registration_date.length() != 0 
				&& expiration_date != null && expiration_date.length() != 0){ 
			int addflg=0;
			
			if(todo.length() > 50){
				addflg=1;
				
				//エラー時の表示用
				TodoList adderrTodo=new TodoList(registration_date, expiration_date, todo, compint);
				//HttpSession session=request.getSession();
				session.setAttribute("adderrTodo", adderrTodo);
				
				System.out.println("AddTodo失敗(Todo50_1)");
				request.setAttribute("msgTodo", "Todo項目は50文字以下で入力してください。");
				
				//同じ画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addlist.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultReg==false) {
				addflg=1;
				
				//エラー時の表示用
				TodoList adderrTodo=new TodoList(registration_date, expiration_date, todo, compint);
				session.setAttribute("adderrTodo", adderrTodo);
				
				System.out.println("AddTodo失敗(Reg_1)");
				request.setAttribute("msgReg", "登録日が正しくありません。");
				
				//同じ画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addlist.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultExpi==false) {
				addflg=1;
				
				//エラー時の表示用
				TodoList adderrTodo=new TodoList(registration_date, expiration_date, todo, compint);
				session.setAttribute("adderrTodo", adderrTodo);
				
				System.out.println("AddTodo失敗(Expi_1)");
				request.setAttribute("msgExpi", "登録日が正しくありません。");
				
				//同じ画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addlist.jsp");
				dispatcher.forward(request, response);
			}
				
			
			if(addflg==0) {
			//成功時
			if(compint==0) {
				//TodoをTodoリストに追加(完了フラグ0)
				TodoList todoList2=new TodoList(user_id, registration_date, expiration_date, todo);
				PostAddLogic postAddLogic=new PostAddLogic();
				postAddLogic.execute(todoList2);
			}else {
				//TodoをTodoリストに追加(完了フラグ1)
				TodoList todoList2=new TodoList(user_id, registration_date, expiration_date, today, todo);
				PostAddLogic postAddLogic=new PostAddLogic();
				postAddLogic.execute(todoList2);
			}
			System.out.println("DiseaseRegisterServlet.javaのdoPost");
		
			//Todoリストを取得してリクエストスコープに保存（追加）
			getListLogic=new GetListLogic();
			todoList=getListLogic.execute(loginUser);
			request.setAttribute("todoList", todoList);
		
			//管理者メイン画面にリダイレクト
				//response.sendRedirect("/WEB-INF/jsp/main.jsp");
			//管理者メイン画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMain");
				dispatcher.forward(request, response);
			}
		}else {
			//エラー時の表示用
			TodoList adderrTodo=new TodoList(registration_date, expiration_date, todo, compint);
			//HttpSession session=request.getSession();
			session.setAttribute("adderrTodo", adderrTodo);
			
			if(todo.length() == 0){
				System.out.println("AddTodo失敗(Todo)");
				request.setAttribute("msgTodo", "TODO項目は必ず入力してください。");
			}else if(todo.length() > 50){
				System.out.println("AddTodo失敗(Todo50)");
				request.setAttribute("msgTodo", "Todo項目は50文字以下で入力してください。");
			}
			
			if(registration_date.length() == 0){
				System.out.println("AddTodo失敗(Reg)");
				request.setAttribute("msgReg", "登録日は必ず入力してください。");
			}else if(resultReg==false){
				System.out.println("AddTodo失敗(RegFalse)");
				request.setAttribute("msgReg", "登録日が正しくありません。");
			}
			
			if(expiration_date.length() == 0){
				System.out.println("AddTodo失敗(Expi)");
				request.setAttribute("msgExpi", "期限日は必ず入力してください。");
			}else if(resultExpi==false){
				System.out.println("AddTodo失敗(ExpiFalse)");
				request.setAttribute("msgExpi", "登録日が正しくありません。");
			}
			
			System.out.println("AddList失敗");
			
			//同じ画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addlist.jsp");
			dispatcher.forward(request, response);
		}
	}	
}
