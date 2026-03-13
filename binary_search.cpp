#include <iostream>
#include <vector>

template<typename T>
int
my_binary_search(const std::vector<T>& vec, T element)
{

  int right = vec.size() - 1;

  int left = 0;

  int mid = 0;

  while(left < right){

  mid = (right - left)/2;


    if(vec.at(mid) == element) return mid;

    if(vec.at(mid) < element) left = mid;

    if(vec.at(mid) < element) right = mid;

  }

  return -1;

}

int
main()
{

  std::vector<int> vector  = {2, 3, 12, 55, 300, 5, 4, 52, 120};

  auto el =  my_binary_search(vector, 300);

  std::cout << el;
 
  std::cout << '\n';

}
