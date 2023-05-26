package es.uniovi.dlp.visitor.codegeneration;

import es.uniovi.dlp.ast.Expression;
import es.uniovi.dlp.ast.Type;
import es.uniovi.dlp.ast.expressions.*;
import es.uniovi.dlp.ast.types.FunType;
import es.uniovi.dlp.visitor.AbstractVisitor;

public class ValueCGVisitor extends AbstractVisitor<Type, Type> {

  private CodeGenerator generator;
  private AddressCGVisitor addressVisitor;

  ValueCGVisitor(CodeGenerator generator) {
    this.generator = generator;
  }

  public void setAddressCGV(AddressCGVisitor address) {
    this.addressVisitor = address;
  }

  @Override
  public Type visit(ArithmeticOperation arithmetic, Type param) {
    arithmetic.getLeftExpression().accept(this, param);
    generator.promoteTo(arithmetic.getLeftExpression().getType(), arithmetic.getType());
    arithmetic.getRightExpression().accept(this, param);
    generator.promoteTo(arithmetic.getRightExpression().getType(), arithmetic.getType());
    generator.arithmetic(arithmetic.getOperation(), arithmetic.getType());
    return null;
  }

  @Override
  public Type visit(Cast cast, Type param) {
    super.visit(cast, param);
    generator.promoteTo(cast.getExp().getType(), cast.getCastType());
    return null;
  }

  @Override
  public Type visit(CharLiteral charLiteral, Type param) {
    generator.push(charLiteral.getType(), charLiteral.getCharacter());
    return null;
  }

  @Override
  public Type visit(DoubleLiteral doubleLiteral, Type param) {
    generator.push(doubleLiteral.getType(), doubleLiteral.getValue());
    return null;
  }

  @Override
  public Type visit(IntLiteral intLiteral, Type param) {
    generator.push(intLiteral.getType(), intLiteral.getValue());
    return null;
  }

  @Override
  public Type visit(UnaryMinus unaryMinus, Type param) {
    super.visit(unaryMinus, param);
    generator.arithmetic("-", unaryMinus.getType());
    return null;
  }

  @Override
  public Type visit(Negation not, Type param) {
    super.visit(not, param);
    generator.logical("!");
    return null;
  }

  @Override
  public Type visit(ComparisonOperation comparison, Type param) {
    Type superiorType =
        comparison
            .getLeftExpression()
            .getType()
            .getSuperiorType(comparison.getRightExpression().getType());
    comparison.getLeftExpression().accept(this, param);
    generator.promoteTo(comparison.getLeftExpression().getType(), superiorType);
    comparison.getRightExpression().accept(this, param);
    generator.promoteTo(comparison.getRightExpression().getType(), superiorType);
    generator.comparison(comparison.getOperation(), superiorType);
    return null;
  }

  @Override
  public Type visit(LogicalOperation logical, Type param) {
    super.visit(logical, param);
    generator.logical(logical.getOperation());
    generator.promoteTo(logical.getType(), logical.getRightExpression().getType());
    return null;
  }

  @Override
  public Type visit(Variable variable, Type param) {
    super.visit(variable, param);
    variable.accept(addressVisitor, param);
    generator.load(variable.getType());
    return null;
  }

  @Override
  public Type visit(ArrayAccess arrayAccess, Type param) {

    arrayAccess.accept(addressVisitor, param);
    generator.load(arrayAccess.getType());
    return null;
  }

  @Override
  public Type visit(FieldAccess fieldAccess, Type param) {
    fieldAccess.accept(addressVisitor, param);
    generator.promoteTo(fieldAccess.getExpression().getType(), fieldAccess.getType());
    generator.load(fieldAccess.getType());
    return null;
  }

  @Override
  public Type visit(Invocation functionInvocation, Type param) {
    int i = 0;
    for (Expression expression : functionInvocation.getArgs()) {
      expression.accept(this, null);
      generator.promoteTo(
          functionInvocation.getArgs().get(i).getType(),
          ((FunType) functionInvocation.getName().getDefinition().getType())
              .getParams()
              .get(i)
              .getType());
      i++;
    }
    generator.call(functionInvocation.getName().getName());
    return null;
  }
}
