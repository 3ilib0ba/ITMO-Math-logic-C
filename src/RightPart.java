public class RightPart {
    public static Expression startExpressionRightPart = null;
    public static Expression endExpressionRightPart = null;

    public static void setStartExprRight(Expression tree) {
        startExpressionRightPart = tree;
    }

    public static void setEndExprRight() {
        Expression tree = Expression.zipping(ListOfHypoAndAxi.alpha, startExpressionRightPart);
        endExpressionRightPart = tree;
    }


}
