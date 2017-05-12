<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bulma.css"/>
<head>
  <link rel="stylesheet" href="ext/bulma.css"/>
  <link href="ext/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
  <script type="text/javascript" src="ext/jquery-1.8.2.js"></script>
  <script type="text/javascript" charset="gbk" src="ext/umeditor/umeditor.config.js"></script>
  <script type="text/javascript" charset="gbk" src="ext/umeditor/umeditor.min.js"></script>
  <script type="text/javascript" src="ext/umeditor/lang/zh-cn/zh-cn.js"></script>
  <script type="text/javascript">
  $(document).ready(function() {
    $(".delete").click(function() {
      if(confirm("确定删除该文章？"))
        return true;
      return false;
    });
  });
  </script>
</head>
<body class="layout-documentation page-layout">
<section class="hero is-primary">
  <div class="hero-body">
    <div class="container">
      <h1 class="title">管理后台</h1>
      <h2 class="subtitle"></h2>
    </div>
  </div>
  <div class="hero-foot">
    <nav class="tabs is-boxed">
      <div class="container">
        <ul>
          <li><a href="index.aspx">首页</a></li>
          <li><a href="gallery.aspx">日记</a></li>
        </ul>
      </div>
    </nav>
  </div>
</section>