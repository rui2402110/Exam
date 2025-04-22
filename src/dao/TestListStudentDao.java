package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListSubject;

public class TestListStudentDao extends Dao {
	// 基本となるSQL文（school_cdによる検索）
	private String baseSql = "select * from test where school_cd= ? ";

	// ResultSetからクラスリストを作成するメソッド
		private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
			// Listを作成
			List<TestListSubject> list = new ArrayList<>();
			try {
				//結果セットの各行をクラスオブジェクトに変換
				while (rSet.next()) {



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
