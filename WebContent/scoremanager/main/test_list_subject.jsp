<%--成績管理・科目jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
<%--タイトル設定 --%>
<c:param name="title">
得点管理システム
</c:param>
<c:param name="scripts"></c:param>

<%--メイン --%>
<c:param name="content">

<section class="mb-3">
<%--見出し --%>
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

    <%-- 検索条件1：入学年度・クラス・科目 --%>
    <p class="fw-bold px-4">科目情報</p>
    <%-- formタグを設定 あて先はExecuteAction --%>
    <form method="get" action="TestListSubjectExecute.action">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">

        <%--入学年度 --%>
            <div class="col-2">
                <label class="form-label">入学年度</label>
                <select class="form-select" name="f1">
                    <option value="0">-----</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
            </div>


       <%--クラス --%>
            <div class="col-2">
                <label class="form-label">クラス</label>
                <select class="form-select" name="f2">
                    <option value="0">-----</option>
                    <c:forEach var="classNum" items="${class_num_set}">
                        <option value="${classNum}" <c:if test="${classNum==f2}">selected</c:if>>${classNum}</option>
                    </c:forEach>
                </select>
            </div>

       <%--科目 --%>
            <div class="col-4">
                <label class="form-label">科目</label>
                <select class="form-select" name="f3">
                    <option value="0">-----</option>
                    <c:forEach var="subject" items="${subject_set}">
                        <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
                    </c:forEach>
                </select>
            </div>

       <%--検索ボタン --%>
            <div class="col-2 text-center">
                <button class="btn btn-secondary">検索</button>
            </div>
            <input type="hidden" name="f" value="sj">
        </div>
    </form>

    <%-- 検索条件2：学生番号 --%>
    <p class="fw-bold px-4">学生情報</p>
    <%-- formタグを設定 あて先はExecuteAction --%>
    <form method="get" action="TestListStudentExecute.action">

        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">
            <div class="col-2">
                <label class="form-label">学生番号</label>
            </div>
            <div class="col-4">
                <input type="text" class="form-control" name="f4" value="${f4}" placeholder="学生番号を入力してください">
            </div>
            <div class="col-2 text-center">
                <button class="btn btn-secondary">検索</button>
            </div>
            <input type="hidden" name="f" value="st">
        </div>
    </form>

    <%-- ✅ エラーメッセージまとめ --%>
    <c:if test="${not empty errors['f1']}">
        <div class="text-danger mx-3">${errors['f1']}</div>
    </c:if>
    <c:if test="${empty test_list && not empty searched}">
        <div class="text-danger mx-3">学生情報が存在しませんでした</div>
    </c:if>

    <%-- 成績一覧表示 --%>
    <c:if test="${not empty test_list}">
        <div class="px-4 mb-2">
            科目：<c:out value="${subjectCd}" />
        </div>
        <table class="table table-hover">
            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>1回目の点数</th>
                <th>2回目の点数</th>
            </tr>
            <c:forEach var="test" items="${test_list}">
                <tr>
                    <td>${test.entYear}</td>
                    <td>${test.classNum}</td>
                    <td>${test.studentNo}</td>
                    <td>${test.studentName}</td>
                    <td>${test.getPoint(1)}</td>
                    <td>${test.getPoint(2)}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <%-- 成績一覧（学生） --%>
    <c:if test="${not empty student_info}">
       <div class="px-4 mb-2">
        氏名：${student_info.name}（${student_info.no}）
       </div>
         <table class="table table-hover">
                <tr>
                   <th>科目名</th>
                   <th>科目コード</th>
                   <th>回数</th>
                   <th>点数</th>
                </tr>
        <c:forEach var="score" items="${score_list}">
            <tr>
                <td>${score.subject_name}</td>
                <td>${score.subject_cd}</td>
                <td>${score.count}</td>
                <td>${score.point}</td>
            </tr>
          </c:forEach>
        </table>
      </c:if>


    <%-- 利用方法メッセージ --%>
    <div class="text-info mx-3">
        科目情報を選択または学生番号を入力して検索ボタンをクリックしてください
    </div>
</section>

</c:param>
</c:import>
=======
<%--見出し --%>
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧(科目)</h2>

    <%-- 検索条件1：入学年度・クラス・科目 --%>
    <p class="fw-bold px-4">科目情報</p>
    <form method="get" action="TestListSubjectExecute.action">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">

        <%--入学年度 --%>
            <div class="col-2">
                <label class="form-label">入学年度</label>
                <select class="form-select" name="f1">
                    <option value="0">-----</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
            </div>

        <%--クラス --%>
            <div class="col-2">
                <label class="form-label">クラス</label>
                <select class="form-select" name="f2">
                    <option value="0">-----</option>
                    <c:forEach var="classNum" items="${class_num_set}">
                        <option value="${classNum}" <c:if test="${classNum==f2}">selected</c:if>>${classNum}</option>
                    </c:forEach>
                </select>
            </div>

        <%--科目 --%>
            <div class="col-4">
                <label class="form-label">科目</label>
                <select class="form-select" name="f3">
                    <option value="0">-----</option>
                    <c:forEach var="subject" items="${subject_set}">
                        <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
                    </c:forEach>
                </select>
            </div>

         <%--検索 --%>
            <div class="col-2 text-center">
                <button class="btn btn-secondary">検索</button>
            </div>
            <input type="hidden" name="f" value="sj">
        </div>
    </form>

    <%-- 検索条件2：学生番号 --%>
    <p class="fw-bold px-4">学生情報</p>
    <form method="get" action="TestListStudentExecute.action">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">
            <div class="col-2">
                <label class="form-label">学生番号</label>
            </div>
            <div class="col-4">
                <input type="text" class="form-control" name="f4" value="${f4}" placeholder="学生番号を入力してください">
            </div>
            <div class="col-2 text-center">
                <button class="btn btn-secondary">検索</button>
            </div>
            <input type="hidden" name="f" value="st">
        </div>
    </form>

    <%-- エラーメッセージ --%>
    <c:if test="${not empty errors['f1']}">
        <div class="text-danger mx-3">${errors['f1']}</div>
    </c:if>

    <%-- 成績一覧表示 --%>
    <c:if test="${not empty test_list}">
        <div class="px-4 mb-2">
            科目：<c:out value="${subject_name}" />
        </div>
        <table class="table table-hover">
            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>1回目の点数</th>
                <th>2回目の点数</th>
            </tr>
            <c:forEach var="test" items="${test_list}">
                <tr>
                    <td>${test.entYear}</td>
                    <td>${test.classNum}</td>
                    <td>${test.studentNo}</td>
                    <td>${test.studentName}</td>
                    <td>${test.getPoints().get(1)}</td>
                    <td>${test.getPoints().get(2)}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <%-- 利用方法メッセージ --%>
    <div class="text-info mx-3">
        科目情報を選択または学生番号を入力して検索ボタンをクリックしてください
    </div>

