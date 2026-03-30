#include <iostream>
#include <filesystem>
#include <cstdint>
#include <fstream>
#include <vector>
#include <bits/stdc++.h>

struct Piece{
public:

  std::vector<double> inputs;

  std::string type;

};

class Perceptron{
public:

  double learning_rate;

  double weights_size;

  double bias = (((double)rand()) / RAND_MAX);

  std::vector<double> weights{};

  void
  generate_weights()
  {
    
    for(int i = 0; i < weights_size; i++){
    weights.push_back(0.0);
    weights[i] = (((double)rand()) / RAND_MAX);
    } 


  }

  std::string
  classify(const std::vector<double>& inputs)
  {

    double net = 0;

    for (size_t i = 0; i < inputs.size(); i++) {
    net += inputs[i] * weights[i];
    }
    
    return (net >= bias) ? "Iris-virginica" : "Iris-versicolor";

  }

  uint32_t
  train(const Piece& piece, bool update = true) {
    int target = (piece.type == "Iris-versicolor") ? 0 : 1;

    double net = 0;
    for(size_t i = 0; i < piece.inputs.size(); i++) {
    net += piece.inputs[i] * weights[i];
    }

    int prediction = (net >= bias) ? 1 : 0;

    if (prediction == target) {
    return 1;
    } 

    if (update) {
    double error = (double)target - (double)prediction;
    double factor = learning_rate * error;

    for(size_t i = 0; i < weights.size(); i++) {
       weights[i] += factor * piece.inputs[i];
    }
    bias -= factor; 
    }

    return 0;
}
 
};

std::string
read_file(std::filesystem::path path)
{

  std::ifstream file(path);

  if(!file){
    throw std::runtime_error("failed to open " + path.string());
  }

  return std::string(std::istreambuf_iterator<char>(file), std::istreambuf_iterator<char>());


}

std::vector<Piece> 
parse_set(const std::string& file_content) 
{
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
            p.inputs.push_back(std::stod(parts[i]));
        }

        training_set.push_back(p);
    }
    return training_set;


}

int
main(){

  Perceptron perceptron;

  double accuracy = 0;

  std::vector<Piece> train_set = parse_set(read_file("/home/vendetta/Downloads/perceptron.data"));

  uint32_t all = train_set.size();

  uint32_t correct = 0;

  std::vector<Piece> test_set = parse_set(read_file("/home/vendetta/Downloads/perceptron.test.data"));

  perceptron.learning_rate = 0.01;

  perceptron.weights_size = train_set.at(0).inputs.size();

  perceptron.generate_weights();

  int epochs = 0;

  while(accuracy < 99 && epochs < 2000){

    correct = 0;

    for( auto set :  train_set){

      correct += perceptron.train(set);

    }
    accuracy = (correct * 100.0) / all;

    epochs++;
  }
  std::cout << "number of epochs trained: " << epochs << "\n";

    correct = 0;

    for( auto set :  test_set){

      correct += perceptron.train(set);

    }
    accuracy = (correct * 100.0) / test_set.size();

  std::cout << "accuracy: " << accuracy << "\n";
  
  std::cout << "typo go if u want to enter your vector or exit if you want to leave\n";

  std::string user_input;

  std::vector<double> user_vec;

  while(user_input != "exit"){

    std::cin >> user_input;

    user_vec.clear();

    if(user_input == "go"){

      double val;

      for(int i = 0; i < train_set.at(0).inputs.size(); i++){
        
        std::cin >> val;
        user_vec.push_back(val);

      }

    } else {};

    std::string result = perceptron.classify(user_vec);
    std::cout << "result: " << result << "\n";
    
  }
  return 0;
}
