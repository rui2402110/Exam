package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;
import bean.TestListSubject;

public class TestListStudentDao extends Dao {
	// 基本となるSQL文（student_noによる検索）
	private String baseSql = "SELECT " +
        "   STUDENT.NAME AS STUDENT_NAME, " +
        "   STUDENT.NO AS STUDENT_NO, " +
        "   SUBJECT.NAME AS SUBJECT_NAME, " +
        "   TEST.SUBJECT_CD, " +
        "   MAX(CASE WHEN TEST.NO = 1 THEN TEST.NO ELSE NULL END) AS NO_1, " +
        "   MAX(CASE WHEN TEST.NO = 1 THEN TEST.POINT ELSE NULL END) AS POINT_1, " +
        "   MAX(CASE WHEN TEST.NO = 2 THEN TEST.NO ELSE NULL END) AS NO_2, " +
        "   MAX(CASE WHEN TEST.NO = 2 THEN TEST.POINT ELSE NULL END) AS POINT_2 " +
        "FROM " +
        "   STUDENT " +
        "JOIN " +
        "   TEST ON STUDENT.NO = TEST.STUDENT_NO AND STUDENT.SCHOOL_CD = TEST.SCHOOL_CD " +
        "JOIN " +
        "   SUBJECT ON TEST.SUBJECT_CD = SUBJECT.CD AND TEST.SCHOOL_CD = SUBJECT.SCHOOL_CD " +
        "WHERE " +
        "   STUDENT.NO = ? " + // ここにデータを入力
        "GROUP BY " +
        "   STUDENT.NAME, STUDENT.NO, SUBJECT.NAME, TEST.SUBJECT_CD " +
        "ORDER BY " +
        "   SUBJECT.NAME";

	// ResultSetからクラスリストを作成するメソッド
		private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
			// Listを作成
			List<TestListSubject> list = new ArrayList<>();
			try {
				//結果セットの各行をクラスオブジェクトに変換
				while (rSet.next()) {
					TestListStudent testListStudent = new TestListStudent();
					testListStudent.setSubjectName(rSet.getString("subject_name"));
					testListStudent.setSubjectCd(rSet.getString("subject_cd"));
					testListStudent.setNum(rSet.getInt("subject_num"));
					testListStudent.setPoint(rSet.getInt("point"));
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}

			return list;
		}
		//学校、入学年度、クラスを使用したフィルタリング
		public List<TestListSubject> filter(Student student) throws Exception {
			List<TestListSubject> list = new ArrayList<>();
			Connection connection = getConnection();
			PreparedStatement statement = null;
			ResultSet rSet = null;

			//条件が適用された時にこのStringがbaseSQLに追加される
			String condition = "and ent_year=? and class_num=?";

			//
			String order = "order by no asc";

			try {
				//SQLを連結
				statement = connection.prepareStatement(baseSql + condition + order);
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
