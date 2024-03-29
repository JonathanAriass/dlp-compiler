package es.uniovi.dlp.ast.definitions;

import es.uniovi.dlp.ast.AbstractDefinition;
import es.uniovi.dlp.ast.Statement;
import es.uniovi.dlp.ast.Type;
import es.uniovi.dlp.visitor.AbstractVisitor;
import java.util.ArrayList;
import java.util.List;

public class FunctionDefinition extends AbstractDefinition {

  private List<VarDefinition> varDefinitionList;
  private List<Statement> statementsList;

  public FunctionDefinition(
      String name,
      Type type,
      List<VarDefinition> varDefinitions,
      List<Statement> statementsList,
      int line,
      int column) {
    super(name, type, line, column);
    this.varDefinitionList = varDefinitions;
    this.statementsList = statementsList;
  }

  public List<VarDefinition> getVarDefinitionList() {
    return new ArrayList<>(varDefinitionList);
  }

  public List<Statement> getStatementsList() {
    return new ArrayList<>(statementsList);
  }

  @Override
  public <ReturnType, ParamType> ReturnType accept(
      AbstractVisitor<ReturnType, ParamType> visitor, ParamType param) {
    return visitor.visit(this, param);
  }
}
