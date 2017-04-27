<?php
include_once 'imessage.php';
include_once 'DefaultMessage.php';

class ImageTextMessage extends DefaultMessage implements iMessage{
	
	public function responseMsg($data) {
		$postStr = $this->getPostStr();
	
		//extract post data
		if (!empty($postStr)){
			$postObj = $this->getPostObj();
			$fromUsername = $postObj->FromUserName;
			$toUsername = $postObj->ToUserName;
			$keyword = trim($postObj->MsgId);
			$time = time();
			$textTpl = "<xml>
							<ToUserName><![CDATA[%s]]></ToUserName>
							<FromUserName><![CDATA[%s]]></FromUserName>
							<CreateTime>%s</CreateTime>
							<MsgType><![CDATA[news]]></MsgType>
							<ArticleCount>%s</ArticleCount>
							<Articles>%s</Articles>
							</xml>";
			if(true) {
				$count = count($data['answer']);
				$articles = $this->getTempl($data['answer']);
				$resultStr = sprintf($textTpl, $fromUsername, $toUsername, $time, $count, $articles);
				echo $resultStr;
			}else{
				echo "Input something...";
			}
		} else {
			echo "未找到";
		}
	}
	private function getTempl($items){
		$textTpl = "<item><Title><![CDATA[%s]]></Title><Description><![CDATA[%s]]></Description><PicUrl><![CDATA[%s]]></PicUrl><Url><![CDATA[%s]]></Url></item>";
		$text = '';
		foreach ($items as $item){
			$text .= sprintf($textTpl, $item['title'], $item['description'], $item['picurl'], $item['url']);
		}
		return $text;
	}
	
	private function logger($content) {
		file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
	}
}
?>