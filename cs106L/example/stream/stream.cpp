#include <iostream>
#include <cstring>
#include <sstream>

using namespace std;

int stringToInteger(const string& s);
void stringToIntegerTest();

int main()
{
    stringToIntegerTest();
    return 0;
}

int stringToInteger(const string& s)
{
    int num = 0;
    istringstream iss(s);
    iss >> num;
    if (iss.fail()) throw new std::domain_error("The input is invalid");
    char remain;
    if (iss >> remain) 
        throw new std::domain_error("There is more than one character");
    return num;
}

void stringToIntegerTest()
{
    string num = "0";
    while (cin >> num)
    {
        int res = stringToInteger(num);
        cout << res << endl;
    }
    
}