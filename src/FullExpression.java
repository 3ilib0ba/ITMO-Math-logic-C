public class FullExpression {
    public static Expression allTree = null;
    public static Expression current = null;

    public static void getCurrentToRoot() {
        if (current != null) {
            while (current.getParent() != null) {
                current = current.getParent();
            }
            while (current.getOperation() == Expression.Operation.SKOBKA) {
                current = current.getLeftBranch();
                current.setParent(null);
            }
        }
    }

    public static String toStringAllTree() {
        getCurrentToRoot();
        return current.toString();
    }

    public static void addVarNode(Expression expression) {
        // Проверка на то, есть ли переменная в мапе, если нет, то добавить

        if (current == null) {
            allTree = expression;
            current = allTree;
        } else {
            switch (current.getOperation()) {
                case OR: //A->(A|(A&!(C->!D)))
                case AND:
                    // работа если в корне был OR or AND
                    if (current.getLeftBranch() == null) {
                        current.setLeftBranch(expression);
                        current.getLeftBranch().setParent(current);
                        current = current.getLeftBranch();
                    } else if (current.getRightBranch() == null) {
                        current.setRightBranch(expression);
                        current.getRightBranch().setParent(current);
                        current = current.getRightBranch();
                    }
                    break;


                case IMPL: // работа если в корне был IMPL
                    if (current.getRightBranch() == null) {
                        current.setRightBranch(expression);
                        current.getRightBranch().setParent(current);
                        current = current.getRightBranch();
                    } else if (current.getLeftBranch() == null) {
                        current.setLeftBranch(expression);
                        current.getLeftBranch().setParent(current);
                        current = current.getLeftBranch();
                    }
                    break;
                case SKOBKA:
                    current.setLeftBranch(expression);
                    expression.setParent(current);
                    current = current.getLeftBranch();
                    break;
                default:
//                    System.out.println("пришло два подряд значения без операции");
                    System.exit(4);
                    break;
            }
        }
    }

    public static void findCorrectPlaceOper(Expression expression, Expression.Operation operation) {
        switch (operation) {
            case AND:
                while (current.getParent() != null && current.getParent().getOperation() == Expression.Operation.AND) {
                    current = current.getParent();
                }
                if (current.getParent() == null) {
                    current.setParent(expression);
                    expression.setLeftBranch(current);
                    current = current.getParent();
                } else if (current.getParent().getOperation() == Expression.Operation.SKOBKA) {
                    current.getParent().setLeftBranch(expression);
                    expression.setParent(current.getParent());
                    current.setParent(expression);
                    expression.setLeftBranch(current);
                    current = current.getParent();
                } else {
                    if (current.getParent().getOperation() == Expression.Operation.OR || current.getParent().getOperation() == Expression.Operation.IMPL) {
                        current.getParent().setRightBranch(expression); // TODO подумать про то, всегда ли это будет правой веткой
                        expression.setParent(current.getParent());
                        expression.setLeftBranch(current);
                        current.setParent(expression);
                        current = current.getParent();
                    } else {
//                        System.out.println("Родитель определен и не является операцией(не нулл, не &, ne |, ne ->");
                        System.exit(3);
                    }
                }
                break;
            case OR:
            case IMPL:
                while (current.getParent() != null && (current.getParent().getOperation() == Expression.Operation.OR
                        || current.getParent().getOperation() == Expression.Operation.AND)) {
                    current = current.getParent();
                }
                if (current.getParent() == null) {
                    current.setParent(expression);
                    expression.setLeftBranch(current);
                    current = current.getParent();
                } else if (current.getParent().getOperation() == Expression.Operation.SKOBKA) {
                    current.getParent().setLeftBranch(expression);
                    expression.setParent(current.getParent());
                    current.setParent(expression);
                    expression.setLeftBranch(current);
                    current = current.getParent();
                } else {
                    if (current.getParent().getOperation() == Expression.Operation.IMPL) {
                        current.getParent().setRightBranch(expression); // TODO подумать про то, всегда ли это будет правой веткой
                        expression.setParent(current.getParent());
                        expression.setLeftBranch(current);
                        current.setParent(expression);
                        current = current.getParent();
                    } else {
//                        System.out.println("Родитель определен и не является операцией(не нулл, не &, ne |, ne ->");
                        System.exit(3);
                    }
                }
                break;
        }
    }

    public static void addOperNode(Expression expression) {
        if (current.getParent() == null) {
            current.setParent(expression);
            // TODO подумать всегда ли в левого ребенка
            expression.setLeftBranch(current);
            current = current.getParent();
            return;
        }

        Expression.Operation parentOper = current.getParent().getOperation();
        Expression parentOfCurr = current.getParent();
        Expression.Operation newOper = expression.getOperation();

        switch (newOper) {
            case AND:
                if (parentOper == Expression.Operation.AND || parentOper == Expression.Operation.SKOBKA) {
                    findCorrectPlaceOper(expression, Expression.Operation.AND);
                } else {
                    // TODO возможно не только на место правого возможна подстановка
                    parentOfCurr.setRightBranch(expression);
                    expression.setParent(parentOfCurr);
                    expression.setLeftBranch(current);
                    current.setParent(expression);
                    current = current.getParent();
                }
                break;
            case OR:
                if (parentOper == Expression.Operation.IMPL) {
                    parentOfCurr.setRightBranch(expression);
                    expression.setParent(parentOfCurr);
                    expression.setLeftBranch(current);
                    current.setParent(expression);
                    current = current.getParent();
                } else {
                    findCorrectPlaceOper(expression, Expression.Operation.OR);
                }
                break;
            case IMPL:
                findCorrectPlaceOper(expression, Expression.Operation.IMPL);
                break;
        }
    }

    // value can be "(" or ")"
    public static void addSkobka(String value) {
        if (value.equals("(")) {
            if (current == null) {
                allTree = new Expression(null, null, null, null, Expression.Operation.SKOBKA, 0);
                current = allTree;
//                if (!ParsingSystem.localIsTrue) {

                current.setTrue(ParsingSystem.localIsTrue);
//                    ParsingSystem.localIsTrue = true;
                ParsingSystem.localIsTrue = 0;

            } else {
                Expression skobka = new Expression(null, null, null, null, Expression.Operation.SKOBKA, 0);
                skobka.setParent(current);
                skobka.setTrue(ParsingSystem.localIsTrue);
                ParsingSystem.localIsTrue = 0;

                if (current.getOperation() != Expression.Operation.SKOBKA) {
                    current.setRightBranch(skobka);
                    current = current.getRightBranch();
                } else {
                    current.setLeftBranch(skobka);
                    current = current.getLeftBranch();
                }
            }
        } else if (value.equals(")")) {
            while (current.getParent().getOperation() != Expression.Operation.SKOBKA) {
                current = current.getParent();
            }
            current = current.getParent();

//            if (    FullExpression.current != null &&
//                    FullExpression.current.getParent() != null &&
//                    FullExpression.current.getParent().getRightBranch() == FullExpression.current
//            ) {
//                if (current.getParent().getOperation() == Expression.Operation.IMPL) {
//                    current.getParent().setRightBranch(current.getLeftBranch());
//                    current.getLeftBranch().setParent(current.getParent());
//                    //current = current.getParent();
//                }
//            }
        } else {
//            System.out.println("Ошибка при обработке: ожидается ( или )");
            System.exit(5);
        }
    }
}