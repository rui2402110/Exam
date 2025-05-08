<%-- 登録成功JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">

<%--タイトル設定 --%>
	<c:param name="title">
		得点管理システム
	</c:param>

<%--メイン --%>
	<c:param name="content">
		<div id="wrap_box">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2"></h2>
			<div id="wrap_box">
			<%--成功メッセージ --%>
				<p class="text-center" style="background-color:#66CC99">変更が完了しました</p>
				<%--学生番号一覧リンク --%>
				<a href="StudentList.action">学生番号一覧リンク</a>
			</div>
		</div>
	</c:param>
</c:import>