<%-- 登録成功JSP（科目情報変更後の完了通知画面） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 共通レイアウト(base.jsp)の取り込み -->
<c:import url="/common/base.jsp">

    <!-- タイトル設定（ブラウザタブや共通ヘッダーに反映） -->
    <c:param name="title">
        得点管理システム
    </c:param>

    <!-- コンテンツ部分の描画 -->
    <c:param name="content">

        <!-- 全体コンテンツのラッパー -->
        <div id="wrap_box">

            <!-- ページ見出し -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">
                科目情報変更
            </h2>

            <!-- 完了メッセージとリンク -->
            <div id="wrap_box">
                <!-- 成功メッセージ（中央表示 + 背景色で強調） -->
                <p class="text-center" style="background-color:#66CC99">
                    変更が完了しました
                </p>

                <!-- 次のアクションへの導線：一覧へ戻る -->
                <a href="SubjectList.action">科目一覧</a>

                <!-- メニュー画面へ戻る（タイポに注意：Menut → Menu か確認） -->
                <a href="Menut.action">戻る</a>
            </div>

        </div>
    </c:param>
</c:import>
