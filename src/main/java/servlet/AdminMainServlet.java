package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.DiseaseItemLogic;
import model.Account;
import model.DiseaseItemModel;
import model.GetListLogic;
import model.TodoList;
import model.TodoSearch;
import model.UserModel;
import settings.PageSettings;
/**
 * Servlet implementation class Login
 */
@WebServlet("/AdminMain")
public class AdminMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminMainServlet() {
		super();
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			
		// Diseaseリストを取得する。
		DiseaseItemLogic logic;
		logic = new DiseaseItemLogic();
			
			//セッションスコープからインスタンスを取得
		HttpSession session=request.getSession();
		Account loginUser=(Account) session.getAttribute("loginUser");
		int user_id=loginUser.getUserId();
		//System.out.println("SearchUser: "+user_id);
		
		//Diseaseリストを取得してリクエストスコープに保存
		//GetListLogic getListLogic=new GetListLogic();
		//List<TodoList> todoList=getListLogic.execute(loginUser);
		//request.setAttribute("todoList", todoList);
		
		//GetListLogic getListLogic=new GetListLogic();
		//List<TodoList> todoList=getListLogic.execute(loginUser);
		List<DiseaseItemModel> diseaseList = logic.find();
		request.setAttribute("diseaseList", diseaseList);
		
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		
		String todosearch=request.getParameter("todosearch");
		//System.out.println("SearchGet: "+todosearch);
		
		//todosearchをセッションスコープに保存
		TodoSearch search=new TodoSearch(todosearch);
		//HttpSession session2=request.getSession();
		session.setAttribute("search",search);
		//System.out.println("SearchSession: "+search);
		
		System.out.println("AdminMain.javaのdoGet");
		
		/*
		//TodoをTodoリストから検索
		TodoList todoList2=new TodoList(user_id, todosearch);
		System.out.println("List2Get: "+todoList2);
		
		List<TodoList> todoList3=getListLogic.executeSearch(loginUser,todosearch);
		System.out.println("List3Get: "+todoList3);
		request.setAttribute("todoList", todoList3);
		
		if(todosearch==null) {
		//if(todosearch.length()==0 || todosearch==null) {
			System.out.println("List3null");
			todoList3=getListLogic.execute(loginUser);
			request.setAttribute("todoList", todoList3);
			
			//メイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
			dispatcher.forward(request, response);
		}else {
			System.out.println("List3OK");
			//メイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
			dispatcher.forward(request, response);
		}*/	
		
		//メイン画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMain.jsp");
		dispatcher.forward(request, response);
		
		return;
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}
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
		
		//今日の日付
		String today=request.getParameter("today");
		System.out.println("TodayMainjava: "+today);
		
		/*
		//入力値チェック
		if(text != null && text.length() != 0) {
			
			//セッションスコープに保存されたユーザー情報を取得
			HttpSession session=request.getSession();
			User loginUser=(User) session.getAttribute("loginUser");
			*/	
			//TodoをTodoリストに追加
			//TodoList todoList2=new TodoList(user_id, date, date, todo);
			//PostAddLogic postAddLogic=new PostAddLogic();
			//postAddLogic.execute(todoList2);
			
			System.out.println("AdminMain.javaのdoPost");
		/*
			
		}else {
			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "つぶやきが入力されていません");
		}
		*/
		
		
		//メイン画面にフォワード
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		//dispatcher.forward(request, response);
		
		//メイン画面にリダイレクト
				//response.sendRedirect("./index.jsp");
				response.sendRedirect("/WEB-INF/jsp/adminMain.jsp");
	}	
}
