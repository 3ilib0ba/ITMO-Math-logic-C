public class Expression {
    // static method to zipping two expressions
    // like as zip(A, B) -> return A -> B
    public static Expression zipping(Expression A, Expression B) {
        return new Expression(A, B, null, null, Operation.IMPL, 0);
    }

    // static method to compare two expressions
    // if A == B -> return true...
    public static boolean compareExpressions(Expression A, Expression B) {
        if (A == null && B == null) {
            return true;
        }
        if (A == null || B == null) {
            return false;
        }
//        System.out.println("\n" + "operation: " + A.operation + " // " + B.operation);
        if (A.operation != Operation.VAR && B.operation != Operation.VAR) {
//            System.out.println("A current oper = " + A.operation);
//            System.out.println("B current oper = " + B.operation);
            return A.isTrue == B.isTrue && A.operation == B.operation &&
                    Expression.compareExpressions(A.rightBranch, B.rightBranch) &&
                    Expression.compareExpressions(A.leftBranch, B.leftBranch);
        } else if (A.variable != null && B.variable != null) {
//            System.out.println("variable: " + A.variable + " // " + B.variable);
//            System.out.println("isTrue: " + A.isTrue + " // " + B.isTrue);
            return A.isTrue == B.isTrue && A.variable.equals(B.variable);
        } else {
            return false;
        }
    }

    public static boolean compareWithoutTrue(Expression A, Expression B) {
        if (A == null && B == null) {
            return true;
        }
        if (A == null || B == null) {
            return false;
        }

        int trueA = A.getTrue();
        A.setTrue(B.getTrue());
        boolean result =  compareExpressions(A, B);
        A.setTrue(trueA);
        return result;
    }

    private Expression leftBranch;
    private Expression rightBranch;
    private Expression parent;
    private final String variable;
    private Operation operation;
    private int isTrue;

    // отвечает за индекс, соответствующей в доказательстве(ПОСЛЕ ПЕРЕНОСА альфа) строки
    // создан для строчек из доказательства ДО ПЕРЕНОСА альфа
    public int sootvet = -1;

    private final int[] modusPonensNumbers = {-1, -1};

    public Expression(Expression leftBranch, Expression rightBranch, Expression parent, String variable, Operation operation, int isTrue) {
        this.leftBranch = leftBranch;
        this.rightBranch = rightBranch;
        this.parent = parent;
        this.variable = variable;
        this.operation = operation;
        this.isTrue = isTrue;
    }

    public Expression() {
        this.leftBranch = null;
        this.rightBranch = null;
        this.parent = null;
        this.variable = null;
        this.operation = null;
        this.isTrue = 0;
    }

    // delta1 и delta2 номера строк корректного доказательства правой части
    // из которых выводится данная строка правилом Modus Ponens
    public void setModusPonensLines(int delta1, int delta2) {
        modusPonensNumbers[0] = delta1;
        modusPonensNumbers[1] = delta2;
    }

    // check для того чтобы проверять, была ли эта строка из ЛИСТ_2 выведена как MP
    public boolean checkModusPonens() {
        return modusPonensNumbers[0] != -1 && modusPonensNumbers[1] != -1;
    }

    public int getModusFirst() {
        return modusPonensNumbers[0];
    }

    public int getModusSecond() {
        return modusPonensNumbers[1];
    }

    public void setTrue(int aTrue) {
        this.isTrue = aTrue;
    }

    public enum Operation {
        AND, OR, IMPL, VAR, SKOBKA
    }

    public Operation getOperation() {
        return operation;
    }

    public Expression getLeftBranchWithout() {
        if (leftBranch != null) {
            if (leftBranch.getOperation() == Operation.SKOBKA) {
                return leftBranch.getLeftBranchWithout();
            }
        }
        return leftBranch;
    }

    public Expression getLeftBranch() {
        return leftBranch;
    }

    public Expression getParent() {
        return parent;
    }

    public Expression getRightBranchWithout() {
        if (rightBranch != null) {
            if (rightBranch.getOperation() == Operation.SKOBKA) {
                return rightBranch.getLeftBranchWithout();
            }
        }
        return rightBranch;
    }

    public Expression getRightBranch() { return rightBranch; }

    public int getTrue() {
        return isTrue;
    }

    public String getVariable() {
        return variable;
    }

    public void setParent(Expression parent) {
        this.parent = parent;
    }

    public void setLeftBranch(Expression leftBranch) {
        this.leftBranch = leftBranch;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setRightBranch(Expression rightBranch) {
        this.rightBranch = rightBranch;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("");
        int negationlength = isTrue;
        if (negationlength == 0) {
            switch (operation) {
                case OR:
                    result.append("(").append(leftBranch != null ? leftBranch.toString() : "").append(" | ").append(rightBranch != null ? rightBranch.toString() : "").append(")");
                    break;
                case AND:
                    result.append("(").append(leftBranch != null ? leftBranch.toString() : "").append(" & ").append(rightBranch != null ? rightBranch.toString() : "").append(")");
                    break;
                case IMPL:
                    result.append("(").append(leftBranch != null ? leftBranch.toString() : "").append(" -> ").append(rightBranch != null ? rightBranch.toString() : "").append(")");
                    break;
                case SKOBKA:
                    result.append(leftBranch != null ? leftBranch.toString() : "");
                    break;
                case VAR:
                    result.append(variable);
                    break;
            }
        } else {
            for (int i = 0; i < negationlength; i++) {
                result.append("!");
            }

            switch (operation) {
                case SKOBKA:
                    result.append(leftBranch != null ? leftBranch.toString() : "");
                    break;
                case VAR:
                    result.append(variable);
                    break;
            }

            for (int i = 0; i < negationlength; i++) {
                result.append("");
            }
        }

        return result.toString();
    }
}