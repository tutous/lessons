package de.tutous.spring.boot.common.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class BooleanBuilder {

	private enum Operator {
		AND, OR
	}

	private Map<Integer, Collection<Boolean>> result = new HashMap<Integer, Collection<Boolean>>();
	private int block = 0;

	private Operator operator;

	private BooleanBuilder(Operator operator) {
		this.operator = operator;
	}

	public static AndBooleanBuilder byAnd() {
		return new AndBooleanBuilder();
	}

	public static OrBooleanBuilder byOr() {
		return new OrBooleanBuilder();
	}

	protected void checkTrue(Boolean value) {
		if (!result.containsKey(block)) {
			result.put(block, new ArrayList<Boolean>());
		}
		result.get(block).add(value);
	}

	protected boolean newBlock() {
		if (result.containsKey(block) && !result.get(block).isEmpty() && !result.containsKey(block + 1)) {
			block++;
			return true;
		}
		return false;
	}

	public BooleanResult build() {
		return new BooleanResult(block + 1, operator, result.entrySet().stream().map(e -> e.getValue().stream()));
	}

	/**
	 * 
	 * @author FQ6Y9ZU
	 *
	 */
	public static class AndBooleanBuilder extends BooleanBuilder implements BooleanOps<AndBooleanBuilder> {

		public AndBooleanBuilder() {
			super(Operator.AND);
		}

		public AndBooleanBuilder or() {
			newBlock();
			return this;
		}

		@Override
		public AndBooleanBuilder isTrue(Boolean value) {
			checkTrue(value);
			return this;
		}

	}

	/**
	 * 
	 * @author FQ6Y9ZU
	 *
	 */
	public static class OrBooleanBuilder extends BooleanBuilder implements BooleanOps<OrBooleanBuilder> {

		public OrBooleanBuilder() {
			super(Operator.OR);
		}

		public OrBooleanBuilder and() {
			newBlock();
			return this;
		}

		@Override
		public OrBooleanBuilder isTrue(Boolean value) {
			checkTrue(value);
			return this;
		}
	}

	/**
	 * 
	 * @author FQ6Y9ZU
	 *
	 */
	public static class BooleanResult {

		private Stream<Stream<Boolean>> values;
		private long countBlocks;
		private Operator operator;
		private Boolean allMatch;

		public BooleanResult(int countBlocks, Operator operator, Stream<Stream<Boolean>> values) {
			this.countBlocks = countBlocks;
			this.operator = operator;
			this.values = values;
		}

		public Boolean isTrue() {
			return getResult();
		}

		public Boolean isFalse() {
			return !getResult();
		}

		private Boolean getResult() {
			if (Objects.isNull(allMatch)) {
				if (Operator.AND == operator) {
					long count = values.filter(l -> l.allMatch(v -> v)).count();
					allMatch = count > 0;
				} else if (Operator.OR == operator) {
					long count = values.filter(l -> l.anyMatch(v -> v)).count();
					allMatch = count == countBlocks;
				} else {
					throw new IllegalArgumentException();
				}
			}
			return allMatch;
		}

	}

}
