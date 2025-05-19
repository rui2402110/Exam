<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="mb-3">
            <h2 class="rainbow-bg pankiji-font" style="width: 830px; height: 50px; padding-left: 18px; padding-top: 8px;">
                クラス管理
            </h2>
            <div class="my-2 text-end px-4">
                <a href="ClassCreate.action">新規登録</a>
            </div>

            <%-- メッセージ表示 --%>
            <c:if test="${not empty message}">
                <div style="color: orange; background-color: #e0f7fa; width: 250px; font-size: 20pt; position: absolute; top: 30%; left: 50%;">
                    ${message}
                </div>
            </c:if>

            <%-- クラス一覧 --%>
            <c:choose>
                <c:when test="${not empty classList}">
                    <div>検索結果：${fn:length(classList)}件</div>
                    <table class="table table-hover">
                        <tr>
                            <th>学校コード</th>
                            <th>クラス</th>
                            <th>生徒数</th>
                        </tr>
                        <c:forEach var="data" items="${classList}">
                            <tr>
                                <td>${data.school.cd}</td>
                                <td>${data.class_num}</td>
                                <td>${data.studentCount}</td>
                                <td>
                                    <a href="ClassUpdate.action?classnum=${data.class_num}&schoolcd=${data.school.cd}">編集</a>
                                </td>
                                <td>
								    <c:if test="${sessionScope.userAuth}">
								        <c:choose>
								            <c:when test="${data.studentCount == 0}">
								                <a href="ClassDelete.action?classnum=${data.class_num}&schoolcd=${data.school.cd}"
								                   onclick="return confirm('本当に削除しますか？');">削除</a>
								            </c:when>
								            <c:otherwise>
								                <a href="ClassUpdate.action?classnum=${data.class_num}&schoolcd=${data.school.cd}"
								                   onclick="alert('このクラスには生徒がいます。所属している生徒を変更してください。');">削除</a>
								            </c:otherwise>
								        </c:choose>
								    </c:if>
								</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <div>クラスが存在しませんでした</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>
