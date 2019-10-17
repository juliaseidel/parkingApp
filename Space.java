package Parking;

import java.io.Serializable;
import java.util.Random;

/**
 * Space enum with 26 spaces
 * and random space generator
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public enum Space implements Serializable {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

    public static Space getRandomSpace() {
        Random random = new Random();
        return values()[random.nextInt(Space.values().length)];
    }
}
