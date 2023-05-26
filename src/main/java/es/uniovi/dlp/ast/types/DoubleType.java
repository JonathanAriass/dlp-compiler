package es.uniovi.dlp.ast.types;

import es.uniovi.dlp.ast.AbstractType;
import es.uniovi.dlp.ast.Type;
import es.uniovi.dlp.visitor.AbstractVisitor;

public class DoubleType extends AbstractType {
  public DoubleType(int line, int column) {
    super(line, column);
  }

  @Override
  public <ReturnType, ParamType> ReturnType accept(
      AbstractVisitor<ReturnType, ParamType> visitor, ParamType param) {
    return visitor.visit(this, param);
  }

  @Override
  public Type cast(Type type) {
    if (type instanceof IntType || type instanceof DoubleType) {
      return type;
    }
    return super.cast(type);
  }

  @Override
  public Type arithmetic(Type type) {
    if (type instanceof DoubleType || type instanceof IntType || type instanceof CharType) {
      return this;
    }
    return super.arithmetic(type);
  }

  @Override
  public Type comparison(Type type) {
    if (type instanceof IntType || type instanceof DoubleType || type instanceof CharType) {
      return new IntType(type.getLine(), type.getColumn()); // 0: false, 1..*: true
    }
    return super.comparison(type);
  }

  @Override
  public Type assignment(Type type) {
    if (type instanceof DoubleType || type instanceof IntType || type instanceof CharType) {
      return type; // FAQ: Should be char assignable to double?
    }
    return super.assignment(type);
  }

  @Override
  public boolean promotableTo(Type to) {
    return (to instanceof FunType && ((FunType) to).getReturnType() instanceof DoubleType)
        || to instanceof DoubleType;
  }

  @Override
  public boolean promotesTo(Type to) {
    return (to instanceof DoubleType);
  }

  @Override
  public Type getSuperiorType(Type to) {
    return this;
  }

  @Override
  public boolean isArithmetic() {
    return true;
  }

  @Override
  public int getNumberOfBytes() {
    return 4;
  }

  @Override
  public String toString() {
    return "double";
  }
}
