package es.uniovi.dlp.ast.types;

import es.uniovi.dlp.ast.AbstractType;
import es.uniovi.dlp.visitor.AbstractVisitor;

public class ErrorType extends AbstractType {

  public ErrorType(int line, int column) {
    super(line, column);
  }

  @Override
  public <ReturnType, ParamType> ReturnType accept(
      AbstractVisitor<ReturnType, ParamType> visitor, ParamType param) {
    return null;
  }

  @Override
  public String toString() {
    return "error";
  }
}
