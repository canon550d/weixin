<?php 
include_once 'question.php';
include_once 'TextMessage.php';
include_once 'ImageMessage.php';
include_once 'ImageTextMessage.php';

$question = new Question();
$item = $question->getAnswer("你好");

$answer = $item['answer'];
$message = new TextMessage();
$message->responseText($answer);

?>