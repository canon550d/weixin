<?php
include_once 'question.php';
include_once 'signature.php';
include_once 'TextMessage.php';
include_once 'ImageMessage.php';
include_once 'ImageTextMessage.php';

/**
  * wechat php test
  */

$signature = new Signature();
if($signature->checkSignature()){
	$message = new DefaultMessage();
	$postObj = $message->getPostObj();
	
	if(!isset($postObj)){
		$message = new TextMessage();
		$message->responseMsg();
		exit;
	}

	// text,image,voice,video,shortvideo,location,link
	if($postObj->MsgType=='text'){
		$keyword = $postObj->Content;
		
		$question = new Question();
		$item = $question->getAnswer($keyword);
		
		$className = $item['type'];
		$myclass = new ReflectionClass($className);
		$message = $myclass->newInstanceArgs();
		$message->responseMsg($item);
	
	}else if($postObj->MsgType=='image'){
		$message = new TextMessage();
		$message->responseText("给我看看你的照片吧");
	
	}else if($postObj->MsgType=='voice'){
		$message = new TextMessage();
		$message->responseText("你的声音很好听哦");
	
	}else if($postObj->MsgType=='video'){
		$message = new TextMessage();
		$message->responseText("真棒，再多拍一点给我看吧");
	
	}else if($postObj->MsgType=='shortvideo'){
		$message = new TextMessage();
		$message->responseText("给我看更多的小视频吧？");
	
	}else if($postObj->MsgType=='location'){
		$message = new TextMessage();
		$message->responseText("你在哪里呢？");
	
	}else if($postObj->MsgType=='link'){
		$message = new TextMessage();
		$message->responseText("不要给我发奇奇怪怪的东西");
	}
	
}

function traceHttp() {
	logger("REMOTE_ADDR:".$_SERVER['REMOTE_ADDR'].((strpos("101.226.103.62", "101.226")!==false)?" From WeiXin":" Unknown IP"));
	logger("Query_STRING:".$_SERVER['QUERY_STRING']);
	//logger(file_get_contents("php://input"));
}

function logger($content) {
	file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
}
?>