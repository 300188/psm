<%@page pageEncoding="UTF-8" isELIgnored="false"%>
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
				<form id="userForm" role="form">
				  <div class="form-group">
					<label for="exampleInputPassword1">部门名称</label>
					<input type="text" class="form-control" value="${department.name}" id="departmentname" placeholder="请输入名称">
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

                $("#resetBtn").click(function(){
                    // Jquery[0] ==> DOM
                    // $(DOM) ==> Jquery
                    $("#userForm")[0].reset();
                });

			    $("#updateBtn").click(function(){
			    	var departmentname = $("#departmentname").val();
			    	if ( departmentname == "" ) {
                        layer.msg("名称不能为空，请输入", {time:2000, icon:5, shift:6}, function(){
                        	
                        });
                        return;
			    	}
			    	
			    	var loadingIndex = null;
			    	$.ajax({
			    		type : "POST",
			    		url  : "${APP_PATH}/department/update",
			    		data : {
			    			"name" : departmentname,
			    			"id"  : "${param.id}"
			    		},
			    		beforeSend : function() {
			    			loadingIndex = layer.msg('处理中', {icon: 16});
			    		},
			    		success : function(msg) {
			    			layer.close(loadingIndex);
                            var result = $.parseJSON( msg );

                            if ( result.success ) {
		                        layer.msg("信息修改成功", {time:1000, icon:6}, function(){
		                        	window.location.href = "${APP_PATH}/department/index";
		                        });
			    			} else {
		                        layer.msg("信息修改失败，请重新操作", {time:2000, icon:5, shift:6}, function(){
		                        	
		                        });
			    			}
			    		}
			    	});
			    });
            });
        </script>
  </body>
</html>
