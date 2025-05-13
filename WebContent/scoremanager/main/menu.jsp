<%-- メニューJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- base.jsp を読み込み -->
<c:import url="/common/base.jsp">
<%-- タイトル設定 --%>
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<%-- メイン --%>
	<c:param name="content">
		<section class="me-4">
		<!-- 見出し -->
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">メニュー</h2>

			<div class="row text-center px-4 fs-3 my-5">

				<%-- 学生管理欄 --%>
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #dbb;">
					<a href="StudentList.action">学生管理</a>
				</div>

				<%-- 成績管理欄 --%>
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #bdb;">
					<div>
						<div class="">成績管理</div>
						<div class="">
						<%-- 成績登録欄 --%>
							<a href="TestRegist.action">成績登録</a>
						</div>

						<div class="">
						<%-- 成績参照欄 --%>
							<a href="TestList.action">成績参照</a>
						</div>
					</div>
				</div>

				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #bbd;">
					<a href="SubjectList.action">科目管理</a>
				</div>
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #ddb;">
					<a href="ClassList.action">クラス管理</a>
				</div>
				<div style="width: 145px; height: 160px; background: linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet); border-radius: 10px; display: flex; flex-direction: column; justify-content: center; align-items: center; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); transition: transform 0.3s, box-shadow 0.3s;">
					<a href="SubjectList.action">教員管理</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>