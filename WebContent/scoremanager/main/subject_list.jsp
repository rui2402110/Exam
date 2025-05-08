<%--科目一覧jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 共通テンプレート base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
    <%-- 画面タイトル設定 --%>
    <c:param name="title">
        得点管理システム
    </c:param>

    <%-- スクリプト領域（今回はなし） --%>
    <c:param name="scripts"></c:param>

    <%-- コンテンツ本体 --%>
    <c:param name="content">
        <section class="mb-3">
            <%-- 見出し --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>

            <%-- 新規登録リンク --%>
            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <%-- 科目一覧表示テーブル --%>
            <table class="table table-hover">
                <tr>
                    <th>科目コード</th>
                    <th>科目名</th>
                </tr>

                <%-- JSTLループ：科目リストを1行ずつ表示 --%>
                <c:forEach var="subject" items="${subject}">
                    <tr>
                        <td>${subject.cd}</td> <%-- 科目コード表示 --%>
                        <td>${subject.name}</td> <%-- 科目名表示 --%>

                        <%-- 編集リンク --%>
                        <td>
                            <a href="SubjectUpdate.action?cd=${subject.cd}">編集</a>
                        </td>

                        <%-- 削除リンク --%>
                        <td>
                            <a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </c:param>
</c:import>

