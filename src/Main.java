import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) { // ((A->B)->C)->D
        String test_string_ax = "A|-A" +
//                "\n(((!A)))"
                "\nA->B->A" +
                "\n((A->A)->(A->(A->A)))" +   ""                        /* первый конец */
//                "\nA->(A&A)" +   ""                        /* первый конец */
//                "\n(A->B)->(A->B->C)->(A->C)" +
//                "\n(A->(D|E))->(A->(D|E)->!C)->(A->!C)" +       /* второй конец */
//                "\nA->B->A&B" +
//                "\n!A->(B&C)->!A&(B&C)" +                       /* третий конец */
//                "\nA&B->A" +
//                "\n(M&N)&B->(M&N)" +                            /* четвертый конец */
//                "\nA&B->B" +
//                "\n!V&(N->A)->(N->A)" +                         /* пятый конец */
//                "\nA->A|B" +
//                "\n(A|B->C)->(A|B->C)|  ! B" +                  /* шестой конец */
//                "\nB->A|B" +
//                "\nB->(A|C->D)|B" +                             /* седьмой конец */
//                "\n(A->C)->(B->C)->(A|B->C)" +
//                "\n((P->Q)->!C)->(B->!C)->((P->Q)|B->!C)" +     /* восьмой конец */
//                "\n(A->B)->(A->!B)->!A" +
//                "\n((P->Q)->B)->((P->Q)->!B)->!(P->Q)" +        /* девятый конец */
//                "\n!!A->A" +
//                "\n!!!(B&!C)->!(B&!C)"                          /* десятый конец*/
                ;

        String test_string = "!A->!!!A|-!!!!A->!!A\n" +
                "!A->!!!A\n" +
                "(!A->!!!A)->(!!!!A->(!A->!!!A))\n" +
                "(!!!!A->(!A->!!!A))\n" +
                "(!!!!A->(!A->!!!!A))\n" +
                "((!!!!A->(!A->!!!!A))->(!!!!A->(!!!!A->(!A->!!!!A))))\n" +
                "(!!!!A->(!!!!A->(!A->!!!!A)))\n" +
                "(!!!!A->(!!!!A->!!!!A))\n" +
                "(!!!!A->((!!!!A->!!!!A)->!!!!A))\n" +
                "((!!!!A->(!!!!A->!!!!A))->((!!!!A->((!!!!A->!!!!A)->!!!!A))->(!!!!A->!!!!A)))\n" +
                "((!!!!A->((!!!!A->!!!!A)->!!!!A))->(!!!!A->!!!!A))\n" +
                "(!!!!A->!!!!A)\n" +
                "((!!!!A->!!!!A)->((!!!!A->(!!!!A->(!A->!!!!A)))->(!!!!A->(!A->!!!!A))))\n" +
                "((!!!!A->(!!!!A->(!A->!!!!A)))->(!!!!A->(!A->!!!!A)))\n" +
                "(!!!!A->(!A->!!!!A))\n" +
                "((!A->!!!A)->((!A->!!!!A)->!!A))\n" +
                "(((!A->!!!A)->((!A->!!!!A)->!!A))->(!!!!A->((!A->!!!A)->((!A->!!!!A)->!!A))))\n" +
                "(!!!!A->((!A->!!!A)->((!A->!!!!A)->!!A)))\n" +
                "((!!!!A->(!A->!!!A))->((!!!!A->((!A->!!!A)->((!A->!!!!A)->!!A)))->(!!!!A->((!A->!!!!A)->!!A))))\n" +
                "((!!!!A->((!A->!!!A)->((!A->!!!!A)->!!A)))->(!!!!A->((!A->!!!!A)->!!A)))\n" +
                "(!!!!A->((!A->!!!!A)->!!A))\n" +
                "((!!!!A->(!A->!!!!A))->((!!!!A->((!A->!!!!A)->!!A))->(!!!!A->!!A)))\n" +
                "((!!!!A->((!A->!!!!A)->!!A))->(!!!!A->!!A))\n" +
                "(!!!!A->!!A)";
        //A->B,!B|-!A
//        A->B
//        !B->A->!B
//        !B
//        A->!B
//        (A->B)->(A->!B)->!A
//        (A->!B)->!A
//        !A

//        System.out.println(test_string + "\n");
        //1 a->b->a                         success
        //2 (a->b)->(a->b->y)->(a->y)       success
        //3 a-b-a&b                         success
        //4 a&b-a                           success
        //5 a&b-b                           success
        //6 a-a|b                           success
        //7 b-a|b                           success
        //8 (a->y)->(b->y)->(a|b->y)        success
        //9 (a->b)->(a->!b)->!а             success
        //10 не не a -> a                   success

        Scanner scanner = new Scanner(System.in);
//        Scanner scanner = new Scanner(test_string);
//        Scanner scanner = new Scanner(test_string_ax);
        ParsingSystem parser = new ParsingSystem();
        String test = scanner.nextLine();

        // разбили на вхождения гипотезы и альфа и правую часть
        String[] hypos = test.split(",");
        String[] alphaAndRightPart = hypos[hypos.length - 1].split("\\|-");
        String alpha = alphaAndRightPart[0];
        String right = alphaAndRightPart[1];
        hypos[hypos.length - 1] = null;
//        System.out.println("hypos =          " + Arrays.toString(hypos));
//        System.out.println("new right part = " + alpha + " -> " + right);
//        System.out.println("");

        // парсер всех гипотез и занесение их в ListOfHypoAndAxi
        for (int i = 0; i < hypos.length; i++) {
            if (hypos[i] == null) {
                // вышли когда дошли до конца
                break;
            }
            parser.run(preparser(hypos[i]));
            FullExpression.getCurrentToRoot();
            ListOfHypoAndAxi.addHypo(FullExpression.current);
            FullExpression.allTree = null;
            FullExpression.current = null;
            ParsingSystem.localIsTrue = 0;
        }
        parser.run(preparser(alpha));
        FullExpression.getCurrentToRoot();
        ListOfHypoAndAxi.alpha = FullExpression.current;
        FullExpression.allTree = null;
        FullExpression.current = null;
        ParsingSystem.localIsTrue = 0;

        parser.run(preparser(right));
        FullExpression.getCurrentToRoot();
        RightPart.setStartExprRight(FullExpression.current);
        FullExpression.allTree = null;
        FullExpression.current = null;
        ParsingSystem.localIsTrue = 0;

        RightPart.setEndExprRight();

//      Вывод всех гипотез, альфы и правых частей, проверка на корректность парсера
//        System.out.println(Arrays.toString(ListOfHypoAndAxi.HYPO.toArray()));
//        System.out.println(ListOfHypoAndAxi.alpha);
//        System.out.println(RightPart.startExpressionRightPart);
//        System.out.println(RightPart.endExpressionRightPart);
//        System.out.println("");


        // пишем ввод всех строчек доказательств(корректных) приходящих на входе
        while (scanner.hasNextLine()) {
            String pointOfDokvo = scanner.nextLine();

            parser.run(preparser(pointOfDokvo));
            FullExpression.getCurrentToRoot();
            ListOfDokvoCorrect.listOfPoints.add(FullExpression.current); // Добавили точку доказательства
            //System.out.println(FullExpression.current);
            // Проверяем на то, что это MP и если это так, то в данное дерево записываем индексы точек доказательства,
            // используемых в данном ModusPonens
            ListOfDokvoCorrect.checkForModusPonuns(ListOfDokvoCorrect.listOfPoints.get(ListOfDokvoCorrect.listOfPoints.size() - 1));
            FullExpression.allTree = null;
            FullExpression.current = null;
            ParsingSystem.localIsTrue = 0;
        }

        // вывод всех точек доказательства
//        System.out.println("\n ВЫВОД ТОЧЕК ДОКАЗАТЕЛЬСТВА ДО ПЕРЕНОСА АЛЬФЫ");
//        System.out.println(Arrays.toString(ListOfDokvoCorrect.listOfPoints.toArray()));

        // создаем и выводим все необходимые точки доказательства ПОСЛЕ переноса альфы в другую часть
//        System.out.println("\n ВЫВОД ТОЧЕК ДОКАЗАТЕЛЬСТВА ПОСЛЕ ПЕРЕНОСА АЛЬФЫ");
        ListOfNewDokvo.init();
//        System.out.println(Arrays.toString(ListOfNewDokvo.listOfPoints.toArray()));

        // переделываем доказательство под перенесенную альфу в другую часть
//        System.out.println("\n ВЫВОД ВСЕГО НОВОГО ДОКАЗАТЕЛЬСТВА");
        ListOfNewDokvo.startDokvo();

        // выводим все новое доказательство
//        System.out.println("\n ОКОНЧАТЕЛЬНЫЙ ВЫВОД ВСЕХ НУЖНЫХ СТРОК");
        StringBuilder str = new StringBuilder();
        for (Expression e : ListOfHypoAndAxi.HYPO) {
            String result = e.toString();
            if (result.length() > 2) {
                int size = result.length();
                str.append(result, 1, size - 1).append(", ");
            } else {
                str.append(result).append(", ");
            }
        }
        if (str.length() > 0) {
            str.setLength(str.length() - 2);
        }

        String rightPart = RightPart.endExpressionRightPart.toString();
        int sizeRight = rightPart.length();
        System.out.println(str.toString() + "|-" + (sizeRight > 2
                                                        ? rightPart.substring(1, sizeRight - 1)
                                                        : rightPart));
        for (Expression e : ListOfNewDokvo.listOfDokvosAll) {
            String result = e.toString();
            if (result.length() > 2) {
                System.out.println(result.substring(1, result.length() - 1));
            } else {
                System.out.println(e);
            }
        }
    }

    // NOTE: оставить
    public static List<String> preparser(String input) {
        return zipMetavariables(parserSpaces(input));
    }

    // NOTE: оставить
    public static List<String> parserSpaces(String input) { //((PPP->PPP')->PPP)->PPP
//        input = input.replace("\r", "");
//        input = input.replace("\n", "");
//        input = input.replace(" ", "");
//        input = input.replace(">", "");
        String[] inp = input.split("");

//        return Arrays.asList(inp);
        List<String> data = Arrays.asList(inp);
        return data.stream()
                .filter(i -> !Objects.equals(i, " ") && !Objects.equals(i, ">") && !Objects.equals(i, "\r") && !Objects.equals(i, "\n"))
                .collect(Collectors.toList());
    }

    // NOTE: оставить
    public static List<String> zipMetavariables(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            if (isVariable(data.get(i)) && i != data.size() - 1 && isVariable(data.get(i + 1))) {
                data.set(i, data.get(i) + data.get(i + 1));
                data.remove(i + 1);
                i--;
                continue;
            }
            if (data.get(i).equals("-")) {
                data.set(i, "->");
            }
        }
        return data;
    }

    // NOTE: оставить
    public static Boolean isVariable(String theString) {
        return theString.matches("[A-Z]?([0-9A-Z'])");
    }
}
//

//A->B,!B|-!A
//        A->B
//        !B->A->!B
//        !B
//        A->!B
//        (A->B)->(A->!B)->!A
//        (A->!B)->!A
//        !A