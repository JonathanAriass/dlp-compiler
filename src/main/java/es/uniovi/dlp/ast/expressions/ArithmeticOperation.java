package es.uniovi.dlp.ast.expressions;

import es.uniovi.dlp.ast.AbstractExpression;
import es.uniovi.dlp.ast.Expression;
import es.uniovi.dlp.visitor.AbstractVisitor;

public class ArithmeticOperation extends AbstractExpression {
  private final String operation;
  private final Expression leftExpression;
  private final Expression rightExpression;

  public ArithmeticOperation(
      String operation,
      Expression leftExpression,
      Expression rightExpression,
      int line,
      int column) {
    super(line, column);
    this.operation = operation;
    this.leftExpression = leftExpression;
    this.rightExpression = rightExpression;
  }

  public String getOperation() {
    return operation;
  }

  public Expression getLeftExpression() {
    return leftExpression;
  }

  public Expression getRightExpression() {
    return rightExpression;
  }

  @Override
  public <ReturnType, ParamType> ReturnType accept(
      AbstractVisitor<ReturnType, ParamType> visitor, ParamType param) {
    return visitor.visit(this, param);
  }
}
