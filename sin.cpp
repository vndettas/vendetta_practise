#include <iostream>
#include <cmath>

long long 
factorial(int n) {
    long long f = 1;
    for (int i = 1; i <= n; i++) {
        f *= i;
    }
    return f;
}

double 
my_sin(double x, int terms) {
    double result = 0.0;
    
    for (int n = 0; n < terms; n++) {
       int power = 2 * n + 1; 
       
        double term = std::pow(-1, n) * std::pow(x, power) / factorial(power);
        result += term;
    }
    
    return result;
}

int main() {
    double x = 0;
    int terms = 7; 
    std::cin >> x; 
    std::cout << "sin : " << my_sin(x, terms) << '/n';
    
    return 0;
}
