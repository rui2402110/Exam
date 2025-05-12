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
	private String baseSql = " SELECT TEST.STUDENT_NO as student_no, TEST.SUBJECT_CD, TEST.SCHOOL_CD, TEST.NO, " +
            " TEST.POINT, TEST.CLASS_NUM, STUDENT.ent_year " +
            " FROM TEST " +
            " JOIN STUDENT ON TEST.STUDENT_NO = STUDENT.NO ";

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
		//
		StudentDao stuDao = new StudentDao();
		SubjectDao subDao =new SubjectDao();
		try{
			while (rSet.next()){
				Test test =new Test();
				// 最終的にreturnするデータはstudent , subject , school , no , point , class_numなのでそれを取る

				// studentオブジェクトを作成
				Student student = new Student();
				// rSetのデータを使用してstudentからデータを取得
				String studentNo =rSet.getString("student_no");
				//
				student =stuDao.get(studentNo);
				// setterでset
	            test.setStudent(student);

	            // subjectオブジェクトを作成
	            Subject subject = new Subject();
	            // rSetのデータを使用してsubjectからデータを取得
	            String subjectCd = (rSet.getString("subject_cd"));
	            //
	            subject=subDao.get(subjectCd, school);
	            // setterでset
	            test.setSubject(subject);

				test.setSchool(school);

				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));

				list.add(test);
			}


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
	    String condition = " WHERE TEST.SUBJECT_CD = ? AND TEST.class_num = ? AND STUDENT.ENT_YEAR = ? AND TEST.NO = ? ";
	    String order = "order by no asc";

	    try {
	        connection = getConnection();
	        //SQLを連結
	        statement = connection.prepareStatement(baseSql + condition + order);
	        System.out.println(baseSql + condition + order);
	        statement.setString(1, subject.getCd());
	        statement.setString(2, classNum);
	        statement.setInt(3, entYear);
	        statement.setInt(4, num);
	        System.out.println("1,"+ subject.getCd());
	        System.out.println("2,"+ entYear);
	        System.out.println("3,"+ classNum);

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
		//
		Connection connection = getConnection();
		boolean result = false;

			// トランザクション開始
        	connection.setAutoCommit(false);
        	try{
        		System.out.println("Listサイズ：" + list.size());

        	//送られたlistの回数分saveメソッドを呼び出す
        	for (int i = 0; i < list.size(); i++) {
        		System.out.println("saveメソッド開始");
        		System.out.println(list.get(i));
        		result = save(list.get(i), connection);

        	}
        	} catch(Exception e){
        		throw e;
        	} finally {
        		//connectionの閉じる処理を移動

                if (connection != null) {
                    try {
                    	//connectionのオートコミットが無効になっているので追加
                    	connection.commit();
                        connection.close();
                    } catch (SQLException sqle) {
                        throw sqle;
                    }
                }
            }

        	return result ;
	}

	private boolean save(Test test , Connection connection )throws Exception{

        boolean result = false;
        PreparedStatement statement = null;
        try {
        	// 試験が既に存在するかどうか確認
            Test tes = get(test.getStudent(), test.getSubject(), test.getSchool() , test.getNo());
            // 試験が存在していた場合はUPDATE、しなかった場合はINSERTを実行
            if (tes == null) {
                statement = connection.prepareStatement(
                        "INSERT INTO TEST (STUDENT_NO , SUBJECT_CD , SCHOOL_CD , NO , POINT , CLASS_NUM) VALUES (?, ?, ?, ?, ?)");
                // 送られたtestのデータをセット
                statement.setString(1, test.getStudent().getNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setString(3, test.getSchool().getCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClassNum());
            } else {
                statement = connection.prepareStatement(
                		" UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ? AND CLASS_NUM = ? ");
                // 送られたtestのデータをセット
                statement.setInt(1, test.getPoint());
                statement.setString(2, test.getStudent().getNo());
                statement.setString(3, test.getSubject().getCd());
                statement.setString(4, test.getSchool().getCd());
                statement.setInt(5, test.getNo());
                //足らなかったから追加
                statement.setString(6,test.getClassNum());
            }
            // 実行して影響を受けた行数を確認
            int affected = statement.executeUpdate();
            result = (affected > 0);
            System.out.println(result);
        } catch (Exception e) {
            throw e;
        }finally {
            // リソースを解放
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return result ;
    }

}
