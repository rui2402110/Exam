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
    // 基本となるSQL文（school_cdによる検索）
    private String baseSql = "select * from subject where school_cd= ? ";

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

		String order = "order by cd asc";
		try {
			//SQLを連結
			statement = connection.prepareStatement(baseSql + order);
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

 		try {
 			Subject existingSchool = get(subject.getCd() ,subject.getSchool());

 			// 科目コードが既に存在する場合はUPDATE、していない場合はINSERTを実行
 			if (existingSchool == null) {
 				statement = connection.prepareStatement(
 						"INSERT INTO SUBJECT (school_cd , cd , name) VALUES (?, ?, ?)");
 			} else {
 				statement = connection.prepareStatement(
 						"UPDATE subject SET school_cd = ? , cd = ? , name = ? WHERE cd = ?");
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
