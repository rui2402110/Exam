<%-- ヘッダー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
	<h1 class="fs-1">得点管理システム</h1>
</div>
<style>
    /* 基本的なテキストボックスのスタイル */
    .text-input {
      width: 400px;
      padding: 10px;
      margin: 8px 0;
      box-sizing: border-box;
      border: 2px solid #ccc;
      border-radius: 4px;
      font-size: 16px;
      font-family: Arial, sans-serif;

      transition: border 0.3s ease;
    }

    /* フォーカス時のスタイル */
    .text-input:focus {
      border: 2px solid #4a90e2;
      outline: none;
      box-shadow: 0 0 5px rgba(74, 144, 226, 0.5);
      background-color: #e0f7fa;
    }

    /* 無効状態のスタイル */
    .text-input:disabled {
      background-color: #f5f5f5;
      border-color: #ddd;
      color: #999;
      cursor: not-allowed;
    }

    /* エラー状態のスタイル */
    .text-input.error {
      border-color: #e74c3c;
    }

    /* 読み取り専用のスタイル */
    .text-input[readonly] {
      background-color: #f9f9f9;
      border-color: #eee;
      cursor: default;
    }
</style>
<c:if test="${user.isAuthenticated()}">
	<div class="nav align-self-end">
		<span class="nav-item px-2">${user.getName()}様</span>
		<a class="nav-item px-2" href="Logout.action">ログアウト</a>
	</div>
</c:if>