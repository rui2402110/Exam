package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

	// 基本となるSQL文（school_cdによる検索）
	private String baseSql = "select * from student where school_cd= ? ";

	//学生番号からデータを取得するメソッド
	public Student get(String no) throws Exception {
		//SQLに接続
		Connection connection = getConnection();
		PreparedStatement statement = null;
		//使用する変数を定義
		ResultSet rSet = null;
		Student student = null;

		try {
			// 学生番号で検索するSQL文を準備
			statement = connection.prepareStatement("select * from student where no = ?");
			statement.setString(1, no);
			//SQL文を起動
			rSet = statement.executeQuery();

			//結果が存在する場合、studentオブジェクトに変換していく
			if (rSet.next()) {
				student = new Student();
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));

				//関連する学校情報も取得
				SchoolDao schoolDao = new SchoolDao();
				School school = schoolDao.get(rSet.getString("school_cd"));
				student.setSchool(school);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// リソースをclose
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

		return student;
	}

	// ResultSetから学生リストを作成するメソッド
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		// Listを作成
		List<Student> list = new ArrayList<>();
		try {
			//結果セットの各行を学生オブジェクトに変換
			while (rSet.next()) {
				Student student = new Student();
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);

				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	//学校、入学年度、クラス、在学状態を使用したフィルタリング
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		//条件が適用された時にこのStringがbaseSQLに追加される
		String condition = "and ent_year=? and class_num=?";

		//
		String order = "order by no asc";
		String conditionIsAttend = "";

		//受け取ったisAttendがtrueだった場合、is_attendがtrueのものを検索する
		if (isAttend  ==true) {
			conditionIsAttend = "and is_attend=true ";
		}
		try {
			//SQLを連結
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);

			//SQLを実行
			rSet = statement.executeQuery();
			list = postFilter(rSet, school);

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

	//学校、入学年度、在学状態を使用したフィルタリング
	//上記のfilterと似たコードなのでコメントは省く
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String condition = "and ent_year= ? ";
		String order = "order by no asc";
		String conditionIsAttend = "";

		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		try {
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			rSet = statement.executeQuery();
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
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

	//学校と在学状態のみを使用したフィルタリング
	//上記のfilterと似たコードなのでコメントは省く
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String order = "order by no asc";
		String conditionIsAttend = "";

		if (isAttend) {
			conditionIsAttend = "and is_attend=true ";
		}
		try {
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			rSet = statement.executeQuery();
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
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

	// 学生データを保存するメソッド　(INSERT、UPDATEのどちらにも対応)
	public boolean save(Student student) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		boolean result = false;

		try {
			Student existingStudent = get(student.getNo());

			// 学生番号が既に存在する場合はUPDATE、していない場合はINSERTを実行
			if (existingStudent == null) {
				statement = connection.prepareStatement(
						"INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)");
			} else {
				statement = connection.prepareStatement(
						"UPDATE student SET name=?, ent_year=?, class_num=?, is_attend=?, school_cd=? WHERE no=?");
			}

			if (existingStudent == null) {
				// INSERTの場合のデータをセット
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.getIsAttend());
				statement.setString(6, student.getSchool().getCd());
			} else {
				// UPDATEの場合のデータをセット
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.getIsAttend());
				statement.setString(5, student.getSchool().getCd());
				statement.setString(6, student.getNo());
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