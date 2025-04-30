package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//DAO
		ClassNumDao cNumDao =new ClassNumDao();
		SubjectDao subDao = new SubjectDao() ;
		StudentDao sDao = new StudentDao();
		TestDao tDao = new TestDao();


		//bean list
		ClassNum ClassNum =new ClassNum();
		Subject subject =new Subject();
		Test test =new Test();
		Student student =new Student();

		//メソッドとスタブ
		String entYear ="";
		String classNum="";
		String subjectCd="";
		String subjectName="";
		String no ="";
		// List<Test> の作成
		List<Test> testList = new ArrayList<>();


		//JSPから送られたデータを取得
		entYear  = req.getParameter("f1");//入学年度
		classNum = req.getParameter("f2");//クラス
		subjectCd = req.getParameter("f3");//科目名？科目コード？
		no = req.getParameter("f4");//学生番号
		String paramName = "point_" + no; // パラメータ名を構築
		String point = req.getParameter(paramName); // 得点を取得


		//入力値チェック
		if(point==null){

			//入学年度、クラス、科目、回数のいずれかが未入力の場合
			if (entYear!=null && classNum != null && subjectCd != null && no != null){
				//subject
				//科目コード
				subject= subDao.get(subjectCd,teacher.getSchool());
				subject.setName(subject.getName());

				//student
				//入学年度 String ->int
				student.setEntYear(Integer.parseInt(entYear));
				//氏名
				student.setName(student.getName());

				//test
				test =tDao.get(student, subject,teacher.getSchool(), Integer.parseInt(no));


				//情報格納
				//科目
				test.setSubject(subject);
				//受験回数
				test.setNo(test.getNo());
				//入学年度、氏名
				test.setStudent(student);
				//クラス
				test.setClassNum(classNum);
				//学生番号
				test.setNo(Integer.parseInt(no));
				req.getRequestDispatcher("test_regist.jsp").forward(req, res);

			}else{
				// 認証失敗の場合
				// エラーメッセージをセット
				String errors = ("入学年度とクラスと科目と回数を選択してください");
				req.setAttribute("errors", errors);

				String url = "test_regist.jsp";
				req.getRequestDispatcher(url).forward(req, res);
			}
		}else{
			if (Integer.parseInt(point) >= 0 && Integer.parseInt(point) <= 100){
				//subject
				//科目コード
				subject= subDao.get(subjectCd,teacher.getSchool());
				subject.setName(subject.getName());

				//student
				//入学年度 String ->int
				student.setEntYear(Integer.parseInt(entYear));
				//氏名
				student.setName(student.getName());

				//test
				test =tDao.get(student, subject,teacher.getSchool(), Integer.parseInt(no));


				//情報格納
				//科目
				test.setSubject(subject);
				//受験回数
				test.setNo(test.getNo());
				//入学年度、氏名
				test.setStudent(student);
				//クラス
				test.setClassNum(classNum);
				//学生番号
				test.setNo(Integer.parseInt(no));
				//得点
				test.setPoint(Integer.parseInt(point));

				// テストデータをリストに追加
				testList.add(test);

				// DAOで保存処理（複数テストデータを一括で保存）
				tDao.save(testList);

				// 確認ログ
				System.out.println("保存されたテストデータの件数: " + testList.size());


				// フォワード
				req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			}else{
				// 認証失敗の場合
				// エラーメッセージをセット
				String errors = ("0~100の範囲で入力してください");
				req.setAttribute("errors", errors);

				String url = "test_regist.jsp";
				req.getRequestDispatcher(url).forward(req, res);
			}
		}
	}
}
