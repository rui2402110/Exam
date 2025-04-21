//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import bean.School;
//import bean.Student;
//
//public class TestListSubjectDao extends Dao {
//	// 基本となるSQL文（school_cdによる検索）
//	private String baseSql = "select * from test where school_cd= ? ";
//
//	// ResultSetから学生リストを作成するメソッド
//		private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
//			// Listを作成
//			List<Student> list = new ArrayList<>();
//			try {
//				//結果セットの各行を学生オブジェクトに変換
//				while (rSet.next()) {
//					Student student = new Student();
//					student.setNo(rSet.getString("no"));
//					student.setName(rSet.getString("name"));
//					student.setEntYear(rSet.getInt("ent_year"));
//					student.setClassNum(rSet.getString("class_num"));
//					student.setAttend(rSet.getBoolean("is_attend"));
//					student.setSchool(school);
//
//					list.add(student);
//				}
//			} catch (SQLException | NullPointerException e) {
//				e.printStackTrace();
//			}
//
//			return list;
//		}
//		//学校、入学年度、クラス、在学状態を使用したフィルタリング
//		public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
//			List<Student> list = new ArrayList<>();
//			Connection connection = getConnection();
//			PreparedStatement statement = null;
//			ResultSet rSet = null;
//
//			//条件が適用された時にこのStringがbaseSQLに追加される
//			String condition = "and ent_year=? and class_num=?";
//
//			//
//			String order = "order by no asc";
//			String conditionIsAttend = "";
//
//			//受け取ったisAttendがtrueだった場合、is_attendがtrueのものを検索する
//			if (isAttend  ==true) {
//				conditionIsAttend = "and is_attend=true ";
//			}
//			try {
//				//SQLを連結
//				statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
//				statement.setString(1, school.getCd());
//				statement.setInt(2, entYear);
//				statement.setString(3, classNum);
//
//				//SQLを実行
//				rSet = statement.executeQuery();
//				list = postFilter(rSet, school);
//
//			} catch (Exception e) {
//				throw e;
//			} finally {
//				//リソースをclose
//				if (statement != null) {
//					try {
//						statement.close();
//					} catch (SQLException sqle) {
//						throw sqle;
//					}
//				}
//				if (connection != null) {
//					try {
//						connection.close();
//					} catch (SQLException sqle) {
//						throw sqle;
//					}
//				}
//			}
//			return list;
//		}
//
//}
