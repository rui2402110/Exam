/**
 * 教員データをDTO (Data Transfer Object) として扱うクラス。
 * データ転送時にパスワードをマスクするなど、加工を施して利用する。
 */
package dto;  // DTO用のパッケージ

import bean.Teacher;

public class TeacherDTO {
    private String id;
    private String name;
    private String maskedPassword;
    private String school;

    /**
     * 教員オブジェクトからDTOオブジェクトを生成
     * パスワードは「******」でマスク
     * 学校情報は名前を取得してセット
     */
    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.maskedPassword = "******";  // パスワードの加工
        this.school = teacher.getSchool().getName();
    }

    // Getterメソッド
    public String getId() { return id; }
    public String getName() { return name; }
    public String getMaskedPassword() { return maskedPassword; }
    public String getSchool() { return school; }
}