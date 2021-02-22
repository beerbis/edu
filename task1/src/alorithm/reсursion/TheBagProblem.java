package alorithm.reсursion;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class TheBagProblem {
    public static void main(String[] args) {
        List<Thing> things = Arrays.asList(
                new Thing("Книга", 1, 600),
                new Thing("Бинокль", 2, 5000),
                new Thing("Аптечка", 4, 1500),
                new Thing("пол Аптечки", 2, 750),
                new Thing("ещё пол Аптечки", 2, 750),
                new Thing("горбушка", 1, 375),
                new Thing("носок", 1, 375),
                new Thing("Ноутбук", 2, 40000),
                new Thing("Котелок", 1, 375)
        );

        ProblemDecision decision = findMaximum(9, things);

        System.out.print("Лучший варианты (цена = ");
        System.out.print(decision.maxValue);
        System.out.println("):");

        for (List<Thing> option: decision.options) {
            System.out.print("Загрузка = ");
            System.out.print(option.stream().map(Thing::safeGetWeight).reduce(0, Integer::sum));
            System.out.print(" :");
            System.out.println(option);
        }
    }

    private static ProblemDecision findMaximum(int limit, List<Thing> things) {
        ProblemDecision decision = new ProblemDecision();
        findMaximum(limit, things, decision, 0);
        return decision;
    }

    private static void findMaximum(int limit, List<Thing> things, ProblemDecision varActualDecision, int startIdx) {
        if (things == null || things.isEmpty()) return;

        if (things.stream().map(Thing::safeGetWeight).reduce(0, Integer::sum) <= limit) {
            Integer sum = things.stream().map(Thing::safeGetPrice).reduce(0, Integer::sum);

            if (sum > varActualDecision.maxValue) {
                varActualDecision.override(sum, things);
            } else if (sum == varActualDecision.maxValue) {
                varActualDecision.appendOption(things);
            };

            return;
        }

        for (int i = startIdx; i < things.size(); i++) {
            Thing tmp = things.get(i);
            try {
                things.set(i, null);
                findMaximum(limit, things, varActualDecision, i + 1);
            } finally {
                things.set(i, tmp);
            }
        }
    }

    static class ProblemDecision {
        private int maxValue = 0;
        private List<List<Thing>> options = new ArrayList<>();

        public ProblemDecision() {
        }

        public void override(int maxValue, List<Thing> option) {
            requireNonNull(option);

            this.maxValue = maxValue;
            options.clear();
            appendOption(option);
        }

        public void appendOption(List<Thing> option) {
            options.add(option.stream().filter(e -> e != null).collect(Collectors.toList()));
        }
    }

    static class Thing {
        private final String name;
        private final int weight;
        private final int price;

        public Thing(String name, int weight, int price) {
            this.name = name;
            this.weight = weight;
            this.price = price;
        }

        public static int safeGetWeight(Thing thing) {
            return thing == null ? 0 : thing.weight;
        }

        public static int safeGetPrice(Thing thing) {
            return thing == null ? 0 : thing.price;
        }

        @Override
        public String toString() {
            return name + "(" + price + ")";
        }
    }
}
