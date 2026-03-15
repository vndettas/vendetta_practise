#include <iostream>
#include <cmath>
#include <algorithm>
#include <filesystem>
#include <fstream>
#include <string>
#include <vector>
#include <map>


struct Piece{
public:

  std::string type;

  std::vector<double> vec;

private:

};


struct Distance{
public:

  std::string type;

  double distance;

  bool operator<(const Distance& other) const {
        return distance < other.distance;
  }

};


std::string
get_type(std::vector<Distance>& distances, uint32_t k) {
    std::sort(distances.begin(), distances.end());

    std::map<std::string, int> votes;
    for (int i = 0; i < k && i < distances.size(); ++i) {
        votes[distances[i].type]++;
    }

    std::string type;
    uint32_t max = 0;
    for (const auto& pair : votes) {
        if (pair.second > max) {
            max = pair.second;
            type = pair.first;
        }
    }

    return type;
}


std::vector<Piece> 
parse_set(const std::string& file_content) {
    std::vector<Piece> training_set;
    std::stringstream stream(file_content);
    std::string line;

    while (stream >> line) {
        Piece p;
        std::stringstream line_stream(line);
        std::string part;
        std::vector<std::string> parts;

        while (std::getline(line_stream, part, ',')) {
            parts.push_back(part);
        }

        p.type = parts.back();
        for (size_t i = 0; i < parts.size() - 1; ++i) {
            p.vec.push_back(std::stod(parts[i]));
        }

        training_set.push_back(p);
    }
    return training_set;
}


double
calculate_distance(Piece p1, Piece p2)
{

  double distance = 0;

for (size_t i = 0; i < p1.vec.size(); ++i) {
        double diff = p1.vec[i] - p2.vec[i];
        distance += diff * diff;
    }

  return std::sqrt(distance);

}


std::string
read_file(std::filesystem::path path)
{

  std::ifstream file(path);

  if(!file){
    throw std::runtime_error("failed to open " + path.string());
  }

  return std::string(std::istreambuf_iterator<char>(file), std::istreambuf_iterator<char>());


}

int
main (int argc, char *argv[]) {
  
  std::string training_set_path;
  std::string test_set_path;
  std::cout << "Paste training set path: " << '\n';
  std::cin >> training_set_path;
  std::cout << "Paste test set path: " << '\n';
  std::cin >> test_set_path;
  std::vector<Piece> training_set = parse_set(read_file(training_set_path));
  std::vector<Piece> test_set = parse_set(read_file(test_set_path));
  std::cout << "Choose k: " << '\n';
  uint32_t k = 0;
  std::cin >> k;
  std::cout << '\n';
  uint32_t correct_types = 0;
  uint32_t all_types = test_set.size();

  for(const Piece& test_piece : test_set){
    std::vector<Distance> distances;

    for(const Piece& training_piece : training_set){
      Distance distance;
      distance.type = training_piece.type;
      distance.distance = calculate_distance(test_piece, training_piece);
      distances.push_back(distance);

    }
    std::string type = get_type(distances, k);
    if(type == test_piece.type) correct_types++;
    

  }

  uint32_t accuracy = (double) correct_types / all_types * 100;
  std::cout << accuracy;
  return 0;
}
