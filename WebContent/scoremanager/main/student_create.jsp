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
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
        <div class="my-2 text-end px-4">
        </div>

        <%-- formタグを設定 あて先はExecuteAction --%>
        <form method="get" action="StudentCreateExecite.action">

        <%-- 入学年度 --%>
        <div class="col-4">
                    <label class="form-label" for="student-fl-select">入学年度</label>
                    <select class="form-select" id="student-fl-select" name="f1">

						<%-- 一番最初の値に------を追加 --%>
                        <option value="0">-----</option>

                        <%-- DBのデータを表示するため、JSTLでIF文を記述 --%>
                        <c:forEach var="year" items="${ent_year_set}">
                            <option value="${year}" <c:if test="${year==ent_year}">selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                    <%-- エラー処理 --%>
                </div>
                <%
                String errors1 = (String)request.getAttribute("errors1"); %>
                <% if(errors1 != null) { %>
    			<div style="color: orange;"><%= errors1 %></div>
				<% } %>


		<%-- 学生番号 --%>
        <div class="col-4">
        <label class="form-label" for="student-fl-select">学生番号</label>
         <br>
         <%-- 学生番号入力フォーム --%>
        <input type="text" id="no" name="no" value="${no}" class="text-input" placeholder="学生番号を入力してください" required>
        <%-- エラー処理 --%>
        </div>
        		<%
                String errors2 = (String)request.getAttribute("errors2"); %>
                <% if(errors2 != null) { %>
    			<div style="color: orange;"><%= errors2 %></div>
				<% } %>


		<%-- 氏名 --%>
        <div class="col-4">
        <label class="form-label" for="student-fl-select">氏名</label>
        <br>
        <%-- 氏名入力フォーム --%>
        <input type="text" id="name" name="name" value="${name}" class="text-input" placeholder="氏名を入力してください" required>
        </div>


        <%-- コース --%>
        <div class="col-4">
                    <label class="form-label" for="student-f2-select">クラス</label>
                    <select class="form-select" id="student-f2-select" name="f2">

                    <%-- 一番最初の値に------を追加 --%>
                        <option value="0">--------</option>

                       <%-- DBのデータを表示するため、JSTLでIF文を記述 --%>
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
                        </c:forEach>
                    </select>
                </div>

                <%
                String errors3 = (String)request.getAttribute("errors3"); %>
                <% if(errors2 != null) { %>
    			<div style="color: orange;"><%= errors3 %></div>
				<% } %>

                <%-- 登録ボタン --%>
        <div class="col-2 text-center">
        			<br>
                    <button class="btn btn-secondary" id="filter-button">登録して終了</button>

                </div>
                <%-- エラー処理 --%>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </form>

        <%-- 戻るボタン --%>
        <div class="my-2 text-end px-4">
            <a href="StudentList.action">戻る</a>
        </div>

</section>
</c:param>
</c:import>