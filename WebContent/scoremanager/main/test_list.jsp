<%--成績管理一覧 --%>
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
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

    <%-- 検索条件1：入学年度・クラス・科目 --%>
    <p class="fw-bold px-4">科目情報</p>
    <%-- formタグを設定 あて先はExecuteAction --%>
    <form method="get" action="TestListSubjectExecute.action">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">

        	<%--入学年度 --%>
            <div class="col-2">
                <label class="form-label">入学年度</label>
                <select class="form-select" name="f1">
                    <option value="0">-----</option>
                    <c:forEach var="ent_year" items="${ent_year_set}">
                        <option value="${ent_year}" <c:if test="${ent_year==f1}">selected</c:if>>${ent_year}</option>
                    </c:forEach>
                </select>
            </div>


            <%--クラス --%>
            <div class="col-2">
                <label class="form-label">クラス</label>
                <select class="form-select" name="f2">
                    <option value="0">-----</option>
                    <c:forEach var="class_num" items="${class_num_set}">
                        <option value="${class_num}" <c:if test="${class_num==f2}">selected</c:if>>${class_num}</option>
                    </c:forEach>
                </select>
            </div>


            <%--科目 --%>
            <div class="col-4">
                <label class="form-label">科目</label>
                <select class="form-select" name="f3">
                    <option value="0">-----</option>
                    <c:forEach var="subject" items="${subject_set}">
                        <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2 text-center">
                <button class="btn btn-secondary">検索</button>
            </div>
            <%-- hiddenで識別子 --%>
            <input type="hidden" name="f" value="sj">
        </div>
    </form>

    <%-- 検索条件2：学生番号 --%>
    <p class="fw-bold px-4">学生情報</p>
    <%-- formタグを設定 あて先はExecuteAction --%>
    <form method="get" action="TestListStudentExecute.action">

        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">
            <div class="col-2">
                <label class="form-label">学生番号</label>
            </div>
            <div class="col-4">
                <input type="text" class="form-control" name="f4" value="${f4}" maxlength="10" placeholder="学生番号を入力してください" required>
            </div>
            <div class="col-2 text-center">
                <button class="btn btn-secondary">検索</button>
            </div>
            <%-- hiddenで識別子 --%>
            <input type="hidden" name="f" value="st">
        </div>
    </form>

    <%-- エラーメッセージ --%>
    <c:if test="${not empty errors['f1']}">
        <div class="text-danger mx-3">${errors['f1']}</div>
    </c:if>
    	<%
	    String error1 = (String)request.getAttribute("error1");
    	String error2 = (String)request.getAttribute("error2");
    	%>
    	<% if(error1 != null) { %>
   		<div style="color: orange;"><%= error1 %></div>
		<% } %>
		<% if(error2 != null) { %>
   		<div style="color: orange;"><%= error2 %></div>
		<% } %>

    <%-- 利用方法メッセージ --%>
    <div class="text-info mx-3">
        科目情報を選択または学生番号を入力して検索ボタンをクリックしてください
    </div>
</section>

</c:param>
</c:import>



