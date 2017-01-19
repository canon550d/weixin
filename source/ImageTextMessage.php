<?php
include_once 'imessage.php';
include_once 'DefaultMessage.php';

class ImageTextMessage extends DefaultMessage implements iMessage{
	
	public function responseMsg() {
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
							<ArticleCount>2</ArticleCount>
							<Articles>
							<item>
							<Title><![CDATA[title1]]></Title>
							<Description><![CDATA[description1]]></Description>
							<PicUrl><![CDATA[%s]]></PicUrl>
							<Url><![CDATA[http://www.baidu.com]]></Url>
							</item>
							<item>
							<Title><![CDATA[title]]></Title>
							<Description><![CDATA[description]]></Description>
							<PicUrl><![CDATA[picurl]]></PicUrl>
							<Url><![CDATA[url]]></Url>
							</item>
							</Articles>
							</xml>";
			if(true) {
				$picUrl = 'http://mmbiz.qpic.cn/mmbiz_jpg/7VPL37NE5SZq7VLp8BQoKZm1BVbOmVAmT8VfHvOdT2aB8YkPzXWBHERMhLT0OE4qmDZGzj8y5icqXgx9MiaUsbFw/0';
				
				$resultStr = sprintf($textTpl, $fromUsername, $toUsername, $time, $picUrl, $mediaId, $msgId);
				echo $resultStr;
			}else{
				echo "Input something...";
			}
		} else {
			echo "未找到";
		}
	}
	private function logger($content) {
		file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
	}
}
?>