package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    // subjectのデータを取得するメソッド
    public Subject get(String cd , School school) throws Exception {// cdがsubject_cd
        // SQLに接続
        Connection connection = getConnection();
        PreparedStatement statement = null;
        // 使用する変数を定義
        ResultSet rSet = null;
        Subject subject = null;

        try {
            // 学校コードで検索するSQL文を準備
            statement = connection.prepareStatement("select * from subject where cd = ? AND school_cd = ?");

            // JSPから受け取ったschoolからsubject_cdを割り出す
            statement.setString(1, cd);
            statement.setString(2, school.getCd());
            rSet = statement.executeQuery();

            // 結果が存在する場合、subjectオブジェクトに変換していく
            if (rSet.next()) {
                subject = new Subject();
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);

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
        return subject;
    }


    //学校コードを使用したフィルタリング
    public List<Subject> filter(School school) throws Exception {
    	List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement("select * from subject where school_cd= ? order by cd asc");
			statement.setString(1, school.getCd());

			//SQLを実行
			rSet = statement.executeQuery();

			while (rSet.next()) {
				Subject subject = new Subject();
			    subject.setCd(rSet.getString("cd"));
			    subject.setName(rSet.getString("name"));
			    list.add(subject); // リストに追加
			}
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

    // 学生データを保存するメソッド　(INSERT、UPDATEのどちらにも対応)
	public boolean save(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		boolean result = false;
		School school = null ;

		try {
//			System.out.println(subject.getCd());
//			System.out.println(subject.getSchool());
			Subject subj = get(subject.getCd() ,subject.getSchool());
//			System.out.println(subj);

			// 科目コードが既に存在する場合はUPDATE、していない場合はINSERTを実行
			if (subj == null) {
				statement = connection.prepareStatement(
						"INSERT INTO SUBJECT (school_cd , cd , name) VALUES (?, ?, ?)");
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
			} else {
				statement = connection.prepareStatement(
						"UPDATE subject SET school_cd = ? , cd = ? , name = ? WHERE cd = ?");
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
				statement.setString(4, subject.getCd());
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
	public boolean delete(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		boolean result = false;
		try {
			Subject existingSchool = get(subject.getCd() ,subject.getSchool());

			// 貰った値から科目コードとクラスコードを取得
			String subjectCd = "";
			School school = null ;
			String schoolCd = "";
			subjectCd = subject.getCd();
			school = subject.getSchool();
			schoolCd = school.getCd();

			System.out.println("削除対象: cd=" + subject.getCd() + ", school_cd=" + subject.getSchool().getCd());


			// 科目コードが存在する場合はDELETEを実行
			if (existingSchool != null) {
				statement = connection.prepareStatement(
						"DELETE from subject where cd= ? and school_cd= ? ");
				statement.setString(1, subjectCd);
	            statement.setString(2, schoolCd);
	            statement.executeUpdate();
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
				}
			return result;

	}


}