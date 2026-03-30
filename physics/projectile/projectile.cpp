#include <iostream>
#include <SFML/Graphics.hpp>
#include <cmath>


const double g = 5.81;

const double m = 15.0;

const double k = 0.01;

const double dt = 0.1;

struct Projectile{

public:


  Projectile(double _x, double _y, double _vx, double _vy, sf::Color _color) : x{_x}, y{_y}, vx{_vx}, vy{_vy}, color{_color} {};

  double x;

  double y; 

  double vx; 

  double vy;

  sf::Color color;

  std::vector<sf::CircleShape> path;

void move_Midpoint()
  {

    double V = std::sqrt(vx * vx + vy * vy);

    //Force ressistance
    double ax = (-k * V * vx)/ m;
    //Force ressistance and gravity force
    double ay = ((-k * V * vy) - (m * g))/ m;

    double half_vx = vx + (ax * (dt/2.0));
    double half_vy = vy + (ay * (dt/2.0));

    double V_half = std::sqrt(half_vx * half_vx + half_vy * half_vy);

    double half_ax = (-k * V_half * half_vx)/ m;
    double half_ay = ((-k * V_half * half_vy) - (m * g))/ m;

    x =  x + (half_vx * dt);
    y =  y + (half_vy * dt);

    vx = vx + (half_ax * dt);
    vy = vy + (half_ay * dt);


    save_path();
  }

  void move_Euler()
  {

    double V = std::sqrt(vx * vx + vy * vy);

    //Force ressistance
    double ax = (-k * V * vx)/ m;
    //Force ressistance and gravity force
    double ay = ((-k * V * vy) - (m * g))/ m;

    x =  x + (vx * dt);
    y =  y + (vy * dt);

    vx = vx + (ax * dt);
    vy = vy + (ay * dt);

    save_path();

  } 

void
save_path() {
        sf::CircleShape dot(2.0f);
        dot.setPosition(static_cast<float>(x * 1.0), static_cast<float>(750.0 - y * 1.0));
        dot.setFillColor(color);
        path.push_back(dot);
    }
};

int main() {
    sf::RenderWindow window(sf::VideoMode(1920, 1080), "");

    Projectile p1(0, 200, 230, 90, sf::Color::Red);
    Projectile p2(0, 200, 230, 90, sf::Color::Green);

    sf::Time t1 = sf::seconds(0.1);


    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed) window.close();
        }

        if (p1.y >= -0.1) p1.move_Euler();
        if (p2.y >= -0.1) p2.move_Midpoint();

        window.clear(sf::Color(25, 25, 25));

        for (const auto& dot : p1.path) window.draw(dot);
        sf::sleep(t1);
        for (const auto& dot : p2.path) window.draw(dot);

        window.display();
    }

    return 0;
}

