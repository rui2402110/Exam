<%--教員情報変更jsp --%>
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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">教員情報変更</h2>

            <%-- 登録フォーム開始 --%>
            <form method="get" action="TeacherUpdateExecute.action">

                <%-- ID入力欄 --%>
                <div class="col-4">
                    <label class="form-label" for="no">ID</label><br>
                    <input type="text" id="id" name="id" value="${id}" class="text-input"
                           placeholder="IDを入力してください" readonly>

                    <%-- エラー表示（該当データが存在しない場合） --%>
                    <c:if test="${not empty errors['notfound']}">
                        <div class="text-danger">${errors['notfound']}</div>
                    </c:if>
                </div>

                <%-- パスワード入力欄 --%>
                <div class="col-4">
                    <label class="form-label" for="subject-fl-select">パスワード</label><br>
                    <input type="text" id="pass" name="pass" value="${pass}" class="text-input"
                           placeholder="パスワードを入力してください" required>
                </div>

                <%-- 教員名入力欄 --%>
                <div class="col-4">
                    <label class="form-label" for="subject-fl-select">パスワード</label><br>
                    <input type="text" id="name" name="name" value="${name}" class="text-input"
                           placeholder="氏名を入力してください" required>
                </div>

				<%-- 管理者権限入力欄（カスタムスライドスイッチ対応） --%>
				<div class="col-4 d-flex align-items-center">
				    <label class="switch">
				        <input type="checkbox" id="auth" name="auth" value="true"
				            <c:if test="${auth}">checked</c:if>
				        >
				        <span class="slider"></span>
				    </label>
				    <label for="auth" class="ms-2 mb-0">管理者権限を付与</label>
				</div>





                <%-- 登録ボタン --%>
                <div class="col-2">
                    <br>
                    <button class="btn btn-secondary" id="filter-button">変更</button>
                </div>

                <%-- 入力チェックエラー表示 --%>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
            </form>

            <%-- 戻るリンク --%>
            <div>
                <a href="TeacherList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
