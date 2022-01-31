import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListOfHypoAndAxi {
    public static List<Expression> HYPO = new ArrayList<>();
    public static Expression alpha;

    public static void addHypo(Expression hypo) {
        HYPO.add(hypo);
    }

    public static boolean isHypo(Expression checkingExpr) {
        for (Expression hypoAndAxiom : HYPO) {
            if (Expression.compareExpressions(hypoAndAxiom, checkingExpr)) {
                return true;
            }
        }
        return false;
    }


    public static boolean publicCheckAllAxis(Expression expression) {
        return ListOfHypoAndAxi.uno(expression) ||
                ListOfHypoAndAxi.duo(expression) ||
                ListOfHypoAndAxi.tres(expression) ||
                ListOfHypoAndAxi.quattro(expression) ||
                ListOfHypoAndAxi.payt(expression) ||
                ListOfHypoAndAxi.six(expression) ||
                ListOfHypoAndAxi.seven(expression) ||
                ListOfHypoAndAxi.octo(expression) ||
                ListOfHypoAndAxi.nine(expression) ||
                ListOfHypoAndAxi.deca(expression);

    }

    public static boolean uno(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    Expression.compareExpressions(
                            expression.getLeftBranchWithout(),
                            expression.getRightBranchWithout().getRightBranchWithout()) &&
                    expression.getTrue() == 0 &&
                    expression.getRightBranchWithout().getTrue() == 0
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean duo(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getLeftBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getRightBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&

                    expression.getLeftBranch().getOperation() == Expression.Operation.SKOBKA &&
                    expression.getRightBranchWithout().getLeftBranch().getOperation() == Expression.Operation.SKOBKA &&
                    expression.getRightBranchWithout().getRightBranch().getOperation() == Expression.Operation.SKOBKA &&

                    Expression.compareExpressions(expression.getLeftBranch().getLeftBranchWithout().getLeftBranchWithout(), expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getLeftBranchWithout()) &&
                    Expression.compareExpressions(expression.getLeftBranch().getLeftBranchWithout().getLeftBranchWithout(), expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getLeftBranchWithout()) &&
                    Expression.compareExpressions(expression.getLeftBranch().getLeftBranchWithout().getRightBranchWithout(), expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getRightBranchWithout().getLeftBranchWithout()) &&
                    Expression.compareExpressions(expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getRightBranchWithout(), expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getRightBranchWithout().getRightBranchWithout())
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean tres(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getRightBranchWithout().getOperation() == Expression.Operation.AND &&
                    Expression.compareExpressions(expression.getLeftBranchWithout(), expression.getRightBranchWithout().getRightBranchWithout().getLeftBranchWithout()) &&
                    Expression.compareExpressions(expression.getRightBranchWithout().getLeftBranchWithout(), expression.getRightBranchWithout().getRightBranchWithout().getRightBranchWithout()) &&
                    expression.getRightBranchWithout().getTrue() == 0 && expression.getRightBranchWithout().getRightBranchWithout().getTrue() == 0
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean quattro(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getLeftBranchWithout().getOperation() == Expression.Operation.AND &&
                    Expression.compareExpressions(expression.getLeftBranchWithout().getLeftBranchWithout(), expression.getRightBranchWithout()) &&
                    expression.getLeftBranchWithout().getTrue() == 0 && expression.getTrue() == 0
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean payt(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getLeftBranchWithout().getOperation() == Expression.Operation.AND &&
                    Expression.compareExpressions(expression.getLeftBranchWithout().getRightBranchWithout(), expression.getRightBranchWithout()) &&
                    expression.getLeftBranchWithout().getTrue() == 0 && expression.getTrue() == 0
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean six(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getOperation() == Expression.Operation.OR &&
                    Expression.compareExpressions(expression.getLeftBranchWithout(), expression.getRightBranchWithout().getLeftBranchWithout()) &&
                    expression.getTrue() == 0 && expression.getRightBranchWithout().getTrue() == 0
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean seven(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getOperation() == Expression.Operation.OR &&
                    Expression.compareExpressions(expression.getLeftBranchWithout(), expression.getRightBranchWithout().getRightBranchWithout()) &&
                    expression.getTrue() == 0 && expression.getRightBranchWithout().getTrue() == 0
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean octo(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getLeftBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&

                    expression.getLeftBranch().getOperation() == Expression.Operation.SKOBKA &&
                    expression.getRightBranchWithout().getLeftBranch().getOperation() == Expression.Operation.SKOBKA &&
                    expression.getRightBranchWithout().getRightBranch().getOperation() == Expression.Operation.SKOBKA &&

                    expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getLeftBranchWithout().getOperation() == Expression.Operation.OR &&

                    Expression.compareExpressions(expression.getLeftBranch().getLeftBranchWithout().getLeftBranchWithout(), expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getLeftBranchWithout().getLeftBranchWithout()) &&
                    Expression.compareExpressions(expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getLeftBranchWithout(), expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getLeftBranchWithout().getRightBranchWithout()) &&
                    Expression.compareExpressions(expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getRightBranchWithout(), expression.getLeftBranch().getLeftBranchWithout().getRightBranchWithout()) &&
                    Expression.compareExpressions(expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getRightBranchWithout(), expression.getRightBranchWithout().getRightBranch().getLeftBranchWithout().getRightBranchWithout())
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean nine(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getLeftBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&
                    expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getOperation() == Expression.Operation.IMPL &&

                    expression.getLeftBranch().getOperation() == Expression.Operation.SKOBKA &&
                    expression.getRightBranchWithout().getLeftBranch().getOperation() == Expression.Operation.SKOBKA &&

                    Expression.compareWithoutTrue(expression.getRightBranchWithout().getRightBranchWithout(), expression.getLeftBranch().getLeftBranchWithout().getLeftBranchWithout()) &&
                    Expression.compareWithoutTrue(expression.getRightBranchWithout().getRightBranchWithout(), expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getLeftBranchWithout()) &&

                    Expression.compareWithoutTrue(expression.getLeftBranch().getLeftBranchWithout().getRightBranchWithout(), expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getRightBranchWithout()) &&
                    (expression.getRightBranchWithout().getRightBranchWithout().getTrue() - expression.getLeftBranch().getLeftBranchWithout().getLeftBranchWithout().getTrue() == 1) &&
                    (expression.getRightBranchWithout().getRightBranchWithout().getTrue() - expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getLeftBranchWithout().getTrue() == 1) &&
                    (expression.getRightBranchWithout().getLeftBranch().getLeftBranchWithout().getRightBranchWithout().getTrue() - expression.getLeftBranch().getLeftBranchWithout().getRightBranchWithout().getTrue() == 1)  // FIXME POTENTIAL TROUBLE
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static boolean deca(Expression expression) {
        try {
            if (expression.getOperation() == Expression.Operation.IMPL &&
                    Expression.compareExpressions(expression.getLeftBranchWithout().getLeftBranchWithout(), expression.getRightBranchWithout().getLeftBranchWithout()) &&
                    Expression.compareExpressions(expression.getLeftBranchWithout().getRightBranchWithout(), expression.getRightBranchWithout().getRightBranchWithout()) &&
                    Expression.compareWithoutTrue(expression.getLeftBranchWithout(), expression.getRightBranchWithout()) && // FIXME POTENTIAL TROUBLE
                    expression.getLeftBranchWithout().getTrue() - expression.getRightBranchWithout().getTrue() == 2 &&
                    expression.getTrue() == 0
            ) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
}
