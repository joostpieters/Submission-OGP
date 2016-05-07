package hillbillies.model.expressions;

import java.util.NoSuchElementException;

import hillbillies.model.Vector;
import hillbillies.part3.programs.SourceLocation;

public class PositionOfExpression extends Expression implements PositionExpression {
	
	public PositionOfExpression(UnitExpression target, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.target = target;
	}

	@Override
	public Vector evaluate() throws NoSuchElementException {
		return target.evaluate().getPosition();
	}
	
	private final UnitExpression target;
}
