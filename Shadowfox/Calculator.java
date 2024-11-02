import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;
import java.util.ArrayList;

public class Calculator {
    private static ArrayList<String> history = new ArrayList<>();
    private static final String LOG_FILE = "calculator_errors.log";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculating = true;

        while (continueCalculating) {
            try {
                System.out.println("\n===== Calculator =====");
                System.out.println("1. Basic Arithmetic");
                System.out.println("2. Scientific Calculations");
                System.out.println("3. Unit Conversions");
                System.out.println("4. Statistics");
                System.out.println("5. Matrix Calculations");
                System.out.println("6. Polynomial Solver");
                System.out.println("7. Exit");
                System.out.print("Choose an option (1-7): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        performBasicArithmetic(scanner);
                        break;
                    case 2:
                        performScientificCalculations(scanner);
                        break;
                    case 3:
                        performUnitConversions(scanner);
                        break;
                    case 4:
                        performStatistics(scanner);
                        break;
                    case 5:
                        performMatrixCalculations(scanner);
                        break;
                    case 6:
                        performPolynomialSolver(scanner);
                        break;
                    case 7:
                        continueCalculating = false;
                        System.out.println("Exiting the calculator. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1, 2, 3, 4, 5, 6, or 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        scanner.close();
    }

    // Basic Arithmetic Operations
    public static void performBasicArithmetic(Scanner scanner) {
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        System.out.println("Choose operation: +, -, *, /");
        char operation = scanner.next().charAt(0);

        double result = 0;
        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    logError("Division by zero attempted.");
                    System.out.println("Error: Division by zero is not allowed.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid operation.");
                return;
        }
        System.out.println("Result: " + result);
        history.add("Basic Arithmetic: " + num1 + " " + operation + " " + num2 + " = " + result);
    }

    // Scientific Calculations
    public static void performScientificCalculations(Scanner scanner) {
        System.out.println("1. Square Root");
        System.out.println("2. Exponentiation");
        System.out.println("3. Trigonometric Functions");
        System.out.print("Choose an option (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter a number: ");
                double num = scanner.nextDouble();
                if (num >= 0) {
                    double sqrtResult = Math.sqrt(num);
                    System.out.println("Square root: " + sqrtResult);
                    history.add("Square root of " + num + " = " + sqrtResult);
                } else {
                    logError("Square root of negative number attempted.");
                    System.out.println("Error: Square root of negative numbers is not allowed.");
                }
                break;
            case 2:
                System.out.print("Enter the base number: ");
                double base = scanner.nextDouble();
                System.out.print("Enter the exponent: ");
                double exponent = scanner.nextDouble();
                double powerResult = Math.pow(base, exponent);
                System.out.println("Result: " + powerResult);
                history.add("Exponentiation: " + base + " ^ " + exponent + " = " + powerResult);
                break;
            case 3:
                System.out.println("Choose function: sin, cos, tan");
                String trigFunc = scanner.next().toLowerCase();
                System.out.print("Enter angle in degrees: ");
                double angle = scanner.nextDouble();
                double radians = Math.toRadians(angle);
                switch (trigFunc) {
                    case "sin":
                        System.out.println("sin(" + angle + ") = " + Math.sin(radians));
                        break;
                    case "cos":
                        System.out.println("cos(" + angle + ") = " + Math.cos(radians));
                        break;
                    case "tan":
                        System.out.println("tan(" + angle + ") = " + Math.tan(radians));
                        break;
                    default:
                        System.out.println("Invalid function.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Unit Conversions
    public static void performUnitConversions(Scanner scanner) {
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Meters to Feet");
        System.out.println("3. Kilograms to Pounds");
        System.out.println("4. Liters to Gallons");
        System.out.print("Choose an option (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter temperature in Celsius: ");
                double celsius = scanner.nextDouble();
                double fahrenheit = (celsius * 9 / 5) + 32;
                System.out.println("Temperature in Fahrenheit: " + fahrenheit);
                break;
            case 2:
                System.out.print("Enter length in meters: ");
                double meters = scanner.nextDouble();
                double feet = meters * 3.28084;
                System.out.println("Length in feet: " + feet);
                break;
            case 3:
                System.out.print("Enter weight in kilograms: ");
                double kilograms = scanner.nextDouble();
                double pounds = kilograms * 2.20462;
                System.out.println("Weight in pounds: " + pounds);
                break;
            case 4:
                System.out.print("Enter volume in liters: ");
                double liters = scanner.nextDouble();
                double gallons = liters * 0.264172;
                System.out.println("Volume in gallons: " + gallons);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Statistics
    public static void performStatistics(Scanner scanner) {
        System.out.print("Enter the number of elements: ");
        int n = scanner.nextInt();
        double[] numbers = new double[n];
        System.out.println("Enter " + n + " numbers:");

        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextDouble();
        }

        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        double mean = sum / n;

        double median = calculateMedian(numbers);
        double mode = calculateMode(numbers);

        System.out.println("Mean: " + mean);
        System.out.println("Median: " + median);
        System.out.println("Mode: " + mode);
    }

    private static double calculateMedian(double[] numbers) {
        java.util.Arrays.sort(numbers);
        int middle = numbers.length / 2;
        if (numbers.length % 2 == 0) {
            return (numbers[middle - 1] + numbers[middle]) / 2.0;
        } else {
            return numbers[middle];
        }
    }

    private static double calculateMode(double[] numbers) {
        java.util.HashMap<Double, Integer> countMap = new java.util.HashMap<>();
        for (double number : numbers) {
            countMap.put(number, countMap.getOrDefault(number, 0) + 1);
        }
        double mode = numbers[0];
        int maxCount = 0;
        for (java.util.Map.Entry<Double, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode;
    }

    // Matrix Calculations
    public static void performMatrixCalculations(Scanner scanner) {
        System.out.print("Enter number of rows for matrices: ");
        int rows = scanner.nextInt();
        System.out.print("Enter number of columns for matrices: ");
        int cols = scanner.nextInt();

        double[][] matrix1 = new double[rows][cols];
        double[][] matrix2 = new double[rows][cols];

        System.out.println("Enter elements for Matrix 1:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Element [" + (i + 1) + "][" + (j + 1) + "]: ");
                matrix1[i][j] = scanner.nextDouble();
            }
        }

        System.out.println("Enter elements for Matrix 2:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Element [" + (i + 1) + "][" + (j + 1) + "]: ");
                matrix2[i][j] = scanner.nextDouble();
            }
        }

        double[][] resultMatrix = new double[rows][cols];
        System.out.println("Choose operation: 1. Addition 2. Subtraction");
        int choice = scanner.nextInt();

        if (choice == 1) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }
            System.out.println("Result of Addition:");
        } else if (choice == 2) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    resultMatrix[i][j] = matrix1[i][j] - matrix2[i][j];
                }
            }
            System.out.println("Result of Subtraction:");
        } else {
            System.out.println("Invalid operation.");
            return;
        }

        printMatrix(resultMatrix);
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    // Polynomial Solver
    public static void performPolynomialSolver(Scanner scanner) {
        System.out.print("Enter the degree of the polynomial: ");
        int degree = scanner.nextInt();
        double[] coefficients = new double[degree + 1];
        System.out.println("Enter coefficients from highest degree to constant term:");

        for (int i = 0; i <= degree; i++) {
            coefficients[i] = scanner.nextDouble();
        }

        System.out.print("Enter the value of x: ");
        double x = scanner.nextDouble();
        double result = 0;

        for (int i = 0; i <= degree; i++) {
            result += coefficients[i] * Math.pow(x, degree - i);
        }

        System.out.println("Polynomial result: " + result);
    }

    // Error Logging
    private static void logError(String errorMessage) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(errorMessage);
        } catch (IOException e) {
            System.out.println("An error occurred while logging the error: " + e.getMessage());
        }
    }
}
