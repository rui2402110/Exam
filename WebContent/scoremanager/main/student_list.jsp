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
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>
        <div class="my-2 text-end px-4">
        <%-- 新規登録リンク --%>
            <a href="StudentCreate.action">新規登録</a>
        </div>

        <form method="get">
            <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                <div class="col-4">
                    <label class="form-label" for="student-fl-select">入学年度</label>
                    <select class="form-select" id="student-fl-select" name="f1">
                        <option value="0">-----</option>
                        <c:forEach var="year" items="${ent_year_set}">
                            <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-4">
                    <label class="form-label" for="student-f2-select">クラス</label>
                    <select class="form-select" id="student-f2-select" name="f2">
                        <option value="0">--------</option>
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-2 form-check text-center">
                    <label class="form-check-label" for="student-f3-check">在学中</label>
                    <input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="1" <c:if test="${!empty f3}">checked</c:if>>
                </div>
                <div class="col-2 text-center">
                    <button class="btn btn-secondary" id="filter-button">絞り込み</button>
                </div>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
            </div>
        </form>

        <c:choose>
            <c:when test="${students.size() > 0}">
                <div>検索結果：${students.size()}件</div>
                <table class="table table-hover">
                    <tr>
                        <th>入学年度</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>クラス</th>
                        <th class="text-center">在学中</th>
                        <th></th>
                    </tr>
                    <c:forEach var="student" items="${students}">
                        <tr>
                            <td>${student.entYear}</td>
                            <td>${student.no}</td>
                            <td>${student.name}</td>
                            <td>${student.classNum}</td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${student.getIsAttend()}">○</c:when>
                                    <c:otherwise>×</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="StudentUpdate.action?no=${student.no}">編集</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <div>学生情報が存在しませんでした</div>
            </c:otherwise>
        </c:choose>
    </section>
</c:param>
</c:import>
