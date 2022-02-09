foo := abe.c cd.c .c
arr := arr
num := 1
arr1 := $(foo:.c=.d)
arr2 := $(foo:%.c=%.d)
arr2 += niubi
define print
	@echo $(arr1) 
	@echo $(arr2)
	@echo $($(arr)$(num))
endef
print : 
	$(print)	
	echo $(MAKEFLAGS)
	make -f Makefile2
