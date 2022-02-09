#include <iostream>
#include <cstring>
#include <sstream>

<<<<<<< HEAD
using std::cin;
using std::cout;
using std::endl;
using std::string;

int stringToInteger(const string& s);
void stringToIntegerTest();
string IntegerToString(int myInt);
int getInteger();
=======
using namespace std;

int stringToInteger(const string& s);
void stringToIntegerTest();
>>>>>>> 58e5b02bcd7dc0f7c244bb4771b160b72a5a9a8f

int main()
{
    stringToIntegerTest();
    return 0;
}

<<<<<<< HEAD
string IntegerToString(int myInt) {
    /*
     * We'll specifically use an ostringstream, which is just a
     * stringstream that you can only put things into.
     */

    /*
     * Putting the int into the ostringstream will convert
     * it into a string format.
     */
    using std::ostringstream;
    ostringstream oss;
    oss << myInt;
    /* Ask for the string inside the ostringstream. */
    return oss.str();
}

int getInteger() {
        /* First we get a line of input from the user. */
        /*
         * We'll  use an istringstream because we only want to pull data
         * out of the stream once we have put the string's contents in it.
         */
        string str;
        cout << "Input an Integer: ";
        std::getline(cin, str);
        std::istringstream iss(str);
        /*
         * Try getting an int from the stream. If this is not succesful
         * then user input was not a valid input.
         */
        int num;
        if (!(iss >> num))
        {
            throw new std::domain_error("The input is invalid");
        }
        char remain;
        if(iss >> remain)
        {
            throw new std::domain_error("There is more than one valid character");
        }
            /*
             * See if we can extract a char from the stream.
             * If so, the user had junk after a valid int in their input.
             */
            
            /*
             * Input was succesfully converted to int with no
             * trailing stuff at the end.
             */
                
    return num;
}

int stringToInteger(const string& s)
{
    using std::istringstream;
    int num = 0;
    istringstream iss(s);
    
=======
int stringToInteger(const string& s)
{
    int num = 0;
    istringstream iss(s);
>>>>>>> 58e5b02bcd7dc0f7c244bb4771b160b72a5a9a8f
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