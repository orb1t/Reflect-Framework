package nth.reflect.fw.infrastructure.random.generator.address;

import java.util.List;
import java.util.stream.Collectors;

import nth.reflect.fw.infrastructure.random.Random;
import nth.reflect.fw.infrastructure.random.RandomGenerator;
import nth.reflect.fw.infrastructure.random.ValueGenerator;
import nth.reflect.fw.infrastructure.random.generator.collection.FromListGenerator;
import nth.reflect.fw.infrastructure.random.generator.resource.Resources;

public class PostalCodeGenerator extends RandomGenerator<String> {

	public static String DEFAULT_POSTAL_CODE_FORMAT="#####";
	private final RandomGenerator<String> postalCodeFormatGenerator;

	public PostalCodeGenerator() {
		 List<RandomCountry> randomCountries = Resources.countryRepository().getAll();
		List<String> postalCodeFormats = randomCountries.stream().map(RandomCountry::getPostalCodeFormat).collect(Collectors.toList());
		postalCodeFormatGenerator = new FromListGenerator<String>(postalCodeFormats);
	}

	public PostalCodeGenerator(RandomGenerator<String> postalCodeFormatGenerator) {
		this.postalCodeFormatGenerator = postalCodeFormatGenerator;
	}

	public PostalCodeGenerator forCountry(RandomCountry country) {
		String postalCodeFormat = country.getPostalCodeFormat();
		RandomGenerator<String> postalCodeFormatGenerator = new ValueGenerator<String>(postalCodeFormat);
		return new PostalCodeGenerator(postalCodeFormatGenerator);
	}

	@Override
	public String generate() {
		String postCodeFormat = postalCodeFormatGenerator.generate();
		String postalCode = Random.format().forFormat(postCodeFormat).generate();
		return postalCode;
	}

}
