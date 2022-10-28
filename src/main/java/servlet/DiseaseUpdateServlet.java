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
import model.PostUpdateLogic;
import model.TodoList;
import model.UserModel;
import model.ValidationCheck;
/**
 * Servlet implementation class Login
 */
@WebServlet("/DiseaseUpdate")
public class DiseaseUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Todoリストを取得してリクエストスコープに保存
		GetListLogic getListLogic=new GetListLogic();
		List<TodoList> todoList=getListLogic.executeUpdate();
		request.setAttribute("todoList", todoList);
		System.out.println("Update.javaのdoGet");
		
		//ログインしているか確認のため、セッションスコープからユーザー情報を取得
		//HttpSession session =request.getSession();
		//User loginUser=(User) session.getAttribute("loginUser");
		
		//if(loginUser==null) {
			//response.sendRedirect("/docoTsubu/");
		//}else {
			//メイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
			
			//メイン画面にリダイレクト
			//response.sendRedirect("./main.jsp");
		//}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		
		//セッションスコープからインスタンスを取得
		HttpSession session=request.getSession();
		UserModel loginUser=(UserModel) session.getAttribute("loginUser");
		
		//Update用
		int user_id=1; //更新データとして用いていない
		int idno=Integer.parseInt(request.getParameter("idno"));
		String registration_date=request.getParameter("registration_date");
		String todo=request.getParameter("todo");
		String expiration_date=request.getParameter("expiration_date");
		String finished_date=request.getParameter("finished_date");
		String today=request.getParameter("today");
		//int comp=Integer.parseInt(request.getParameter("complete"));
		
		/*
		if(finished_date!=null) {
		String finished_date1=finished_date.substring(0,4);
		String finished_date2=finished_date.substring(5,7);
		String finished_date3=finished_date.substring(8);
		int finished_dateint=Integer.parseInt(finished_date1+finished_date2+finished_date3);
		}
		*/
		
		//期限日チェック用
		String today1=today.substring(0,4);
		String today2=today.substring(5,7);
		String today3=today.substring(8);
		int todayint=Integer.parseInt(today1+today2+today3);
		//System.out.println("UPtodayStr: "+today1+today2+today3);
		//System.out.println("UPtodayInt: "+todayint);
		
		String comp=request.getParameter("complete");
		if(comp==null){
			System.out.println("compNull");
			finished_date=null;
			comp="0";
		}else if(comp.equals("1")) {
			System.out.println("comp1");
			//すでに完了日があるなら完了日のままにする
			//if(finished_dateint > todayint){
				finished_date=today;
			//}
		}
		int compint=Integer.parseInt(comp);
		
		String del=request.getParameter("delete");
		if(del==null) {
			del="0";
		}
		int delint=Integer.parseInt(del);
		
		//System.out.println("UPidno: "+idno);
		//System.out.println("UPregistration_date: "+registration_date);
		//System.out.println("UPfinished_date: "+finished_date);
		//System.out.println("UPtodo: "+todo_item);
		//System.out.println("UPcomp: "+comp);
		//System.out.println("UPtoday: "+today);
		//System.out.println("UPdel: "+delint);
		
		
		ValidationCheck validationCheck=new ValidationCheck();
		boolean resultReg=validationCheck.isDate(registration_date);
		System.out.println("ResultReg:" +resultReg);
		
		boolean resultExpi=validationCheck.isDate(expiration_date);
		System.out.println("ResultExpi:" +resultExpi);
		
		//入力値チェック
		if(todo != null && todo.length() != 0
						&& registration_date != null && registration_date.length() != 0 
						&& expiration_date != null && expiration_date.length() != 0){ 
			int updateflg=0;
			
			if(todo.length() > 50){
				updateflg=1;
				
				//エラー時の表示用
				TodoList updateerrTodo=new TodoList(registration_date, expiration_date, todo, compint);
				//HttpSession session=request.getSession();
				session.setAttribute("updateerrTodo", updateerrTodo);
				
				System.out.println("UpdateTodo失敗(Todo50_1)");
				request.setAttribute("msgTodo", "Todo項目は50文字以下で入力してください。");
				
				//同じ画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updatelist.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultReg==false) {
				updateflg=1;
				
				//エラー時の表示用
				TodoList updateerrTodo=new TodoList(registration_date, expiration_date, todo, compint);
				session.setAttribute("updateerrTodo", updateerrTodo);
				
				System.out.println("UpdateTodo失敗(Reg_1)");
				request.setAttribute("msgReg", "登録日が正しくありません。");
				
				//同じ画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updatelist.jsp");
				dispatcher.forward(request, response);
			}
			
			if(resultExpi==false) {
				updateflg=1;
				
				//エラー時の表示用
				TodoList updateerrTodo=new TodoList(registration_date, expiration_date, todo, compint);
				session.setAttribute("updateerrTodo", updateerrTodo);
				
				System.out.println("UpdateTodo失敗(Expi_1)");
				request.setAttribute("msgExpi", "登録日が正しくありません。");
				
				//同じ画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updatelist.jsp");
				dispatcher.forward(request, response);
			}
			
			if(updateflg==0){
			//if(todo.length() <= 50){
			//成功時
			//TodoをTodoリストに更新
			TodoList todoList2=new TodoList(idno, user_id, registration_date, expiration_date, finished_date, todo, delint);
			PostUpdateLogic postUpdateLogic=new PostUpdateLogic();
			postUpdateLogic.executeUpdate(todoList2);
			
			System.out.println("Update.javaのdoPost");
		
			//Todoリストを取得してリクエストスコープに保存（追加）	
			GetListLogic getListLogic=new GetListLogic();
			List<TodoList> todoList=getListLogic.execute(loginUser);
			request.setAttribute("todoList", todoList);
		
			//メイン画面にリダイレクト
			response.sendRedirect("/WEB-INF/jsp/main.jsp");
			}
		}else {
					//エラー時の表示用
					TodoList updateerrTodo=new TodoList(registration_date, expiration_date, todo, compint);
					session.setAttribute("updateerrTodo", updateerrTodo);
					
					if(todo.length() == 0){
						System.out.println("UpdateTodo失敗(Todo)");
						request.setAttribute("msgTodo", "TODO項目は必ず入力してください。");
					}else if(todo.length() > 50){
						System.out.println("UpdateTodo失敗(Todo50)");
						request.setAttribute("msgTodo", "Todo項目は50文字以下で入力してください。");
					}
					
					if(registration_date.length() == 0){
						System.out.println("UpdateTodo失敗(Reg)");
						request.setAttribute("msgReg", "登録日は必ず入力してください。");
					}else if(resultReg==false){
						System.out.println("UpdateTodo失敗(RegFalse)");
						request.setAttribute("msgReg", "登録日が正しくありません。");
					}
					
					if(expiration_date.length() == 0){
						System.out.println("UpdateTodo失敗(Expi)");
						request.setAttribute("msgExpi", "期限日は必ず入力してください。");
					}else if(resultExpi==false){
						System.out.println("UpdateTodo失敗(ExpiFalse)");
						request.setAttribute("msgExpi", "登録日が正しくありません。");
					}
					
					System.out.println("UpdateList失敗");
					
					//同じ画面にフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updatelist.jsp");
					dispatcher.forward(request, response);
		}
	}	
}
