object = stack.o
st : $(object)
	cc -o st $(object)
$(object) : stack.h
.PHONY : clean
# - means execute the command and ignore errors
clean : 
	- rm stack.o st