<%--クラス一覧jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
<%--タイトル設定--%>
<c:param name="title">
得点管理システム
</c:param>
<c:param name="scripts"></c:param>

<%-- メイン --%>
<c:param name="content">
    <section class="mb-3">
    <%-- 見出し --%>
        <h2 class="rainbow-bg pankiji-font" style="width: 830px; height: 50px; padding-left: 18px; padding-top: 8px;">
		  クラス管理
		</h2>
        <div class="my-2 text-end px-4">
            <a href="ClassCreate.action">新規登録</a>
        </div>

		<%-- メッセージ表示 --%>
        <%String message = (String)request.getAttribute("message"); %>
        <% if(message != null) { %>
    	<div style="color: orange; background-color: #e0f7fa; width: 250px; font-size: 20pt; position: absolute; top: 30%; left: 50%;">
    		<%= message %>
    	</div>
		<% } %>

<%-- クラス一覧 --%>
        <c:choose>
            <c:when test="${classMap.size() > 0}">
                <div>検索結果：${classMap.size()}件</div>
                <table class="table table-hover">
                    <tr>
                        <th>学校コード</th>
                        <th>クラス</th>
                        <th></th>
                    </tr>
                    <%-- classMap（Map<String, String>）の内容をループで表示 --%>
                    <c:forEach var="data" items="${classMap}">
        			<tr>
            			<td>${data.value}</td>
            			<td>${data.key}</td>
            			<td>
                            <a href="ClassUpdate.action?classnum=${data.key}" style="margin-left: auto; margin-right: 0;">編集</a>
                            <div style="display: inline-block; width: 50px;"  style="margin-left: auto; margin-right: 0;"></div>
                             <%--<a href="ClassDelete.action?classnum=${data.key}" style="margin-left: auto; margin-right: 0;">削除</a>--%>
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
