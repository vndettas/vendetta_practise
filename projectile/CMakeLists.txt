cmake_minimum_required(VERSION 4.0)
project(projectile)

set(CMAKE_CXX_STANDARD 17)
# Отключаем лишние модули SFML, чтобы не искать OpenAL и прочее
set(SFML_BUILD_AUDIO FALSE CACHE BOOL "" FORCE)
set(SFML_BUILD_NETWORK FALSE CACHE BOOL "" FORCE)

include(FetchContent)

FetchContent_Declare(
    sfml
    GIT_REPOSITORY https://github.com/SFML/SFML.git
    GIT_TAG 2.6.1 
)

FetchContent_MakeAvailable(sfml)

add_executable(projectile projectile.cpp)

target_link_libraries(projectile sfml-graphics sfml-window)
