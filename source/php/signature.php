<?php 
class Signature {
	private $token;
	
	private function getToken(){
		/*
		if(!isset($this->token)){
			$config = parse_ini_file ('weixin.ini', true);
			$this->token = $config['Weixin']['token'];
		}
		return $this->token;
		*/
		require_once 'config.php';
		return TOKEN;
	}
	
	public function checkSignature() {
		// you must define TOKEN by yourself
		if ($this->getToken() == null) {
			throw new Exception('TOKEN is not defined!');
		}
	
		$signature = $_GET["signature"];
		$timestamp = $_GET["timestamp"];
		$nonce = $_GET["nonce"];
	
		$token = $this->getToken();
		$tmpArr = array($token, $timestamp, $nonce);
		// use SORT_STRING rule
		sort($tmpArr, SORT_STRING);
		$tmpStr = implode( $tmpArr );
		$tmpStr = sha1( $tmpStr );
	
		if( $tmpStr == $signature ){
			return true;
		}else{
			return false;
		}
	}
	
	private function logger($content) {
		file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
	}
}
?>