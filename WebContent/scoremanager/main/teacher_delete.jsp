<%--教員削除jsp --%>
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
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">教員情報削除</h2>
	<%-- formタグを設定 あて先はExecuteAction --%>
    <form method="post" action="TeacherDeleteExecute.action" class="px-4">
    	<%--メッセージ --%>
        <p class="text-danger">
            「${teacher_name}（${id}）」を削除してよろしいですか？
        </p>

        <%-- hidden inputで情報を送信 --%>
        <input type="hidden" name="id" value="${id}">
        <input type="hidden" name="teacher_name" value="${teacher_name}">
		<%--削除ボタン --%>
        <div class="my-2">
            <input type="submit" value="削除" class="btn btn-danger">
        </div>
    </form>

	<%--戻るリンク --%>
    <div class="mt-3">
        <a href="TeacherList.action">戻る</a>
    </div>
</section>

</c:param>
</c:import>