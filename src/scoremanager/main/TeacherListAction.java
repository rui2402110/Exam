package scoremanager.main;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // DAOを利用して教師データを取得
        TeacherDao tDao = new TeacherDao();
        List<TeacherDTO> teacherList = tDao.filter(teacher.getSchool())
            .stream()  // ここで Stream を作成
            .map(TeacherDTO::new) // DTOへ変換
            .collect(Collectors.toList());

        // JSPにデータをセット
        req.setAttribute("teacher", teacherList);
        req.getRequestDispatcher("teacher_list.jsp").forward(req, res);
    }
}

// DTO (加工用データクラス)
class TeacherDTO {
    private String id;
    private String name;
    private String maskedPassword;
    private String school;

    // コンストラクタでデータを加工
    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.maskedPassword = "******"; // パスワードを伏せる
        this.school = teacher.getSchool().getName();
    }

    // Getter (必要に応じて追加)
    public String getId() { return id; }
    public String getName() { return name; }
    public String getMaskedPassword() { return maskedPassword; }
    public String getSchool() { return school; }
}