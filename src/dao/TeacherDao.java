package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {
	/**
	 * getメソッド 教員IDを指定して教員インスタンスを1件取得する
	 *
	 * @param id:String
	 *            教員ID
	 * @return 教員クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Teacher get(String id) throws Exception {
		// 教員インスタンスを初期化
		Teacher teacher = new Teacher();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from teacher where id=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
				teacher.setId(resultSet.getString("id"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setName(resultSet.getString("name"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				teacher.setSchool(schoolDao.get(resultSet.getString("school_cd")));
				teacher.setAuth(resultSet.getBoolean("auth"));
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスにnullをセット
				teacher = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return teacher;
	}

	/**
	 * loginメソッド 教員IDとパスワードで認証する
	 *
	 * @param id:String
	 *            教員ID
	 * @param password:String
	 *            パスワード
	 * @return 認証成功:教員クラスのインスタンス, 認証失敗:null
	 * @throws Exception
	 */
	public Teacher login(String id, String password) throws Exception {
		// 教員クラスのインスタンスを取得
		Teacher teacher = get(id);
		// 教員がnullまたはパスワードが一致しない場合
		if (teacher == null || !teacher.getPassword().equals(password)) {
			return null;
		}
		return teacher;
	}
	public List<Teacher> filter(School school) throws Exception {
	    List<Teacher> list = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rSet = null;

	    try {
	        connection = getConnection();
	        statement = connection.prepareStatement("SELECT * FROM TEACHER WHERE SCHOOL_CD = ?");
	        statement.setString(1, school.getCd());

	        // Execute the query - this was missing in the original code
	        rSet = statement.executeQuery();

	        while (rSet.next()) {
	            Teacher teacher = new Teacher();
	            teacher.setAuth(rSet.getBoolean("AUTH"));
	            teacher.setId(rSet.getString("ID"));
	            teacher.setPassword(rSet.getString("PASSWORD"));
	            teacher.setName(rSet.getString("NAME"));
	            teacher.setSchool(school);
	            list.add(teacher);
	        }
	    } catch (SQLException e) {
	        throw e;
	    } finally {
	        // Close resources in reverse order of creation
	        if (rSet != null) {
	            try {
	                rSet.close();
	            } catch (SQLException e) {
	                // Log exception but continue closing resources
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                // Log exception but continue closing resources
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                throw e; // Re-throw the exception if connection close fails
	            }
	        }
	    }

	    return list;
	}

	// ユーザーを保存するメソッド　(INSERT、UPDATEのどちらにも対応予定)
	public boolean save(Teacher teacher)throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		boolean result = false;
		School school = null ;

		try{
			Teacher teach =get(teacher.getId());
			// Idが既に存在する場合はUPDATE、していない場合はINSERTを実行
			if(teach ==null){
				statement = connection.prepareStatement(
						"INSERT INTO TEACHER ( ID, PASSWORD, NAME, SCHOOL_CD, AUTH) VALUES(?, ?, ?, ?, ?)");
				statement.setString(1, teacher.getId());
				statement.setString(2, teacher.getPassword());
				statement.setString(3, teacher.getName());
				statement.setString(4, teacher.getSchool().getCd());
				statement.setBoolean(5, teacher.getAuth());
			}else{
				statement = connection.prepareStatement(
						"UPDATE TEACHER SET PASSWORD = ? , NAME = ? , SCHOOL_CD = ? ,AUTH=? WHERE ID = ?");
				statement.setString(1, teacher.getPassword());
				statement.setString(2, teacher.getName());
				statement.setString(3, teacher.getSchool().getCd());
				statement.setBoolean(4, teacher.getAuth());
				statement.setString(5, teacher.getId());
			}
			// 実行して影響を受けた行数を確認
			int affected = statement.executeUpdate();
			result = (affected > 0);
		} catch (Exception e) {
			throw e;
		} finally {
			// リソースを解放
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
		return result;
	}
}
