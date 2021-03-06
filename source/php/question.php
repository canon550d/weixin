<?php
class Question {
	private $fileName = 'faq.js';

	private function read(){
		$json = file_get_contents($this->fileName);
		return json_decode($json, TRUE);
	}
	
	public function getAnswer($keyword){
		if($keyword == null) {
			return array('keyword'=>"Volvo", 'answer'=>"没有关键词", 'type'=>"TextMessage");
		}
		
		$json = $this->read();
		foreach ($json as $faq){
			$hasKeyword = (strpos($faq['question'], $keyword)!==false);
			if($hasKeyword){
				return array('keyword'=>"Volvo", 'answer'=>$faq['answer'], 'type'=>$faq['type']);
			}
		}
		return array('keyword'=>"Volvo", 'answer'=>"没有找到答案", 'type'=>"TextMessage");
	}
	
	private function logger($content) {
		file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
	}
}
?>