<%-- 教員一覧 JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
    <%-- タイトル設定 --%>
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <%-- メインコンテンツ --%>
    <c:param name="content">
        <section class="mb-3">
            <%-- 見出し --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">教員管理</h2>

            <div class="my-2 text-end px-4">
                <c:choose>
                    <%-- ログインユーザーが管理者の場合のみ編集可能 --%>
                    <c:when test="${sessionScope.userAuth}">
                        <%-- 新規登録リンク --%>
                        <a href="TeacherCreate.action">新規登録</a>
                    </c:when>
                    <c:otherwise>
                        <a href="TeacherCreate.action" onclick="return confirm('新規登録できますが管理者権限の設定ができません。');">
                            新規登録
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>

            <p>所属学校：${school.name}</p>

            <table class="table table-hover">
                <tr>
                    <th>ID</th>
                    <th>パスワード</th>
                    <th>教員名</th>
                    <th>管理者権限</th>
                    <th></th>
                    <th></th>
                </tr>

                <c:forEach var="teacher" items="${teacherList}">
                    <tr>
                        <td>${teacher.id}</td>
                        <td>${teacher.maskedPassword}</td>
                        <td>${teacher.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${teacher.auth}">管理者</c:when>
                                <c:otherwise>一般ユーザー</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.userAuth}">
                                    <a href="TeacherUpdate.action?id=${teacher.id}">編集</a>
                                </c:when>
                                <c:otherwise>
                                    <button onclick="alert('管理者権限がないため編集ができません')">編集</button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.userAuth}">
                                    <a href="TeacherDelete.action?id=${teacher.id}">削除</a>
                                </c:when>
                                <c:otherwise>
                                    <button onclick="alert('管理者権限がないため削除ができません')">削除</button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

                <c:choose>
                    <c:when test="${not userAuth}">
                        <p>一般ユーザーは一部の機能を制限されています</p>
                    </c:when>
                </c:choose>
            </table>

            <%-- メッセージ表示 --%>
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) { %>
                <script>
                    alert("${message}");
                </script>
            <% } %>
        </section>





    </c:param>
</c:import>
