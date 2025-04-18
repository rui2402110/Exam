package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
    // 基本となるSQL文（school_cdによる検索）
    private String baseSql = "select * from subject ";

    // subjectのデータを取得するメソッド
    public Subject get(String cd , School school) throws Exception {
        // SQLに接続
        Connection connection = getConnection();
        PreparedStatement statement = null;
        // 使用する変数を定義
        ResultSet rSet = null;
        Subject subject = null;

        try {
            // 学校コードで検索するSQL文を準備
            statement = connection.prepareStatement("select * from subject where subject_cd = ?");

            // JSPから受け取ったschoolからsubject_cdを割り出す
            statement.setString(1, school.getCd());
            rSet = statement.executeQuery();

            // 結果が存在する場合、studentオブジェクトに変換していく
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
}