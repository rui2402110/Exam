<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
<c:param name="title">
得点管理システム
</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
    <section class="mb-3">
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
        <div class="my-2 text-end px-4">
            <a href="SubjectCreate.action">新規登録</a>
        </div>

        <c:choose>
            <c:when test="${subject.size() > 0}">
                <table class="table table-hover">
                    <tr>
                        <th>科目コード</th>
                        <th>科目名</th>
                        <th></th>
                    </tr>
                    <c:forEach var="subject" items="${subject}">
                        <tr>
                            <td>${subject.code}</td>
                            <td>${subject.name}</td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${subjectt.getIsAttend()}">○</c:when>
                                    <c:otherwise>×</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="SubjectUpdate.action?no=${subject.no}">編集</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    </section>
</c:param>
</c:import>