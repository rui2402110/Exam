package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;

public class TestRegistExecuteAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//DAO
		SubjectDao subDao = new SubjectDao() ;
		SubjectDao sDao = new SubjectDao();
		TestDao tDao = new TestDao();
		TestListSubject TestListSubject =new TestListSubject();

		//list
		Subject subjects =new Subject();
		Student students =new Student();


		//メソッドとスタブ
		String entYear ="";
		String classNum="";
		String subjectCd="";
		String subjectName="";
		String no ="";

		//JSPから送られたデータを取得
		entYear  = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		no = req.getParameter("f4");

		//入力値チェック
		//入学年度、クラス、科目、回数のいずれかが未入力の場合
		if (entYear!=null || classNum != null || subjectCd != null|| no != null){
			//GET
			subjects= subDao.get(subjectCd,teacher.getSchool());
			students=sDao.get(cd, school);

			//情報格納
			//科目,回数

			//入学年度 String ->int
			TestListSubject1.setEntYear(Integer.parseInt(entYear));
			//クラス
			TestListSubject1.setClassNum(classNum);
			//学生番号
			//氏名
			//得点


		}
	}
}
