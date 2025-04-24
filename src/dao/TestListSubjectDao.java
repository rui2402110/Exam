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
					testListSubject.setEntYear(rSet.getInt("ent_year"));
					testListSubject.setStudentNo(rSet.getString("student_no"));
					testListSubject.setStudentName(rSet.getString("student_name"));
					testListSubject.setClassNum(rSet.getString("class_num"));

					// Mapを定義
					Map<Integer, Integer> points = new HashMap<Integer, Integer>();
					// Mapにそれぞれデータを入れる
					points.put(rSet.getInt("point_1"),rSet.getInt("point_2"));
					// Mapのデータをクラスオブジェクトに変間
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
