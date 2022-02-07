VPATH = ../stack
#st : stack.o
#	cc -o st stack.o
#stack.o : stack.h
#.PHONY : clean
#clean :
#	rm *.o st
sources = stack.c
st : stack.o
	cc -o st stack.o
include $(sources:.c=.d)

%.d: %.c
	@set -e; rm -f $@; \
	$(CC) -MM $(CPPFLAGS) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$; \

.PHONY : clean
clean :
	rm -f *.d* *.o st