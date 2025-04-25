package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;

public class TestRegistExecuteAction {

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
		List<TestListSubject> list;

		//JSPから送られたデータを取得
		entYear  = req.getParameter("f1");//入学年度
		classNum = req.getParameter("f2");//クラス
		subjectCd = req.getParameter("f3");//科目名？科目コード？
		no = req.getParameter("f4");//学生番号

		//入力値チェック
		if
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
			String errors1 = ("入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors1", errors1);

			String url = "test_regist.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		}

		if()
	}
}
