package me.dowen.solr.analyzers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("unused")
@RunWith(com.carrotsearch.randomizedtesting.RandomizedRunner.class)
public class TestPinyinTransformTokenFilter {

	private PinyinTransformTokenFilter filter;

	@Before
	public void before() {
		MockTokenizer tokenizer = new MockTokenizer(new StringReader("和平 重量 and 中国"));
		this.filter = new PinyinTransformTokenFilter(tokenizer);
	}

	@Test
	public void test() throws IOException {
		this.filter.reset();
		int position = 0;
		while (this.filter.incrementToken()) {
			CharTermAttribute termAtt = this.filter.getAttribute(CharTermAttribute.class);
			String token = termAtt.toString();
			int increment = this.filter.getAttribute(PositionIncrementAttribute.class).getPositionIncrement();
			position += increment;
			OffsetAttribute offset = this.filter.getAttribute(OffsetAttribute.class);
			TypeAttribute type = this.filter.getAttribute(TypeAttribute.class);
			System.out.println(position + "[" + offset.startOffset() + "," + offset.endOffset() + "} (" + type.type() + ") " + token);
		}
	}

}
