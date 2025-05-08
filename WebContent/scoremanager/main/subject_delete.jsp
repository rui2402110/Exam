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
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

    <form method="post" action="SubjectDeleteExecute.action" class="px-4">
        <p class="text-danger">
            「${subject_name}（${cd}）」を削除してよろしいですか？
        </p>

        <%-- hidden inputで情報を送信 --%>
        <input type="hidden" name="subject_cd" value="${cd}">
        <input type="hidden" name="subject_name" value="${subject_name}">

        <div class="my-2">
            <input type="submit" value="削除" class="btn btn-danger">
        </div>
    </form>

    <div class="mt-3">
        <a href="SubjectList.action">戻る</a>
    </div>
</section>

</c:param>
</c:import>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 共通レイアウトを読み込む -->
<c:import url="/common/base.jsp">

    <!-- ページタイトルの指定 -->
    <c:param name="title">
        得点管理システム
    </c:param>

    <!-- スクリプトなどがあれば追加（今回は空） -->
    <c:param name="scripts"></c:param>

    <!-- メインコンテンツの設定 -->
    <c:param name="content">

<section class="mb-3">
    <!-- 画面見出し -->
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

    <!-- 削除確認フォーム（POSTで送信） -->
    <form method="post" action="SubjectDeleteExecute.action" class="px-4">

        <!-- 確認メッセージ：削除対象の科目名とコードを表示 -->
        <p class="text-danger">
            「${subject_name}（${cd}）」を削除してよろしいですか？
        </p>

        <!-- 削除処理に必要なデータをhiddenで送信 -->
        <input type="hidden" name="subject_cd" value="${cd}">
        <input type="hidden" name="subject_name" value="${subject_name}">

        <!-- 削除ボタン（赤で強調） -->
        <div class="my-2">
            <input type="submit" value="削除" class="btn btn-danger">
        </div>
    </form>

    <!-- 戻るリンク：一覧画面に戻る -->
    <div class="mt-3">
        <a href="SubjectList.action">戻る</a>
    </div>
</section>

</c:param>
</c:import>

