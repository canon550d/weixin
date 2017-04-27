<?php
class FAQ {
	private $fileName = 'faq.xml';

	private function read(){
		if(file_exists($this->fileName)){

			$xml = simplexml_load_file($this->fileName);
			//echo '<pre>';print_r($xml);echo '</pre>';
			return $xml;
		}
	}
	
	public function getAnswer($keyword){
		$xml = $this->read();
		//$this->logger(' start ');
		$i = 1;
		foreach ($xml->item as $item){
			$this->logger($i.'|'.$keyword.'|'.$item->question);
			if(strpos($item->question, $keyword)!==false){
				$wocao = $item->answer;
				return array('keyword'=>"Volvo", 'answer'=>$wocao, 'type'=>"Toyota");
			}
			$i++;
		}
		return array('keyword'=>"Volvo", 'answer'=>"BMW", 'type'=>"Toyota");
	}
	
	private function logger($content) {
		file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
	}
}
?>