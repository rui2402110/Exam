<%-- 登録成功JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- base.jsp を読み込み -->
<c:import url="/common/base.jsp">
<!-- タイトル設定 -->
	<c:param name="title">
		得点管理システム
	</c:param>

	<!-- メイン -->
	<c:param name="content">
		<div id="wrap_box">

			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2"></h2>
			<div id="wrap_box">
			<!-- 完了メッセージ -->
				<p class="text-center" style="background-color:#66CC99">登録が完了しました</p>
			<!-- 戻るリンク -->
				<a href="ClassCreate.action">戻る</a>
			<!-- 一覧へのリンク -->
				<a href="ClassList.action">クラス一覧リンク</a>
			</div>
		</div>
	</c:param>
</c:import>