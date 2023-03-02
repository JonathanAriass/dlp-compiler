package es.uniovi.dlp.ast;

import java.util.ArrayList;
import java.util.List;

public class Program extends AbstractASTNode {

  private List<Definition> definitions;

  public Program(List<Definition> definitions, int line, int column) {
    super(line, column);
    this.definitions = definitions;
  }

  public List<Definition> getDefinitions() {
    return new ArrayList<>(definitions);
  }
}
