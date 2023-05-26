package es.uniovi.dlp.ast;

import es.uniovi.dlp.ast.types.ErrorType;
import java.util.List;

public abstract class AbstractType extends AbstractASTNode implements Type {

  // FAQ: check Type interface to see what is the question.

  public AbstractType(int line, int column) {
    super(line, column);
  }

  @Override
  public Type indexing(Type type) {
    if (type instanceof ErrorType) {
      return type;
    }
    return null;
  }

  @Override
  public Type cast(Type type) {
    if (type instanceof ErrorType) {
      return type;
    }
    return null;
  }

  @Override
  public Type dot(String type) {
    return null;
  }

  @Override
  public Type arithmetic(Type type) {
    if (type instanceof ErrorType) {
      return type;
    }
    return null;
  }

  @Override
  public Type logical(Type type) {
    if (type instanceof ErrorType) {
      return type;
    }
    return null;
  }

  @Override
  public Type call(List<Expression> args) {
    return null;
  }

  @Override
  public Type comparison(Type type) {
    if (type instanceof ErrorType) {
      return type;
    }
    return null;
  }

  @Override
  public Type assignment(Type type) {
    if (type instanceof ErrorType) {
      return type;
    }
    return null;
  }

  @Override
  public boolean isIndexable() {
    return false;
  }

  @Override
  public boolean allowDot(String field) {
    // this will have to be true for those errors where the type
    // is not valid (ex: examples/errors/types/invalid_struct.xana, fourth condition)
    return true;
  }

  @Override
  public boolean isArithmetic() {
    return false;
  }

  @Override
  public boolean isLogical() {
    return false;
  }

  @Override
  public boolean hasOtherArgs(List<Expression> args) {
    return false;
  }

  @Override
  public boolean promotableTo(Type to) {
    return false;
  }

  @Override
  public boolean promotesTo(Type to) {
    return false;
  }

  @Override
  public Type getSuperiorType(Type to) {
    if (to instanceof ErrorType) {
      return to;
    }
    return null;
  }

  @Override
  public int getNumberOfBytes() {
    return 0;
  }

  @Override
  public int getFieldOffset(String field) {
    return -1024;
  }
}
