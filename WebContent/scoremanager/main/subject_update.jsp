<%--科目情報変更jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 共通テンプレート（ヘッダーなど）を読み込む --%>
<c:import url="/common/base.jsp">

    <%-- 画面タイトルを設定 --%>
    <c:param name="title">
        得点管理システム
    </c:param>

    <%-- 必要なスクリプト領域（今回は空） --%>
    <c:param name="scripts"></c:param>

    <%-- ページコンテンツ開始 --%>
    <c:param name="content">
        <section class="mb-3">
            <%-- 画面見出し --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

            <%-- 登録フォーム開始 --%>
            <form method="get" action="SubjectUpdateExecute.action">

                <%-- 科目コード入力欄（編集不可、readonly） --%>
                <div class="col-4">
                    <label class="form-label" for="no">科目コード</label><br>
                    <input type="text" id="cd" name="cd" value="${cd}" class="text-input"
                           placeholder="科目コードを入力してください" readonly>

                    <%-- エラー表示（該当データが存在しない場合） --%>
                    <c:if test="${not empty errors['notfound']}">
                        <div class="text-danger">${errors['notfound']}</div>
                    </c:if>
                </div>

                <%-- 科目名入力欄 --%>
                <div class="col-4">
                    <label class="form-label" for="subject-fl-select">科目名</label><br>
                    <input type="text" id="name" name="name" value="${name}" class="text-input"
                           placeholder="科目名を入力してください" required>
                </div>

                <%-- 登録ボタン --%>
                <div class="col-2 text-center">
                    <br>
                    <button class="btn btn-secondary" id="filter-button">変更</button>
                </div>

                <%-- 入力チェックエラー表示 --%>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
            </form>

            <%-- 戻るリンク --%>
            <div>
                <a href="SubjectList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
