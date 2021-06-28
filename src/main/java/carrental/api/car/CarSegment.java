package carrental.api.car;

public enum CarSegment {
    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    S('S'),
    M('M'),
    J('J');


    private final char carSegment;

    CarSegment(char carSegment) {
        this.carSegment = carSegment;
    }
}
