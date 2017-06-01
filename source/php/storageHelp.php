<?php 
use sinacloud\sae\Storage as Storage;

$storage = new Storage();

$bucket = "travel";
$uri = $_GET['uri'];
$uri = urldecode($uri);
$preview = $_GET['preview'];
$view = $_GET['view'];
$method = "";
$seconds ="";

$file = $storage->getUrl($bucket, $uri);

$f = new SaeFetchurl();
$img_data = $f->fetch( $file);

if(isset($preview)){
	$img= new SaeImage();
	$img->setData($img_data);
	
	$rule = '16,24,32,48,64,96,128,192,256,384,512';
	if(strpos($rule, $preview) === false){
		$preview = '32';
	}
	$img->resize($preview);
	$content = $img->exec();
	
	header('content-type:image/jpg;');
	echo $content;

}elseif(isset($view)){
	header('content-type:image/jpg;');
	echo $img_data;

}else{
	header("Content-type: application/octet-stream");
	header("Content-Transfer-Encoding: binary");
	header('content-disposition:attachment;filename='. basename($file));
// 	header('content-length:'. filesize($file));
	echo $img_data;
}
?>
