package es.uniovi.dlp.ast;

public abstract class AbstractDefinition extends AbstractASTNode implements Definition {

  private String name;
  private Type type;

  private int scope;

  public AbstractDefinition(String name, Type type, int line, int column) {
    super(line, column);
    this.name = name;
    this.type = type;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Type getType() {
    return this.type;
  }

  @Override
  public void setScope(int scope) {
    this.scope = scope;
  }

  @Override
  public int getScope() {
    return this.scope;
  }
}
