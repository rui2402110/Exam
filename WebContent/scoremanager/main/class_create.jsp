<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
<%-- タイトル設定 --%>
<c:param name="title">
得点管理システム
</c:param>
<c:param name="scripts"></c:param>

<%-- メイン --%>
<c:param name="content">
    <section class="mb-3">
    <%-- 見出し --%>
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報登録</h2>
        <div class="my-2 text-end px-4">
        </div>
        <%-- 登録 --%>
        <form method="get" action="ClassCreateExecute.action">
        <%-- 入力欄 --%>
        <div class="col-4">
	        <label class="form-label" for="student-fl-select">クラス</label>
	        <br>
	        <!-- クラス名入力 -->
	        <input type="text" id="name" name="class_num" class="text-input" value="${name}" placeholder="クラス名を入力してください" required>
        </div>

        <%-- -登録ボタン --%>
        <div class="col-2 text-center">
        		<br>
                <button class="btn btn-secondary" id="filter-button">登録して終了</button>
                </div>

                <%-- エラーメッセージ出力 --%>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </form>

        <%-- 戻るリンク --%>
        <div class="my-2 text-end px-4">
            <a href="ClassList.action">戻る</a>
        </div>

</section>
</c:param>
</c:import>