import java.util.ArrayList;

public class ListOfDokvoCorrect {
    public static ArrayList<Expression> listOfPoints = new ArrayList<>();

    public static boolean checkForModusPonuns(Expression checking) {
        if (ListOfHypoAndAxi.isHypo(checking)
                || ListOfHypoAndAxi.publicCheckAllAxis(checking)
                || Expression.compareExpressions(ListOfHypoAndAxi.alpha, checking)) {
            //System.out.println(checking + " является гипотезой, аксиомой или альфа гипотезой");
            return false;
        }
        // если пришли на этот шаг, то эта строка была получена как MP двух предыдущих
        //System.out.println(checking + " является MP");
        int indexAimplB = -1;
        int indexA = -1;
        for (int i = listOfPoints.size(); i > 0; i--) {
            Expression point = listOfPoints.get(i - 1);
            if (point.getOperation() == Expression.Operation.IMPL &&
                    Expression.compareExpressions(point.getRightBranchWithout(), checking)) {
                indexAimplB = listOfPoints.indexOf(point);

                //System.out.println("                             " + point.getLeftBranch());
                for (Expression pointLocal : listOfPoints) {
                    if (point.getLeftBranchWithout().toString().equals(pointLocal.toString())) {
                        indexA = listOfPoints.indexOf(pointLocal);
                        break;
                    }
                    if ((point.getLeftBranch().getOperation() == Expression.Operation.SKOBKA
                            ? Expression.compareExpressions(pointLocal, point.getLeftBranchWithout())
                            : Expression.compareExpressions(pointLocal, point.getLeftBranch()))) {
                        indexA = listOfPoints.indexOf(pointLocal);
                        break;
                    }
                }
                break;
            }
        }
        checking.setModusPonensLines(indexA, indexAimplB);

        return true;
    }
}
