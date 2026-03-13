#include <iostream>
#include <cstdint>

template<typename T>
class BinarySearchTree{
public:

  T*                    get_Root();

  T*                    search(uint32_t index);

private:

  T* root;

};

template<typename F>
struct Node {
public: 

  Node*                 left;

  Node*                 right;

 
F
search(uint32_t index, F* root)
{

  }

private:

  //T must have comparison operators overloaded
  F                     data;


};


int
main()
{

  return 0;
}
