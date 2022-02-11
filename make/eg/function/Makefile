foo := a b c
bar = $(findstring a, $(strip $(foo)))
objects := $(foreach n,stack queue,$(n).o)

obj :
	echo $(objects)
	echo $(if (ifeq $(bar),b),yes,no)

test :
ifdef bar
	echo "yes"
else
	echo "no"
endif
