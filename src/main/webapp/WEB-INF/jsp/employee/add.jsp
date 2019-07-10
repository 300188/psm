<%@page pageEncoding="UTF-8" isELIgnored="false" %>
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
				  <li class="active">新增</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form id="userForm" id="userForm" role="form">
				  <div class="form-group">
					<label for="exampleInputPassword1">员工名称</label>
					<input type="text" class="form-control" id="name" placeholder="请输入员工名称">
				  </div>
					<strong>部门</strong><select id="dep" class="form-control" onchange="getPosition()" >
					<option value="0">
						请选择
					</option>
					<c:forEach items="${departmentList}" var="department">
						<option value="${department.id}">
								${department.name}
						</option>
					</c:forEach>
				</select>
					<strong>职位</strong><select id="pos" class="form-control">
					<option value="0">
						请选择
					</option>
				</select>
				  <button id="insertBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
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
			    
			    $("#insertBtn").click(function(){
			    	var name = $("#name").val();
                    var did = $("#dep").val();
                    var pid = $("#pos").val();
			    	if ( name == "" ||did == "0" ) {
                        layer.msg("姓名和部门不能为空！", {time:2000, icon:5, shift:6}, function(){
                        	
                        });
                        return;
			    	}
			    	
			    	var loadingIndex = null;
			    	$.ajax({
			    		type : "POST",
			    		url  : "${APP_PATH}/employee/insert",
			    		data : {
			    			"name"  : name,
			    			"departmentid"  : did,
			    			"positionid"  : pid
			    		},
			    		beforeSend : function() {
			    			loadingIndex = layer.msg('处理中', {icon: 16});
			    		},
			    		success : function(msg) {
			    			layer.close(loadingIndex);
                            var result = $.parseJSON( msg );
                            if ( result.success ) {
		                        layer.msg("增加成功", {time:1000, icon:6}, function(){
		                        	window.location.href = "${APP_PATH}/employee/index";
		                        });
			    			} else {
		                        layer.msg("员工增加失败(该身份证已被使用)，请重新操作", {time:2000, icon:5, shift:6}, function(){
		                        	
		                        });
			    			}
			    		}
			    	});
			    });
            });
		    $("#resetBtn").click(function(){
		    	// Jquery[0] ==> DOM
		    	// $(DOM) ==> Jquery
		    	$("#userForm")[0].reset();
		    });

		    //实现二级联动
            function getPosition(){

                var did = $("#dep").val();
                $.ajax({
                    type : "POST",
                    url  : "${APP_PATH}/employee/select",
                    data : {
                        "departmentid":did
                    },
                    beforeSend : function(){
                        loadingIndex = layer.msg('处理中', {icon: 16});
                    },
                    success : function(msg) {
                        layer.close(loadingIndex);
                        var result = $.parseJSON( msg );
                        if ( result.success ) {
                            // 局部刷新页面数据
                            var tableContent = "";
                            var positions = result.data;

                            $.each(positions, function(i, position){
                                tableContent += '  <option value="'+position.id+'">'+position.name+'</option>';
                            });
                            $("#pos").html(tableContent);
                        } else {
                            layer.msg("查询失败,刷新重试", {time:2000, icon:5, shift:6}, function(){

                            });
                        }
                    }
                });

            }

        </script>
  </body>
</html>
