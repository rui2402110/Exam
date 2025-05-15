package scoremanager.main;
import java.util.Scanner;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;
@MultipartConfig(
		maxFileSize=10000000,
		maxRequestSize=10000000,
		fileSizeThreshold=10000000,
		location = "C:\temp"
	)
public class CsvExecuteAction extends Action  {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		StudentDao sDao = new StudentDao();
		Part csvFile = req.getPart("csv");  // ファイル取得
		Student students = new Student() ;
		String error2 = null;

	    try (Scanner scanner = new Scanner(csvFile.getInputStream())) { // InputStreamでスキャナー作成

	        while (scanner.hasNextLine()) { // 次の行があるかチェック

	            String line = scanner.nextLine(); // 1行だけ読み込む
	            System.out.println(line); // デバッグ用に表示
	            String[] data = line.split(","); // 同じ行を処理
	            // noに被りがあった場合は通常の処理を行わずにnoが被りだった場合のエラーデータを取得しStudentCreateActionにフォワード

	            if( sDao.get(data[0].trim()) ==null){

				// エラーの表示が必要ない場合データをセット
					students.setNo(data[0].trim());
					students.setName(data[1].trim());
					students.setEntYear(Integer.parseInt(data[2].trim()));
					students.setClassNum(data[3].trim());
					students.setSchool(teacher.getSchool());
					sDao.save(students);


				}else{
					// 認証失敗の場合
					// エラーメッセージをセット
					error2 = ("学生番号が重複しているデータがあります。データを確認してください");


					break ;
				}
	        }

	        if(error2 == null){
	        	// JSPへフォワード
			    req.getRequestDispatcher("student_csv_done.jsp").forward(req, res);
	        }else{
	        	req.setAttribute("error2", error2);
	        	String url = "student_csv.jsp";
				req.getRequestDispatcher(url).forward(req, res);
	        }
	    }
	}
}
// DELETE FROM STUDENT WHERE NO ='501' AND NO ='502'