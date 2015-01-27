package ca.sammy.common.util;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CsvFileUtilTest {

	@Test
	public void test_read_csv_input_file() throws IOException
	{
		List<String> inputs = CsvFileUtil.readStockCSVFile("input.csv");
		
		System.out.println(inputs);
		Assert.assertNotNull(inputs);
		Assert.assertEquals(145, inputs.size());
	}
}
