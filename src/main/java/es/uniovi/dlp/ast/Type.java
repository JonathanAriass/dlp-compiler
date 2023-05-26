package es.uniovi.dlp.ast;

import java.util.List;

public interface Type extends ASTNode {

  Type indexing(Type type);

  Type cast(Type type);

  Type dot(String type);

  Type arithmetic(Type type);

  Type logical(Type type);

  Type call(List<Expression> args);

  Type comparison(Type type);

  Type assignment(Type type);

  boolean isIndexable();

  boolean allowDot(String field);

  boolean isArithmetic();

  boolean isLogical();

  boolean hasOtherArgs(List<Expression> args);

  boolean promotableTo(Type to);

  Type getSuperiorType(Type to);

  boolean promotesTo(Type to);

  int getNumberOfBytes();

  int getFieldOffset(String field);
}
