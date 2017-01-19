<?php
class Question {
	private $fileName = 'faq.js';

	private function read(){
		$json = file_get_contents($this->fileName);
		return json_decode($json, TRUE);
	}
	
	public function getAnswer($keyword){
		if($keyword == null) {
			return array('keyword'=>"Volvo", 'answer'=>"没有关键词", 'type'=>"Toyota");
		}
		
		$json = $this->read();
		foreach ($json as $faq){
			//$hasKeyword = (strpos($faq['question'], $keyword)!==false);
			$hasKeyword = $faq['question']==$keyword;
			$this->logger($keyword.'|'.$faq['question'].'|'.$hasKeyword);
			if($hasKeyword){
				return array(
						'keyword'=>"Volvo",
						'answer'=>$faq['answer'], 
						'type'=>$faq['type']);
			}
		}
		return array('keyword'=>"Volvo", 'answer'=>"没有找到答案", 'type'=>"Toyota");
	}
	
	private function logger($content) {
		file_put_contents("log.html", date('Y-d-d H:i:s ').$content."\r\n<br/>\r\n", FILE_APPEND);
	}
}
?>