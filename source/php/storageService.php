<?php
use sinacloud\sae\Storage as Storage;

$name = $_GET['name'];
if(isset($name))
	$name .= '.ini';
else
	$name = 'empty.ini';
if(!file_exists($name))
	$name = 'empty.ini';

$config = parse_ini_file ($name, true);
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
$page = $_GET['page'];
if(!isset($page))
	$page = $_POST['page'];
if(!isset($page))
	$page = 1;
if($page<1)
	$page = 1;

$limit = 5;
$min = ($page-1)*$limit;
$max = $page*$limit;
$num = 0;

foreach ($pres as $pre){// 读取每个目录下的文件
	$imgs = $storage->getBucket($bucket, $pre);
	if($num>=$min && $num<$max){
		//echo $pre, '(', count($imgs),')<br/>';
		$data['info'][$num%5] = $pre.'('. count($imgs) .')';
	}
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
			if($num>=$min && $num<$max){
				$data['list'][] = $img['name'];
			}
			$num += 1;
		}
	}
}
echo json_encode($data);
?>