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
