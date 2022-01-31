import java.util.LinkedList;
import java.util.List;

public class ParsingSystem {
    private static int position = 0;
    private static List<String> data = new LinkedList<>();
    // global var to ! sign
    public static int localIsTrue = 0;

    public static void defaultParser() {
        try {
            String nowValue = ParsingSystem.data.get(ParsingSystem.position++);

            if (nowValue.equals("&") || nowValue.equals("|") || nowValue.equals("->")) {
                FullExpression.addOperNode(ParsingSystem.getTreeFromOper(nowValue));
            } else if (nowValue.equals("!")) {
                //ParsingSystem.localIsTrue = !(ParsingSystem.localIsTrue); // Check refactoring
                ParsingSystem.localIsTrue += 1;
            } else {
                if (nowValue.equals("(") || nowValue.equals(")")) {
                    FullExpression.addSkobka(nowValue);
                } else {
                    FullExpression.addVarNode(ParsingSystem.getTreeFromVar(nowValue));
                }
            }

//            if (FullExpression.current != null &&
//                    FullExpression.current.getParent() != null &&
//                    FullExpression.current.getParent().getRightBranch() != null
//            ) {
//                if (FullExpression.current.equals(FullExpression.current.getParent().getRightBranch()) &&
//                        FullExpression.current.getParent().getOperation() == Expression.Operation.IMPL &&
//                        FullExpression.current.getOperation() == Expression.Operation.SKOBKA
//                ) {
//                    FullExpression.current.getParent().setRightBranch(FullExpression.current.getLeftBranch());
//                    FullExpression.current.getLeftBranch().setParent(FullExpression.current.getParent());
//                    FullExpression.current = FullExpression.current.getParent();
//                }
//            }

//            if (FullExpression.current.getParent() != null) {
//
////                FullExpression.current = FullExpression.current.getParent();
//                if (FullExpression.current.getOperation() == Expression.Operation.SKOBKA) {
//                    if (FullExpression.current.getLeftBranch() != null &&
//                            FullExpression.current.getLeftBranch().getOperation() == Expression.Operation.SKOBKA) {
//                        if (FullExpression.current.getLeftBranch().getLeftBranch() != null) {
//                            FullExpression.current.setLeftBranch(FullExpression.current.getLeftBranch().getLeftBranch());
//                            FullExpression.current.getLeftBranch().setParent(FullExpression.current);
//                        } else {
//                            FullExpression.current.setLeftBranch(null);
//                        }
//                    }
//                }

//                if (tmp.getLeftBranch() )

//                FullExpression.current = tmp;
//            }

            defaultParser();
        } catch (RuntimeException ignored) {

        }
    }

    public static Expression getTreeFromVar(String var) {
        Expression result = new Expression(null, null, null, var, Expression.Operation.VAR, localIsTrue);
        localIsTrue = 0;
        return result;
    }

    public static Expression getTreeFromOper(String oper) {
        Expression.Operation operation = null;
        switch (oper) {
            case "&":
                operation = Expression.Operation.AND;
                break;
            case "->":
                operation = Expression.Operation.IMPL;
                break;
            case "|":
                operation = Expression.Operation.OR;
                break;
        }
        return new Expression(null, null, null, oper, operation, 0);
    }

    void run(List<String> data) {
        ParsingSystem.data = data;
        position = 0;
        defaultParser();
    }
}