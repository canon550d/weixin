<?php
include_once 'imessage.php';

class ImageMessage implements iMessage{
	
	public function responseMsg() {
		//get post data, May be due to the different environments
		$postStr = $GLOBALS["HTTP_RAW_POST_DATA"];
	
		//extract post data
		if (!empty($postStr)){
			/* libxml_disable_entity_loader is to prevent XML eXternal Entity Injection,
			 the best way is to check the validity of xml by yourself */
			libxml_disable_entity_loader(true);
			$postObj = simplexml_load_string($postStr, 'SimpleXMLElement', LIBXML_NOCDATA);
			$fromUsername = $postObj->FromUserName;
			$toUsername = $postObj->ToUserName;
			$keyword = trim($postObj->MsgId);
			$time = time();
			$textTpl = "<xml>
							<ToUserName><![CDATA[%s]]></ToUserName>
							<FromUserName><![CDATA[%s]]></FromUserName>
							<CreateTime>%s</CreateTime>
							<MsgType><![CDATA[image]]></MsgType>
							<PicUrl><![CDATA[%s]]></PicUrl>
							<MediaId><![CDATA[%s]]></MediaId>
							<MsgId>%s</MsgId>
							</xml>";
			if(true) {
				$picUrl = 'http://mmbiz.qpic.cn/mmbiz_jpg/7VPL37NE5SZq7VLp8BQoKZm1BVbOmVAmT8VfHvOdT2aB8YkPzXWBHERMhLT0OE4qmDZGzj8y5icqXgx9MiaUsbFw/0';
				$mediaId = 'MI4nXP0nRkdkv8mF20aNNWo69VIRqmdQVd0vou6dPVuqsoaE33PKz5sbONEw0__e';
				$msgId = '123311';
				$resultStr = sprintf($textTpl, $fromUsername, $toUsername, $time, $picUrl, $mediaId, $msgId);
				echo $resultStr;
			}else{
				echo "Input something...";
			}
		} else {
			echo "Œ¥’“µΩ";
			exit;
		}
	}
	private function logger($content) {
		file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
	}
}
?>