<%-- 成績管理（仮） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
<c:param name="title">
得点管理システム
</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">

<section class="mb-3">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

    <%-- 検索フォーム --%>
    <form method="get" action="TestRegist.action">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
            <div class="col-2">
                <label class="form-label" for="f1">入学年度</label>
                <select class="form-select" id="f1" name="f1">
                    <option value="0">-----</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2">
                <label class="form-label" for="f2">クラス</label>
                <select class="form-select" id="f2" name="f2">
                    <option value="0">-----</option>
                    <c:forEach var="classNum" items="${class_num_set}">
                        <option value="${classNum}" <c:if test="${classNum==f2}">selected</c:if>>${classNum}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-4">
                <label class="form-label" for="f3">科目</label>
                <select class="form-select" id="f3" name="f3">
                    <option value="0">-----</option>
                    <c:forEach var="subject" items="${subject_set}">
                        <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2">
                <label class="form-label" for="f4">回数</label>
                <select class="form-select" id="f4" name="f4">
                    <c:forEach var="count" begin="1" end="10">
                        <option value="${count}" <c:if test="${count==f4}">selected</c:if>>${count}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2 text-center">
                <button class="btn btn-secondary" id="filter-button">検索</button>
            </div>
        </div>
    </form>

    <%-- 成績入力フォーム --%>
    <%
    String error1 = (String)request.getAttribute("error1"); %>
    <% if(error1 != null) { %>
   	<div style="color: orange;"><%= error1 %></div>
	<% } %>
    <c:if test="${student_set.size()>0}">
        <form method="post" action="TestRegistExecute.action">
            <div class="px-4 mb-3">

            </div>
            <table class="table table-hover">
                <tr>
                    <th>入学年度</th>
                    <th>クラス</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>点数</th>
                </tr>
                <c:forEach var="points" items="${points}">
                    <tr>
                        <td>${points.student.getEntYear()}</td>
                        <td>${points.classNum}</td>
                        <td>${points.student.getNo()}</td>
                        <td>${points.student.getName()}</td>
                        <td>
                            <input type="text" name="point_${points.student.no}" value="${points.point}" size="4" class="form-control">
                            <input type="hidden" name="regist" value="${points.student.no}">

                            <%--エラーを表示--%>
                            <c:if test="${not empty errors[points.student.no]}">
                            <div class="text-warning">${errors[points.student.no]}</div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <%-- hiddenで共通情報 --%>
            <input type="hidden" name="count" value="${f4}">
            <input type="hidden" name="subject" value="${f3}">
            <div class="mt-3">
                <button type="submit" class="btn btn-secondary">登録して終了</button>
            </div>
        </form>
    </c:if>

</section>

</c:param>
</c:import>
