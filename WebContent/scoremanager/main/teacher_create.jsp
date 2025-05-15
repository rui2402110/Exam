<%-- 教員登録 JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <%-- メインコンテンツ --%>
    <c:param name="content">
        <section class="form-section mb-3">
            <%-- 見出し --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">新規教員情報登録</h2>

            <%-- フォーム本体 --%>
            <form method="get" action="TeacherCreateExecute.action">

                <%-- ユーザーID --%>
                <div class="form-block col-4">
                    <label class="form-label" for="id" title="新しく登録する教員のユーザーIDを入力してください">ユーザーID</label>
                    <input type="text" id="id" name="id" value="${id_set}" class="text-input" placeholder="新規教員IDを入力してください" required maxlength="10">
                </div>

                <%-- パスワード --%>
                <div class="form-block col-4">
                    <label class="form-label" for="pass1" title="ログインに使用するパスワードを入力してください">パスワード</label>
                    <input type="text" id="pass1" name="pass1" value="${pass1_set}" class="text-input" placeholder="パスワードを入力してください" required maxlength="30">
                    <input type="text" id="pass2" name="pass2" value="${pass2_set}" class="text-input mt-2" placeholder="同じパスワードを入力してください" required maxlength="30">
                    <div style="color: orange;">${errors1}</div>
                    <div style="color: orange;">${errors2}</div>
                </div>

                <%-- 教員名 --%>
                <div class="form-block col-4">
                    <label class="form-label" for="name" title="表示される教員名を入力してください">教員名</label>
                    <input type="text" id="name" name="name" value="${name_set}" class="text-input" placeholder="教員名を入力してください" required maxlength="10">
                </div>

                <%-- 管理者権限（スライドスイッチ） --%>
                <div class="form-block col-4 mt-3">
                    <label title="設定すると、他ユーザーの管理が可能になります">
                        管理者権限を付与：
                        <c:choose>
                            <c:when test="${sessionScope.userAuth}">
                                <label class="switch">
                                    <input type="checkbox" id="auth" name="auth" value="true">
                                    <span class="slider"></span>
                                </label>
                            </c:when>
                            <c:otherwise>
                                <label class="switch">
                                    <input type="checkbox" id="auth" name="auth" value="true" disabled>
                                    <span class="slider"></span>
                                </label>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </div>

                <%-- 登録ボタン --%>
                <div class="form-block col-2 text-center mt-4">
                    <button class="btn btn-secondary w-100" id="filter-button">登録</button>
                </div>

                <%-- 共通エラーメッセージ --%>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
            </form>

            <%-- 戻るリンク --%>
            <div class="mt-3">
                <a href="TeacherList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
