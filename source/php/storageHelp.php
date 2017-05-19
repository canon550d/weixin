<?php 
use sinacloud\sae\Storage as Storage;

$storage = new Storage();

$bucket = "travel";
$uri = $_GET['uri'];
$method = "";
$seconds ="";

$file = $storage->getUrl($bucket, $uri);

$f = new SaeFetchurl();
$img_data = $f->fetch( $file);

$img= new SaeImage();
$img->setData($img_data);

$img->resize(200,200);
$content = $img->exec();

header('content-type:image/jpg;');
echo $content;
?>
