
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-3 col-md-2 sidebar">

<div class="tree">
    <ul style="padding-left:0px;" class="list-group">
        <li class="list-group-item tree-closed" >
            <span><a href="${APP_PATH}/toMainPage"><i class="glyphicon glyphicon-dashboard"></i> 人事管理系统</a></span>
        </li>
        <c:forEach items="${rootPermission.children}" var="permission">
        <c:if test="${empty permission.children}">
        <li class="list-group-item tree-closed" >
            <span><a href="${APP_PATH}${permission.url}"><i class="${permission.icon}"></i> ${permission.name}</a></span>
        </li>
        </c:if>
        <c:if test="${not empty permission.children}">
        <li class="list-group-item tree-closed">
            <span><i class="${permission.icon}"></i> ${permission.name} <span class="badge" style="float:right">${permission.children.size()}</span></span>
            <ul style="margin-top:10px;display:none;">
                <c:forEach items="${permission.children}" var="child">
                    <li style="height:30px;">
                        <span><a href="${APP_PATH}${child.url}"><i class="${child.icon}"></i> ${child.name}</a></span>
                    </li>
                </c:forEach>
            </ul>
        </li>

        </c:if>
        </c:forEach>
    </ul>
    <%--<ul style="padding-left:0px;" class="list-group">
        <li class="list-group-item tree-closed" >
            <a href="${APP_PATH}/toMainPage"><i class="glyphicon glyphicon-dashboard"></i> 人事管理系统</a>
        </li>
        <li class="list-group-item">
            <span><i class="glyphicon glyphicon-tasks"></i> 用户权限管理 <span class="badge" style="float:right">4</span></span>
            <ul style="margin-top:10px;">
                <li style="height:30px;">
                    <a href="${APP_PATH}/user/index"><i class="glyphicon glyphicon-user"></i> 用户管理</a>
                </li>
            </li>
                <li style="height:30px;">
                    <a href="${APP_PATH}/role/index"><i class="glyphicon glyphicon-certificate"></i> 角色管理</a>
                </li>
                <li style="height:30px;">
                    <a href="${APP_PATH}/permission/index"><i class="glyphicon glyphicon-lock"></i> 权限管理</a>
                </li>
            </ul>
        </li>
        <li class="list-group-item tree-closed">
            <span><i class="glyphicon glyphicon glyphicon-tasks"></i> 基本功能 <span class="badge" style="float:right">4</span></span>
            <ul style="margin-top:10px;">
                <li style="height:30px;">
                <a href="${APP_PATH}/employee/index"><i class="glyphicon glyphicon-user"></i> 员工管理</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-certificate"></i> 部门管理</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-lock"></i> 职位管理</a>
                </li>
            </ul>
        </li>
        <li class="list-group-item tree-closed">
            <span><i class="glyphicon glyphicon-ok"></i> 业务中心 <span class="badge" style="float:right">3</span></span>
            <ul style="margin-top:10px;display:none;">
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-check"></i> 请假审核</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-check"></i> 公告管理</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-check"></i> 项目审核</a>
                </li>
            </ul>
        </li>
        <li class="list-group-item tree-closed">
            <span><i class="glyphicon glyphicon-th-large"></i> 合同管理 <span class="badge" style="float:right">7</span></span>
            <ul style="margin-top:10px;display:none;">
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-picture"></i> 资质维护</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-equalizer"></i> 分类管理</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-random"></i> 流程管理</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-hdd"></i> 广告管理</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-comment"></i> 消息模板</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-list"></i> 项目分类</a>
                </li>
                <li style="height:30px;">
                    <a href="#"><i class="glyphicon glyphicon-tags"></i> 项目标签</a>
                </li>
            </ul>
        </li>
        <li class="list-group-item tree-closed" >
            <a href="#"><i class="glyphicon glyphicon-list-alt"></i> 下载中心</a>
        </li>
    </ul>--%>
</div>

</div>