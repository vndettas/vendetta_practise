cmake_minimum_required(VERSION 4.0)
project(earth_moon_sun)

set(CMAKE_CXX_STANDARD 17)
set(SFML_BUILD_AUDIO FALSE CACHE BOOL "" FORCE)
set(SFML_BUILD_NETWORK FALSE CACHE BOOL "" FORCE)

include(FetchContent)

FetchContent_Declare(
    sfml
    GIT_REPOSITORY https://github.com/SFML/SFML.git
    GIT_TAG 2.6.1 
)

FetchContent_MakeAvailable(sfml)

add_executable(earth_moon_sun earth_moon_sun.cpp)

target_link_libraries(earth_moon_sun sfml-graphics sfml-window)
