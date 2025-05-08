<%--クラス情報更新jsp --%>
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
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報変更</h2>
        <div class="my-2 text-end px-4">
        </div>
       	<%-- クラス情報変更 --%>
        <form method="get" action="ClassUpdateExecute.action">

        <%-- 学校コード --%>
        <div class="col-4">
	        <label class="form-label" for="student-fl-select">学校コード</label>
	         <br>
	        <input type="text" class="text-input" id="school_cd" name="school_cd" value="${school_cd_set}" placeholder="学校コードを入力してください" readonly>
        </div>

        <input type="hidden" name="class_num" value="${class_num_set}">

        <div class="col-4">
                    <label class="form-label" for="student-f2-select">クラス</label>
                    <input type="text" class="text-input" id="class_num" name="new_class_num" value="${class_num_set}" placeholder="クラスを入力してください" required>
                </div>

                <%-- エラー処理 --%>
                <%
                String errors1 = (String)request.getAttribute("errors1"); %>
                <% if(errors1 != null) { %>
    			<div style="color: orange;"><%= errors1 %></div>
				<% } %>

				<%-- 送信ボタン --%>
        <div class="col-2 text-center">
        			<br>
                    <button class="btn btn-secondary" id="filter-button">変更</button>
                </div>
                <%-- エラー処理 --%>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </form>

        <%-- 戻るリンク --%>
        <div>
            <a href="ClassList.action">戻る</a>
        </div>

</section>
</c:param>
</c:import>