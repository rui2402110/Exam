package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	// 基本となるSQL文（student_noによる検索）// 明日CLASS_NUMをSQLで取るところから
	private String baseSql = "SELECT " +
            "  STUDENT.NAME AS STUDENT_NAME, " +
            "  STUDENT.CLASS_NUM AS CLASS_NUM, " +
            "  STUDENT.NO AS STUDENT_NO, " +
            "  SUBJECT.NAME AS SUBJECT_NAME, " +
            "  SUBJECT.CD AS SUBJECT_CD, " +
            "  TEST.SUBJECT_CD, " +
            "  TEST.NO AS TEST_NO, " +
            "  TEST.POINT AS TEST_POINT " +
            "FROM " +
            "  STUDENT " +
            "JOIN " +
            "  TEST ON STUDENT.NO = TEST.STUDENT_NO AND STUDENT.SCHOOL_CD = TEST.SCHOOL_CD " +
            "JOIN " +
            "  SUBJECT ON TEST.SUBJECT_CD = SUBJECT.CD AND TEST.SCHOOL_CD = SUBJECT.SCHOOL_CD " +
            "WHERE " +
            "  STUDENT.NO = ? " + //ここにデータを入力
            "ORDER BY " +
            "  STUDENT.NO, SUBJECT.CD, TEST.NO";

	// ResultSetからクラスリストを作成するメソッド
		private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
			// Listを作成
			List<TestListStudent> list = new ArrayList<>();
			try {
				//結果セットの各行をクラスオブジェクトに変換
				while (rSet.next()) {
					TestListStudent testListStudent = new TestListStudent();
					testListStudent.setSubjectName(rSet.getString("subject_name"));
					testListStudent.setSubjectCd(rSet.getString("subject_cd"));
					testListStudent.setNum(rSet.getInt("TEST_NO"));
					testListStudent.setPoint(rSet.getInt("TEST_POINT"));
					System.out.println(rSet.getString("subject_name"));
					System.out.println(rSet.getString("subject_cd"));
					System.out.println(rSet.getInt("TEST_NO"));
					System.out.println(rSet.getInt("TEST_POINT"));
					System.out.println("------------------");

					list.add(testListStudent);
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}

			return list;
		}
		//学校、入学年度、クラスを使用したフィルタリング
		public List<TestListStudent> filter(Student student) throws Exception {
			List<TestListStudent> list = new ArrayList<>();
			Connection connection = getConnection();
			PreparedStatement statement = null;
			ResultSet rSet = null;

			try {
				//SQLを連結
				statement = connection.prepareStatement(baseSql);
				statement.setString(1, student.getNo());

				//SQLを実行
				rSet = statement.executeQuery();

				list = postFilter(rSet);

			} catch (Exception e) {
				throw e;
			} finally {
				//リソースをclose
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
			}
			return list;
		}
}
