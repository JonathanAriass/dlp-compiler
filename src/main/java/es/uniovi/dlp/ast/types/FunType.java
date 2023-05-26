package es.uniovi.dlp.ast.types;

import es.uniovi.dlp.ast.AbstractType;
import es.uniovi.dlp.ast.Expression;
import es.uniovi.dlp.ast.Type;
import es.uniovi.dlp.ast.definitions.VarDefinition;
import es.uniovi.dlp.visitor.AbstractVisitor;
import java.util.ArrayList;
import java.util.List;

public class FunType extends AbstractType {

  private List<VarDefinition> params;
  private Type returnType;

  public FunType(List<VarDefinition> params, Type returnType, int line, int column) {
    super(line, column);
    this.params = params;
    this.returnType = returnType;
  }

  public List<VarDefinition> getParams() {
    return new ArrayList<>(this.params);
  }

  public Type getReturnType() {
    return this.returnType;
  }

  @Override
  public <ReturnType, ParamType> ReturnType accept(
      AbstractVisitor<ReturnType, ParamType> visitor, ParamType param) {
    return visitor.visit(this, param);
  }

  @Override
  public boolean isLogical() {
    System.out.println("Entra aqui");
    if (returnType instanceof IntType) {
      return true;
    }
    return false;
  }

  @Override
  public boolean promotableTo(Type to) {
    return returnType.promotableTo(to);
  }

  @Override
  public Type call(List<Expression> args) {
    if (hasOtherArgs(args)) {
      return super.call(args);
    }
    for (int i = 0; i < args.size(); i++) {
      if (!args.get(i).getType().promotesTo(params.get(i).getType())) {
        return super.call(args);
      }
    }
    return returnType;
  }

  @Override
  public boolean hasOtherArgs(List<Expression> args) {
    if (args.size() != params.size()) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "function";
  }
}
