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
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報登録</h2>
        <div class="my-2 text-end px-4">
        </div>
        <form method="get" action="ClassCreateExecute.action">
        <div class="col-4">
        <label class="form-label" for="student-fl-select">クラス</label>
        <br>
        <input type="text" id="name" name="class_num" class="text-input" value="${name}" placeholder="クラス名を入力してください" required>
        </div>
        <div class="col-2 text-center">
        			<br>
                    <button class="btn btn-secondary" id="filter-button">登録して終了</button>
                </div>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </form>
        <div class="my-2 text-end px-4">
            <a href="ClassList.action">戻る</a>
        </div>

</section>
</c:param>
</c:import>