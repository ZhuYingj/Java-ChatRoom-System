package environment;

public class Pair<A, B> {
    A key;
    B value;
    public Pair(A firstValue, B secondValue) {
        this.key = firstValue;
        this.value = secondValue;
    }

    public A getKey() {
        return this.key;
    }

    public B getValue() {
        return this.value;
    }
}
