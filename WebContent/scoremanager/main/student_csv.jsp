<%-- 生徒情報登録jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
    <%-- タイトル設定 --%>
    <c:param name="title">
        得点管理システム
    </c:param>
    <c:param name="scripts"></c:param>

    <%-- メイン --%>
    <c:param name="content">
        <section class="mb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生登録-CSV-
            </h2>
            <div class="my-2 text-end px-4"></div>

            <%-- formタグを設定 あて先はExecuteAction --%>
            <form method="post" action="CsvExecute.action" enctype="multipart/form-data" id="csvForm">
                <div id="drop-area" style="border: 2px dashed #ccc; padding: 20px; text-align: center; margin-bottom: 1em; cursor: pointer;">
                    <p>ここにCSVファイルをドラッグ＆ドロップ<br>またはクリックして選択</p>
                    <input type="file" name="csv" id="csvFile" style="display: none;">
                    <p id="fileName" style="margin-top: 10px;"></p>
                </div>
                <div>
                    <input type="submit" value="送信する" class="btn btn-secondary">
                </div>
            </form>

            <%-- 戻るボタン --%>
            <div class="my-2 text-end px-4">
                <a href="StudentList.action">戻る</a>
            </div>
        </section>

        <script>
            const dropArea = document.getElementById('drop-area');
            const fileInput = document.getElementById('csvFile');
            const fileName = document.getElementById('fileName');

            // ドロップエリアクリックでファイル選択ダイアログを開く
            dropArea.addEventListener('click', () => {
                fileInput.click();
            });

            // ドラッグオーバー時のハイライト
            ['dragenter', 'dragover'].forEach(eventName => {
                dropArea.addEventListener(eventName, (e) => {
                    e.preventDefault();
                    dropArea.style.backgroundColor = '#f0f0f0';
                });
            });

            // ドラッグアウト/ドロップ時のハイライト解除
            ['dragleave', 'drop'].forEach(eventName => {
                dropArea.addEventListener(eventName, (e) => {
                    e.preventDefault();
                    dropArea.style.backgroundColor = '';
                });
            });

            // ファイルドロップ時の処理
            dropArea.addEventListener('drop', (e) => {
                const files = e.dataTransfer.files;
                if (files.length > 0) {
                    const file = files[0];
                    if (file.type === "text/csv" || file.name.endsWith(".csv")) {
                        fileInput.files = files;
                        fileName.textContent = "選択されたファイル: " + file.name;
                    } else {
                        alert("CSVファイルを選択してください。");
                    }
                }
            });

            // 通常のファイル選択時
            fileInput.addEventListener('change', () => {
                if (fileInput.files.length > 0) {
                    fileName.textContent = "選択されたファイル: " + fileInput.files[0].name;
                }
            });
        </script>
    </c:param>
</c:import>
