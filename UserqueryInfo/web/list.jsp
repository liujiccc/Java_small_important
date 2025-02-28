<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>
/*
        通过形参传id，非常好
*/
        function deleteUser(id) {
            //用户安全提示
            if (confirm("您确定要删除吗?")){
                //访问路径，跳到delUserServlet
                location.href="${pageContext.request.contextPath}/delUserServlet?id="+id;
            }
        }

        window.onload=function () {
            //给删除选中按钮添加单击事件
            document.getElementById("delSelected").onclick=function () {
                if (confirm("您确定要删除选中条目?")){
                    var flag=false;
                    //判断是否有选中条目
                    var cbs = document.getElementsByName("uid");
                    //遍历
                    for (var i=0;i<cbs.length;i++){
                        if(cbs[i].checked){
                            //有一条条目选中了，按删除选中就不会空指针异常
                            flag=true;
                            break;
                        }
                    }
                    if (flag){
                        //有条目被选中
                        //表单提交,下面的form是form设置的id值
                        document.getElementById("form").submit();
                    }
                }
            }

            //1.获取第一个cb，全选全不选的操作
            document.getElementById("firstCb").onclick=function () {
                //2.获取下边列表中所有的cb，这里要用getElementsByName
                var cbs = document.getElementsByName("uid");
                //遍历
                for (var i=0;i<cbs.length;i++){
                    //4.设置这些cbs[i]的checked状态 = firstCb.checked
                    cbs[i].checked=this.checked;
                }
            }

        }
    </script>
</head>

<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>
    <div style="float: left">
        <%--把bootstrap的内联表单加进来--%>
        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">姓名：</label>
                <%--设置value是为了复杂查询时点了另外一页可以回显数据--%>
                <input type="text" class="form-control" name="name" value="${condition.name[0]}" id="exampleInputName2" placeholder="Jane Doe">
            </div>
            <div class="form-group">
                <label for="exampleInputName3">籍贯：</label>
                <input type="text" class="form-control" name="address" value="${condition.address[0]}" id="exampleInputName3" placeholder="Jane Doe">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail2">邮箱：</label>
                <input type="text" class="form-control" name="email" value="${condition.email[0]}" id="exampleInputEmail2" placeholder="jane.doe@example.com">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>

    <div style="float: right;margin: 5px">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
<%--
        上面定义一个方法来获取delSelected这个来成了一个方法再获取下面的表单
--%>
        <a class="btn btn-primary" href="javascript:void(0);" id="delSelected">删除选中</a>
    </div>

<%--
    添加一个表单来提交删除选中的复选框的id，设置id为form给上面函数来获取这个表单
--%>
    <form id="form" action="${pageContext.request.contextPath}/delSelectedServlet" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstCb"></th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>籍贯</th>
                <th>QQ</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>

            <%--通过el表达式来获取servlet传给它的集合--%>
            <c:forEach items="${pb.list}" var="user" varStatus="s">
                <tr>
                    <th><input type="checkbox" name="uid" value="${user.id}"></th>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;
    <%--
                        为每一行提供删除功能,deleteUser是上面定义的方法
    --%>
                        <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id});">删除</a></td>
                </tr>

            </c:forEach>

        </table>
    </form>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <%--在第一页再点击上一页设置禁用状态，但还是可以点击会报错（在后台UserServiceImpl代码写控制<0就返回1就行）--%>
                <c:if test="${pb.currentPage ==1}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage !=1}">
                    <li>
                </c:if>

                    <%--返回上一页（点击后，当前页码-1就行），这里</li>和上面c:if其中一个匹配--%>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage-1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <%--中间的数字,currentPage=${i}&rows=5(当前页码写死了)就这样发送给findUserByPageServlet获取的--%>
                <c:forEach begin="1" end="${pb.totalPage}" var="i" step="1">
                    <%--用c:if来搬bootstrap来弄个激活状态--%>
                    <c:if test="${pb.currentPage==i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>

                    <c:if test="${pb.currentPage!=i}">
                        <li><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>
                </c:forEach>


                <%--在最后页再点击下一页设置禁用状态，但还是可以点击会报错（在后台UserServiceImpl代码写控制）--%>
                <c:if test="${pb.currentPage ==pb.totalPage}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage !=pb.totalPage}">
                    <li>
                </c:if>

                    <%--下一页，和上一页差不多的配置--%>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage+1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>


                <span style="font-size: 25px;margin-left: 5px">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
