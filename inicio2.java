package controlador2;

import java.util.Scanner;

public class inicio2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leer el número de tipos de moneda
        int numTipos = scanner.nextInt();
        int[] valores = new int[numTipos];
        int[] cantidades = new int[numTipos];

        // Leer los valores de las monedas
        for (int i = 0; i < numTipos; i++) {
            valores[i] = scanner.nextInt();
        }

        // Leer las cantidades de cada tipo de moneda
        for (int i = 0; i < numTipos; i++) {
            cantidades[i] = scanner.nextInt();
        }

        // Leer el valor del producto
        int valorProducto = scanner.nextInt();

        // Llamar a la función para encontrar la combinación mínima
        int[] resultado = encontrarCambioMinimo(valores, cantidades, valorProducto);

        // Imprimir el resultado
        if (resultado == null) {
            System.out.println("NO");
        } else {
            System.out.print("SI ");
            for (int i = 0; i < resultado.length; i++) {
                System.out.print(resultado[i] + " ");
            }
        }
    }

    public static int[] encontrarCambioMinimo(int[] valores, int[] cantidades, int valorProducto) {
        int n = valores.length;
        int[] dp = new int[valorProducto + 1];
        int[][] monedasUsadas = new int[valorProducto + 1][n];

        // Inicializar dp con un valor alto
        for (int i = 1; i <= valorProducto; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        // Algoritmo de programación dinámica
        dp[0] = 0; // Se necesitan 0 monedas para alcanzar el valor 0
        for (int i = 0; i < n; i++) {
            for (int j = valorProducto; j >= 0; j--) {
                if (dp[j] != Integer.MAX_VALUE) {
                    for (int k = 1; k <= cantidades[i] && j + k * valores[i] <= valorProducto; k++) {
                        if (dp[j] + k < dp[j + k * valores[i]]) {
                            dp[j + k * valores[i]] = dp[j] + k;
                            System.arraycopy(monedasUsadas[j], 0, monedasUsadas[j + k * valores[i]], 0, n);
                            monedasUsadas[j + k * valores[i]][i] += k;
                        } else if (dp[j] + k == dp[j + k * valores[i]]) {
                            int[] temp = new int[n];
                            System.arraycopy(monedasUsadas[j], 0, temp, 0, n);
                            temp[i] += k;
                            if (compararSoluciones(temp, monedasUsadas[j + k * valores[i]]) > 0) {
                                dp[j + k * valores[i]] = dp[j] + k;
                                System.arraycopy(temp, 0, monedasUsadas[j + k * valores[i]], 0, n);
                            }
                        }
                    }
                }
            }
        }

        return dp[valorProducto] == Integer.MAX_VALUE ? null : monedasUsadas[valorProducto];
    }

    public static int compararSoluciones(int[] sol1, int[] sol2) {
        for (int i = 0; i < sol1.length; i++) {
            if (sol1[i] != sol2[i]) {
                return sol1[i] - sol2[i];
            }
        }
        return 0;
    }
}
