<%--学生一覧jsp --%>
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
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">教員管理</h2>
        <div class="my-2 text-end px-4">
        <%-- 新規登録リンク --%>
            <a href="StudentCreate.action">新規登録</a>
        </div>
        	<table class="table table-hover">
		         <tr>
		             <th>ID</th>
		             <th>パスワード</th>
		             <th>教員名</th>
		             <th>管理者権限</th>
		             <th></th>
		         </tr>
		         <c:forEach var="teacher" items="${teacher}">
		             <tr>
		                 <td>${teacher.id}</td>
		                 <td>${teacher.password}</td>
		                 <td>${teacher.name}</td>
		                 <td class="text-center">
		                     <c:choose>
		                         <c:when test="${teacher.getAuth()}">Administrator</c:when>
		                         <c:otherwise>Standard User</c:otherwise>
		                     </c:choose>
		                 </td>
		                 <td>
		                 	<c:choose>
		                         <c:when test="${teacher.getAuth()}">
		                         	<a href="StudentUpdate.action?no=${student.no}">編集</a>
		                         </c:when>
		                         <c:otherwise></c:otherwise>
		                     </c:choose>
		                 </td>
		             </tr>
		         </c:forEach>
		     </table>
    </section>
</c:param>
</c:import>
