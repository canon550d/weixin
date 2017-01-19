<?php
include_once 'imessage.php';
include_once 'DefaultMessage.php';

class TextMessage extends DefaultMessage implements iMessage{
	
	public function responseMsg() {
		$this->responseText(null);
	}
	
	public function responseText($content){
		$postStr = $this->getPostStr();
		
		//extract post data
		if (!empty($postStr)){
			$postObj = $this->getPostObj();
			$fromUsername = $postObj->FromUserName;
			$toUsername = $postObj->ToUserName;
			$keyword = trim($postObj->Content);
			$time = time();
			$textTpl = "<xml>
							<ToUserName><![CDATA[%s]]></ToUserName>
							<FromUserName><![CDATA[%s]]></FromUserName>
							<CreateTime>%s</CreateTime>
							<MsgType><![CDATA[%s]]></MsgType>
							<Content><![CDATA[%s]]></Content>
							<FuncFlag>0</FuncFlag>
							</xml>";
				
			$contentStr = "no keyword!";
			if($content!=null){
				$contentStr = $content;
			}elseif(!empty( $keyword )) {
				//TODO search($keyword)
				$contentStr = "Welcome to wechat world!";
			}
			$msgType = "text";
			$resultStr = sprintf($textTpl, $fromUsername, $toUsername, $time, $msgType, $contentStr);
			echo $resultStr;
		}else {
			echo $content;
		}
	}
}
?>