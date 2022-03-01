import java.util.Arrays;

public interface MatrixExpression {
    
    public static final MatrixExpression empty = new Identity();    

    public static MatrixExpression make(double value) {
        return new Scalar(value);
    }

    public static MatrixExpression make(double[][] array) {
        return new Matrix(array);
    }

    public static MatrixExpression times(MatrixExpression m1, MatrixExpression m2) {
        return new Product(m1, m2);
    }

    MatrixExpression times(MatrixExpression m);

    MatrixExpression scalars();

    MatrixExpression matrices();

    MatrixExpression optimize();

    boolean isIdentity();
}

class Identity implements MatrixExpression {

    
    

    @Override
    public MatrixExpression scalars() {
        throw new UnsupportedOperationException("The Matrix is empty");
    }

    @Override
    public MatrixExpression matrices() {
        throw new UnsupportedOperationException("The Matrix is empty");
    }

    @Override
    public MatrixExpression optimize() {
        throw new UnsupportedOperationException("The Matrix is empty");
    }

    @Override
    public boolean isIdentity() {
        return true;
    
    }

    @Override
    public MatrixExpression times(MatrixExpression m) {
        throw new UnsupportedOperationException("The Matrix is empty");
    }

}

class Scalar implements MatrixExpression {

    private final double value;

    Scalar(double value) {
        this.value = value;
    }


    @Override
    public MatrixExpression scalars() {
        return this;
    }

    @Override
    public MatrixExpression matrices() {
        return empty;
    }

    @Override
    public MatrixExpression optimize() {
        throw new UnsupportedOperationException("The matrix don't need to optimize");
    }

    @Override
    public boolean isIdentity() {
        return value == 1;
    }

    @Override
    public MatrixExpression times(MatrixExpression m) {
        return new Product(this, m);
    }
}

class Matrix implements MatrixExpression {

    private final double[][] array;

    Matrix(double[][] array) {
        this.array = new double[array.length][];
        for (int i = 0; i < array.length; i++) {
            this.array[i] = Arrays.copyOf(array[i], array[i].length);
        }
    }

    @Override
    public MatrixExpression scalars() {
        return empty;
    }

    @Override
    public MatrixExpression matrices() {
        return this;
    }

    @Override
    public MatrixExpression optimize() {
        throw new UnsupportedOperationException("The matrix don't need to optimize");
    }

    @Override
    public boolean isIdentity() {
        if (array.length != array[0].length)
            return false;
        
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                double expected = (i == j) ? 1:0;
                if (array[i][j] != expected)
                    return false;
            }
        }
        return true;
    }

    @Override
    public MatrixExpression times(MatrixExpression m) {
        return new Product(this, m);
    }
}

class Product implements MatrixExpression {

    private final MatrixExpression m1;
    private final MatrixExpression m2;

    Product(MatrixExpression m1, MatrixExpression m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public MatrixExpression times(MatrixExpression m) {
        return new Product(this, m);
    }

    public MatrixExpression times(MatrixExpression m1, MatrixExpression m2) {
        return new Product(m1, m2);
    }

    @Override
    public MatrixExpression scalars() {
        return times(m1.scalars(), m2.scalars());
    }

    @Override
    public MatrixExpression matrices() {
        return times(m1.matrices(), m2.matrices());
    }

    @Override
    public MatrixExpression optimize() {
        return times(scalars(), matrices());
    }

    @Override
    public boolean isIdentity() {
        return m1.isIdentity() && m2.isIdentity();
    }
}