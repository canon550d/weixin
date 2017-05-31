<?php
use sinacloud\sae\Storage as Storage;

$config = parse_ini_file ('../daochen.ini', true);
if(isset($config)){
	$prefixes = $config['gallery']['prefix'];
	$excludes = $config['gallery']['exclude'];
	//echo $prefixes;
}

if(isset($prefixes)){
	$pres = explode(',', $prefixes);
}
if(isset($excludes)){
	$rules =  explode(',', $excludes);
}
// echo '<br/>';

$storage = new Storage();
$bucket = "travel";

?>
<!DOCTYPE html>
<html class="route-documentation">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../html/css/bulma.css"/>
</head>
<body class="layout-documentation page-layout">
<div class="container">
  <nav class="nav">
    <div class="nav-left">
      <a class="nav-item is-brand">
        <img src="../html/img/x.jpg" alt="Bulma logo">
      </a>
    </div>
    <div class="nav-center">
      <a class="nav-item">
        <span class="icon">
          <i class="fa fa-github"></i>
        </span>
      </a>
    </div>
    <span class="nav-toggle" id="nav-toggle">
      <span></span>
      <span></span>
      <span></span>
    </span>
    <div class="nav-right nav-menu" id="nav-menu">
      <a class="nav-item" href="#">Home</a>
      <a class="nav-item" href="#">About me</a>
    </div>
  </nav>
</div>
<section class="hero is-primary">
  <div class="hero-body">
    <div class="container">
      <h1 class="title">探索.发现</h1>
      <h2 class="subtitle">找找新鲜，找找快乐</h2>
    </div>
  </div>
  <div class="hero-foot">
    <nav class="tabs is-boxed">
      <div class="container">
        <!-- <ul>
          <li><a href="index.html">首页</a></li>
          <li class="is-active"><a href="column.html">日记</a></li>
          <li><a href="about.html">关于我</a></li>
        </ul> -->
      </div>
    </nav>
  </div>
</section><section class="section">
  <div class="container">
    <div class="columns">

      <div class="column">
        <div class="">
<?php 
$num = 1;
foreach ($pres as $pre){// 读取每个目录下的文件
	$imgs = $storage->getBucket($bucket, $pre);
	echo $pre, ' (', count($imgs),')<br/>';
	foreach($imgs as $img){//
		//echo '-- '; print_r($img); echo '<br/>';
		$show = true;
		foreach ($rules as $rule){
			if(strpos($img['name'], $rule) !== false){
				$show = false;
				break;
			}
		}
		if($show){
			$num += 1;
			echo '--', basename($img['name']), '<br/>';
?>

              <figure class="image is-4by3">
                <a href="javascript:void(0)"><!-- ../storageHelp.php?uri=<?php echo urlencode($img['name']);?> -->
                  <img src="../storageHelp.php?uri=<?php echo urlencode($img['name']);?>&preview=128" alt="Image" lazyload></a>
              </figure>

<?php
		}
	}
}
?>
        </div>
      </div>
    </div>
  </div>
</section>

<footer class="footer">
  <div class="container">
    <div class="content has-text-centered">
      <p>全部打包下载
        <strong>尚未做</strong> 等我有空...
        
      </p>
    </div>
  </div>
</footer>

<div class="modal">
  <div class="modal-background"></div>
  <div class="modal-content">
    <p class="image">
      <img src="">
    </p>
  </div>
  <button class="modal-close"></button>
</div>

<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../html/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("img").click(function() {
      $src = $(this).attr("src").replace('preview','view');
      $(".modal").addClass("is-active");
      $(".modal img").attr("src", $src);
    });
    
    $(".modal-close").click(function() {
      $(".modal").removeClass("is-active");
    });
  });
</script>
</body>
</html>