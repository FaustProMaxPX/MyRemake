sources = queue.c
queue : queue.o
	cc -o queue queue.o
	@echo $(Make)
include $(sources:.c=.d)

%.d: %.c
	@set -e; rm -f $@; \
	$(CC) -MM $(CPPFLAGS) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$; \

.PHONY:clean
clean : 
	rm *.d *.o queue