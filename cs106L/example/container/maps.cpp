#include <iostream>
#include <unordered_map>
#include <cstring>
#include <set>
#include <vector>

using std::cout;
using std::endl;
using std::string;
using std::set;
using std::unordered_map;
using std::unordered_multimap;

void iteratorMap();
void multiMap();
void setIt();

int main()
{
//    iteratorMap();
    //multiMap();
    //setIt();
    std::vector<int> vec;
    auto it = vec.begin();
    

    return 0;
}

void iteratorMap()
{
    unordered_map<int, int> map;
    for (int i = 0; i < 10; i++) {
        map.insert({i, 2*i});
    }
    unordered_map<int, int>::iterator it = map.begin();
    for (;it != map.end(); it++) {
        cout << (*it).first << " " << (*it).second << endl;
    }
}

void multiMap()
{
    unordered_multimap<string,int> map;
    map.insert({"cxc", 1});
    map.insert({"cxc", 10});
    for (auto it = map.begin(); it != map.end(); it++)
    {
        cout << (*it).first + " " << (*it).second << "\n";
    }
}

void setIt()
{
    set<int> num_set {1, 79, 3, 50};
    for (auto it : num_set) cout << it << "\n";
    
    // lower_bound, find the element greater or equal than parameter
    set<int>::iterator it = num_set.lower_bound(50);
    set<int>::iterator end = num_set.upper_bound(78);
    cout << "================\n";
    while (it != end) {
        cout << *it << "\n";
        it++;
    }
}