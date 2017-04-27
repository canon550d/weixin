<?php
include_once 'imessage.php';
include_once 'DefaultMessage.php';

class TextMessage extends DefaultMessage implements iMessage{
	
	public function responseMsg($data) {
		$this->responseText($data['answer']);
	}
	
	public function responseText($content){
		$postStr = $this->getPostStr();
		
		//extract post data
		if (!empty($postStr)){
			$postObj = $this->getPostObj();
			$fromUsername = $postObj->FromUserName;
			$toUsername = $postObj->ToUserName;

			$textTpl = "<xml>
							<ToUserName><![CDATA[%s]]></ToUserName>
							<FromUserName><![CDATA[%s]]></FromUserName>
							<CreateTime>%s</CreateTime>
							<MsgType><![CDATA[%s]]></MsgType>
							<Content><![CDATA[%s]]></Content>
							<FuncFlag>0</FuncFlag>
							</xml>";
			
			$time = time();
			$msgType = "text";
			$contentStr = "no keyword!";
			if(!empty( $content )) {
				$contentStr = $content;
			}
			
			$resultStr = sprintf($textTpl, $fromUsername, $toUsername, $time, $msgType, $contentStr);
			echo $resultStr;
		}else {
			echo $content;
		}
	}
}
?>