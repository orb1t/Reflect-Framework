package nth.reflect.fw.infrastructure.random.generator.name;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

import nth.reflect.fw.infrastructure.random.Random;

public class CompanyNameGeneratorTest {

	
	static final String A_COMPANY_NAME = "3M";
	
	@Test
	public void testForNoParameter() {
		Set<String> companyNames = Random.companyName().generateSet(100*1000);
		assertThat(companyNames, hasSize(greaterThan(100)));
		assertThat(companyNames, hasItem(A_COMPANY_NAME));
		assertThat(companyNames, not(hasItem(isEmptyString())));
	}
	

}
