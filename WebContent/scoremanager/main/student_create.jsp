<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="mb-3">
			<!-- 見出し -->
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4 pankiji-font">
				学生情報登録
			</h2>

			<!-- 🔽 CSV登録リンク -->
			<div class="right">
				<a href="Csv.action">CSVファイルの登録はこちらから</a>
			</div>

			<!-- 学生登録フォーム -->
			<form method="get" action="StudentCreateExecite.action">

				<!-- 入学年度 -->
				<div class="col-4 mb-2">
					<label class="form-label">入学年度</label>
					<select class="form-select" name="f1">
						<option value="0">-----</option>
						<c:forEach var="year" items="${ent_year_set}">
							<option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>${year}</option>
						</c:forEach>
					</select>
					<% String errors1 = (String) request.getAttribute("errors1"); %>
					<% if (errors1 != null) { %>
						<div class="text-warning"><%= errors1 %></div>
					<% } %>
				</div>

				<!-- 学生番号 -->
				<div class="col-4 mb-2">
					<label class="form-label">学生番号</label>
					<input type="text" name="no" value="${no}" class="form-control" maxlength="10" placeholder="学生番号を入力してください" required>
					<% String errors2 = (String) request.getAttribute("errors2"); %>
					<% if (errors2 != null) { %>
						<div class="text-warning"><%= errors2 %></div>
					<% } %>
				</div>

				<!-- 氏名 -->
				<div class="col-4 mb-2">
					<label class="form-label">氏名</label>
					<input type="text" name="name" value="${name}" class="form-control" maxlength="10" placeholder="氏名を入力してください" required>
				</div>

				<!-- クラス -->
				<div class="col-4 mb-2">
					<label class="form-label">クラス</label>
					<select class="form-select" name="f2">
						<option value="0">--------</option>
						<c:forEach var="num" items="${class_num_set}">
							<option value="${num}" <c:if test="${num == class_num}">selected</c:if>>${num}</option>
						</c:forEach>
					</select>
					<% String errors3 = (String) request.getAttribute("errors3"); %>
					<% if (errors3 != null) { %>
						<div class="text-warning"><%= errors3 %></div>
					<% } %>
				</div>

				<!-- 登録ボタン -->
				<div class="col-2">
					<button class="btn btn-secondary">登録して終了</button>
				</div>
			</form>

			<!-- 戻るリンク -->
			<div class="text-end px-4 mt-3">
				<a href="StudentList.action">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>
