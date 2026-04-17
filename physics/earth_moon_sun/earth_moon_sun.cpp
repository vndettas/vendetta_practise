#include <iostream>
#include <SFML/Graphics.hpp>
#include <cmath>
#include <vector>


const double G = 6.6743e-11;
const double Ms = 1.989e30;
const double Mz = 5.972e24;
const double Mk = 7.347e22;
const double R_ZS = 1.5e11;
const double R_ZK = 3.844e8;

struct Body{
public:

  Body(double _x, double _y, double _vx, double _vy, double _mass): x(_x), y(_y), vx(_vx), vy(_vy), mass(_mass) {}

  double x;

  double y;

  double ax = 0.0;

  double ay = 0.0;

  double vx;

  double vy;

  double mass;

private:

};

void
calculate_accelerations(std::vector<Body>& bodies)
{

  for (int i = 0; i < bodies.size(); ++i) {
    bodies[i].ax = 0.0;
    bodies[i].ay = 0.0;

        for (int j = 0; j < bodies.size(); ++j) {
            if (i == j) continue;

            double dx = bodies[j].x - bodies[i].x;
            double dy = bodies[j].y - bodies[i].y;

            double r = std::sqrt(dx * dx + dy * dy);

            double a = G * bodies[j].mass / (r * r * r);

            bodies[i].ax += a * dx;
            bodies[i].ay += a * dy;
        
    }

  }
}

int main()
{

   double v_earth = std::sqrt(G * Ms / R_ZS);
    double v_moon = std::sqrt(G * Mz / R_ZK);

    Body sun {0.0, 0.0, 0.0, 0.0, Ms};
    Body earth {R_ZS, 0.0, 0.0, v_earth, Mz};
    Body moon {R_ZS + R_ZK, 0.0, 0.0, v_earth + v_moon, Mk};

    std::vector<Body> bodies = {sun, earth, moon};

    double dt = 3600.0; 

    
    sf::RenderWindow window(sf::VideoMode(800, 800), "Solar System Simulation");
    window.setFramerateLimit(60);

    double scale = 300.0 / R_ZS;
    double center_x = 400.0;
    double center_y = 400.0;

    sf::CircleShape sunShape(15.f);
    sunShape.setFillColor(sf::Color::Yellow);
    sunShape.setOrigin(15.f, 15.f);

    sf::CircleShape earthShape(8.f);
    earthShape.setFillColor(sf::Color::Blue);
    earthShape.setOrigin(8.f, 8.f);

    sf::CircleShape moonShape(4.f);
    moonShape.setFillColor(sf::Color::White);
    moonShape.setOrigin(4.f, 4.f);

    
    while (window.isOpen()) {
        
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                window.close();
        }

        for (int step = 0; step < 12; ++step) {
            
            std::vector<Body> original_bodies = bodies;

            calculate_accelerations(bodies);

            for (int i = 0; i < bodies.size(); ++i) {
                bodies[i].x += bodies[i].vx * (dt / 2.0);
                bodies[i].y += bodies[i].vy * (dt / 2.0);
                bodies[i].vx += bodies[i].ax * (dt / 2.0);
                bodies[i].vy += bodies[i].ay * (dt / 2.0);
            }

            calculate_accelerations(bodies);

            for (int i = 0; i < original_bodies.size(); ++i) {
                original_bodies[i].x += bodies[i].vx * dt;
                original_bodies[i].y += bodies[i].vy * dt;
                original_bodies[i].vx += bodies[i].ax * dt;
                original_bodies[i].vy += bodies[i].ay * dt;
            }
            bodies = original_bodies;
        }

        window.clear(sf::Color::Black);

        sunShape.setPosition(center_x + bodies[0].x * scale, center_y + bodies[0].y * scale);
        earthShape.setPosition(center_x + bodies[1].x * scale, center_y + bodies[1].y * scale);
        
        double dx_moon = bodies[2].x - bodies[1].x;
        double dy_moon = bodies[2].y - bodies[1].y;
        moonShape.setPosition(center_x + bodies[1].x * scale + dx_moon * scale * 40.0, 
                              center_y + bodies[1].y * scale + dy_moon * scale * 40.0);

        window.draw(sunShape);
        window.draw(earthShape);
        window.draw(moonShape);

        window.display();
    }

    return 0;
}
