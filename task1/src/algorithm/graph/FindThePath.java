package algorithm.graph;

import algorithm.graph.lesson.Graph;

public class FindThePath {
    private static final String C01Piter = "(01)питер";
    private static final String C02NewLadoga = "(02)новая ладога";
    private static final String C03Tihvin = "(03)тихвин";
    private static final String C04Cherep = "(04)череп";
    private static final String C05Vologda = "(05)вологда";
    private static final String C06Vitigra = "(06)вытегра";
    private static final String C07Pudog = "(07)пудож";
    private static final String C08Kargopol = "(08)каргополь";
    private static final String C09LadeunoePole = "(09)ладейное поле";
    private static final String C10Petrik = "(10)петрик";
    private static final String C11MedvegeGorsk = "(11)медвежьегорск";
    private static final String C12Nyandoma = "(12)няндома";
    private static final String C13Cygma = "(13)сяжма";
    private static final String C21Velsk = "(21)вельск";
    private static final String C14Shenkursk = "(14)шенкурск";
    private static final String C15Bereznik = "(15)березник";
    private static final String C16Holmogory = "(16)холмогоры";
    private static final String C17Akhaggelsk = "(17)архангельск";
    private static final String C18Onega = "(18)онега";
    private static final String C19Mirny = "(19)мирный";
    private static final String C20Brin = "(20)брин";

/* Легенда.                     1 - питер            15 - березник
                     17         2 - новая ладога     16 - холмогоры
                    / \         3 - тихвин           17 - архангельск
                   /   16       4 - череп            18 - онега
                  /     |       5 - вологда          19 - мирный
                  |  .--20      6 - вытегра          20 - брин
                  / /   \       7 - пудож
                18 /    15      8 - каргополь
                 \/     |       9 - ладейное поле
         /- 11   19     14      10 - петрик
       10   \     |     /\      11 - медвежьегорск
       /     \    |    /  21    12 - няндома
      9 - 6 - 7 - 8 - 12 /      13 - сяжма
1 - 2/             5 - 13       21 - вельск
     \3 ---- 4 ---/             14 - шенкурск
 */
    public static void main(String[] args) {
        Graph graph = initMap(new Graph(21));
        //простейшие обходы
        graph.path(C09LadeunoePole, C10Petrik).forEach(System.out::println);
        graph.path(C09LadeunoePole, C06Vitigra).forEach(System.out::println);
        graph.path(C09LadeunoePole, C02NewLadoga).forEach(System.out::println);
        graph.path(C05Vologda, C04Cherep).forEach(System.out::println);;
        //почти равнозначные обходы
        graph.path(C05Vologda, C08Kargopol).forEach(System.out::println);
        graph.path(C04Cherep, C08Kargopol).forEach(System.out::println);
    }

    private static Graph initMap(Graph graph) {
        graph.addVertex(C01Piter, C02NewLadoga, C03Tihvin, C04Cherep, C05Vologda, C06Vitigra
                , C07Pudog, C08Kargopol, C09LadeunoePole, C10Petrik, C11MedvegeGorsk, C12Nyandoma
                , C13Cygma, C21Velsk, C14Shenkursk, C15Bereznik, C16Holmogory, C17Akhaggelsk
                , C18Onega, C19Mirny, C20Brin);
        graph.addEdges(C02NewLadoga, C01Piter, C03Tihvin, C09LadeunoePole);
        graph.addEdges(C04Cherep, C03Tihvin, C05Vologda);
        graph.addEdges(C13Cygma, C05Vologda, C21Velsk);
        graph.addEdges(C14Shenkursk, C21Velsk, C15Bereznik, C12Nyandoma);
        graph.addEdges(C10Petrik, C09LadeunoePole, C11MedvegeGorsk);
        graph.addEdges(C06Vitigra, C09LadeunoePole, C07Pudog);
        graph.addEdges(C11MedvegeGorsk, C07Pudog);
        graph.addEdges(C08Kargopol, C07Pudog, C12Nyandoma, C19Mirny);
        graph.addEdges(C15Bereznik, C14Shenkursk, C20Brin);
        graph.addEdges(C19Mirny, C08Kargopol, C18Onega, C20Brin);
        graph.addEdges(C17Akhaggelsk, C16Holmogory, C18Onega);
        graph.addEdges(C16Holmogory, C20Brin);
        return graph;
    }
}
