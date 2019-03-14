<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> <!--如session设置为false，SPRING_SECURITY_LAST_EXCEPTION会始终为null-->
<%@ page isELIgnored="false" %>
 <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="js/libs/jquery/jquery-2.1.4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  </head>

  <body>

    <div class="container">

        <form class="form-signin"  action='login' method='POST'>
        <h2 class="form-signin-heading">Dubbo Monitor</h2>
        <label for="inputUsername" class="sr-only">用户名</label>
        <input name="username" type="username" id="inputUsername" class="form-control" placeholder="用户名" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required>
        <label for="totpcode" class="sr-only">totpcode</label>
        <input name="totpcode" type="totpcode" id="totpcode" class="form-control" placeholder="动态码" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        <div class="col-xs-12">
        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">
            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
        </c:if>
         </div>
      </form>

    </div> <!-- /container -->

  </body>
</html>