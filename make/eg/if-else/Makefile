vpath % ../stack
bar =
foo = $(bar)
tmp =
ifdef foo
	tmp = "yes"
else
	tmp = "no"
endif
show :	
	echo $(tmp)

objects = stack.o

libs_for_gcc = -lgnu
normal_libs =

foo: $(objects)
ifeq ($(CC), gcc)
	$(CC) -o foo $(objects) $(libs_for_gcc)
else
	$(CC) -o foo $(objects) $(normal_libs)
endif
