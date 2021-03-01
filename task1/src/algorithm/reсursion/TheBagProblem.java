package algorithm.reсursion;

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

        ProblemDecision.find(9, things).display();
    }

    static class ProblemDecision {
        private final int limit;
        private final List<Thing> things;
        private int maxValue = 0;
        private final List<List<Thing>> options = new ArrayList<>();

        private ProblemDecision(int limit, List<Thing> things) {
            this.limit = limit;
            this.things = things;
        }

        private void override(int maxValue, List<Thing> option) {
            requireNonNull(option);

            this.maxValue = maxValue;
            options.clear();
            appendOption(option);
        }

        private void appendOption(List<Thing> option) {
            options.add(option.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        }

        public static ProblemDecision find(int limit, List<Thing> things) {
            ProblemDecision decision = new ProblemDecision(limit, things);
            decision.find(0);
            return decision;
        }

        private void find(int startIdx) {
            if (things == null || things.isEmpty()) return;

            if (things.stream().map(Thing::safeGetWeight).reduce(0, Integer::sum) <= limit) {
                Integer sum = things.stream().map(Thing::safeGetPrice).reduce(0, Integer::sum);

                if (sum > maxValue) {
                    override(sum, things);
                } else if (sum == maxValue) {
                    appendOption(things);
                }

                return;
            }

            for (int i = startIdx; i < things.size(); i++) {
                Thing tmp = things.get(i);
                try {
                    things.set(i, null);
                    find(i + 1);
                } finally {
                    things.set(i, tmp);
                }
            }
        }

        public void display() {
            System.out.print("Лучший варианты (цена = ");
            System.out.print(maxValue);
            System.out.println("):");

            for (List<Thing> option: options) {
                System.out.print("Загрузка = ");
                System.out.print(option.stream().map(Thing::safeGetWeight).reduce(0, Integer::sum));
                System.out.print(" :");
                System.out.println(option);
            }
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
