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
	private String baseSql ="SELECT " +
            " STUDENT.ENT_YEAR, " +
            " STUDENT.NAME, " +
            " TEST.STUDENT_NO, " +
            " TEST.SUBJECT_CD, " +
            " TEST.SCHOOL_CD, " +
            " MAX(CASE WHEN TEST.NO = 1 THEN TEST.POINT ELSE NULL END) AS POINT_1, " +
            " MAX(CASE WHEN TEST.NO = 2 THEN TEST.POINT ELSE NULL END) AS POINT_2, " +
            " MAX(TEST.CLASS_NUM) AS CLASS_NUM " +
            "FROM " +
            " TEST " +
            "JOIN " +
            " STUDENT " +
            " ON " +
            " TEST.STUDENT_NO = STUDENT.NO " +
            " WHERE " +
            " TEST.SCHOOL_CD = ? " ;

	// ResultSetからクラスリストを作成するメソッド
		private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
			// Listを作成
			List<TestListSubject> list = new ArrayList<>();
			try {
				System.out.println("xxx");
				//結果セットの各行をクラスオブジェクトに変換
				while (rSet.next()) {
					System.out.println("aaa2");
					TestListSubject testListSubject = new TestListSubject();
					testListSubject.setEntYear(rSet.getInt("ent_year"));
					testListSubject.setClassNum(rSet.getString("class_num"));
					testListSubject.setStudentNo(rSet.getString("student_no"));
					testListSubject.setStudentName(rSet.getString("name"));
//					if (rSet.getString("point_1") !=null ){
//						System.out.println(rSet.getInt("point_1"));
//						testListSubject.putPoint(1,rSet.getInt("point_1"));
//					} else{
//						testListSubject.putPoint(1,-1);
//					}
//					if (rSet.getString("point_2") !=null ){
//						testListSubject.putPoint(2,rSet.getInt("point_2"));
//					} else{
//						testListSubject.putPoint(2,-1);
//					}
//					System.out.println("aaa");
//					System.out.println(rSet.getInt("point_1"));


					// Mapを定義
					Map<Integer, Integer> points = new HashMap<Integer, Integer>();
					// Mapにそれぞれデータを入れる
					int point1 = rSet.getInt("point_1");
					points.put(1,point1);
					points.put(2,rSet.getInt("point_2"));
					System.out.println(rSet.getInt("point_1"));
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
			String condition = " AND STUDENT.ENT_YEAR = ? AND TEST.CLASS_NUM = ? ";
			//
			String order = " GROUP BY STUDENT.ENT_YEAR, STUDENT.NAME, TEST.STUDENT_NO, TEST.SUBJECT_CD, TEST.SCHOOL_CD ORDER BY TEST.STUDENT_NO ASC, TEST.SUBJECT_CD ASC";

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
