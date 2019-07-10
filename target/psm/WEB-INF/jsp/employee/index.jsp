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
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>
  <%@include file="../common/nav.jsp"%>

  <div class="container-fluid">
      <div class="row">
          <%@include file="../common/sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
    <strong>按部门</strong><select id="dep" class="form-control" onchange="pageQuery(1)">
        <option value="0">
            全部
        </option>
    <c:forEach items="${departmentList}" var="department">
        <option value="${department.id}">
                ${department.name}
        </option>
    </c:forEach>
        </select>
    <strong>按职位</strong><select id="pos" class="form-control" onchange="pageQuery(1)">
        <option value="0">
            全部
        </option>
    <c:forEach items="${positionList}" var="position">
        <option value="${position.id}">
            ${position.name}
        </option>
    </c:forEach>
        </select>
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">姓名查询</div>
      <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>

</form>
<button type="button" class="btn btn-danger" onclick="deleteRoles()" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/employee/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <form id="roleForm">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox" id="allSelBox"></th>
                  <th>姓名</th>
                  <th>部门</th>
                  <th>职位</th>
                  <%--<th>状态</th>--%>
                  <th>入职时间</th>
                  <th>工龄</th>
                  <th>添加合同</th>
                  <th>添加记录</th>
                  <th>添加工资</th>
                  <th>详情信息</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              
              <tbody id="roleData">
                  
              </tbody>
              
			  <tfoot>
			     <tr >
				     <td colspan="12" align="center">
						<ul class="pagination">

                        </ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
            </form>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>
  <%--弹出层--%>
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <%--<div class="modal-dialog  modal-full"" role="document">--%>
          <div class="modal-dialog modal-lg" role="document">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title" id="myModalLabel">个人信息</h4>
              </div>
              <div class="modal-body">
                  <table class="table">
                      <tbody id="informationData">

                      </tbody>
                  </table>
              <div class="modal-footer"id="informationButton">
                  </div>
          </div>
      </div>
  </div>

    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH}/script/docs.min.js"></script>
	<script src="${APP_PATH}/layer/layer.js"></script>
        <script type="text/javascript">
            var likeflg = false;
            var depid = 0;
            var posid =0;
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
			    
			    pageQuery(1);
			    
			    $("#queryBtn").click(function(){
			    	var queryText = $("#queryText").val();
			    	if ( queryText == "" ) {
			    		likeflg = false;
			    	} else {
			    		likeflg = true;
			    	}
			    	pageQuery(1);
			    });
			    
			    $("#allSelBox").click(function(){
			    	var flg = this.checked;
			    	$("#roleData :checkbox").each(function(){
			    		this.checked = flg;
			    	});
			    });
            });


            // 分页查询
            function pageQuery( pageno ) {
            	var loadingIndex = null;
            	
            	var jsonData = {"pageno" : pageno, "pagesize" : 9,"departmentid":$("#dep").val(),"positionid":$("#pos").val()};
            	if ( likeflg == true ) {
            		jsonData.queryText = $("#queryText").val();
            	}
            	
            	$.ajax({
            		type : "POST",
            		url  : "${APP_PATH}/employee/pageQuery",
            		data : jsonData,
            		beforeSend : function(){
            			loadingIndex = layer.msg('处理中', {icon: 16});
            		},
            		success : function(msg) {
            			layer.close(loadingIndex);
                        var result = $.parseJSON( msg );
            			if ( result.success ) {
            				// 局部刷新页面数据
            				var tableContent = "";
            				var pageContent = "";
            				
            				var employeePage = result.data;
            				var employees = employeePage.datas;

            				$.each(employees, function(i, employee){
                                //工龄
                                var now = new Date();
                                var Time = new Date(parseInt(employee.createTime)) ;
                                var commonTime = Time.toLocaleString();
                                var t = now.getTime() - Time.getTime();
                                var workAge =  Math.ceil(t/1000/60/60/24)+'天';

            	                tableContent += '<tr>';
	          	                tableContent += '  <td>'+(i+1)+'</td>';
	          					tableContent += '  <td><input type="checkbox" name="employeeid" value="'+employee.id+'"></td>';
	          	                tableContent += '  <td>'+employee.name+'</td>';
	          	                tableContent += '  <td>'+employee.department.name+'</td>';
	          	                tableContent += '  <td>'+employee.position.name+'</td>';
	          	               //  if (employee.state == 0){
	          	               //      tableContent += '  <td style="color: brown">'+'离职'+'</td>';
                                // } else{
	          	               //      tableContent += '  <td>'+'在职'+'</td>';
                                // }
	          	                tableContent += '  <td>'+commonTime+'</td>';
	          	                tableContent += '  <td>'+workAge+'</td>';
	          	                tableContent += '  <td><button type="button" onclick="goAddContractPage('+employee.id+')" class="btn btn-success btn-xs">合同</button></td>';
	          	                tableContent += '  <td><button type="button" onclick="goAddRecordPage('+employee.id+')" class="btn btn-success btn-xs">记录</button></td>';
	          	                tableContent += '  <td><button type="button" onclick="goAddSalaryPage('+employee.id+')" class="btn btn-success btn-xs">工资</button></td>';
	          	                tableContent += '  <td><button type="button" onclick="goInformationPage('+employee.id+')" class="btn btn-success btn-xs">更多</button></td>';
	          	                tableContent += '  <td>';
	          					tableContent += '      <button type="button" onclick="goAssignPage('+employee.id+')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
	          					tableContent += '      <button type="button" onclick="goUpdatePage('+employee.id+')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
	          					tableContent += '	  <button type="button" onclick="deleteRole('+employee.id+', \''+employee.name+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
	          					tableContent += '  </td>';
	          	                tableContent += '</tr>';
            				});
            				
            				if ( pageno > 1 ) {
            					pageContent += '<li><a href="#" onclick="pageQuery('+(pageno-1)+')">上一页</a></li>';
            				}
            				
            				for ( var i = 1; i <= employeePage.totalno; i++ ) {
            					if ( i == pageno ) {
            						pageContent += '<li class="active"><a  href="#">'+i+'</a></li>';
            					} else {
            						pageContent += '<li ><a href="#" onclick="pageQuery('+i+')">'+i+'</a></li>';
            					}
            				}
            				
            				if ( pageno < employeePage.totalno ) {
            					pageContent += '<li><a href="#" onclick="pageQuery('+(pageno+1)+')">下一页</a></li>';
            				}

            				$("#roleData").html(tableContent);
            				$(".pagination").html(pageContent);
            			} else {
                            layer.msg("员工信息分页查询失败", {time:2000, icon:5, shift:6}, function(){
                            	
                            });
            			}
            		}
            	});
            }
            function deleteRoles() {
            	var boxes = $("#roleData :checkbox:checked");
            	if ( boxes.length == 0 ) {
                    layer.msg("请选择需要删除的员工信息", {time:2000, icon:5, shift:6}, function(){
                    	
                    });
            	} else {
        			layer.confirm("删除选择的员工信息, 是否继续",  {icon: 3, title:'提示'}, function(cindex){
        				// 删除选择的角色信息
        				$.ajax({
        					type : "POST",
        					url  : "${APP_PATH}/employee/deletes",
        					data : $("#roleForm").serialize(),
        					success : function(msg) {
                                var result = $.parseJSON( msg );
        						if ( result.success ) {
        							pageQuery(1);
        						} else {
                                    layer.msg("员工信息删除失败", {time:2000, icon:5, shift:6}, function(){
                                    	
                                    });
        						}
        					}
        				});
        				
        				layer.close(cindex);
        			}, function(cindex){
        			    layer.close(cindex);
        			});
            	}
            }
            function deleteRole( id, name ) {
    			layer.confirm("删除员工信息【"+name+"】, 是否继续",  {icon: 3, title:'提示'}, function(cindex){
    			    
    				// 删除用户信息
    				$.ajax({
    					type : "POST",
    					url  : "${APP_PATH}/employee/delete",
    					data : { id : id },
    					success : function(msg) {
                            var result = $.parseJSON( msg );
    						if ( result.success ) {
    							pageQuery(1);
    						} else {
                                layer.msg("员工信息删除失败", {time:2000, icon:5, shift:6}, function(){
                                	
                                });
    						}
    					}
    				});
    				
    				layer.close(cindex);
    			}, function(cindex){
    			    layer.close(cindex);
    			});
            }
            
            function goUpdatePage(id) {
            	window.location.href = "${APP_PATH}/employee/edit?id="+id;
            }

            function goAddContractPage(id) {
            	window.location.href = "${APP_PATH}/contract/add?id="+id;
            }

            function goAddSalaryPage(id) {
            	window.location.href = "${APP_PATH}/salary/add?id="+id;
            }

            
            function goAssignPage(id) {
            	window.location.href = "${APP_PATH}/employee/assign?id="+id;
            }


            function  goAddRecordPage(id){
                window.location.href = "${APP_PATH}/record/add?id="+id;
            }
            function goInformationPage(id) {
                var eid = id;
                $.ajax({
                    type : "POST",
                    url  : "${APP_PATH}/employee/information",
                    data : { id : id },
                    success : function(msg) {
                        var result = $.parseJSON( msg );
                        if ( result.success ) {
                            // 局部刷新页面数据
                            var tableContent = "";
                            var pageContent = "";
                            var information = result.data;
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'姓名'+'</td>';
                            tableContent += '  <td>'+information.name+'</td>';
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'性别'+'</td>';
                            if(information.sex==0){

                            tableContent += '  <td>'+'男'+'</td>';
                            }else if(information.sex==1){

                            tableContent += '  <td>'+'女'+'</td>';
                            }else{

                            tableContent += '  <td>'+'保密'+'</td>';
                            }
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'年龄'+'</td>';
                            tableContent += '  <td>'+information.age+'</td>';
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'出生日期'+'</td>';
                            tableContent += '  <td>'+information.date+'</td>';
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'学历'+'</td>';
                            tableContent += '  <td>'+information.edu+'</td>';
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'电话'+'</td>';
                            tableContent += '  <td>'+information.phone+'</td>';
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'地址'+'</td>';
                            tableContent += '  <td>'+information.address+'</td>';
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'已婚'+'</td>';
                            if(information.ismarried==0){

                            tableContent += '  <td>'+'是'+'</td>';
                            }else if(information.ismarried==1){
                            tableContent += '  <td>'+'否'+'</td>';

                            }else{

                            tableContent += '  <td>'+'保密'+'</td>';
                            }
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'身份证号'+'</td>';
                            tableContent += '  <td>'+information.idCard+'</td>';
                            tableContent += '</tr>';
                            tableContent += '<tr>';
                            tableContent += '  <td>'+'邮箱'+'</td>';
                            tableContent += '  <td>'+information.email+'</td>';
                            tableContent += '</tr>';
                            /*tableContent += '<tr>';
                            tableContent += '  <td>'+'状态'+'</td>';
                            tableContent += '  <td>'+information.status+'</td>';
                            tableContent += '</tr>';*/
                            pageContent += '<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>';
                            pageContent += '<button type="button" id="btn_submit" onclick="goAssignPage('+id+')" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>修改</button>';
                            $("#informationData").html(tableContent);
                            $("#informationButton").html(pageContent);
                            $("#myModalLabel").text("个人信息");
                            $('#myModal').modal();
                        } else {


                            layer.confirm("该员工还未完善信息,是否去完善？",  {icon: 3, title:'提示'}, function(cindex){

                                goAssignPage(id);

                                layer.close(cindex);
                            }, function(cindex){
                                layer.close(cindex);
                            });


                        }
                    }
                });
            }




        </script>

  </body>
</html>
