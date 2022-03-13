/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.assertArrayEquals;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    // expr = number* op (expr)
    // op ::= [+|*]
    // number ::= [0-9]+
    public static final Expression empty = new Empty();
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        if (input.length() == 0) return empty;
        int i = 0;
        for (i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '*')
                break;
        }
        Expression num, op;
        String rest;
        num = new Number(input.substring(0, i).trim());
        // String op = i != input.length() - 1 ? input.substring(i, i + 1).trim() : empty;
        // String rest = input.substring(i + 1, input.length()).trim();
        if (i == input.length()) {
            op = empty;
            rest = "";
        }
        else {
            op = new Operation(input.substring(i, i + 1).trim());
            rest = input.substring(i + 1, input.length()).trim();
        }
        return new Expr(num, op, parse(rest));
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    public boolean isEmpty();    
}

class Empty implements Expression {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
}

class Number implements Expression {

    private final String number;

    private static final String[] valid = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public Number(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Number other = (Number) obj;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        return result;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return number;
    }

    private void checkRep() {
        for (int i = 0; i < number.length(); ++i) {
            
        } 
    }
}

class Operation implements Expression {

    private final String operation;

    public Operation(String operation) {
        this.operation = operation;
        checkRep();
    }
    
    private void checkRep() {
        assert operation != null && (operation.equals("+") || operation.equals("*"));
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((operation == null) ? 0 : operation.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Operation other = (Operation) obj;
        if (operation == null) {
            if (other.operation != null)
                return false;
        } else if (!operation.equals(other.operation))
            return false;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return operation;
    }
    
}

class Expr implements Expression {

    private final Expression first;
    private final Expression op;
    private final Expression second;

    public Expr(Expression first, Expression op, Expression second) {
        this.first = first;
        this.op = op;
        this.second = second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((op == null) ? 0 : op.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) 
            return false;
        if (obj == this)
            return true;
        if (this.getClass() != obj.getClass())
            return false;
        Expr expr = (Expr) obj;
        
        if (!op.equals(expr.op))
            return false;
        return (first.equals(expr.first) && second.equals(expr.second)) || 
            (first.equals(expr.second) && second.equals(expr.first));
    }

    @Override
    public String toString() {
        return first.toString() + op.toString() + second.toString();
    }

    @Override
    public boolean isEmpty() {
        return first.isEmpty() || op.isEmpty() || second.isEmpty();
    }    
}