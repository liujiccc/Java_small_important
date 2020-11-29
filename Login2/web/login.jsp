<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
    <script>
        /*
            分析：
                点击超链接或者图片，需要换一张
                1.给超链接和图片绑定单击事件

                2.重新设置图片的src属性值

         */
        window.onload=function () {
            //1.获取图片对象
            var img = document.getElementById("img");
            //2.绑定单击事件
            img.onclick=function () {
                //加时间戳，这里加时间戳是为换图片的时候不使用缓存，而是欺骗服务器？加个时间戳就永不相同了
                var date = new Date().getTime();
                img.src="/bbb/CheckCodeServlet?"+date;
            }

            //1.获取超链接
            var a_change = document.getElementById("change");
            a_change.onclick=function () {
                var date1 = new Date().getTime();
                a_change.href="/bbb/CheckCodeServlet?"+date;
            }

        }
    </script>
    <style>
        div{
            color: red;
        }
    </style>
</head>
<body>

<%--
给浏览器用，所以要加虚拟目录
--%>
<form action="${pageContext.request.contextPath}/LoginServlet">
<%--<form action="/bbb/LoginServlet">--%>
    <table>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>验证码</td>
            <td><input type="text" name="checkCode"></td>
        </tr>
        <tr>
            <td><img id="img" src="/bbb/CheckCodeServlet" alt=""></td>
            <td><a href="" id="change">看不清换一张?</a></td>
        </tr>
        <tr>
            <td><input type="submit" value="登录"></td>
        </tr>
    </table>
</form>

<div><%=request.getAttribute("cc_error")==null?"":request.getAttribute("cc_error")%></div>
<div><%=request.getAttribute("login_error")==null?"":request.getAttribute("login_error")%></div>

</body>
</html>
