<?php
include_once 'imessage.php';

class DefaultMessage implements iMessage {
	
	protected function getPostStr(){
		//get post data, May be due to the different environments
		return $postStr = $GLOBALS["HTTP_RAW_POST_DATA"];
	}
	
	public function responseMsg($data){
	}
	
	/**
	 * ToUserName
	 * FromUserName
	 * CreateTime
	 * MsgType
	 */
	public function getPostObj() {
		$postStr = $this->getPostStr();
		
		//extract post data
		if (!empty($postStr)){
			/* libxml_disable_entity_loader is to prevent XML eXternal Entity Injection,
			 the best way is to check the validity of xml by yourself */
			libxml_disable_entity_loader(true);
			$postObj = simplexml_load_string($postStr, 'SimpleXMLElement', LIBXML_NOCDATA);
			
			return $postObj;
		}else {
			return null;
		}
	}
}
?>