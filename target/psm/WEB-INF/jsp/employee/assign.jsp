<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/main.css">
	<link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/bootstrap-datetimepicker.min.css">

	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>
  <%@include file="../common/nav.jsp"%>

  <div class="container-fluid">
	  <div class="row">
		  <%@include file="../common/sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">修改</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
					<form id="userForm"  role="form">
						<div class="form-group" class="col-sm-7">
					<label for="exampleInputPassword1">姓名</label>
					<input type="text" class="form-control" value="${information.name}" disabled="disabled" id="name" placeholder="请输入姓名">
				  		</div>

					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">性别</label>
						<select id="sex" class="form-control">
							<c:if test="${information.sex==0}">
								<option value="0" selected="selected">男</option>
								<option value="1">女</option>
							</c:if>
							<c:if test="${information.sex==1}">
								<option value="0">男</option>
								<option value="1" selected="selected">女</option>
							</c:if>
							<c:if test="${information.sex!=0&&information.sex!=1}">
								<option value="0" selected="selected">男</option>
								<option value="1">女</option>
							</c:if>
								<option value="2">保密</option>
						</select>
					</div>



						<div class="form-group" class="col-sm-7">
							<label for="exampleInputPassword1">出生日期</label>
							<div class="input-group date form_datetime col-md-5" data-date="1999-07-16"  data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">

								<input class="form-control"  id="date" size="16" type="text" value="${information.date}" readonly>
								<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
								<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
							</div>
							<input type="hidden"  value="" /><br/>
						</div>



					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">电话</label>
						<input type="text" class="form-control" value="${information.phone}" id="phone" placeholder="请输入电话">
					</div>



					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">详细地址</label>
						<input type="text" class="form-control" value="${information.address}" id="address" placeholder="请输入地址">
						<p class="help-block label label-warning">请输入正确的地址，如：福建省福州市闽侯县上街镇XX村XX街XX号</p>
					</div>



					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">学历</label>
						<select id="edu" class="form-control">
						<c:if test="${information.edu=='本科'}">
							<option value="0" selected="selected">本科</option>
							<option value="1">博士</option>
							<option value="2">研究生</option>
							<option value="3">其他</option>
						</c:if>
						<c:if test="${information.edu=='博士'}">
							<option value="0" >本科</option>
							<option value="1">博士</option>
							<option value="2" selected="selected">研究生</option>
							<option value="3">其他</option>
						</c:if>
						<c:if test="${information.edu=='研究生'}">
							<option value="0" >本科</option>
							<option value="1">博士</option>
							<option value="2" selected="selected">研究生</option>
							<option value="3">其他</option>
						</c:if>
							<c:if test="${information.edu==''||information.edu==null}">
							<option value="0" selected="selected" >本科</option>
							<option value="1">博士</option>
							<option value="2">研究生</option>
							<option value="3">其他</option>
						</c:if>
						</select>
					</div>

					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">邮箱</label>
						<input type="text" class="form-control" value="${information.email}" id="email" placeholder="请输入邮箱">
						<p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
					</div>



					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">是否已婚</label>
						<select id="ismarried" class="form-control">
							<c:if test="${information.ismarried==0}">
								<option value="0" selected="selected">是</option>
								<option value="1">否</option>
								<option value="2">保密</option>
							</c:if>
							<c:if test="${information.ismarried==1}">
								<option value="0">是</option>
								<option value="1" selected="selected">否</option>
								<option value="2">保密</option>
							</c:if>
							<c:if test="${information.sex!=0&&information.sex!=1}">
								<option value="0" >是</option>
								<option value="1">否</option>
								<option value="2" selected="selected">保密</option>
							</c:if>
						</select>
					</div>

					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">身份证号</label>
						<input type="text" class="form-control" value="${information.idCard}" id="idCard" placeholder="请输入身份证号">
						<p class="help-block label label-warning">请输入合法的身份证号！（必填）</p>

					</div>


					<div class="form-group" class="col-sm-7">
						<label for="exampleInputPassword1">status</label>
						<select id="status" class="form-control">
							<c:if test="${information.status==0}">
								<option value="0" selected="selected">0</option>
								<option value="1">1</option>
								<option value="2">保密</option>
							</c:if>
							<c:if test="${information.status==1}">
								<option value="0">0</option>
								<option value="1" selected="selected">1</option>
								<option value="2">保密</option>
							</c:if>
							<c:if test="${information.status!=0&&information.status!=1}">
								<option value="0" >0</option>
								<option value="1">1</option>
								<option value="2" selected="selected">保密</option>
							</c:if>
						</select>
					</div>

				  <button id="updateBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-pencil"></i> 修改</button>
				  <button id="resetBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title" id="myModalLabel">帮助</h4>
		  </div>
		  <div class="modal-body">
			<div class="bs-callout bs-callout-info">
				<h4>测试标题1</h4>
				<p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
			  </div>
			<div class="bs-callout bs-callout-info">
				<h4>测试标题2</h4>
				<p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
			  </div>
		  </div>
		  <!--
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary">Save changes</button>
		  </div>
		  -->
		</div>
	  </div>
	</div>
    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH}/script/docs.min.js"></script>
	<script src="${APP_PATH}/layer/layer.js"></script>
	<script src="${APP_PATH}/javascript/bootstrap-datetimepicker.min.js"></script>
	<script src="${APP_PATH}/javascript/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${APP_PATH}/javascript/distpicker.data.min.js"></script>
	<%--<script src="${APP_PATH}/javascript/zh.js"></script>--%>


        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});

                $('.form_datetime').datetimepicker({
                    minView: "month", //选择日期后，不会再跳转去选择时分秒
                    language:  'zh-CN',
                    format: 'yyyy-mm-dd',
                    todayBtn:  1,
                    autoclose: 1
                });

                $("#resetBtn").click(function(){
                    // Jquery[0] ==> DOM
                    // $(DOM) ==> Jquery
                    $("#userForm")[0].reset();
                });

			    $("#updateBtn").click(function(){

			    	var name = $("#name").val();
                    var sex = $("#sex").val();
                    var date = $("#date").val();
                    var phone = $("#phone").val();
                    var address = $("#address").val();
                    var edu = $("#edu").find("option:selected").text();
                    var email = $("#email").val();
                    var ismarried = $("#ismarried").val();
                    var idCard = $("#idCard").val();
                    var status = $("#status").val();
			    	if ( name == "" ||idCard=="") {
                        layer.msg("名称和身份证不能为空，请输入", {time:2000, icon:5, shift:6}, function(){
                        	
                        });
                        return;
			    	}
			    	
			    	var loadingIndex = null;
			    	$.ajax({
			    		type : "POST",
			    		url  : "${APP_PATH}/employee/infoUpdate",
			    		data : {
			    			"id": "${param.id}",
			    			"name" : name,
							"sex":sex,
							"date":date,
							"phone":phone,
							"address":address,
							"edu":edu,
							"email":email,
							"ismarried":ismarried,
							"idCard":idCard,
							"status":status
			    		},
			    		beforeSend : function() {
			    			loadingIndex = layer.msg('处理中', {icon: 16});
			    		},
			    		success : function(msg) {
			    			layer.close(loadingIndex);
                            var result = $.parseJSON( msg );
                            if ( result.success ) {
		                        layer.msg("信息修改成功", {time:1000, icon:6}, function(){
		                        	window.location.href = "${APP_PATH}/employee/index";
		                        });
			    			} else {
		                        layer.msg("信息修改失败，请重新操作", {time:2000, icon:5, shift:6}, function(){
		                        	
		                        });
			    			}
			    		}
			    	});
			    });
            });

            function getSelectDate(result){
                //这里获取选择的日期
                console.log(result);
            }



        </script>
  </body>
</html>
