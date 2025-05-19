<%--学生情報変更jsp --%>
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
<c:param name="content">
    <section class="mb-3">
    <%--見出し --%>
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
        <div class="my-2 text-end px-4">
        </div>

        <%--formタグを設定 あて先はExecuteAction --%>
        <form method="get" action="StudentUpdateExecute.action">

        <%--入学年度 --%>
        <div class="col-4">
                    <label class="form-label" for="student-fl-select">入学年度</label>
                    <br>
                    <input type="text" id="Year" name="ent_year" class="text-input" value="${ent_year}" placeholder="入学年度" readonly>
                </div>
                <%--エラー処理 --%>


		<%--学生番号 --%>
        <div class="col-4">
        <label class="form-label" for="student-fl-select">学生番号</label>
         <br>
        <input type="text" id="no" name="no" value="${no}" class="text-input" placeholder="学生番号を入力してください" readonly>
        </div>


        <%--氏名 --%>
        <div class="col-4">
        <label class="form-label" for="student-fl-select">氏名</label>
        <br>
        <input type="text" id="name" name="name" value="${name}" class="text-input" maxlength="10" placeholder="氏名を入力してください" required>
        </div>


        <%--クラス --%>
        <div class="col-4">
                    <label class="form-label" for="student-f2-select">クラス</label>
                    <%--選択式 --%>
                    <select class="form-select" id="student-f2-select" name="class_num">

                        <option value="0">--------</option>
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
                        </c:forEach>
                    </select>
                </div>
                <%
                String errors1 = (String)request.getAttribute("errors1"); %>
                <% if(errors1 != null) { %>
    			<div style="color: orange;"><%= errors1 %></div>
				<% } %>



        <%--在学中チェックボックス --%>
        <div class="col-4">
        <p><input type="checkbox" name="is_attend" ${is_attend}>在学中</p>
        </div>
        <div class="col-2">
        			<br>
        			<%--編子変更ボタン --%>
                    <button class="btn btn-secondary" id="filter-button">変更</button>
                </div>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </form>

        <%--戻る --%>
        <div >
            <a href="StudentList.action">戻る</a>
        </div>

</section>
</c:param>
</c:import>