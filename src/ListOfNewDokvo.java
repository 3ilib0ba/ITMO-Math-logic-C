import java.util.ArrayList;

public class ListOfNewDokvo {
    // Лист с теми же точками доказательства, но с альфа
    public static ArrayList<Expression> listOfPoints = new ArrayList<>();
    // счетчик для пробеганий по листам точек вывода
    public static int i = 0;

    // Итоговый результат, вывод всех точек доказательства листа выше
    public static ArrayList<Expression> listOfDokvosAll = new ArrayList<>();

    public static void init() {
        // получаем точки доказательства, которые ДОЛЖНЫ быть в новом доказательтве
        for (Expression point : ListOfDokvoCorrect.listOfPoints) {
            listOfPoints.add(Expression.zipping(ListOfHypoAndAxi.alpha, point));
        }
    }

    public static void startDokvo() {
        int size = listOfPoints.size();
        Expression alpha = ListOfHypoAndAxi.alpha;
        Expression currentDo;
        Expression currentPosle;
        Expression modusLocalJ;
        Expression modusLocalK;

        for (i = 0; i < size; i++) {
            currentDo = ListOfDokvoCorrect.listOfPoints.get(i);
            currentPosle = ListOfNewDokvo.listOfPoints.get(i);

            if (ListOfHypoAndAxi.publicCheckAllAxis(currentDo) || ListOfHypoAndAxi.isHypo(currentDo)) {
                // первый случай в доказательстве
                // то есть пришедшая точка доказательства есть гипотеза или аксиома
                //System.out.println("\nПЕРВЫЙ СЛУЧАЙ");

                Expression tree = Expression.zipping(currentDo, Expression.zipping(alpha, currentDo));
                listOfDokvosAll.add(tree);
//                System.out.println("Добавляю к выводу строку " + tree);

                listOfDokvosAll.add(currentDo);
//                System.out.println("Добавляю к выводу строку " + currentDo);

                // currentPosle является случаем применения правила MP к двум предыдущим строчкам
                listOfDokvosAll.add(currentPosle);
                currentDo.sootvet = listOfDokvosAll.size() - 1;
//                System.out.println("Добавляю к выводу строку " + currentPosle);

            } else if (Expression.compareExpressions(currentDo, ListOfHypoAndAxi.alpha)) {
                // второй случай в доказательстве
                // то есть пришедшая точка доказательства есть гипотеза альфа
                //System.out.println("\nВТОРОЙ СЛУЧАЙ");

                Expression treeOne = Expression.zipping(alpha, Expression.zipping(alpha, alpha));
                listOfDokvosAll.add(treeOne);
//                System.out.println("Добавляю к выводу строку " + treeOne);

                Expression treeDuo = Expression.zipping(
                        treeOne,
                        Expression.zipping(
                                Expression.zipping(
                                        alpha,
                                        Expression.zipping(
                                                Expression.zipping(alpha, alpha),
                                                alpha
                                        )
                                ),
                                Expression.zipping(alpha, alpha)
                        )
                );
                listOfDokvosAll.add(treeDuo);
//                System.out.println("Добавляю к выводу строку " + treeDuo);

                // является MP из прошлых двух строк
                Expression treeThird = Expression.zipping(
                        Expression.zipping(
                                alpha,
                                Expression.zipping(
                                        Expression.zipping(alpha, alpha),
                                        alpha
                                )
                        ),
                        Expression.zipping(alpha, alpha)
                );
                listOfDokvosAll.add(treeThird);
//                System.out.println("Добавляю к выводу строку " + treeThird);

                Expression treeFour = Expression.zipping(
                        alpha,
                        Expression.zipping(
                                Expression.zipping(alpha, alpha),
                                alpha
                        )
                );
                listOfDokvosAll.add(treeFour);
//                System.out.println("Добавляю к выводу строку " + treeFour);

                // является MP из прошлых двух строк
                Expression treeFive = Expression.zipping(alpha, alpha);
                listOfDokvosAll.add(treeFive);
                currentDo.sootvet = listOfDokvosAll.size() - 1;
//                System.out.println("Добавляю к выводу строку " + treeFive);

            } else {
                //System.out.println("\nТРЕТИЙ СЛУЧАЙ");
                // третий случай в доказательстве
                // то есть пришедшая точка доказательства была получена как MP своих предыдущих
                if (!currentDo.checkModusPonens()) {
                    System.out.println(currentDo);
//                    System.out.println("Правило было как бы модус поненс, но почему то оно им " + "не является, а попало в эту ветку");
                    System.exit(10);
                } else {
                    // Получение соответствующих строк в новом доказательстве строк
                    modusLocalJ = ListOfNewDokvo.listOfDokvosAll
                            .get(ListOfDokvoCorrect.listOfPoints.get(currentDo.getModusFirst()).sootvet);
                    modusLocalK = ListOfNewDokvo.listOfDokvosAll
                            .get(ListOfDokvoCorrect.listOfPoints.get(currentDo.getModusSecond()).sootvet);

                    Expression treeOne = Expression.zipping(
                            modusLocalJ,
                            Expression.zipping(
                                    modusLocalK,
                                    currentPosle
                            )
                    );
                    listOfDokvosAll.add(treeOne);
//                    System.out.println("Добавляю к выводу строку " + treeOne);

                    // Следующее дерево является модус поненс от строк modusLocalJ и предыдущей
                    Expression treeSecond = Expression.zipping(
                            modusLocalK,
                            currentPosle
                    );
                    listOfDokvosAll.add(treeSecond);
//                    System.out.println("Добавляю к выводу строку " + treeSecond);

                    // Следующее дерево является модус понос от предыдущей строки и modusLocalK
                    Expression treeThird = currentPosle;
                    listOfDokvosAll.add(treeThird);
                    currentDo.sootvet = listOfDokvosAll.size() - 1;
//                    System.out.println("Добавляю к выводу строку " + treeThird);

                }
            }
        }

    }

}
