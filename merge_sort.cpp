#include <iostream>
#include <vector>

template<typename T>
void merge(std::vector<T>, int )
void mergesort(std::vector<T> vec, int left, int right){

  if(vec.size  <= 1) return vec;
  
  int mid = l + (r-l)/2;

  mergesort(vec, left , mid);
  mergesort(vec, mid + 1, right); 

  merge(vec, 0, vec.size());

}


int main(){

}
