<%-- 登録成功JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- base.jsp を読み込み --%>
<c:import url="/common/base.jsp">
<%--タイトル設定 --%>
	<c:param name="title">
		得点管理システム
	</c:param>

	<%-- スクリプト設定 --%>
	<c:param name="scripts">
		<script>
			// JSP読み込み時に実行されるコード
			$(document).ready(function() {
				console.log("Document ready - スクリプトが実行されます");

				// データを直接JavaScriptの変数として定義
				const students = [
					<c:forEach items="${studentList}" var="student" varStatus="status">
						{ id: ${student.no}, name: "${student.name}" }<c:if test="${!status.last}">,</c:if>
					</c:forEach>
				];

				const subjects = [
					<c:forEach items="${subjectList}" var="subject" varStatus="status">
						{ id: ${subject.cd}, name: "${subject.name}" }<c:if test="${!status.last}">,</c:if>
					</c:forEach>
				];

				const testNumbers = [
					<c:forEach items="${testNumbers}" var="number" varStatus="status">
						${number}<c:if test="${!status.last}">,</c:if>
					</c:forEach>
				];

				console.log("学生データ:", students);
				console.log("科目データ:", subjects);
				console.log("テスト回数:", testNumbers);

				// テストデータを追加するボタンのイベントハンドラ
				$("#add-record").on("click", function() {
					addTestRecord(students, subjects, testNumbers);
				});

				// 初期表示時に1つのレコードを追加
				addTestRecord(students, subjects, testNumbers);
			});

			// テストレコードを追加する関数
			function addTestRecord(students, subjects, testNumbers) {
				console.log("テストレコード追加関数が呼び出されました");

				// 新しいレコード用のコンテナ
				const container = $("<div>").addClass("item-container").css({
					"border": "1px solid #cce5ff",
					"border-radius": "4px",
					"padding": "15px",
					"margin-bottom": "10px",
					"background-color": "#f8fbff",
					"box-shadow": "0 1px 3px rgba(0,0,0,0.05)",
					"display": "flex",
					"justify-content": "space-between",
					"align-items": "center",
					"transition": "all 0.3s ease"
				});

				// ホバー効果
				container.hover(
					function() {
						$(this).css({
							"background-color": "#e6f3ff",
							"box-shadow": "0 4px 8px rgba(0,0,0,0.08)",
							"border-color": "#99c2ff"
						});
					},
					function() {
						$(this).css({
							"background-color": "#f8fbff",
							"box-shadow": "0 1px 3px rgba(0,0,0,0.05)",
							"border-color": "#cce5ff"
						});
					}
				);

				// フィールドを格納するdiv
				const fieldsDiv = $("<div>").addClass("item-fields").css({
					"display": "flex",
					"flex-wrap": "wrap",
					"gap": "10px",
					"flex": "1"
				});

				// 1. 生徒名選択フィールド
				const studentField = createFieldGroup("生徒名", createSelectElement("student[]", students));

				// 2. 科目名選択フィールド
				const subjectField = createFieldGroup("科目名", createSelectElement("subject[]", subjects));

				// 3. テスト回数選択フィールド
				const testNumberField = createFieldGroup("テスト回数", createTestNumberSelect("testNumber[]", testNumbers));

				// 4. 点数入力フィールド
				const scoreField = createFieldGroup("点数", createScoreInput("score[]"));

				// フィールドを追加
				fieldsDiv.append(studentField, subjectField, testNumberField, scoreField);

				// 削除ボタンを作成
				const removeBtn = $("<button>").attr({
					"type": "button",
					"class": "btn btn-remove"
				}).text("削除").css({
					"background-color": "#ffeef0",
					"color": "#d33a4c",
					"border": "1px solid #f8d7da",
					"border-radius": "4px",
					"padding": "5px 10px",
					"margin-left": "10px",
					"cursor": "pointer",
					"transition": "all 0.2s ease"
				});

				// 削除ボタンのホバー効果
				removeBtn.hover(
					function() {
						$(this).css({
							"background-color": "#ffcccf",
							"color": "#c82333"
						});
					},
					function() {
						$(this).css({
							"background-color": "#ffeef0",
							"color": "#d33a4c"
						});
					}
				);

				// 削除ボタンのクリックイベント
				removeBtn.on("click", function() {
					container.remove();
				});

				// 要素を追加
				container.append(fieldsDiv, removeBtn);

				// フォームに追加
				$("#test-records").append(container);
				console.log("レコードが追加されました");
			}

			// フィールドグループを作成する関数
			function createFieldGroup(labelText, inputElement) {
				const group = $("<div>").addClass("field-group").css({
					"display": "flex",
					"flex-direction": "column",
					"min-width": "120px"
				});

				const label = $("<label>").addClass("field-label").text(labelText).css({
					"margin-bottom": "5px",
					"font-weight": "bold"
				});

				group.append(label, inputElement);

				return group;
			}

			// セレクト要素を作成する関数
			function createSelectElement(name, options) {
				const select = $("<select>").addClass("form-control").attr({
					"name": name,
					"required": true
				}).css({
					"padding": "6px",
					"border-radius": "4px",
					"border": "1px solid #ced4da",
					"transition": "border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out"
				});

				// セレクトボックスのホバー効果
				select.hover(
					function() {
						$(this).css({
							"border-color": "#80bdff",
							"box-shadow": "0 0 0 0.2rem rgba(0, 123, 255, 0.25)"
						});
					},
					function() {
						if (document.activeElement !== this[0]) {
							$(this).css({
								"border-color": "#ced4da",
								"box-shadow": "none"
							});
						}
					}
				);

				// 空のオプション
				select.append($("<option>").val("").text("選択してください"));

				// オプションを追加
				if (options && options.length > 0) {
					$.each(options, function(i, option) {
						const value = option.id !== undefined ? option.id : option;
						const text = option.name !== undefined ? option.name : option;
						select.append($("<option>").val(value).text(text));
					});
				} else {
					console.warn("選択肢がありません: ", name);
					select.append($("<option>").val("no-data").text("データがありません"));
				}

				return select;
			}

			// テスト回数のセレクト要素を作成
			function createTestNumberSelect(name, numbers) {
				const select = $("<select>").addClass("form-control").attr({
					"name": name,
					"required": true
				}).css({
					"padding": "6px",
					"border-radius": "4px",
					"border": "1px solid #ced4da"
				});

				// 空のオプション
				select.append($("<option>").val("").text("選択してください"));

				// オプションを追加
				if (numbers && numbers.length > 0) {
					$.each(numbers, function(i, number) {
						select.append($("<option>").val(number).text(`第${number}回`));
					});
				} else {
					console.warn("テスト回数がありません");
					select.append($("<option>").val("1").text("第1回"));
					select.append($("<option>").val("2").text("第2回"));
				}

				return select;
			}

			// 点数入力フィールドを作成
			function createScoreInput(name) {
				const input = $("<input>").attr({
					"type": "number",
					"class": "form-control",
					"name": name,
					"min": 0,
					"max": 100,
					"placeholder": "0-100",
					"required": true
				}).css({
					"padding": "6px",
					"border-radius": "4px",
					"border": "1px solid #ced4da",
					"width": "100px",
					"transition": "border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out"
				});

				// 入力フィールドのホバー効果
				input.hover(
					function() {
						$(this).css({
							"border-color": "#80bdff",
							"box-shadow": "0 0 0 0.2rem rgba(0, 123, 255, 0.25)"
						});
					},
					function() {
						if (document.activeElement !== this[0]) {
							$(this).css({
								"border-color": "#ced4da",
								"box-shadow": "none"
							});
						}
					}
				);

				return input;
			}
		</script>
	</c:param>

	<%--メイン --%>
	<c:param name="content">
		<div class="container">
			<h2>学生テストデータ入力システム</h2>

			<!-- デバッグ情報表示 -->
			<div style="margin-bottom: 10px; padding: 10px; border: 1px solid #ccc; background-color: #f8f9fa;">
				<p>生徒数: ${studentList.size()} 件</p>
				<p>科目数: ${subjectList.size()} 件</p>
				<p>テスト回数: ${testNumbers.size()} 件</p>
			</div>

			<form action="saveTestData.jsp" method="post" id="testDataForm">
				<div id="test-records" style="border: solid 3px #cce5ff; border-radius: 5px; padding: 10px; background-color: #f0f8ff;">
					<%-- ここに動的にテストデータ入力フォームが追加されます --%>
				</div>
				<br>
				<div class="form-group" style="margin-bottom: 15px;">
					<button type="button" class="btn" id="add-record" style="background-color: #e1f0ff; color: #0056b3; border: 1px solid #cce5ff; padding: 8px 16px; border-radius: 4px; cursor: pointer; transition: background-color 0.2s ease;">テストデータを追加</button>
				</div>

				<div style="margin-top: 15px;">
					<button type="submit" class="btn" style="background-color: #d8eeff; color: #0056b3; border: 1px solid #b8daff; padding: 8px 16px; border-radius: 4px; cursor: pointer; transition: background-color 0.2s ease;">データを保存</button>
				</div>
			</form>
		</div>
	</c:param>
</c:import>