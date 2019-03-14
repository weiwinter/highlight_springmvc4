package com.wisely.highlight_springmvc4.messageconverter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.wisely.highlight_springmvc4.domain.DemoObj;


public class MyMessageConverter extends AbstractHttpMessageConverter<DemoObj> {

	public MyMessageConverter(){
		super(new MediaType("application","x-wisely",Charset.forName("UTF-8")));
	}
	
	@Override
	protected DemoObj readInternal(Class<? extends DemoObj> class1,
			HttpInputMessage inputmessage) throws IOException,
			HttpMessageNotReadableException {
		String temp = StreamUtils.copyToString(inputmessage.getBody(), Charset.forName("UTF-8"));
		String[] tempArr = temp.split("-");
		return new DemoObj(new Long(tempArr[0]),tempArr[1]);
	}

	@Override
	protected boolean supports(Class<?> class1) {
		// TODO Auto-generated method stub
		return DemoObj.class.isAssignableFrom(class1);
	}

	@Override
	protected void writeInternal(DemoObj obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub
		String out = "hello:"+obj.getId()+"-"+obj.getName();
		outputMessage.getBody().write(out.getBytes());
	}

}
