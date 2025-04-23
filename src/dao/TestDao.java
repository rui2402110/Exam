package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
public class TestDao extends Dao {
	// 基本となるSQL文（subject_cdによる検索）
	private String baseSql = "SELECT * FROM TEST WHERE SUBJECT_CD = ? ";

	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		// SQLに接続
        Connection connection = getConnection();
        PreparedStatement statement = null;
        // 使用する変数を定義
        ResultSet rSet = null;
	    Test test = null;

	    try {
	        // 学校コードで検索するSQL文を準備
	        statement = connection.prepareStatement("SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?");

	        statement.setString(1, student.getNo());
	        statement.setString(2, subject.getCd());
	        statement.setString(3, school.getCd());
	        statement.setInt(4, no);

	        rSet = statement.executeQuery();

	        // 結果が存在する場合、Testオブジェクトを作成
	        if (rSet.next()) {
	            test = new Test();
	            test.setSubject(subject);
	            test.setStudent(student);
	            test.setSchool(school);
	            test.setNo(rSet.getInt("no"));
	            test.setPoint(rSet.getInt("point"));
	            // その他必要なTest属性をセット
	            // 他のTest属性があれば設定
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // リソースをclose
	        if (rSet != null) {
	            try {
	                rSet.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	    }

	    return test;
	}

	private List<Test> postFilter(ResultSet rSet , School school) throws Exception {
		// Listを作成
		List<Test> list = new ArrayList<>();
		try{
			Test test =new Test();
			// 最終的にreturnするデータはstudent_no , subject_cd , school_cd , no , point , class_numなのでそれを取る

			// studentオブジェクトを作成
			Student student = new Student();
			// rSetのデータを使用してstudentからデータを取得
			student.setNo(rSet.getString("student_no"));
			// setterでset
            test.setStudent(student);

            // subjectオブジェクトを作成
            Subject subject = new Subject();
            // rSetのデータを使用してsubjectからデータを取得
            subject.setCd(rSet.getString("subject_cd"));
            // setterでset
            test.setSubject(subject);

			test.setSchool(school);

			test.setNo(rSet.getInt("no"));
			test.setPoint(rSet.getInt("point"));
			test.setClassNum(rSet.getString("class_num"));
		}catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list ;
	}
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws SQLException {
	    List<Test> list = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rSet = null;

	    //条件が適用された時にこのStringがbaseSQLに追加される
	    String condition = "and ent_year=? and class_num=?";
	    String order = "order by no asc";

	    try {
	        connection = getConnection();
	        //SQLを連結
	        statement = connection.prepareStatement(baseSql + condition + order);
	        statement.setString(1, subject.getCd());
	        statement.setInt(2, entYear);
	        statement.setString(3, classNum);

	        //SQLを実行
	        rSet = statement.executeQuery();
	        list = postFilter(rSet, school);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        //リソースをclose
	        if (rSet != null) {
	            try {
	                rSet.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                sqle.printStackTrace();
	            }
	        }
	    }
	    return list;
	}
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		boolean result = false;
		School school = null ;
		try {
			Test test = get(list.get(0), list.get(1), list.get(2), list.get(3));
			if ( test == null) {
				statement = connection.prepareStatement(
						"INSERT INTO TEST (STUDENT_NO , SUBJECT_CD , SCHOOL_CD , NO , POINT , CLASS_NUM) VALUES (?, ?, ?, ?, ?)");
				statement.setString(1, );
				statement.setString(2, );
				statement.setString(3, );
				statement.setString(4, );
				statement.setString(5, );
			} else {

			}
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

		return result ;

	}
	private boolean save(Test test , Connection connection ){
		return result ;
	}

}
