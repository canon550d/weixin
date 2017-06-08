<?php 
$data = parse_ini_file ('gallery.ini', true);
echo json_encode($data);
?>