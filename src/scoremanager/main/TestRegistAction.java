package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {
	@Override

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		Test test =new Test();
//		Student student =new Student();
//		Subject subject =new Subject();


		// 値の取得に使う変数を定義
		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";
		String countStr ="";
		int entYear = 0;
		boolean isAttend =true;

//		Map<String, String> errorMap = new HashMap<>();


		// 使用するDAOを定義
		ClassNumDao cNumDao =new ClassNumDao();
		SubjectDao subDao =new SubjectDao();
		StudentDao stuDao =new StudentDao();
		TestDao testDao =new TestDao();

		// 入力された変数を取得
		entYearStr = req.getParameter("f1");//入学年度
		classNum = req.getParameter("f2");//クラス
		subjectCd = req.getParameter("f3");//科目コード
		countStr = req.getParameter("f4");//回数
		String error1 =req.getParameter("error1");

		System.out.println(entYearStr);
		System.out.println(classNum);
		System.out.println(subjectCd);
		System.out.println(countStr);

		// 表示用の年度リストを作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
		    entYearSet.add(i);
		}
//
		if (entYearStr != null && !entYearStr.isEmpty()) {
		    entYear = Integer.parseInt(entYearStr);
		}
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classList = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> subList = subDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに一覧を取得
		List<Student> stuList = stuDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
		// 入学年度とクラス番号を指定

		System.out.println("stuList: " +teacher.getSchool()+ " : "+entYear+ " : "+ classNum+ " : "+ isAttend);
		// JSPに送るデータをセット
		req.setAttribute("ent_year_set",entYearSet);
		req.setAttribute("class_num_set",classList);
		req.setAttribute("subject_set", subList);
		req.setAttribute("student_set", stuList);

		System.out.println("取得した学生数: " + stuList.size());



        List<Test> testList = null ;
        if (entYearStr  != null || classNum != null || subjectCd != null || countStr != null){
        	System.out.println("nullじゃないです");
        	// 入力検証
            if (entYearStr.equals("0") || classNum.equals("0") || subjectCd.equals("0") || countStr.equals("0") ) {
            	System.out.println("エラー通ってます");
                req.setAttribute("error1", "入学年度とクラスと科目と回数を選択してください");
                req.setAttribute("f1", entYearStr);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("f4", countStr);
                req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            }
            // ログインユーザーの学校コードをもとに一覧を取得
            testList = testDao.filter(entYear, classNum, subDao.get(subjectCd, teacher.getSchool()), Integer.parseInt(countStr), teacher.getSchool());
            System.out.println(testList.size());
            // testListの中身を確認
            System.out.println("取得したテスト数: " + testList.size());

            	for (Test test1 : testList) {
            		System.out.println("学生番号: " + test1.getStudent().getNo());
            		System.out.println("科目コード: " + test1.getSubject().getCd());
            		System.out.println("点数: " + test1.getPoint());
            		System.out.println("クラス: " + test1.getClassNum());
            	}
        }

//        //listをStudent studentに変換
//        for (Student student : stuList) {
//        	test.setStudent(student);
//        }

//        test.setSubject(subDao.get(subjectCd,teacher.getSchool()));
        req.setAttribute("points", testList);
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", countStr);
        req.setAttribute("error1", error1);
		// フォワード
	    req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

}