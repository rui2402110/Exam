<%-- ログアウトJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
<%-- タイトル設定 --%>
	<c:param name="title">
		得点管理システム
	</c:param>

<%-- メイン --%>
	<c:param name="content">
		<div id="wrap_box">
		<!-- 見出し -->
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">ログアウト</h2>
			<div id="wrap_box">
			<!-- ログアウトメッセージ -->
				<p class="text-center" style="background-color:#66CC99">ログアウトしました</p>
				<!-- ログインリンク -->
				<a href="../Login.action">ログイン</a>
			</div>
		</div>
	</c:param>
</c:import>