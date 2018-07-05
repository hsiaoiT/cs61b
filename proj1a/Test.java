public class Test {
    public static void main(String[] args) {
        //LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        /*
        LinkedListDeque<String> lld2 = new LinkedListDeque<String>("success");
        lld2.addFirst("modric");
        lld2.addFirst("19");
        lld2.addLast("10");
        lld2.addLast("captain");
        lld2.addLast("win");
        lld2.removeFirst(); // :modric success 10 captain win
        lld2.addLast("Danmark");
        lld2.removeLast();
        String getit = lld2.get(3); //:captain
        System.out.println(getit);
        lld2.printDeque();
        */
        ArrayDeque<String> adeque = new ArrayDeque<String>();
        adeque.addLast("Hala");
        adeque.addLast("Madrid");
        adeque.addLast("!");

        adeque.addFirst(":");
        adeque.addFirst("Madrid");
        adeque.addFirst("Real");

        adeque.addLast("We");
        adeque.addLast("are");
        adeque.addLast("the");
        adeque.addLast("Champions");
        adeque.addLast("!");

        for (int i = 0; i < 80; i ++) {
            adeque.addLast("championes");
        }

        adeque.printDeque();

        for (int i = 0; i < 80; i++) {
            adeque.removeLast();
        }

        String hala = adeque.get(3);
    }
}
