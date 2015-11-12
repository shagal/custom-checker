        public class A {
            public static void main(String[] args) {
                new A().run();
            }

            volatile public Integer a = 1;
            public void run() {
                boolean b = a.equals(a);
                String c = "111";
                if ("sss" == c) {
                    return;
                }
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                A a1 = (A) o;

                return !(a != null ? !a.equals(a1.a) : a1.a != null);

            }

            @Override
            public int hashCode() {
                if (a == 0) {
                    return 1;
                }
                return a;
            }
        };