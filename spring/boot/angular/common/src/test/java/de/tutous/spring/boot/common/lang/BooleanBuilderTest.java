package de.tutous.spring.boot.common.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.tutous.spring.boot.common.lang.BooleanBuilder;

public class BooleanBuilderTest {

	@Test
	public void testByAnd() {
		assertThat(BooleanBuilder.byAnd()
				.isTrue(true)
				.isNull(null)
				.isEquals(this, this)
				.nonNull("")
				.nonNull("", "")
				.build().isTrue()).isEqualTo(true);
		assertThat(BooleanBuilder.byAnd()
				.isTrue(false)
				.isNull(null)
				.isEquals(this, this)
				.nonNull("")
				.nonNull("", "")
				.build().isTrue()).isEqualTo(false);		
	}

	@Test
	public void testByOr() {
		assertThat(BooleanBuilder.byOr()
				.isTrue(true)
				.isNull(null)
				.isEquals(this, this)
				.nonNull("")
				.nonNull("", "")
				.build().isTrue()).isEqualTo(true);
		assertThat(BooleanBuilder.byOr()
				.isTrue(false)
				.isNull(null)
				.isEquals(this, this)
				.nonNull("")
				.nonNull("", "")
				.build().isTrue()).isEqualTo(true);		
	}

}
