package ui;

import org.junit.Assert;
import org.junit.Test;

public class UiUtilsTest {

	@Test
	public void isSmallLength_nameがnull_trueを返すこと() {
		Assert.assertTrue(UiUtils.isSmallLength(null, "fieldName", 0));
	}

	@Test
	public void isSmallLength_nameがnullでない_nameの文字数がlengthより大きい() {
		Assert.assertFalse(UiUtils.isSmallLength("testName", "fieldName", 3));
	}
}
