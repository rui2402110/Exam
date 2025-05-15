<%--生徒情報登録jsp --%>
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
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">CSVを利用した学生登録</h2>
        <div class="my-2 text-end px-4">
        </div>

        <%-- formタグを設定 あて先はExecuteAction --%>
        <form method="post" action="CsvExecute.action" enctype="multipart/form-data">

        	<div>
     			<input type="file" name="csv">
    		</div>
    		<div>
    			<input type="submit" value="送信する">
    		</div>

        </form>

        <%-- 戻るボタン --%>
        <div class="my-2 text-end px-4">
            <a href="StudentList.action">戻る</a>
        </div>


</section>
</c:param>
</c:import>