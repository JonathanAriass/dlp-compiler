package es.uniovi.dlp.visitor.semantic;

import es.uniovi.dlp.ast.Type;
import es.uniovi.dlp.ast.definitions.FunctionDefinition;
import es.uniovi.dlp.ast.expressions.*;
import es.uniovi.dlp.ast.statements.*;
import es.uniovi.dlp.ast.types.*;
import es.uniovi.dlp.ast.types.ErrorType;
import es.uniovi.dlp.error.Error;
import es.uniovi.dlp.error.ErrorManager;
import es.uniovi.dlp.error.ErrorReason;
import es.uniovi.dlp.error.Location;
import es.uniovi.dlp.visitor.AbstractVisitor;

public class TypeCheckingVisitor extends AbstractVisitor<Type, Type> {

  @Override
  public Type visit(FunctionDefinition functionDefinition, Type param) {
    super.visit(functionDefinition, functionDefinition.getType());
    return null;
  }

  @Override
  public Type visit(Assignment assign, Type param) {
    super.visit(assign, param);

    if (assign.getLeftExpression().getType() instanceof ErrorType
        || assign.getRightExpression().getType() instanceof ErrorType) return null;
    if (!assign.getLeftExpression().isLValue()) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(assign.getLine(), assign.getColumn()), ErrorReason.LVALUE_REQUIRED));
      return null;
    }
    if (!assign.getRightExpression().getType().promotesTo(assign.getLeftExpression().getType())) {
      System.out.println("ENTRA AQUI");
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(
                      assign.getLeftExpression().getLine(), assign.getLeftExpression().getColumn()),
                  ErrorReason.INCOMPATIBLE_TYPES));
      return null;
    }
    //    } else {
    //      if
    // (assign.getLeftExpression().getType().assignment(assign.getRightExpression().getType())
    //          == null) {
    //        ErrorManager.getInstance()
    //            .addError(
    //                new Error(
    //                    new Location(
    //                        assign.getLeftExpression().getLine(),
    //                        assign.getLeftExpression().getColumn()),
    //                    ErrorReason.INCOMPATIBLE_TYPES));
    //      }
    //    }
    return null;
  }

  @Override
  public Type visit(Read read, Type param) {
    super.visit(read, param);
    if (!read.getExpression().isLValue()) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(read.getLine(), read.getColumn()), ErrorReason.LVALUE_REQUIRED));
    }
    return null;
  }

  @Override
  public Type visit(ArrayAccess arrayAccess, Type param) {
    super.visit(arrayAccess, param);
    arrayAccess.setType(
        arrayAccess.getArray().getType().indexing(arrayAccess.getIndex().getType()));
    if (!arrayAccess.getArray().getType().isIndexable()) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(arrayAccess.getLine(), arrayAccess.getColumn()),
                  ErrorReason.INVALID_INDEXING));
      arrayAccess.setType(new ErrorType(arrayAccess.getLine(), arrayAccess.getColumn()));
      return null;
    }
    if (arrayAccess.getType() == null) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(
                      arrayAccess.getIndex().getLine(), arrayAccess.getIndex().getColumn()),
                  ErrorReason.INVALID_INDEX_EXPRESSION));
      arrayAccess.setType(new ErrorType(arrayAccess.getLine(), arrayAccess.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(Cast cast, Type param) {
    super.visit(cast, param);
    cast.setType(cast.getExp().getType().cast(cast.getCastType()));
    if (cast.getType() == null) {
      ErrorManager.getInstance()
          .addError(
              new Error(new Location(cast.getLine(), cast.getColumn()), ErrorReason.INVALID_CAST));
      cast.setType(new ErrorType(cast.getLine(), cast.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(FieldAccess fieldAccess, Type param) {
    super.visit(fieldAccess, param);
    if (!fieldAccess.getExpression().getType().allowDot(fieldAccess.getField())) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(fieldAccess.getLine(), fieldAccess.getColumn()),
                  ErrorReason.NO_SUCH_FIELD));
      fieldAccess.setType(new ErrorType(fieldAccess.getLine(), fieldAccess.getColumn()));
      return null;
    }
    fieldAccess.setType(fieldAccess.getExpression().getType().dot(fieldAccess.getField()));
    if (fieldAccess.getType() == null) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(fieldAccess.getLine(), fieldAccess.getColumn()),
                  ErrorReason.INVALID_DOT));
      fieldAccess.setType(new ErrorType(fieldAccess.getLine(), fieldAccess.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(ArithmeticOperation arithmetic, Type param) {
    super.visit(arithmetic, param);
    arithmetic.setType(
        arithmetic
            .getLeftExpression()
            .getType()
            .arithmetic(arithmetic.getRightExpression().getType()));
    if (arithmetic.getType() == null) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(arithmetic.getLine(), arithmetic.getColumn()),
                  ErrorReason.INVALID_ARITHMETIC));
      arithmetic.setType(new ErrorType(arithmetic.getLine(), arithmetic.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(UnaryMinus unaryMinus, Type param) {
    super.visit(unaryMinus, param);
    if (!unaryMinus.getExpression().getType().isArithmetic()) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(
                      unaryMinus.getExpression().getLine(), unaryMinus.getExpression().getColumn()),
                  ErrorReason.INVALID_ARITHMETIC));
      unaryMinus.setType(
          new ErrorType(
              unaryMinus.getExpression().getLine(), unaryMinus.getExpression().getColumn()));
    }
    unaryMinus.setType(new IntType(unaryMinus.getLine(), unaryMinus.getColumn()));
    return null;
  }

  @Override
  public Type visit(LogicalOperation logOp, Type param) {
    super.visit(logOp, param);
    logOp.setType(
        logOp.getLeftExpression().getType().logical(logOp.getRightExpression().getType()));
    if (logOp.getType() == null) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(logOp.getLine(), logOp.getColumn()),
                  ErrorReason.INVALID_LOGICAL,
                  logOp.getRightExpression().getType().toString()));
      logOp.setType(new ErrorType(logOp.getLine(), logOp.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(While wh, Type param) {
    super.visit(wh, param);
    if (wh.getCondition().getType() instanceof ErrorType) {
      return null;
    }
    if (!wh.getCondition().getType().isLogical()) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(wh.getCondition().getLine(), wh.getCondition().getColumn()),
                  ErrorReason.NOT_LOGICAL));
    }
    return null;
  }

  @Override
  public Type visit(Conditional cond, Type param) {
    super.visit(cond, param);
    if (cond.getCondition().getType() instanceof ErrorType) {
      return null;
    }
    if (!cond.getCondition().getType().isLogical()) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(cond.getCondition().getLine(), cond.getCondition().getColumn()),
                  ErrorReason.NOT_LOGICAL));
    }
    return null;
  }

  @Override
  public Type visit(Negation negation, Type param) {
    super.visit(negation, param);
    if (!negation.getExpression().getType().isLogical()) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(
                      negation.getExpression().getLine(), negation.getExpression().getColumn()),
                  ErrorReason.NOT_LOGICAL));
      negation.setType(new ErrorType(negation.getLine(), negation.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(Invocation invocation, Type param) {
    if (invocation.getName().getDefinition() == null) {
      return null;
    }
    super.visit(invocation, param);
    if (invocation.getName().getDefinition().getType().hasOtherArgs(invocation.getArgs())) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(invocation.getLine(), invocation.getColumn()),
                  ErrorReason.INVALID_ARGS));
      invocation.setType(new ErrorType(invocation.getLine(), invocation.getColumn()));
      return null;
    }
    if (!(invocation.getName().getDefinition().getType() instanceof FunType)) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(invocation.getLine(), invocation.getColumn()),
                  ErrorReason.INVALID_INVOCATION));
      invocation.setType(new ErrorType(invocation.getLine(), invocation.getColumn()));
      return null;
    }
    //
    invocation.setType(invocation.getName().getDefinition().getType().call(invocation.getArgs()));
    //    invocation.setType(((FunType)
    // invocation.getName().getDefinition().getType()).getReturnType());
    //
    // System.out.println(invocation.getName().getDefinition().getType().call(invocation.getArgs()));
    if (invocation.getType() == null) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(invocation.getLine(), invocation.getColumn()),
                  ErrorReason.INVALID_ARGS));
      invocation.setType(new ErrorType(invocation.getLine(), invocation.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(ComparisonOperation comp, Type param) {
    super.visit(comp, param);
    //    comp.setType(
    //        comp.getLeftExpression().getType().comparison(comp.getRightExpression().getType()));
    comp.setType(new IntType(0, 0));
    if (comp.getType() == null) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(comp.getLine(), comp.getColumn()), ErrorReason.INVALID_COMPARISON));
      comp.setType(new ErrorType(comp.getLine(), comp.getColumn()));
    }
    return null;
  }

  @Override
  public Type visit(Return ret, Type param) {
    super.visit(ret, param);
    if (!ret.getReturnExpression().getType().promotesTo(((FunType) param).getReturnType())) {
      ErrorManager.getInstance()
          .addError(
              new Error(
                  new Location(ret.getLine(), ret.getColumn()), ErrorReason.INVALID_RETURN_TYPE));
    }
    //    if (!ret.getReturnExpression().getType().promotableTo(((FunType) param).getReturnType()))
    // {
    //      ErrorManager.getInstance()
    //          .addError(
    //              new Error(
    //                  new Location(ret.getLine(), ret.getColumn()),
    // ErrorReason.INVALID_RETURN_TYPE));
    //      //      ret.getReturnExpression().setType(new ErrorType(ret.getLine(),
    // ret.getColumn()));
    //
    //      //      return null;
    //    }
    ret.getReturnExpression().setType(((FunType) param).getReturnType());
    return null;
  }

  @Override
  public Type visit(Variable variable, Type param) {
    super.visit(variable, param);
    variable.setType(variable.getDefinition().getType());
    return null;
  }

  @Override
  public Type visit(IntLiteral intLit, Type param) {
    super.visit(intLit, param);
    intLit.setType(new IntType(intLit.getLine(), intLit.getColumn()));
    return null;
  }

  @Override
  public Type visit(CharLiteral charLit, Type param) {
    super.visit(charLit, param);
    charLit.setType(new CharType(charLit.getLine(), charLit.getColumn()));
    return null;
  }

  @Override
  public Type visit(DoubleLiteral doubleLit, Type param) {
    super.visit(doubleLit, param);
    doubleLit.setType(new DoubleType(doubleLit.getLine(), doubleLit.getColumn()));
    return null;
  }
}
