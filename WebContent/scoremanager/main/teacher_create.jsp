<%--科目登録jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
<c:param name="title">
得点管理システム
</c:param>
<c:param name="scripts"></c:param>

<%--メイン --%>
<c:param name="content">
    <section class="mb-3">
    <%--見出し --%>
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">新規教員情報登録</h2>
        <div class="my-2 text-end px-4">
        </div>

        <%-- formタグを設定 あて先はExecuteAction --%>
        <form method="get" action="TeacherCreateExecute.action">

		<%--ID --%>
        <div class="col-4">
	        <label class="form-label" for="id">ユーザーID</label>
	        <br>
	        <input type="text" id="id" name="id" value="${id_set}" class="text-input" placeholder="新規教員IDを入力してください" required maxlength="10">
        </div>
        <%--password --%>
        <div class="col-4">
	        <label class="form-label" for="pass1">パスワード</label>
	        <br>
	        <input type="text" id="pass1" name="pass1" value="${pass1_set}" class="text-input" placeholder="パスワードを入力してください" required maxlength="30">
	        <br>
	        <input type="text" id="pass2" name="pass2" value="${pass2_set}" class="text-input" placeholder="同じパスワードを入力してください" required maxlength="30">
	        <%--エラー処理 --%>
    			<div style="color: orange;">${errors1}</div>
    			<div style="color: orange;">${errors2}</div>
        </div>

		<%--氏名 --%>
        <div class="col-4">
	        <label class="form-label" for="name">教員名</label>
	        <br>
	        <input type="text" id="name" name="name" value="${name_set}" class="text-input" placeholder="教員名を入力してください" required maxlength="10">
        </div>

        <%--管理者権限 --%>
        <div class = "col-4">
        	<input type="checkbox" id="auth" name="auth" value="true"> 管理者権限を付与
        </div>
        <div class="col-2 text-center">
         <br>
         <%--登録ボタン --%>
         <button class="btn btn-secondary" id="filter-button">登録</button>
        </div>
        <%--エラー処理 --%>
        <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </form>
        <div>
        <%--戻るリンク --%>
            <a href="TeacherList.action">戻る</a>
        </div>

</section>
</c:param>
</c:import>
