<?php
use sinacloud\sae\Storage as Storage;

$config = parse_ini_file ('daochen.ini', true);
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

foreach ($pres as $pre){// 读取每个目录下的文件
	$imgs = $storage->getBucket($bucket, $pre);
	echo $pre, '(', count($imgs),')<br/>';
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
			echo $img['name'], '<br/>';
		}
	}
}

?>