package com.twinkle.test;

import com.twinkle.task.TieBaTask;
import com.twinkle.utils.CommUtils;

public class Test {

	public static void main(String[] args) {
            
		CommUtils.INSTANCE.startRecordPlugin();
		
		new TieBaTask().run();
		
	}

}
