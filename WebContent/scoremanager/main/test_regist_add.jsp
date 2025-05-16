<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
    <%-- タイトル設定 --%>
    <c:param name="title">得点管理システム</c:param>

    <%-- スクリプト設定 --%>
    <c:param name="scripts">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </c:param>

    <%-- メインコンテンツ --%>
    <c:param name="content">
        <div class="container">
            <h2>学生テストデータ入力システム</h2>

            <%-- メッセージ表示 --%>
            <%
                String error1 = (String) request.getAttribute("message");
                String error2 = (String) request.getAttribute("errorMessage");
            %>
            <% if (error1 != null) { %>
                <div style="color: orange;"><%= error1 %></div>
            <% } %>
            <% if (error2 != null) { %>
                <div style="color: orange;"><%= error2 %></div>
            <% } %>

            <%-- フォーム開始 --%>
            <form action="TestRegistAddExecute.action" method="post">
                <div id="form-container">
                    <%-- 最初のフォームブロック --%>
                    <div class="form-block">
                        <div class="mb-3">
                            <label class="form-label">生徒名</label>
                            <select class="form-select" id="f1" name="f1">
                                <option value="0">-----</option>
                                <c:forEach var="student" items="${studentList}">
                                    <option value="${student.no}">${student.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">科目名</label>
                            <select class="form-select" id="f2" name="f2">
                                <option value="0">-----</option>
                                <c:forEach var="subject" items="${subjectList}">
                                    <option value="${subject.cd}">${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">回数</label>
                            <select class="form-select" id="f3" name="f3">
                                <option value="0">-----</option>
                                <c:forEach var="testNumber" items="${testNumbers}">
                                    <option value="${testNumber}">${testNumber}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">点数</label>
                            <input type="text" name="f4" class="form-control" maxlength="20" required />
                        </div>
                    </div>
                </div>

                <%-- 追加ボタン --%>
                <div class="mb-3">
                    <button type="button" class="btn btn-outline-primary" onclick="addFormBlock()">＋ フォーム追加</button>
                </div>

                <%-- 送信ボタン --%>
                <div class="mb-3">
                    <button type="submit" class="btn btn-success">新規登録</button>
                    <a href="TeacherList.action" class="btn btn-secondary">戻る</a>
                </div>
            </form>
        </div>

        <%-- JavaScriptでフォームブロック追加 --%>
        <script>
            let count = 1;
            function addFormBlock() {
                const container = document.getElementById("form-container");

                const block = document.createElement("div");
                block.classList.add("form-block");
                block.innerHTML = `
                    <div class="mb-3">
                        <label class="form-label">生徒名</label>
                        <select class="form-select" id="f1" name="f1">
                            <option value="0">-----</option>
                            <c:forEach var="student" items="${studentList}">
                                <option value="${student.no}">${student.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">科目名</label>
                        <select class="form-select" id="f2" name="f2">
                            <option value="0">-----</option>
                            <c:forEach var="subject" items="${subjectList}">
                                <option value="${subject.cd}">${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">回数</label>
                        <select class="form-select" id="f3" name="f3">
                            <option value="0">-----</option>
                            <c:forEach var="testNumber" items="${testNumbers}">
                                <option value="${testNumber}">${testNumber}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">点数</label>
                        <input type="text" name="f4" class="form-control" maxlength="3" required />
                    </div>
                `;

                count++;
                container.appendChild(block);
            }
        </script>
    </c:param>
</c:import>