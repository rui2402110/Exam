<%--科目登録jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
<%--タイトル --%>
<c:param name="title">
得点管理システム

</c:param>
<c:param name="scripts"></c:param>

<%--メイン --%>
<c:param name="content">
    <section class="mb-3">
    <%--見出し --%>
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
        <div class="my-2 text-end px-4">
        </div>

        <%-- formタグを設定 あて先はExecuteAction --%>
        <form method="get" action="SubjectCreateExecute.action">

		<%--科目コード --%>
        <div class="col-4">
	        <label class="form-label" for="cd">科目コード</label>
	        <br>
	        <input type="text" id="cd" name="cd" value="${cd_set}" class="text-input" maxlength="3" placeholder="科目コードを入力してください" required>
	        <%--エラー処理 --%>
    			<div style="color: orange;">${errors1}</div>
    			<div style="color: orange;">${errors2}</div>
        </div>


		<%--科目名 --%>
        <div class="col-4">
	        <label class="form-label" for="name">科目名</label>
	        <br>
	        <input type="text" id="name" name="name" value="${name_set}" class="text-input" maxlength="20" placeholder="科目名を入力してください" required>
        </div>


        <div class="col-2">
         <br>
         <%--登録ボタン --%>
         <button class="btn btn-secondary" id="filter-button">登録</button>
        </div>
        <%--エラー処理 --%>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </form>
        <div >
        <%--戻るリンク --%>
            <a href="SubjectList.action">戻る</a>
        </div>

</section>
</c:param>
</c:import>