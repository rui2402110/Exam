package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	// 基本となるSQL文（school_cdによる検索）
	private String baseSql = "SELECT  STUDENT_NO, SUBJECT_CD, SCHOOL_CD, MAX(CASE WHEN NO = 1 THEN POINT ELSE NULL END) AS POINT_1, MAX(CASE WHEN NO = 2 THEN POINT ELSE NULL END) AS POINT_2, MAX(CLASS_NUM) AS CLASS_NUMFROM TEST GROUP BY STUDENT_NO, SUBJECT_CD, SCHOOL_CDwhere school_cd= ? ";

	// ResultSetからクラスリストを作成するメソッド
		private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
			// Listを作成
			List<TestListSubject> list = new ArrayList<>();
			try {
				//結果セットの各行をクラスオブジェクトに変換
				while (rSet.next()) {
					TestListSubject testListSubject = new TestListSubject();
					testListSubject.setEntYear(rSet.getInt("entYear"));
					testListSubject.setStudentNo(rSet.getString("studentNo"));
					testListSubject.setStudentName(rSet.getString("studentName"));
					testListSubject.setClassNum(rSet.getString("classnum"));

					Map<Integer, Integer> points = new HashMap<Integer, Integer>();
					points.put(rSet.getInt("point_1"),rSet.getInt("point_2"));
					testListSubject.setPoints(points);

					list.add(testListSubject);
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}

			return list;
		}
		//学校、入学年度、クラスを使用したフィルタリング
		public List<TestListSubject> filter(int entYear , String classNum , Subject subject , School school) throws Exception {
			List<TestListSubject> list = new ArrayList<>();
			Map<Integer, Integer> points = new HashMap<Integer, Integer>();
			Connection connection = getConnection();
			PreparedStatement statement = null;
			ResultSet rSet = null;

			statement = connection.prepareStatement("SELECT SUBJECT_CD, MAX(CASE WHEN NO = 1 THEN POINT ELSE NULL END) AS POINT_1, MAX(CASE WHEN NO = 2 THEN POINT ELSE NULL END) AS POINT_2 FROM TEST GROUP BY STUDENT_NO, SUBJECT_CD, SCHOOL_CD");
			rSet = statement.executeQuery();
			while (rSet.next()) {
			    Integer point1 = rSet.getObject("POINT_1") != null ? rSet.getInt("POINT_1") : null;
			    Integer point2 = rSet.getObject("POINT_2") != null ? rSet.getInt("POINT_2") : null;

			    Map<Integer, Integer> subjectPoints = new HashMap<>();
			    subjectPoints.put(point1 ,  point2);
			}


			//条件が適用された時にこのStringがbaseSQLに追加される
			String condition = "and ent_year=? and class_num=?";

			//
			String order = "order by no asc";

			try {
				//SQLを連結
				statement = connection.prepareStatement(baseSql + condition + order);
				statement.setString(1, school.getCd());
				statement.setInt(2, entYear);
				statement.setString(3, classNum);

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
